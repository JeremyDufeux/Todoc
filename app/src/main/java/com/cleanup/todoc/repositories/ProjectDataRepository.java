package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectDataRepository {
    private final ProjectDao projectDao;
    private static List<Project> projects;

    public ProjectDataRepository(ProjectDao projectDao){
        this.projectDao = projectDao;
    }

    public LiveData<List<Project>> getProjects(){
        projects = projectDao.getProjects().getValue();
        return projectDao.getProjects();
    }

    public static List<Project> getAllProjects(){
        return projects;
    }
}
