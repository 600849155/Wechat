package com.example.administrator.wechat.WeChat.ChattingUI;

/**
 * Created by Administrator on 2017-6-12.
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017-6-5.
 */
public class Msg {
    public static final int TYPE_RECEIVED=0;
    public static final int TYPE_SENT=1;
    private String name;
    private int type;
    private String msg;
    private Date date;
    private String dateStr;


    public Msg(){

    }

    public Msg(String msg, int type) {
        this.msg = msg;
        this.type = type;
        setDate(new Date());

    }



    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    //获取时间
    public String getDateStr()
    {
        return dateStr;
    }

    public void setDate(Date date)
    {
        this.date = date;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.dateStr = df.format(date);

    }

    public String getMsg()//上下文content
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
}
