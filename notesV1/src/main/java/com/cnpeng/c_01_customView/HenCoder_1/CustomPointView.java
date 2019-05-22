package com.cnpeng.c_01_customView.HenCoder_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/10/24:下午3:26
 * <p>
 * 说明：http://hencoder.com/ui-1-1/ Android 开发进阶: 自定义 View 1-1 绘制基础 的练习题第4小题
 */

public class CustomPointView extends View {

    private Paint paint;

    public CustomPointView(Context context) {
        this(context, null);
    }

    public CustomPointView(Context context,
                           @Nullable
                                   AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPointView(Context context,
                           @Nullable
                                   AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStrokeWidth(30);

        //得到一个正方形
        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoint(100, 100, paint);

        //得到一个圆形
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(200, 100, paint);

        //得到一个正方形
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(300, 100, paint);

        //一系列点坐标数组，必须是偶数个
        float[] points = {100, 200, 200, 200, 300, 200, 400, 200, 500, 200};
        //跳过几个数，需要绘制几个数，为保证效果，必须是偶数
        canvas.drawPoints(points, 2, 6, paint);


        paint.setStrokeCap(Paint.Cap.ROUND);
        float[] points2 = {100, 300, 200, 300, 300, 300, 400, 300, 500, 300};
        //5个点全部绘制，不跳过任何一个        
        canvas.drawPoints(points2, paint);
    }
}
