package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;
import androidx.annotation.Nullable;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {
    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public LiveData<List<Task>> getTasks() {
        return taskDao.getTasks();
    }

    public void createTask(Task task) {
        taskDao.insertTask(task);
    }

    public void deleteTask(Task task) {
        taskDao.deleteTask(task);
    }
}
