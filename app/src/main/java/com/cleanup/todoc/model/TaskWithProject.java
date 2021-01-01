package com.cleanup.todoc.model;

public class TaskWithProject extends Task{
    String mProjectName;
    int mProjectColor;

    public TaskWithProject(Task task, String projectName, int projectColor) {
        super(task);
        this.mProjectName = projectName;
        this.mProjectColor = projectColor;
    }

    public String getProjectName() {
        return mProjectName;
    }

    public void setProjectName(String projectName) {
        mProjectName = projectName;
    }

    public int getProjectColor() {
        return mProjectColor;
    }

    public void setProjectColor(int projectColor) {
        mProjectColor = projectColor;
    }
}
