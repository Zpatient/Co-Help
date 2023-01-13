package com.example.mydata;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SQLiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
    }

    public void createDB(View view) {

        SQLiteOpenHelper helper = MySQLiteOpenHelper.getmInstance(this);


        //databases 文件夹的创建需要靠下面这句话  （helper.getReadableDatabase()/helper.getWritableDatabase())--都也可以创建表
        SQLiteDatabase readableDatabase = helper.getWritableDatabase();


    }

    /**
     * 查询
     *
     * @param view
     */

    public void query(View view) {

        SQLiteOpenHelper helper = MySQLiteOpenHelper.getmInstance(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        if (db.isOpen()) {  //确保打开数据库
            //返回一个游标，通过游标去遍历
            Cursor cursor = db.rawQuery("select * from persons", null);


            //迭代游标--往下移动遍历数据
            while (cursor.moveToNext()) {
//                cursor.getInt(0);
                int _id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));

                Log.d("databases","query:_id" + _id + "name:" + name);

            }
            //关闭游标和数据库
            cursor.close();
            db.close();
        }

    }

    public void insert(View view) {
        SQLiteOpenHelper helper = MySQLiteOpenHelper.getmInstance(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        if (db.isOpen()) {

            //插入语句
            String sql = "insert into persons(name) values('zqw')";
            db.execSQL(sql);
            //关闭数据库
            db.close();
        }

    }


    /**
     * 修改第2条为hj
     * @param view
     */
    public void update(View view) {
        SQLiteOpenHelper helper = MySQLiteOpenHelper.getmInstance(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        if (db.isOpen()) {

            String sql = "update persons set name = ? where _id = ?";

            db.execSQL(sql,new Object[]{"hejiang",2});

            db.close();
        }



    }

    /**
     * 删除第三条数据
     * @param view
     */

    public void delete(View view) {

        SQLiteOpenHelper helper = MySQLiteOpenHelper.getmInstance(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        if (db.isOpen()) {
            String sql = "delete from persons where _id=?";
            db.execSQL(sql,new Object[]{3});

            db.close();
        }

    }
}