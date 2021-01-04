package com.cleanup.todoc.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

/**
 * <p>Models for project in which tasks are included.</p>
 *
 * @author Gaëtan HERFRAY
 */
@Entity
public class Project {
    /**
     * The unique identifier of the project
     */
    @PrimaryKey
    private long id;

    /**
     * The name of the project
     */
    @NonNull
    private String name;

    /**
     * The hex (ARGB) code of the color associated to the project
     */
    @ColorInt
    private int color;

    /**
     * Instantiates a new Project.
     *
     * @param id    the unique identifier of the project to set
     * @param name  the name of the project to set
     * @param color the hex (ARGB) code of the color associated to the project to set
     */
    public Project(long id, @NonNull String name, @ColorInt int color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    /**
     * Returns the unique identifier of the project.
     *
     * @return the unique identifier of the project
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the name of the project.
     *
     * @return the name of the project
     */
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * Returns the hex (ARGB) code of the color associated to the project.
     *
     * @return the hex (ARGB) code of the color associated to the project
     */
    @ColorInt
    public int getColor() {
        return color;
    }

    /**
     * Sets the unique identifier of the project.
     *
     * @param id the unique identifier of the project to set
     */
    public void setId(long id){
        this.id = id;
    }

    /**
     * Sets the name of the project.
     *
     * @param name the unique identifier of the project to set
     */
    public void setName(@NonNull String name) {
        this.name = name;
    }

    /**
     * Sets the color of the project.
     *
     * @param color the unique identifier of the project to set
     */
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    @NonNull
    public String toString() {
        return getName();
    }
}
