package com.tuxstone.basefinanciera.models;

/**
 * Created by Ceindetec02 on 07/11/2016.
 */

public class CategoryApp_OLD {
    private int id;
    private String description;


    public CategoryApp_OLD() {
    }

    public CategoryApp_OLD(int id, String description) {
        this.id = id;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
