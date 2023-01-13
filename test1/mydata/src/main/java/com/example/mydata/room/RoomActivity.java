package com.example.mydata.room;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydata.R;
import com.example.mydata.room.manager.DBEngine;

public class RoomActivity extends AppCompatActivity {


    private DBEngine dbEngine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        dbEngine = new DBEngine(this);
    }



    public void insertAction(View view) {
        Student student = new Student("张三",20);
        Student student1 = new Student("李四",22);
        Student student2 = new Student("王五",24);

        dbEngine.insertStudents(student,student1,student2);
    }


    //修改 修改下标为3的
    public void updateAction(View view) {
        Student student = new Student("陈六",28);
        student.setId(3);
        dbEngine.updateStudents(student);
    }


    //删除下标为2的
    public void deleteAction(View view) {
        Student student = new Student();
        student.setId(2);
        dbEngine.deleteStudents(student);

    }

    //全部删除
    public void deleteAllAction(View view) {
        dbEngine.deleteAllStudents();
    }

    public void queryAction(View view) {
        dbEngine.queryStudents();
    }
}