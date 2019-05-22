package com.cnpeng.a_31_RatingBar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.utils.LogUtils;

/**
 * Created by CnPeng on 2017/2/4.
 * <p>
 * RatingBar的基本使用 根据RatingBar的比率值动态改变图片的透明度
 */

public class RatingBarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratingbar);

        final ImageView imageView = (ImageView) findViewById(R.id.iv_ratingbarTest);

        //允许用户改变的RatingBar--
        RatingBar ratingBar01 = (RatingBar) findViewById(R.id.ratingBar01);
        ratingBar01.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                LogUtils.e("当前的Rating是多少：", rating + "");   //打印比率值，输出是占用了几个星

                imageView.setImageAlpha((int) (rating * 255 / 5));  //根据比率动态改变图片透明度
            }
        });

        //不允许用户改变的RatingBar 只能作为一个指示器来使用--因为在xml中设置了属性isIndicator 为true
        RatingBar ratingBar02 = (RatingBar) findViewById(R.id.ratingBar_Indicator);

        //自定义RatingBar 中星星的颜色 
        //        LayerDrawable layerDrawable = (LayerDrawable) ratingBar01.getProgressDrawable();//获取progressDrawable
        //设置星星的颜色为 Color 中预定义的黄色==这种方式会改变所有的RatingBar的星星颜色
        //        layerDrawable.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        //设置星星的颜色为 rgb值的颜色==这种方式会改变所有的RatingBar的星星颜色；getDrawable()方法中传入2 获取到的是被选中时的drawable
        //        layerDrawable.getDrawable(2).setColorFilter(Color.rgb(255, 0, 0), PorterDuff.Mode.SRC_ATOP);
    }
}
