package com.cnpeng.c_01_customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/6/3:下午9:04
 * <p>
 * 说明：自定义控件--自定义TextView，使用画笔绘制内容
 * <p>
 * 计算基线参考： https://github.com/GcsSloop/AndroidNote/blob/master/CustomView/Advance/%5B99%5DDrawText.md
 * 中心位置坐标，centerX, centerY
 * 文本高度：height ＝ descent－ascent
 * descent的坐标：descentY ＝ centerY ＋ 1/2height
 * baseLineY坐标：baseLineY ＝ descentY－descent
 * 通过上面内容可以推算出如下公式：
 * <p>
 * baseLineY ＝ centerY － 1/2(ascent + descent)
 */

public class CustomTextView extends View {

    private String textToDraw;
    private Paint  paint;
    private Rect   rect;

    public CustomTextView(Context context) {
        //        super(context);
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        //        super(context, attrs);
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        textToDraw = "HelloWorld";

        paint = new Paint();
        paint.setTextSize(100);                         //绘制的文本大小
        paint.setColor(Color.RED);//颜色
        paint.setAntiAlias(true);                       //抗锯齿


        rect = new Rect();

        //获取文本的区域（宽、高）。参数依次是：要绘制的文本，从第几个文字开始绘制，到第几个文本截止，存储文字宽高信息的矩形
        paint.getTextBounds(textToDraw, 0, textToDraw.length(), rect);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int ascent = (int) paint.ascent();
        int descent = (int) paint.descent();

        int viewHeight = getHeight();
        int viewWidth = getWidth();
        int textHeight = rect.height();
        int textWidth = rect.width();

        int xCoordinate = viewWidth / 2 - textWidth / 2;      //以View的中心线为文字的中心线
        int yCoordinate = viewHeight / 2 + textHeight / 2;    //两者相加得到Baseline 的坐标，此处一定注意是+！！！！

        canvas.drawText(textToDraw, xCoordinate, yCoordinate, paint);
    }
}
