package com.cnpeng.a_43_scrollView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.utils.LogUtils;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/3/13:下午2:19
 * <p>
 * 说明：ScrollView中嵌套HorizontalScrollView 实现视图同时在水平和垂直方向的滚动
 */

public class ScrollViewActivity extends AppCompatActivity {

    private float                scrollViewHeigh;
    private float                scrollViewWidth;
    private int                  percent;
    private ScrollView           scrollview;
    private HorizontalScrollView horizontalScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);

        init();
    }

    /**
     * 加这个方法本来是想实现在垂直滚动的时候同时触发水平滚动，水平滚动的时候同时触发垂直滚动。
     * 
     * 但是在给ScrollView设置的触摸监听中，并没有监听到 Down 事件，所以，现在得到的效果是，只要垂直滚动，水平方向就会直接滚到最右了
     */
    private void init() {
        scrollview = (ScrollView) findViewById(R.id.scrollView_01);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView01);

        //在视图树中获取控件的宽高，获取完成之后移除视图树        
        scrollview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollViewHeigh = scrollview.getHeight();
                scrollViewWidth = scrollview.getWidth();
                percent = Math.round(scrollViewHeigh / scrollViewWidth);
                LogUtils.e("scrollView的宽高及比率", "宽：" + scrollViewWidth + "高：" + scrollViewHeigh + "比率：" + percent);
                scrollview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        scrollview.setOnTouchListener(new View.OnTouchListener() {
            float startY;
            float curY;
            int moveY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:   //手指按下
                        startY = event.getY();
                        LogUtils.e("检测到down事件了么？", "Yes");
                        break;
                    case MotionEvent.ACTION_MOVE:   //移动中，
                        LogUtils.e("检测到Move事件了么？", "Yes");
                        curY = event.getY();
                        moveY = (int) (curY - startY);
                        horizontalScrollView.scrollBy(moveY * percent, moveY);
                        break;
                    case MotionEvent.ACTION_UP:     //抬手
                        LogUtils.e("检测到Up事件了么？", "Yes");
                        break;
                }

                LogUtils.e("移动中的参数", "startY" + startY + "curY" + curY + "moveY" + moveY);
                return false;
            }
        });
    }
}
