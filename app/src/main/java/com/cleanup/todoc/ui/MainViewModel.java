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
    private static LiveData<List<Project>> projects;
    private static LiveData<List<Task>> tasks;

    // -----------  Repositories  -----------
    private final ProjectDataRepository projectDataSource;
    private final TaskDataRepository taskDataSource;
    private final Executor executor;

    public MainViewModel(ProjectDataRepository projectDataSource, TaskDataRepository taskDataSource, Executor executor) {
        this.projectDataSource = projectDataSource;
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    public LiveData<List<Project>> getProjects(){
        projects = projectDataSource.getProjects();
        return projects;
    }

    public LiveData<List<Task>> getTasks(){
        tasks = taskDataSource.getTasks();
        return tasks;
    }

    public void insertTask(Task task){
        executor.execute(() ->
                taskDataSource.createTask(task));
    }

    public void deleteTask(Task task){
        executor.execute(() ->
                taskDataSource.deleteTask(task));
    }

    @Nullable
    public static Project getProjectById(long projectId) {
        for (Project project : Objects.requireNonNull(projects.getValue())) {
            if (project.getId() == projectId)
                return project;
        }
        return null;
    }


}
