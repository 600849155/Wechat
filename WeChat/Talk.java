package com.example.administrator.wechat.WeChat;

/**
 * Created by Administrator on 2017-5-25.
 */
public class Talk {

    private String name;
    private int imageId;
    private String msg;

    public Talk(String name,String msg, int imageId) {
        this.name = name;
        this.msg = msg;
        this.imageId = imageId;
    }

    public  String getMsg(){
        return  msg;
    }
    public String getName() {
        return name;

    }

    public int getImageId() {
        return imageId;

    }

}
	/*
	 * Talk类里面name表示水果的名字，imageId表示水果对应图片的资源id
	 */

