package com.example.administrator.wechat.WeChat.ChattingUI.HttpUtils;

/**
 * Created by Administrator on 2017-6-12.
 */




public class Result {
    private int code;
    private String text;

    public Result() {
    }

    public Result(int resultCode, String content) {
        this.code = resultCode;
        this.text = content;
    }

    public Result(int resultCode) {
        this.code = resultCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}


