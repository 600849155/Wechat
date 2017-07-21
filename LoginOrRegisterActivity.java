package com.example.administrator.wechat;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.wechat.Comfig.BmobConstants;
import com.example.administrator.wechat.Contact.User;
import com.example.administrator.wechat.Util.CommonUtils;

import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017-7-13.
 */
public class LoginOrRegisterActivity extends BaseActivity implements View.OnClickListener {

    private MyBroadcastReceiver receiver = new MyBroadcastReceiver();
    private EditText et_username;
    private EditText et_password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.loginorrgister );
        init();
        //注册退出广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction( BmobConstants.ACTION_REGISTER_SUCCESS_FINISH );
        registerReceiver( receiver, filter );

    }


    private void init() {
        et_username = (EditText) findViewById( R.id.et_username );
        et_password = (EditText) findViewById( R.id.et_password );
        Button bt_login = (Button) findViewById( R.id.bt_login );
        Button bt_register = (Button) findViewById( R.id.bt_register );
        bt_login.setOnClickListener( this );
        bt_register.setOnClickListener( this );

    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && BmobConstants.ACTION_REGISTER_SUCCESS_FINISH.equals( intent.getAction() )) {
                finish();

            }


        }


    }

    @Override
    public void onClick(View view) {
        if (view.equals( R.id.bt_register )) {
            Intent intent = new Intent( LoginOrRegisterActivity.this, RegisterActivity.class );


        } else {
            boolean isNetConnected = CommonUtils.isNetworkAvailable( this );
            if (!isNetConnected) {
                ShowToast( R.string.network_tips );//当前无网络
                return;
            }
            login();

        }

    }

    private void login() {
        String name = et_username.getText().toString();
        String password = et_password.getText().toString();
        if (TextUtils.isEmpty( name )) {
            ShowToast( R.string.toast_error_username_null );
            return;
        }
        if (TextUtils.isEmpty( password )) {
            ShowToast( R.string.toast_error_password_null );
            return;

        }
        final ProgressDialog progress = new ProgressDialog( LoginOrRegisterActivity.this );
        progress.setMessage( "正在登录..." );
        progress.setCanceledOnTouchOutside( false );//不允许取消
        progress.show();
        User user = new User();
        user.setUsername( name );
        user.setPassword( password );
        userManager.login( user, new SaveListener() {
            @Override
            public void onSuccess() {
                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        progress.setMessage( "正在获取好友列表..." );
                    }
                } );
                //更新用户的地理位置以及好友资料
                updateUserInfos();

            }

            @Override
            public void onFailure(int i, String s) {
                progress.dismiss();
                BmobLog.i( s );
                ShowToast( s );

            }
        } );




    }

    @Override
    protected void onDestroy() {//销毁广播接收器
        super.onDestroy();
        unregisterReceiver( receiver );
    }
}
