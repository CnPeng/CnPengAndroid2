package com.cnpeng.c_01_customView.HenCoder_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/10/24:下午5:09
 * <p>
 * * 说明：http://hencoder.com/ui-1-1/ Android 开发进阶: 自定义 View 1-1 绘制基础 的练习题第10小题——直方图
 * <p>
 * 注意：该页面中，虽然画出了直方图，当时文字如何与矩形块实现居中对齐呢？？？
 */

public class CustomHistogramView extends View {

    private Paint paint;

    List<RectF> rectFs = new ArrayList<>();

    int leftMostX       = 50;    //最左侧X坐标
    int leftMostBottomY = 1000;   //最左下角Y坐标
    int leftMostTopY    = 50;    //最左上角Y坐标
    int rectWidth       = 100;  //矩形区域宽度
    int rectMargin      = 50;  //矩形之间的间距
    int rightMostX      = 1050;    //最右下角X坐标
    int rightMostY      = 1000;    //最右下角Y坐标

    int textBaseLineY = 1040; //绘制Text时传递的 y——基线的Y坐标

    String[] descs = {"c++", "C", "C#", "PHP", "Python", "JAVA"};   //分类信息


    public CustomHistogramView(Context context) {
        this(context, null);
    }

    public CustomHistogramView(Context context,
                               @Nullable
                                       AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomHistogramView(Context context,
                               @Nullable
                                       AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        for (int i = 0; i < descs.length; i++) {
            int left = leftMostX + (rectMargin * (i + 1)) + rectWidth * i;
            int top = leftMostBottomY - leftMostTopY * (i + 5);     //任意定义的测试使用
            int right = left + rectWidth;
            int bottom = leftMostBottomY;
            RectF rectF = new RectF(left, top, right, bottom);
            rectFs.add(rectF);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画左测和底测边线
        float[] lines = {leftMostX, leftMostTopY, leftMostX, leftMostBottomY, leftMostX, leftMostBottomY, rightMostX,
                rightMostY};
        paint.setColor(Color.RED);
        canvas.drawLines(lines, paint);

        //画矩形块
        paint.setColor(Color.BLUE);
        for (RectF rectF : rectFs) {
            canvas.drawRect(rectF, paint);
        }

        //画矩形块下的文字。以矩形的左边框坐标作为文本的X坐标。（暂时未能实现文本与Rect的水平居中对齐）
        paint.setColor(Color.BLACK);
        paint.setTextSize(26);
        for (int i = 0; i < rectFs.size(); i++) {
            RectF rectF = rectFs.get(i);
            canvas.drawText(descs[i], rectF.left, textBaseLineY, paint);
        }
    }
}
