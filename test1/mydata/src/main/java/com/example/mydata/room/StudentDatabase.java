package com.example.mydata.room;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//传入表和版本号
@Database(entities = {Student.class},version = 1,exportSchema = false)
public abstract class StudentDatabase extends RoomDatabase {


    //用户只需要操作Dao就可以对数据库进行增删改查了，因此我们需要写一个公共类让用户拿到Dao
    public abstract StudentDao getStudentDao();

    //单例模式 返回 Database
    private static StudentDatabase INSTANCE;
    public static synchronized StudentDatabase getInstance(Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),StudentDatabase.class,"student_database")

                    //数据库的运行默认是采用异步线程
                    //慎用：强制开启主线程也可以使用 但是在开发中不要这么用，测试的时候可以用
//                    .allowMainThreadQueries()
                    .build();

        }
        return INSTANCE;
    }


}
