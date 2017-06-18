package com.example.administrator.wechat.WeChat.ChattingUI.HttpUtils;

/**
 * Created by Administrator on 2017-6-12.
 */

import com.example.administrator.wechat.WeChat.ChattingUI.Msg;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;



/**
 * Created by Administrator on 2017-6-8.
 */
public class HttpUtils
{
    private static String API_KEY = "13c5e21fda3b4fc0828b31192a890c8c";
    private static String URL = "http://www.tuling123.com/openapi/api";

    /**
     * 发送一个消息，并得到返回的消息
     * @param msg
     * @return
     */
    public static Msg sendMsg(String msg)
    {
        Msg message = new Msg();
        String url = setParams(msg);
        String res = doGet(url);
        Gson gson = new Gson();
        Result result = gson.fromJson(res, Result.class);

        if (result.getCode() > 400000 || result.getText() == null
                || result.getText().trim().equals(""))
        {
            message.setMsg("该功能等待开发...");
        }else
        {
            message.setMsg(result.getText());
        }
        message.setType(Msg.TYPE_RECEIVED);
        message.setDate(new Date());

        return message;
    }

    /**
     * 拼接Url
     * @param msg
     * @return
     */
    private static String setParams(String msg)//设置参数
    {
        try
        {
            msg = URLEncoder.encode(msg, "UTF-8");//将从服务器上传送回来的内容编译成汉字
        } catch (UnsupportedEncodingException e)//抛出无法编译的数据
        {
            e.printStackTrace();
        }
        return URL + "?key=" + API_KEY + "&info=" + msg;
    }

    /**
     * Get请求，获得返回数据
     * @param urlStr
     * @return
     */
    private static String doGet(String urlStr)
    {
        URL url = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try
        {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();//返回一个新的资源文件去查询这个URL
            conn.setReadTimeout(5 * 1000);//设置读取数据超时时间，5秒
            conn.setConnectTimeout(5 * 1000);//设置连接超时时间，5秒
            conn.setRequestMethod("GET");//下达连接服务器的请求，该方法只能在连接之前启动
            if (conn.getResponseCode() == 200)
            {
                is = conn.getInputStream();//返回读取url连接资源文件的输入流
                baos = new ByteArrayOutputStream();//构建一个Byte类型数组输出流
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1)
                {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            } else
            {
                throw new CommonException("服务器连接错误！");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
            throw new CommonException("服务器连接错误！");
        } finally
        {
            try
            {
                if (is != null)
                    is.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                if (baos != null)
                    baos.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            conn.disconnect();
        }

    }

}
