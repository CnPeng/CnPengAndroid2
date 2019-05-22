package com.cnpeng.c_01_customView.HenCoder_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/10/24:下午2:43
 * <p>
 * 说明：http://hencoder.com/ui-1-1/ Android 开发进阶: 自定义 View 1-1 绘制基础 的练习题第一题
 */

public class CustomColorView extends View {
    public CustomColorView(Context context) {
        super(context);
    }

    public CustomColorView(Context context,
                           @Nullable
                                        AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomColorView(Context context,
                           @Nullable
                                        AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        canvas.drawColor(Color.RED);
    }
}
