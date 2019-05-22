package com.cnpeng.a_05_scrollBallWithFinger;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by CnPeng on 2017/1/12.
 * <p>
 * 自定义view--跟随手指移动的小球
 */

public class CusScrollBall extends View {
    private Paint paint = new Paint();      //预定义画笔
    float currentX = 100;   //X 坐标
    float currentY = 200;   //Y 坐标

    public CusScrollBall(Context context) {
        super(context);
    }

    public CusScrollBall(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas); //父类空实现，可以直接注掉
        paint.setColor(Color.RED);

        //绘制一个圆，参数分别是：X坐标点，Y坐标点，半径，画笔 。半径的单位是 px
        canvas.drawCircle(currentX, currentY, 50, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = event.getX();  //获取当前的触摸点位置
        currentY = event.getY();
        invalidate();   //强制重绘
        return true;    //返回true 表示触摸事件已经被处理掉，不用父布局处理
        //        return super.onTouchEvent(event);
    }
}
