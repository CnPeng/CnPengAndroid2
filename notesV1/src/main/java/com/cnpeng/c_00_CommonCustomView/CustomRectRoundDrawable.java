package com.cnpeng.c_00_CommonCustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/5/18:下午5:42
 * <p>
 * 说明：自定义圆形图片。
 * 
 */

public class CustomRectRoundDrawable extends Drawable {

    private       Paint paint;      //画笔
    private       RectF rectF;      //矩形区域（存放图片的矩形区域）
    private final int   width;
    private final int   height;

    public CustomRectRoundDrawable(Context context, int id) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        paint = new Paint();
        paint.setAntiAlias(true);   //启用抗锯齿
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(bitmapShader);  //将指定了bitmap 和 拉伸模式的 着色器对象设置给画笔

        width = bitmap.getWidth();
        height = bitmap.getHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(rectF, 30, 30, paint); //矩形，每个角在X轴的偏移量，每个角在Y轴的偏移量，画笔
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);  //设置画笔的透明度
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);  //设置画笔的颜色过滤器
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicHeight() {   //必须重写这两个方法，否则，不显示任何内容
        return height;
    }

    @Override
    public int getIntrinsicWidth() {    //必须重写这两个方法，否则，不显示任何内容
        return width;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        rectF = new RectF(left, top, right, bottom);
    }
}
