package com.cnpeng.c_00_CommonCustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/5/18:下午5:42
 * <p>
 * 说明：自定义圆形图片。
 * 注意：使用这种方式虽然能得到一个圆形的图片，但是，确是从原图的左上角开始截取的！！！
 */

public class CustomRoundDrawable extends Drawable {

    private Paint paint;      //画笔
    private int   diameter;      //圆的直径  diameter [daɪ'æmɪtə] 直径

    public CustomRoundDrawable(Context context, int id) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        paint = new Paint();
        paint.setAntiAlias(true);   //启用抗锯齿
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(bitmapShader);  //将指定了bitmap 和 拉伸模式的 着色器对象设置给画笔

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        diameter = Math.min(width, height); //取得图片宽高中的小值作为圆的直径
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(diameter / 2, diameter / 2, diameter / 2, paint); //参数依次是 圆点的X坐标，圆点的Y坐标，圆的半径，画笔
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
        return diameter;
    }

    @Override
    public int getIntrinsicWidth() {    //必须重写这两个方法，否则，不显示任何内容
        return diameter;
    }
}
