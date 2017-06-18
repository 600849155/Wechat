package com.example.administrator.wechat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.wechat.WeChat.ChattingUI.HttpUtils.HttpUtils;
import com.example.administrator.wechat.WeChat.ChattingUI.Msg;
import com.example.administrator.wechat.WeChat.ChattingUI.MsgAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChattingActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<Msg>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;


    private Handler mHandler = new Handler() {


        public void handleMessage(android.os.Message msg) {


            Msg from = (Msg) msg.obj;
            msgList.add(from);
            adapter.notifyItemInserted(msgList.size() - 1);//当有新消息时刷新RecyclerView
            msgRecyclerView.scrollToPosition(msgList.size() - 1);//将RecyV定位到最后一行
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting_activity);
        initMsgs();
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);//设置一个布局管理器
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        setTitle( "悦悦" );

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//显示默认返回键

        }
        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用，如果某个页面想隐藏掉返回键比如首页，可以调用mToolbar.setNavigationIcion(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch(item.getItemId()){
           case android.R.id.home:
               Intent intent = new Intent( getApplication(),MainActivity.class);
               startActivity( intent );
               break;

        }
        return true;


    }

    //        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final String msg = inputText.getText().toString();
//
//                if(!"".equals(msg)) {
//                    final Msg to = new Msg(msg, Msg.TYPE_SENT);
//                   to.setDate(new Date());
//                    msgList.add(to);
//                    adapter.notifyItemInserted(msgList.size() - 1);//当有新消息时刷新RecyclerView
//                    msgRecyclerView.scrollToPosition(msgList.size() - 1);//将RecyV定位到最后一行
//                    inputText.setText("");//清空输入框中的内容
//
//
//
//                    new Thread()
//                    {
//                        public void run()
//                        {
//                           Msg from = null;
//                            try
//                            {
//                                from = HttpUtils.sendMsg(msg);
//                            } catch (Exception e)
//                            {
////                                from = new Msg("无法连接服务器...", Msg.TYPE_RECEIVED);
//
//                            }
//
//                            Message message = Message.obtain();
//                            message.obj = from;
//                            mHandler.sendMessage(message);
//
//                        };
//                    }.start();
//
//                }
//
//
//
//
//
//            }
//        });

    public void sendMessage(View view) {
        final String msg = inputText.getText().toString();
        if (TextUtils.isEmpty(msg)) {
            Toast.makeText(this, "您还没有填写信息呢...", Toast.LENGTH_SHORT).show();
            return;
        }

        final Msg to = new Msg(msg, Msg.TYPE_SENT);
        to.setDate(new Date());
        msgList.add(to);
        adapter.notifyItemInserted(msgList.size() - 1);//当有新消息时刷新RecyclerView
        msgRecyclerView.scrollToPosition(msgList.size() - 1);//将RecyV定位到最后一行
        inputText.setText("");//清空输入框中的内容

        // 关闭软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }

        new Thread() {
            public void run() {
                Msg from = null;
                try {
                    from = HttpUtils.sendMsg(msg);
                } catch (Exception e) {
                    from = new Msg("无法连接服务器...", Msg.TYPE_RECEIVED);

                }

                Message message = Message.obtain();
                message.obj = from;
                mHandler.sendMessage(message);

            }

            ;
        }.start();
    }


    private void initMsgs() {
        Msg msg1 = new Msg("我是机器人悦悦，欢迎来撩。", Msg.TYPE_RECEIVED);
        msgList.add(msg1);


    }

}
