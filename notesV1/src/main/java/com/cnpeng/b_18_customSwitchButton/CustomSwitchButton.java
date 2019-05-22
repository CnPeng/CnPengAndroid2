package com.cnpeng.b_18_customSwitchButton;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.cnpeng.utils.LogUtils;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/8/3:上午9:01
 * <p>
 * 说明：自定义开关按钮
 * <p>
 * 因为是继承自View，所以没有 onLayout 方法，只能测量和绘制
 * <p>
 * TODO 还需要加入 padding 的处理
 */

public class CustomSwitchButton extends View {

    private int viewWidth  = 100;   //背景的默认宽
    private int viewHeight = 50;    //背景的默认高

    private int                     xCooidinate;     //圆圈当前的x坐标
    private RectF                   rect;                //view所在的矩形区域
    private RectF                   rectFrame;          //view上层遮罩
    private Paint                   paint;               //画笔
    private boolean                 isChecked;           //是否处于开启状态
    private onCheckedChangeListener changeListener; //状态监听器
    private float                   frameLeft;
    private float                   frameTop;
    private float                   frameRight;
    private float                   frameBottom;
    private boolean                 toLeft;     //是否左划
    private float                   downDot;


    public CustomSwitchButton(Context context) {
        this(context, null);
    }

    public CustomSwitchButton(Context context,
                              @Nullable
                                      AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CustomSwitchButton(Context context,
                              @Nullable
                                      AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rect = new RectF();
        rectFrame = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //继承自View时必须处理 SpecMode ,如果父布局的mode 是 AtMost,并且view中使用了 wrapContent,那么，该view最终会占满屏幕，
        // 所以，测量时需要判断处理下，如果父类的 mode 是 AtMost ，那么就指定父类的宽或高的size
        //测量宽高
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);    //获取宽的布局模式
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);    //获取宽度

        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);    //高的布局模式
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);    //高度

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(viewWidth, viewHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {      //宽度包裹，则指定宽度
            setMeasuredDimension(viewWidth, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {     //高度暴多，则指定高度
            setMeasuredDimension(widthSpecSize, viewHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawCircleSlider(canvas);
    }


    /**
     * 绘制背景
     */
    private void drawBackground(Canvas canvas) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        //绘制背景
        int color = isChecked ? Color.GREEN : Color.GRAY;
        paint.setColor(color);

        viewWidth = measuredWidth == 0 ? viewWidth : measuredWidth;
        viewHeight = measuredHeight == 0 ? viewHeight : measuredHeight;

        rect.set(0, 0, viewWidth, viewHeight);
        canvas.drawRoundRect(rect, viewHeight / 2, viewHeight / 2, paint);  //画背景

        //绘制上层遮罩
        int frameColor = toLeft ? Color.GRAY : Color.GREEN;
        paint.setColor(frameColor);

        rectFrame.set(frameLeft, frameTop, frameRight, frameBottom);
        canvas.drawRoundRect(rectFrame, viewHeight / 2, viewHeight / 2, paint);    //画上层动态变化的背景
    }

    /**
     * 绘制上层滑块
     */
    private void drawCircleSlider(Canvas canvas) {
        //绘制上层圆圈。并根据状态确定中心点坐标
        if (xCooidinate == 0) {     //上层圆圈中心点x轴坐标,初始化页面的时候，会为0
            xCooidinate = isChecked ? (viewWidth - viewHeight / 2) : viewHeight / 2;  //初始化时根据选中状态确定坐标点
        }
        int yCoordinate = viewHeight / 2;   //层圆圈中心点y轴坐标
        int radius = viewHeight / 2 - 4;    //半径
        paint.setColor(Color.WHITE);
        canvas.drawCircle(xCooidinate, yCoordinate, radius, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //触摸点在最左圆心之左或最右圆心之右，不做处理
        int leftBound = viewHeight / 2;             //触摸点最左边界
        int rightBound = viewWidth - leftBound;     //触摸点左右边界


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:   //点击更改状态
                downDot = event.getX();                //按下时的触摸点

                //确定上层滑动圆圈的圆心坐标
                if (downDot <= leftBound) {   //左侧防越界
                    xCooidinate = leftBound;
                } else if (downDot >= rightBound) {    //右侧防越界
                    xCooidinate = rightBound;
                } else {
                    xCooidinate = (int) downDot;
                }

                //确定FrameRect边界
                toLeft = isChecked;//如果原先是选中状态，点一下我就认为你是要左移；如果是未选中状态，我就认为你是要右移
                LogUtils.e("按下时是在左侧？：", String.valueOf(toLeft));

                frameLeft = toLeft ? downDot - leftBound : 0;
                frameRight = toLeft ? viewWidth : downDot + leftBound;
                frameTop = 0;
                frameBottom = viewHeight;
                LogUtils.e("按下时frame坐标：", frameLeft + "/" + frameTop + "/" + frameRight + "/" + frameBottom);

                break;
            case MotionEvent.ACTION_MOVE:
                float moveDot = event.getX();     //移动中的触摸点
                float moveDotY = event.getY();
                //                if (moveDotY < viewHeight) {    //滑动中如果Y超过view的范围，不再响应
                //确定FrameRect层的边界
                if (Math.abs(moveDot - downDot) > 5 && moveDot > leftBound && moveDot < rightBound) {
                    //确定frameRect的边界
                    toLeft = moveDot - downDot < 0; //小于0左划，大于0 右划
                    LogUtils.e("是否左移？：", String.valueOf(toLeft));
                    frameLeft = toLeft ? moveDot - leftBound : 0;
                    frameRight = toLeft ? viewWidth : moveDot + leftBound;
                    frameTop = 0;
                    frameBottom = viewHeight;
                    LogUtils.e("frame坐标：", frameLeft + "/" + frameTop + "/" + frameRight + "/" + frameBottom);
                }

                //确定上层滑动圆圈的圆心坐标
                if (moveDot <= leftBound) {   //左侧防越界
                    xCooidinate = leftBound;
                } else if (moveDot >= rightBound) {    //右侧防越界
                    xCooidinate = rightBound;
                } else {
                    xCooidinate = (int) moveDot;
                }

                if (isChecked && !toLeft) {   //滑动中根据左划右划动态更新选中状态，从而更新最底层背景
                    isChecked = false;
                } else if ((!isChecked) && toLeft) {
                    isChecked = true;
                }
                //                }
                break;
            case MotionEvent.ACTION_UP:
                float upDot = event.getX();   //获取抬手时的触摸点
                LogUtils.e("抬起时的触摸点", upDot + "");
                if (Math.abs(downDot - upDot) < 5) {   //移动距离较小时认为是点击，数值10根据情况可更改
                    isChecked = !isChecked;
                } else {
                    isChecked = xCooidinate > viewWidth / 2;    //手指滑动时即时动态更新背景色
                }

                //抬手时要更新上层滑块x坐标.如果是选中状态，坐标点靠右;未选中坐标点靠左
                xCooidinate = isChecked ? (viewWidth - viewHeight / 2) : viewHeight / 2;  //初始化时根据选中状态确定坐标点

                //抬手的时候处理RectFrame的边界,全都置为0,直接显示底层的背景
                frameLeft = frameRight = frameTop = frameBottom = 0;


                //TODO 只有抬手的时候，才对外暴露监听器
                if (changeListener != null) {
                    changeListener.onChange(isChecked);
                }
                break;
        }

        invalidate();   //请求重绘制
        return true;
    }


    /**
     * CustomSwitchButton的状态监听器
     */
    public interface onCheckedChangeListener {
        void onChange(boolean isChecked);
    }

    public void setOnCheckedChangeListener(onCheckedChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    /**
     * 由外部动态设置选中状态
     */
    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
        invalidate();
    }
}
