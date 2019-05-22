package com.cnpeng.c_01_customView.HenCoder_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/10/24:下午4:04
 * <p>
 * 说明：http://hencoder.com/ui-1-1/ Android 开发进阶: 自定义 View 1-1 绘制基础 的练习题第7小题
 */

public class CustomArcView extends View {

    private Paint paint;
    private RectF rectF;

    public CustomArcView(Context context) {
        this(context, null);

    }

    public CustomArcView(Context context,
                         @Nullable
                                 AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomArcView(Context context,
                         @Nullable
                                 AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectF = new RectF(100, 100, 300, 300);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //API21以后可以使用该方法，其中的startAngle 表示起始角度，sweepAngle 表示扇面覆盖的角度，userCenter表示是否使用圆心
            canvas.drawArc(100, 100, 300, 300, -45, 85, true, paint);
        }

        //API21之前之后均可使用，useCenter为false时，得到的是橄榄形的一半，构不成标准扇形        
        canvas.drawArc(rectF, 45, 80, false, paint);

        //开启画线模式
        paint.setStyle(Paint.Style.STROKE);
        //如果useCenter传入true，会得到一个没有填充的扇形        
        canvas.drawArc(rectF, 130, 70, true, paint);
        //useCenter传入false，得到的只是一条弧线
        canvas.drawArc(rectF, 205, 105, false, paint);
    }
}
