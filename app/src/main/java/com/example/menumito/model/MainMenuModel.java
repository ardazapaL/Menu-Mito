package com.example.menumito.model;

public class MainMenuModel {

    private String id;
    private String name;
    private String url;
    private double priority;

    public MainMenuModel(String id, String name, String url, double priority){
        // EMPTY CONSTRUCTOR
        this.id = id;
        this.name = name;
        this.url = url;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
