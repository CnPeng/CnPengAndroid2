package com.cnpeng.c_01_customView.HenCoder_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/10/24:下午4:24
 * <p>
 * 说明：http://hencoder.com/ui-1-1/ Android 开发进阶: 自定义 View 1-1 绘制基础 的练习题第8小题
 */

public class CustomPathView extends View {

    private Paint paint;
    private Path  path;
    private RectF rectLeft;
    private RectF rectRight;

    public CustomPathView(Context context) {
        this(context, null);
    }

    public CustomPathView(Context context,
                          @Nullable
                                  AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPathView(Context context,
                          @Nullable
                                  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        rectLeft = new RectF(100, 300, 200, 400);
        rectRight = new RectF(200, 300, 300, 400);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //添加心形左上角的半圆，起始角度-225，终止角度215
            path.addArc(100, 100, 200, 200, -225, 215);
            //添加心形右上角半圆，起始角度-180，终止角度225，false 不要强制移过去，而是一点点的画过去
            path.arcTo(200, 100, 300, 200, -180, 225, false);
            //在右半圆画完后，将路径移动到中心线位置，实现心形的封闭
            path.lineTo(200, 250);
            canvas.drawPath(path, paint);
        }

        path.moveTo(100, 300);                      //移动画笔位置
        paint.setStyle(Paint.Style.STROKE);         //设置画线
        path.addArc(rectLeft, -225, 225);           //心形左上角半圆
        path.arcTo(rectRight, -180, 225, false);    //心形右上角半圆
        path.lineTo(200, 450);                      //移动画笔到中心线
        path.close();                               //封闭区域。Style.STROKE 模式必须调用 path.close()， 否则，心形不会闭合。
        canvas.drawPath(path, paint);
    }
}
