package com.cnpeng.A_16_ImageBrowserV2;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * Created by CnPeng on 2017/1/17.
 * <p>
 * 简易图片浏览器V2--可以改变图片的透明度，可以实现局部预览
 */

public class ImageBrowserV2Activtiy extends AppCompatActivity {

    int alpha    = 255;  //初始透明度255 完全透明
    int curImage = 0;     //默认图片
    private ImageView ivAll;
    private ImageView ivLocal;
    int[] imageArray = new int[]{R.drawable.daomeixiong, R.drawable.xiaopohai, R.drawable.gongfuxiongmao, R.drawable
            .milaoshu, R.drawable.pikaqiu};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagebrowser_v2);
        init();
        setIvLocalSource();
    }

    /**
     * 获取控件并初始化点击监听等
     */
    private void init() {
        ivAll = (ImageView) findViewById(R.id.iv_all);
        ivLocal = (ImageView) findViewById(R.id.iv_local);

        ivAll.setImageResource(imageArray[0]);  //设置初始图片

        final Button btAdd = (Button) findViewById(R.id.bt_addAlpha);
        final Button btMinus = (Button) findViewById(R.id.bt_minusAlpha);
        final Button btShowNext = (Button) findViewById(R.id.bt_showNext);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btAdd) {     //增加透明度
                    alpha += 20;
                }
                if (v == btMinus) {     //减小透明度
                    alpha -= 20;
                }

                if (alpha > 255) {      //防止透明度越界
                    alpha = 255;
                }
                if (alpha < 0) {       //防止透明度越界
                    alpha = 0;
                }

                ivAll.setImageAlpha(alpha);      //设置透明度
                ivLocal.setImageAlpha(alpha);

                if (v == btShowNext) {    //展示下一张图片
                    ivAll.setImageResource(imageArray[++curImage % imageArray.length]);
                }
            }
        };

        btAdd.setOnClickListener(clickListener);
        btMinus.setOnClickListener(clickListener);
        btShowNext.setOnClickListener(clickListener);
    }

    /**
     * 展示 局部放大的图片资源
     */
    private void setIvLocalSource() {
        ivAll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                BitmapDrawable image_All = (BitmapDrawable) ivAll.getDrawable();    //获取ivAll中展示的原图片
                Bitmap bitmap_All = image_All.getBitmap();    //将ivAll中的原图转成bitmap位图

                int bmHeight = bitmap_All.getHeight();  //分别获取图片实际宽高以及 iv的宽高
                int ivAllHeight = ivAll.getHeight();
                int bmWidth = bitmap_All.getWidth();
                int ivAllWitdh = ivAll.getWidth();

                double scale = 0.0;
                if (bmHeight > ivAllHeight || bmWidth > ivAllWitdh) {  //判断图片是否大于控件是否有缩放
                    int scaleHeight = Math.round((float) bmHeight / (float) ivAllHeight);  //强转float然后得到四舍五入的比率
                    int scaleWidth = Math.round((float) bmWidth / (float) ivAllWitdh);  //
                    scale = scaleHeight > scaleWidth ? scaleHeight : scaleWidth;    //获取较大的那个压缩比率
                }

                int dx = (int) event.getX();    //获取触摸点的坐标
                int dy = (int) event.getY();
                if (scale != 0) {   //如果有缩放，
                    // 乘以缩放比率后得到要显示的起始坐标点
                    dx *= scale;
                    dy *= scale;
                }


                if (dx + 150 > bitmap_All.getWidth()) { //防止越界,这里的150 dx 拿到的都是px 值，正常的话，需要用dp转换之后的px
                    dx = bitmap_All.getWidth() - 150;
                }
                if (dy + 150 > bitmap_All.getHeight()) {   //加这个判断是为了防止局部放大的iv 中展示的内容超出了原图的区域
                    dy = bitmap_All.getHeight() - 150;
                }

                ivLocal.setImageBitmap(Bitmap.createBitmap(bitmap_All, dx, dy, 150, 150));  //这里的150也应该用dp转px 之后的

                return false;
            }
        });
    }
}
