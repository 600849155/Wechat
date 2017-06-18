package com.example.administrator.wechat.Contact;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.wechat.R;

/**
 * Created by Administrator on 2017-5-29.
 */
public class SideBarView extends View {
    public static String[] b = { "↑","❤","A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#" };
    private int selectPos = -1;

    private final int defaultNormalColor = Color.TRANSPARENT;
    private final int defaultPressColor = Color.parseColor("#1F000000");
    private final int defaultTextSize = 60;
    private final int defaultNorTextColor = Color.parseColor("#cc181818");
    private final int defaultPressTextColor = Color.parseColor("#ff000000");


    private int sideBarBgNorColor;
    private int sideBarBgPressColor;
    private int sideBarTextSize;
    private int sideBarNorTextColor;
    private int sideBarPressTextColor;


    public SideBarView(Context context) {
        this(context, null);
    }

    public SideBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public SideBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SideBarView, defStyleAttr, 0);
        sideBarBgNorColor = typedArray.getColor(R.styleable.SideBarView_sidebar_nor_background,defaultNormalColor);
        sideBarBgPressColor = typedArray.getColor(R.styleable.SideBarView_sidebar_press_background,defaultPressColor);
        sideBarTextSize = typedArray.getInt(R.styleable.SideBarView_sidebar_text_size, defaultTextSize);
        sideBarNorTextColor = typedArray.getColor(R.styleable.SideBarView_sidebar_text_color_nor, defaultNorTextColor);
        sideBarPressTextColor = typedArray.getColor(R.styleable.SideBarView_sidebar_text_color_press, defaultPressTextColor);

        typedArray.recycle();

        init();
    }

    Paint paint;
    Paint paintSelect;
    private void init() {
        paint= new Paint() ;
        paint.setAntiAlias(true);
        paint.setColor(sideBarNorTextColor);
        paint.setTypeface( Typeface.DEFAULT_BOLD);
        paint.setTextSize(sideBarTextSize);
        paintSelect= new Paint() ;
        paintSelect.setAntiAlias(true);
        paintSelect.setTypeface(Typeface.DEFAULT_BOLD);
        paintSelect.setTextSize(sideBarTextSize);
        paintSelect.setColor(sideBarPressTextColor);



    }
    int height;
    int width;
    int perHeight;

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        float x = event.getY();
        //计算点击位置所在的position
        int position = (int) (x / perHeight);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //点击字母条变色
                setBackgroundColor(sideBarBgPressColor);
                //点击时回调
                selectPos = position;
                if(listener != null)
                    listener.onLetterSelected(b[selectPos]);
                invalidate();
                break;


            case MotionEvent.ACTION_MOVE:
                //切换到其他字母时执行
                if(position != selectPos){

                    selectPos = position;
                    if(listener != null)
                        listener.onLetterChanged(b[selectPos]);
                    invalidate();
                }


                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setBackgroundColor(sideBarBgNorColor);
                if(listener != null){
                    listener.onLetterReleased(b[selectPos]);
                }
                break;
        }



        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        height = getHeight();
        width = getWidth();
        perHeight = height / b.length;
        for (int i = 0; i < b.length; i++) {
            //画字母
            canvas.drawText(b[i],width/2 - paint.measureText(b[i])/2,perHeight * i+perHeight,paint);
            //选中的字母变色
            if(selectPos == i){
                canvas.drawText(b[i],width/2 - paint.measureText(b[i])/2,perHeight * i+perHeight,paintSelect);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = resolveMeasure(widthMeasureSpec, true);
        int height = resolveMeasure(heightMeasureSpec,false);
        setMeasuredDimension(width,height);
    }

    private int resolveMeasure(int measureSpec ,boolean isWidth) {

        int result = 0 ;
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();

        // 获取宽度测量规格中的mode
        int mode = MeasureSpec.getMode(measureSpec);

        // 获取宽度测量规格中的size
        int size = MeasureSpec.getSize(measureSpec);
/*
        * 如果，模式为精确模式
        * 当前View的宽度，就是我们
        * 的size ；
        * */
        switch (mode){
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST://根据自己的大小选择任意大小的模式
            case MeasureSpec.UNSPECIFIED://父布局没有限制它的大小就是说，想多大就多大
                float textWidth = paint.measureText(b[0]);
                if(isWidth){//计算宽度
                    result = getSuggestedMinimumWidth() > textWidth ? getSuggestedMinimumWidth() : (int) textWidth;
                    result += padding;
                    result = Math.min(result,size);
                }else{
                    result = size;
                    result = Math.max(result,size);
                }

                break;
        }


        return result;
    }


    public float dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return (int) dp2px(25);
    }


    public interface LetterSelectListener{
        void onLetterSelected(String letter);
        void onLetterChanged(String letter);
        void onLetterReleased(String letter);
    }

    private LetterSelectListener listener;
    public void setOnLetterSelectListen(LetterSelectListener listen){
        this.listener = listen;
    }
}
