package com.example.rxjava.login.bean;

public class ResponseResult {

    private SuccessfulBean data;
    private int code;
    private String message;


    public ResponseResult(SuccessfulBean data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public ResponseResult() {

    }

    public SuccessfulBean getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setData(SuccessfulBean data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "data=" + data +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

}
