package com.example.mydata.room.manager;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mydata.room.Student;
import com.example.mydata.room.StudentDao;
import com.example.mydata.room.StudentDatabase;

import java.util.List;

//DB的引擎
public class DBEngine {

    private StudentDao studentDao;

    public DBEngine(Context context){
        StudentDatabase studentDatabase = StudentDatabase.getInstance(context);
        studentDao = studentDatabase.getStudentDao();
    }

    //dao 增删改查  拿到dao就能对表进行操作

    //insert
    public void insertStudents(Student ... students){
        new InsertAsyncTask(studentDao).execute(students);
    }


    //update
    public void updateStudents(Student... students){
        new UpdateAsyncTask(studentDao).execute(students);
    }


    //delete--有条件删除
    public void deleteStudents(Student... students){
        new DeleteAsyncTask(studentDao).execute(students);
    }


    //delete--全部删除
    public void deleteAllStudents(){
        new DeleteAllAsyncTask(studentDao).execute();
    }


    //query-查询
    public void queryStudents(){
        new QueryAllAsyncTask(studentDao).execute();
    }

    //插入
    static class InsertAsyncTask extends AsyncTask<Student,Void,Void>{


        private StudentDao dao;


        public InsertAsyncTask(StudentDao studentDao) {
            dao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {

            dao.insertStudents(students);

            return null;
        }
    }


    //更新
    static class UpdateAsyncTask extends AsyncTask<Student,Void,Void>{

        private StudentDao dao;

        public UpdateAsyncTask(StudentDao studentDao) {
            dao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {

            dao.updateStudents(students);
            return null;
        }
    }

    //删除--有条件删除
    static class DeleteAsyncTask extends AsyncTask<Student,Void,Void>{

        private StudentDao dao;

        public DeleteAsyncTask(StudentDao studentDao) {
            dao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            dao.deleteStudents(students);
            return null;
        }
    }


    //全部删除
    static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{

        private StudentDao dao;

        public DeleteAllAsyncTask(StudentDao studentDao) {
            dao = studentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllStudents();
            return null;
        }
    }
    //查询
    static class QueryAllAsyncTask extends AsyncTask<Void,Void,Void>{

        private StudentDao dao;

        public QueryAllAsyncTask(StudentDao studentDao) {
            dao = studentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<Student> student = dao.getAllStudent();
            for (Student student1 : student) {
                Log.d("查询","全部查询每一项：" + student1.toString());
            }
            return null;
        }
    }




}
