package com.example.administrator.wechat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.wechat.Contact.ChineseToEnglish;
import com.example.administrator.wechat.Contact.CompareSort;
import com.example.administrator.wechat.Contact.SideBarView;
import com.example.administrator.wechat.Contact.User;
import com.example.administrator.wechat.Contact.UserAdapter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Administrator on 2017-5-27.
 */
public class ContactFragment extends Fragment implements SideBarView.LetterSelectListener {
    ListView mListview;
    UserAdapter mAdapter;
    TextView mTip;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate( R.layout.contact_fragment_listview, null );
        mListview = (ListView) view.findViewById( R.id.listview );
        SideBarView sideBarView = (SideBarView) view.findViewById( R.id.sidebarview );
        mTip = (TextView) view.findViewById( R.id.tip );
        sideBarView.setOnLetterSelectListen( this );
        init();
        return view;

    }


    private void init() {


        String[] contactsArray = getResources().getStringArray( R.array.data );
        String[] headArray = getResources().getStringArray( R.array.head );
        initimage();
        int[] headImageArray = new int[]{R.drawable.plugins_friendnotify, R.drawable.add_friend_icon_addgroup, R.drawable.contact_icon_contacttag, R.drawable.add_friend_icon_offical

        };
        int[] contactsImage = new int[]{
                R.drawable.xiaobao, R.drawable.andyli, R.drawable.caisaojie, R.drawable.caiboyang, R.drawable.duone7, R.drawable.dengjingbo, R.drawable.fatman, R.drawable.fanzhineng, R.drawable.plugins_friendnotify, R.drawable.add_friend_icon_offical, R.drawable.add_friend_icon_addgroup, R.drawable.contact_icon_contacttag, R.drawable.xiaobao, R.drawable.andyli, R.drawable.caisaojie, R.drawable.caiboyang, R.drawable.duone7, R.drawable.dengjingbo, R.drawable.fatman, R.drawable.fanzhineng, R.drawable.plugins_friendnotify, R.drawable.add_friend_icon_offical, R.drawable.add_friend_icon_addgroup, R.drawable.contact_icon_contacttag, R.drawable.xiaobao, R.drawable.andyli, R.drawable.caisaojie, R.drawable.caiboyang, R.drawable.duone7, R.drawable.dengjingbo, R.drawable.fatman, R.drawable.fanzhineng, R.drawable.plugins_friendnotify, R.drawable.contact_icon_contacttag, R.drawable.xiaobao, R.drawable.andyli, R.drawable.caisaojie, R.drawable.caiboyang, R.drawable.duone7, R.drawable.dengjingbo, R.drawable.fatman, R.drawable.fanzhineng, R.drawable.xiaobao, R.drawable.andyli, R.drawable.caisaojie, R.drawable.caiboyang,

        };


        //模拟添加数据到Arraylist
        int length = contactsArray.length;
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            User user = new User();
            user.setName( contactsArray[i] );
            user.setIcon( contactsImage[i] );
            String firstSpell = ChineseToEnglish.getFirstSpell( contactsArray[i] );
            String substring = firstSpell.substring( 0, 1 ).toUpperCase();
            if (substring.matches( "[A-Z]" )) {
                user.setLetter( substring );
            } else {
                user.setLetter( "#" );
            }
            users.add( user );
        }

        for (int i = 0; i < headArray.length; i++) {
            User user = new User();
            user.setName( headArray[i] );
            user.setIcon( headImageArray[i] );
            user.setLetter( "@" );
            users.add( user );
        }

        //排序
        Collections.sort( users, new CompareSort() );

        //设置数据
        mAdapter = new UserAdapter( getActivity() );
        mAdapter.setData( users );
        mListview.setAdapter( mAdapter );

        //设置回调


    }

    private void initimage() {
    }


    @Override
    public void onLetterSelected(String letter) {
        setListviewPosition( letter );
        mTip.setText( letter );
        mTip.setVisibility( View.VISIBLE );
    }

    @Override
    public void onLetterChanged(String letter) {
        setListviewPosition( letter );
        mTip.setText( letter );
    }

    @Override
    public void onLetterReleased(String letter) {
        mTip.setVisibility( View.GONE );
    }

    private void setListviewPosition(String letter) {
        int firstLetterPosition = mAdapter.getFirstLetterPosition( letter );
        if (firstLetterPosition != -1) {
            mListview.setSelection( firstLetterPosition );
        }
    }

}




