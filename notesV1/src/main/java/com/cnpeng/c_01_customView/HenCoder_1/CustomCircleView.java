package com.cnpeng.c_01_customView.HenCoder_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/10/24:下午2:49
 * <p>
 * 说明：http://hencoder.com/ui-1-1/ Android 开发进阶: 自定义 View 1-1 绘制基础 的练习题第二小题
 */

public class CustomCircleView extends View {

    private Paint paint;
    private Paint paint1;

    public CustomCircleView(Context context) {
        this(context, null);
    }

    public CustomCircleView(Context context,
                            @Nullable
                                    AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CustomCircleView(Context context,
                            @Nullable
                                    AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画实心黑色圆
        paint.setColor(Color.BLACK);
        canvas.drawCircle(200, 200, 100, paint);

        //画空心圆圈边线,,stroke颜色红色
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(500, 200, 100, paint);  

        //画空心圆圈边线,,stroke颜色红色,宽度10==》如果直接使用paint会影响上面的这个边线，strokeWidth也会应用于上面这个只画边线的圆
        paint1.setColor(Color.RED);
        paint1.setStrokeWidth(20);
        paint1.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(200, 500, 100, paint1);
        
        //画空心圆圈边线,stroke宽度单位px,stroke颜色红色==>没看出啥特殊效果
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(500, 500, 100, paint);


    }
}
