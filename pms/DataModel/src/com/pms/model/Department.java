package com.pms.model;

public class Department {
    private int id;
    private String name;
    private String manager;
    private String intro;

    public Department() {}

    public Department(int id, String name, String manager, String intro) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.intro = intro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
