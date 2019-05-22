package com.cnpeng.c_01_customView.HenCoder_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/10/24:下午3:46
 * <p>
 * 说明：http://hencoder.com/ui-1-1/ Android 开发进阶: 自定义 View 1-1 绘制基础 的练习题第5/6小题
 */

public class CustomOvalAndLine extends View {

    private Paint paint;
    private RectF rectF;

    public CustomOvalAndLine(Context context) {
        this(context, null);
    }

    public CustomOvalAndLine(Context context,
                             @Nullable
                                     AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CustomOvalAndLine(Context context,
                             @Nullable
                                     AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectF = new RectF(100, 350, 600, 550);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //API 21之后才有该方法，参数中的 left,top,right,bottom 实际是所处Rect的坐标
            canvas.drawOval(100, 100, 600, 300, paint);
        }

        paint.setColor(Color.RED);
        //API 21之前和之后均可用该方法绘制椭圆        
        canvas.drawOval(rectF, paint);


        //画单条圆头线，指定线条宽度为10        
        paint.setStrokeWidth(30);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(100, 600, 500, 650, paint);


        //画一组方头线。Cap 用来设置线条的顶端形状   
        paint.setStrokeCap(Paint.Cap.SQUARE);
        float[] lines = {100, 650, 150, 650, 150, 750, 200, 650, 300, 750};
        //跳过数组中的2个数，然后绘制后面的8个数，8个数每4个确定一条线，共是2条线
        canvas.drawLines(lines, 2, 8, paint);
    }
}
