package com.cnpeng.c_01_customView.HenCoder_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/10/24:下午6:26
 * <p>
 * 说明：http://hencoder.com/ui-1-1/ Android 开发进阶: 自定义 View 1-1 绘制基础 的练习题第11小题——饼图
 * 
 * 注意：还有就是针对不同的区块加文字说明以及连接线，还没写
 */

public class CustomPieChartView extends View {

    private Paint paint;

    public CustomPieChartView(Context context) {
        this(context, null);
    }

    public CustomPieChartView(Context context,
                              @Nullable
                                      AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomPieChartView(Context context,
                              @Nullable
                                      AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //左上角这个比其他的半径稍大一点，注意 sweepRadius 表示扫过的扇面角度
            canvas.drawArc(60, 60, 350, 350, -200, 130, true, paint);

            paint.setColor(Color.BLUE);
            canvas.drawArc(80, 80, 350, 350, -65, 63, true, paint);
            paint.setColor(Color.RED);
            canvas.drawArc(80, 80, 350, 350, 3, 14, true, paint);
            paint.setColor(Color.GREEN);
            canvas.drawArc(80, 80, 350, 350, 20, 10, true, paint);
            paint.setColor(Color.CYAN);
            canvas.drawArc(80, 80, 350, 350, 35, 45, true, paint);
            paint.setColor(Color.LTGRAY);
            canvas.drawArc(80, 80, 350, 350, 85, 75, true, paint);

        }
    }
}
