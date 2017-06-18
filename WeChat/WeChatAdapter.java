package com.example.administrator.wechat.WeChat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.wechat.R;

import java.util.List;

/**
 * Created by Administrator on 2017-5-16.
 */
public class WeChatAdapter extends ArrayAdapter<Talk> {
    private int resourceId;

    public WeChatAdapter(Context context,
                         int textViewResourceId,List<Talk> objects) {
        // context 上下文，textViewResourceId ListView子项布局的id，List<talk> objects数据
        super(context, textViewResourceId, objects);
        // textViewResourceId 是你的布局资源文件的id值，就是类似R.layout.list_item
        resourceId = R.layout.talk_item;

    }




    // getView这个方法在每个子项滚动到屏幕内的时候会被调用,convertview这个参数将之前加载好的布局进行缓存
    public View getView(int position, View convertView, ViewGroup parent) {
        Talk talk = getItem(position);// 获取当前项的talk实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.talkImage = (ImageView) view/* 有多少个控件就写多少个*/
                    .findViewById(R.id.talk_image);
            viewHolder.talkName = (TextView) view
                    .findViewById(R.id.talk_name);
            viewHolder.talkMsg = (TextView) view
                    .findViewById(R.id.talk_msg);
            view.setTag(viewHolder);// 将ViewHolder存储在view中

            if((talk.getName()=="广科小喵"||talk.getName()=="微信支付")){//设置textview颜色

                viewHolder.talkName.setTextColor(android.graphics.Color.parseColor("#FF525983"));

            }

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();// 重新获取ViewHolder
        }
        viewHolder.talkImage.setImageResource(talk.getImageId());
        viewHolder.talkName.setText(talk.getName());
        viewHolder.talkMsg.setText( talk.getMsg() );


        return view;

    }

    class ViewHolder {                        //内部类ViewHolder 用于对控件的实例进行缓存。
                                               //	当convertView为空的时候，创建一个ViewHolder对象
                                           //	，并将控件的实例都存放在ViewHolder里，然后调用View的
                                             //	setTag()方法，将ViewHolder对象存储在View中。
                                               //	当convertView不为空的时候则调用View的getTag()方法，
                                                     //	把ViewHolder重新取出。这样所有控件的实例都缓存
                                             //	在了ViewHolder里，就没有必要每次都通过findViewById()方法来获取控件实例了。
        ImageView talkImage;
        TextView talkName;
        TextView talkMsg;
    }

}

