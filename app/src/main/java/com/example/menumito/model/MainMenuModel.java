package com.example.menumito.model;

public class MainMenuModel {

    private int img_food;
    private String name_food;


    public MainMenuModel(int img_food, String name_food) {
        this.img_food = img_food;
        this.name_food = name_food;
    }

    public int getImg_food() {
        return img_food;
    }

    public void setImg_food(int img_food) {
        this.img_food = img_food;
    }

    public String getName_food() {
        return name_food;
    }

    public void setName_food(String name_food) {
        this.name_food = name_food;
    }
}
