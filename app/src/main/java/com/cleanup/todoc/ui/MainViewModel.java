package com.cleanup.todoc.ui;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskWithProject;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;
import com.cleanup.todoc.utils.CombinedLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

public class MainViewModel extends ViewModel {

    // -----------  Repositories  -----------
    private final ProjectDataRepository projectDataRepository;
    private final TaskDataRepository taskDataRepository;
    private final Executor executor;

    private final LiveData<List<Project>> projectList;
    private final LiveData<List<Task>> taskList;

    private final MutableLiveData<SortMethod> sortMethodLiveData;

    public MainViewModel(ProjectDataRepository projectDataRepository, TaskDataRepository taskDataRepository, Executor executor) {
        this.projectDataRepository = projectDataRepository;
        this.taskDataRepository = taskDataRepository;
        this.executor = executor;

        projectList = projectDataRepository.getProjects();
        taskList = taskDataRepository.getTasks();

        sortMethodLiveData = new MutableLiveData<>();
        sortMethodLiveData.setValue(SortMethod.NONE);
    }

    public LiveData<List<TaskWithProject>> getTaskWithProjects() {
        CombinedLiveData<List<Project>, List<Task>> combineTaskAndProject = new CombinedLiveData<>(projectList, taskList);

        LiveData<List<TaskWithProject>> taskWithProjectList = Transformations.map(combineTaskAndProject, input -> {
            List<Project> projects = input.first;
            List<Task> tasks = input.second;

            if(tasks != null && projects !=null) {
                ArrayList<TaskWithProject> output = new ArrayList<>();
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

        CombinedLiveData<List<TaskWithProject>, SortMethod> combineTaskWithProjectAndSort = new CombinedLiveData<>(taskWithProjectList, sortMethodLiveData);

        return Transformations.map(combineTaskWithProjectAndSort, input -> {
            List<TaskWithProject> taskWithProjects = input.first;
            if (taskWithProjects != null) {
                switch (Objects.requireNonNull(sortMethodLiveData.getValue())) {
                    case ALPHABETICAL:
                        Collections.sort(taskWithProjects, new Task.TaskAZComparator());
                        break;
                    case ALPHABETICAL_INVERTED:
                        Collections.sort(taskWithProjects, new Task.TaskZAComparator());
                        break;
                    case RECENT_FIRST:
                        Collections.sort(taskWithProjects, new Task.TaskRecentComparator());
                        break;
                    case OLD_FIRST:
                        Collections.sort(taskWithProjects, new Task.TaskOldComparator());
                        break;
                }
            }
            return taskWithProjects;
        });
    }

    public void setSortMethod(SortMethod sortMethod){
        sortMethodLiveData.setValue(sortMethod);
    }

    public LiveData<List<Project>> getProjects(){
        return projectList;
    }

    public void insertTask(Task task){
        executor.execute(() ->
                taskDataRepository.createTask(task));
    }

    public void deleteTask(long id){
        executor.execute(() ->
                taskDataRepository.deleteTask(id));
    }

    /**
     * List of all possible sort methods for task
     */
    enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * No sort
         */
        NONE
    }
}
