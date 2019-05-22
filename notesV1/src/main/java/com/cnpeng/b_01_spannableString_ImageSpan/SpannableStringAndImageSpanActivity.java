package com.cnpeng.b_01_spannableString_ImageSpan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/2/23:下午4:43
 * <p>
 * 说明：自定义ImageSpan 实现文字与图片的居中对齐
 * <p>
 * 参考链接1：http://www.cnblogs.com/withwind318/p/5541267.html
 * <p>
 * 参考链接2 http://blog.csdn.net/gaoyucindy/article/details/39473135
 */

public class SpannableStringAndImageSpanActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannbalestring_imagespan);
        init();
    }

    private void init() {

        TextView tv_test = (TextView) findViewById(R.id.tv_test);
        SpannableString spannableString = new SpannableString("点击 按钮有惊喜 \n 后面随便换个行，加点东西");

        //调用自定义的imageSpan,实现文字与图片的横向居中对齐
        CustomImageSpan imageSpan = new CustomImageSpan(this, R.mipmap.ic_launcher, 2);

        //setSpan插入内容的时候，起始位置不替换，会替换起始位置到终止位置间的内容，含终止位置。
        //Spanned.SPAN_EXCLUSIVE_EXCLUSIVE模式用来控制是否同步设置新插入的内容与start/end 位置的字体样式，此处没设置具体字体，所以可以随意设置
        spannableString.setSpan(imageSpan, 2, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_test.setText(spannableString);
    }

    /**
     * 自定义imageSpan实现图片与文字的居中对齐
     */
    class CustomImageSpan extends ImageSpan {

        //自定义对齐方式--与文字中间线对齐
        private int ALIGN_FONTCENTER = 2;

        public CustomImageSpan(Context context, int resourceId) {
            super(context, resourceId);
        }

        public CustomImageSpan(Context context, int resourceId, int verticalAlignment) {
            super(context, resourceId, verticalAlignment);
        }

//        public CustomImageSpan(Context context, int resourceId,int width,int height) {
//            super(context, ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(context.getResources(),resourceId),
//                    AutoUtils.getPercentWidthSize(width), AutoUtils.getPercentWidthSize(height)), 2);
//
//        }
        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
                         Paint paint) {

            //draw 方法是重写的ImageSpan父类 DynamicDrawableSpan中的方法，在DynamicDrawableSpan类中，虽有getCachedDrawable()，
            // 但是私有的，不能被调用，所以调用ImageSpan中的getrawable()方法，该方法中 会根据传入的drawable ID ，获取该id对应的
            // drawable的流对象，并最终获取drawable对象
            Drawable drawable = getDrawable(); //调用imageSpan中的方法获取drawable对象
            canvas.save();

            //获取画笔的文字绘制时的具体测量数据
            Paint.FontMetricsInt fm = paint.getFontMetricsInt();

            //系统原有方法，默认是Bottom模式)
            int transY = bottom - drawable.getBounds().bottom;
            if (mVerticalAlignment == ALIGN_BASELINE) {
                transY -= fm.descent;
            } else if (mVerticalAlignment == ALIGN_FONTCENTER) {    //此处加入判断， 如果是自定义的居中对齐
                //与文字的中间线对齐（这种方式不论是否设置行间距都能保障文字的中间线和图片的中间线是对齐的）
                // y+ascent得到文字内容的顶部坐标，y+descent得到文字的底部坐标，（顶部坐标+底部坐标）/2=文字内容中间线坐标
                transY = ((y + fm.descent) + (y + fm.ascent)) / 2 - drawable.getBounds().bottom / 2;
            }

            canvas.translate(x, transY);
            drawable.draw(canvas);
            canvas.restore();
        }

        /**
         * 重写getSize方法，只有重写该方法后，才能保证不论是图片大于文字还是文字大于图片，都能实现中间对齐
         */
        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            Drawable d = getDrawable();
            Rect rect = d.getBounds();
            if (fm != null) {
                Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
                int fontHeight = fmPaint.bottom - fmPaint.top;
                int drHeight = rect.bottom - rect.top;

                int top = drHeight / 2 - fontHeight / 4;
                int bottom = drHeight / 2 + fontHeight / 4;

                fm.ascent = -bottom;
                fm.top = -bottom;
                fm.bottom = top;
                fm.descent = top;
            }
            return rect.right;
        }
    }
}


