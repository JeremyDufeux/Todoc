package com.cleanup.todoc.database;

import androidx.annotation.VisibleForTesting;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import androidx.annotation.NonNull;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class, Project.class}, version = 2)
public abstract class TodocDatabase extends RoomDatabase {

    // -----------  Singleton  -----------
    private static volatile TodocDatabase INSTANCE;

    // -----------  DAO  -----------
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    // -----------  Instance -----------
    public static TodocDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (TodocDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodocDatabase.class, "TodocDatabase.db")
                            .addMigrations(MIGRATION_1_2)
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Init database in memory for testing, to avoid data conflicts
    @VisibleForTesting
    public static void initDatabaseInMemory(Context context){
        INSTANCE = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class)
                .addMigrations(MIGRATION_1_2)
                .addCallback(prepopulateDatabase())
                .build();
    }

    // ----------- Migration -----------
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE INDEX project_id ON Task (projectId)");
        }
    };

    // ----------- Populate -----------
    private static Callback prepopulateDatabase() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1L);
                contentValues.put("name", "Projet Tartampion");
                contentValues.put("color", 0xFFEADAD1);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues);

                contentValues.put("id", 2L);
                contentValues.put("name", "Projet Lucidia");
                contentValues.put("color", 0xFFB4CDBA);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues);

                contentValues.put("id", 3L);
                contentValues.put("name", "Projet Circus");
                contentValues.put("color", 0xFFA3CED2);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}
