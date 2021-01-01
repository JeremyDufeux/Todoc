package com.cleanup.todoc.ui;

import android.util.Pair;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskWithProject;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;
import com.cleanup.todoc.utils.CombinedLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class MainViewModel extends ViewModel {
    // -----------  Repositories  -----------
    private final ProjectDataRepository projectDataRepository;
    private final TaskDataRepository taskDataRepository;
    private final Executor executor;

    private final LiveData<List<Project>> projectList;
    private final LiveData<List<Task>> taskList;

    public MainViewModel(ProjectDataRepository projectDataRepository, TaskDataRepository taskDataRepository, Executor executor) {
        this.projectDataRepository = projectDataRepository;
        this.taskDataRepository = taskDataRepository;
        this.executor = executor;

        projectList = projectDataRepository.getProjects();
        taskList = taskDataRepository.getTasks();
    }

    public LiveData<List<TaskWithProject>> getTaskWithProjects() {
        CombinedLiveData<List<Project>, List<Task>> combineData = new CombinedLiveData(projectList, taskList);
        return Transformations.map(combineData, input -> {
            List<Project> projects = input.first;
            List<Task> tasks = input.second;

            if(tasks != null && projects !=null) {

                List<TaskWithProject> output = new ArrayList<>();

                for (Task task : tasks) {
                    for (Project project : projects) {
                        if (task.getProjectId() == project.getId()) {
                            output.add(new TaskWithProject(task, project.getName(), project.getColor()));
                        }
                    }
                }
                return output;
            }
            return null;
        });
    }

    public LiveData<List<Project>> getProjects(){
        return projectList;
    }

    public LiveData<List<Task>> getTasks(){
        return taskList;
    }

    public void insertTask(Task task){
        executor.execute(() ->
                taskDataRepository.createTask(task));
    }

    public void deleteTask(long id){
        executor.execute(() ->
                taskDataRepository.deleteTask(id));
    }
}
