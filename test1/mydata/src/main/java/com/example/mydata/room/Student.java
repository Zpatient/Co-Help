package com.example.mydata.room;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


//一张表（主键唯一且自动增长）
@Entity
public class Student {


    //主键唯一且自动增长
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private int age;

    //id自增可以不用传
    public Student(String name, int age) {

        this.name = name;
        this.age = age;
    }

    public Student() {

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
