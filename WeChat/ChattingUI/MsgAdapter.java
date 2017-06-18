package com.example.administrator.wechat.WeChat.ChattingUI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.wechat.R;

import java.util.List;

/**
 * Created by Administrator on 2017-6-12.
 */
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder>{

    private List<Msg> mMsgList;








    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
        TextView createDate;

        public ViewHolder (View view){
            super(view);
            leftLayout =(LinearLayout) view.findViewById(R.id.left_layout);
            rightLayout =(LinearLayout) view.findViewById(R.id.right_layout);
            leftMsg = (TextView)view.findViewById(R.id.left_msg);
            rightMsg = (TextView)view.findViewById(R.id.right_msg1);
            createDate =(TextView)view.findViewById(R.id.chat_from_createDate);





        }
    }
    public MsgAdapter(List<Msg> msgList){
        mMsgList = msgList;


    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg = mMsgList.get(position);

        if(msg.getType() == Msg.TYPE_RECEIVED){//如果是收到消息
            holder.leftLayout.setVisibility(View.VISIBLE);//则显示左边的消息布局
            holder.rightLayout.setVisibility( View.GONE);//将右边消息布局隐藏
            holder.leftMsg.setText(msg.getMsg());//LEFTmsg得到字体内容
            holder.createDate.setText(msg.getDateStr());//显示当前时间

        }else if(msg.getType()==Msg.TYPE_SENT){
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getMsg());
            holder.createDate.setText(msg.getDateStr());


        }


    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }







}
