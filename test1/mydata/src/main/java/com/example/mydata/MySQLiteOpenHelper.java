package com.example.mydata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


/**
 * 工具类   采用单例模式(1构造函数私有化 2对外提供函数)  目的是防止多线程的时候同时创建一个实例导致二义性，单例模式只会创建一个实例
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    //2.构造函数
    private static SQLiteOpenHelper mInstance;
    //synchronized-加锁
    public static synchronized SQLiteOpenHelper getmInstance(Context context){
        if (mInstance == null) {
            mInstance = new MySQLiteOpenHelper(context,"hejiang.db",null,1);
        }
        return mInstance;
    }



    //1、通过构造函数拿到数据库的名称和版本---此时构造函数是私有化不能直接调用和实例化，需要利用上面的函数getmInstance获得一个实例化
    private MySQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //创建表  表数据初始化 数据库第一次创建的时候调用此函数，第二次发现有了 就不会重复创建了，也意味着此函数只会调用一次
    //数据库初始化用
    @Override
    public void onCreate(SQLiteDatabase db) {

        /**
         * 主键 -primary key --必须唯一
         * 主键自动增长：例如：1 2 3 4  --关键字autoincrement
         * ：1. _id标准写法  2.  主键只能是Integer类型的
         */

        //创建表：persons表  _id   name
        String sql = "create table persons(_id integer primary key autoincrement, name text)";
        db.execSQL(sql);




    }



    //数据库升级用的
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
