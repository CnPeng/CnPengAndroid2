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
import androidx.annotation.RequiresApi;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/10/24:下午3:09
 * <p>
 * 说明：http://hencoder.com/ui-1-1/ Android 开发进阶: 自定义 View 1-1 绘制基础 的练习题第3小题
 */

public class CustomRectView extends View {

    private Paint paint;
    private RectF rectF;

    public CustomRectView(Context context) {
        this(context, null);
    }

    public CustomRectView(Context context,
                          @Nullable
                                  AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRectView(Context context,
                          @Nullable
                                  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectF = new RectF(100, 500, 200, 600);
    }

    @RequiresApi( api = Build.VERSION_CODES.LOLLIPOP )
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        paint.setColor(Color.RED);
        
        //普通长方体
        canvas.drawRect(100, 100, 200, 200, paint);

        //API 21以上才有drawRoundRect(left,top,right,botton,rx,ry,paint)方法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(100, 300, 300, 400, 100, 10, paint);
        }

        //API 21 之前之后都可以使用该方法绘制圆角矩形
        canvas.drawRoundRect(rectF, 100, 10, paint);
    }
}
