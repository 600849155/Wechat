package com.example.administrator.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.wechat.WeChat.Talk;
import com.example.administrator.wechat.WeChat.WeChatAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 实例化ListView
 */
public class WeChatFragment extends Fragment {
    ListView list;
    private List<Talk> talkList = new ArrayList<Talk>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_listview, null);
        list = (ListView) view.findViewById(R.id.list_view);
        initTalks();
        WeChatAdapter adapter = new WeChatAdapter( getActivity() ,R.layout.talk_item,talkList);
        list.setAdapter(adapter);
        list.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Talk talk =talkList.get( position );

                if(talk.getName().equals("悦悦"))
                {
                    Intent intent = new Intent(getActivity(),ChattingActivity.class);

                    startActivity(intent);
                }



            }
        } );
        return view;
    }

    private void initTalks() {
        Talk apple = new Talk("悦悦","我是机器人悦悦，欢迎来撩。",R.drawable.listview_girlfriend);
        talkList.add(apple);
        Talk banana = new Talk("himbb","[文件]" ,R.drawable.listview_me);
        talkList.add(banana);
        Talk orange = new Talk("订阅号","毒蛇电影：让吴亦凡滚出中国电影！", R.drawable.listview_book);
        talkList.add(orange);
        Talk watermelon = new Talk("广科小喵","小喵提醒：你今天的课表", R.drawable.listview_cat);
        talkList.add(watermelon);
        Talk pear = new Talk("微信支付","微信支付凭证", R.drawable.listview_deal);
        talkList.add(pear);
        Talk grape = new Talk("文件传输助手","[文件]", R.drawable.listview_file);
        talkList.add(grape);
        Talk strawberry = new Talk("清远同乡群","黄韵均：谁知道东门韵达快递几点关门", R.drawable.listview_qygroup);
        talkList.add(strawberry);
        Talk A = new Talk("杜一鸣","几时翻清远？", R.drawable.duone7);
        talkList.add(A);
        Talk B = new Talk("肥佬","[链接]", R.drawable.fatman);
        talkList.add(B);

    }


}



