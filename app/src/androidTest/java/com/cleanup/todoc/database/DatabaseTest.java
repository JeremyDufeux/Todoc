package com.cleanup.todoc.database;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    private TodocDatabase database;

    private static final Project PROJECT_A = new Project(1, "Project A", 0);
    private static final Project PROJECT_B = new Project(2, "Project B", 0);

    private static final Task TASK_A = new Task(1, "Task A", 0);
    private static final Task TASK_B = new Task(2, "Task B", 0);

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb(){
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void insertAndGetProject() throws InterruptedException {
        database.projectDao().insertProject(PROJECT_A);

        List<Project> projects = LiveDataTestUtil.getValue(database.projectDao().getProjects());

        assertEquals(1, projects.size());
        assertTrue(PROJECT_A.getName().equals(projects.get(0).getName()) && PROJECT_A.getId() == projects.get(0).getId());
    }

    @Test
    public void insertAndGetTasks() throws InterruptedException {
        database.projectDao().insertProject(PROJECT_A);
        database.projectDao().insertProject(PROJECT_B);
        database.taskDao().insertTask(TASK_A);
        database.taskDao().insertTask(TASK_B);

        List<Task> tasks = LiveDataTestUtil.getValue(database.taskDao().getTasks());

        assertEquals(2, tasks.size());
    }

    @Test
    public void insertAndDeleteTasks() throws InterruptedException {
        database.projectDao().insertProject(PROJECT_A);
        database.projectDao().insertProject(PROJECT_B);
        database.taskDao().insertTask(TASK_A);
        database.taskDao().insertTask(TASK_B);

        List<Task> tasks = LiveDataTestUtil.getValue(database.taskDao().getTasks());

        database.taskDao().deleteTask(tasks.get(0));

        assertEquals(1, tasks.size());
    }
}
