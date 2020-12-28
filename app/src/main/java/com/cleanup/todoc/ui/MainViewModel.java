package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.Nullable;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

public class MainViewModel extends ViewModel {
    // -----------  Repositories  -----------
    private final ProjectDataRepository projectDataRepository;
    private final TaskDataRepository taskDataRepository;
    private final Executor executor;

    public MainViewModel(ProjectDataRepository projectDataRepository, TaskDataRepository taskDataRepository, Executor executor) {
        this.projectDataRepository = projectDataRepository;
        this.taskDataRepository = taskDataRepository;
        this.executor = executor;
    }

    public LiveData<List<Project>> getProjects(){
        return projectDataRepository.getProjects();
    }

    public LiveData<List<Task>> getTasks(){
        return taskDataRepository.getTasks();
    }

    public void insertTask(Task task){
        executor.execute(() ->
                taskDataRepository.createTask(task));
    }

    public void deleteTask(Task task){
        executor.execute(() ->
                taskDataRepository.deleteTask(task));
    }
}
