package com.example.mybutton.Fragment.Fragment1;


//通过接口实现了活动和碎片的连接

public interface IFragmentCallBack {
    void sendMsgToActivity(String Msg);
    String getMsgFromActivity(String Msg);

}
