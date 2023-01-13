package com.example.rxjava.login.bean;

public class SuccessfulBean {

    private int id;
    private String name;

    public SuccessfulBean(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public SuccessfulBean(){

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

    @Override
    public String toString() {
        return "SuccessfulBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
