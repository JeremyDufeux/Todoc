package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class MainViewModel extends ViewModel {

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
        return projectDataSource.getProjects();
    }

    public LiveData<List<Task>> getTasks(long projectId){
        return taskDataSource.getTasks(projectId);
    }

    public void insertTask(String name, long projectId){
        executor.execute(() ->
                taskDataSource.createTask(new Task(
                        projectId,
                        name,
                        new Date().getTime())));
    }

    public void deleteTask(Task task){
        executor.execute(() ->
                taskDataSource.deleteTask(task));
    }


}
