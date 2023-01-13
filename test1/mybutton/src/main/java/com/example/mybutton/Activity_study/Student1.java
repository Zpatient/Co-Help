package com.example.mybutton.Activity_study;

import android.os.Parcel;
import android.os.Parcelable;





//Android平台开发的 必须使用这种方式 开发性能比Serializable高  Serializable是jvm平台开发的 做java开发用
public class Student1 implements Parcelable {

    public int age;
    public String name;

    public Student1(){

    }

    //BActivity 后读取
    protected Student1(Parcel in) {
        age = in.readInt();
        name = in.readString();
    }


    //AActivity先写入
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(age);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    //Creator必须有
    public static final Creator<Student1> CREATOR = new Creator<Student1>() {
        //通过createFromParcel 创建对象返回给Student1
        @Override
        public Student1 createFromParcel(Parcel in) {
            return new Student1(in);
        }

        @Override
        public Student1[] newArray(int size) {
            return new Student1[size];
        }
    };
}
