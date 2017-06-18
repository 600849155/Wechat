package com.example.administrator.wechat.Contact;

/**
 * Created by Administrator on 2017-5-29.
 */
public class User {
    private String name;
    private String letter;
    private int icon ;

    public int getIcon() {
        return icon;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public void setIcon(int icon) {
        this.icon=icon;
    }


}
