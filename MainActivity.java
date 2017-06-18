package com.example.administrator.wechat;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.wechat.WeChat.FragmentAdapter;
import com.example.administrator.wechat.WeChat.Talk;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Talk> talkList = new ArrayList<Talk>();

    private List<Fragment> mFragments = new ArrayList<>(); //ViewPage选项卡页面集合
    private List<String> mTitles = new ArrayList<>();//Tab标题集合
    private String[] data = {"Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry"};
    private String[] titles = new String[]{"微信", "通讯录", "发现", "我"};
    private TabLayout mTabLayout;
    private FragmentAdapter adapter;
    private ViewPager viewPager;


    /**
     * 图片数组
     */
    private int[] mImgs = new int[]{R.drawable.selector_tab_weixin, R.drawable.selector_tab_friends, R.drawable.selector_tab_find,
            R.drawable.selector_tab_me};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


        viewPager = (ViewPager) findViewById( R.id.viewpager );//新建一个viewlist对象来保存各个分页的内容
        mTabLayout = (TabLayout) findViewById( R.id.tablayout );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );


        setSupportActionBar( toolbar );
        setOverflowButtonAlways();
        initTablaout();


    }


    private void initTablaout() {//tablaout

        for (int i = 0; i < 4; i++) {
            mTitles.add( titles[i] );
        }
        //设置四个碎片
        WeChatFragment fragment1 = new WeChatFragment();
        ContactFragment fragment2 = new ContactFragment();
        DiscoverFragment fragment3 = new DiscoverFragment();
        MeFragment fragment4 = new MeFragment();

        mFragments.add( fragment1 );
        mFragments.add( fragment2 );
        mFragments.add( fragment3 );
        mFragments.add( fragment4 );


        adapter = new FragmentAdapter( getSupportFragmentManager(), mFragments, mTitles );
        viewPager.setAdapter( adapter );//给ViewPager设置适配器
        viewPager.setOffscreenPageLimit( 3 );//解决ListView重复加载的问题
        mTabLayout.setupWithViewPager( viewPager );//将TabLayout和ViewPager关联起来
        mTabLayout.setSelectedTabIndicatorHeight( 0 );
        for (int i = 0; i < 4; i++) {
            //获得到对应位置的Tab
            TabLayout.Tab itemTab = mTabLayout.getTabAt( i );
            if (itemTab != null) {
                //设置自定义的标题
                itemTab.setCustomView( R.layout.item_tab );
                TextView textView = (TextView) itemTab.getCustomView().findViewById( R.id.tv_name );
                textView.setText( mTitles.get( i ) );
                ImageView imageView = (ImageView) itemTab.getCustomView().findViewById( R.id.iv_img );
                imageView.setImageResource( mImgs[i] );
            }
        }
        mTabLayout.getTabAt( 0 ).getCustomView().setSelected( true );
    }


    /***
     * 右侧加号在部分机器上不会显示，所以这里反射，强制
     * 让其显示
     ***/
    private void setOverflowButtonAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get( this );
            Field menuKey = ViewConfiguration.class.getDeclaredField( "sHasPermanentMenuKey" );
            menuKey.setAccessible( true );
            menuKey.setBoolean( config, false );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }

    /**
     * 显示 OverFlow 中的图标
     */
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals( "MenuBuilder" )) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE );
                    m.setAccessible( true );
                    m.invoke( menu, true );
                } catch (Exception e) {
                }
            }
        }
        return super.onPrepareOptionsPanel( view, menu );
    }


}
