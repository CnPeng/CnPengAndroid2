package com.cnpeng.b_16_customDialog;


import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.CustomDialogAntBinding;
import com.cnpeng.utils.LogUtils;


/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/7/25:上午11:58
 * <p>
 * 说明：dialog的通用类，展示和关闭dialog只通过该类就可以实现，避免每次都写一堆控制大小，宽高、背景的代码
 * <p>
 * 1、标题、确认按钮、取消按钮默认都不展示，只有调用相应的设置方法之后才会展示
 * 2、只有传入的点击监听不为null时，点击按钮的同时会关闭dialog
 * <p>
 * 当前已经支持：更改标题，更改消息，更改按钮描述及其点击事件，更改主体内容的背景，更改外层灰色背景的灰度比率，更改尺寸，传入颜色值和角度值自动生成背景，
 * 更改字号，更改字体颜色
 * <p>
 * 还需增加：，更改标题背景，，增加对LV条目的支持，增加最大高度的控制,
 */

public class CustomAlertDialog {

    private final Context                context;
    private final AlertDialog            dialog;             //dialog对象
    private       View                   dialogView;         //dialogView 
    private       CustomDialogAntBinding dialogBinding;

    public CustomAlertDialog(Context context) {
        this.context = context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        dialog = builder.create();

        initDialogView();
    }

    private void initDialogView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        dialogBinding = DataBindingUtil.inflate(inflater, R.layout.custom_dialog_ant, null, false);
        dialogView = dialogBinding.getRoot();
        dialog.setView(dialogView);     //设置view
        setLayoutByPx(0, 0);          //设置宽高
        setBackGroundDrawableResource(0);
        setDimAmount(0.15f);
    }

    /**
     * 展示dialog
     */
    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 关闭dialog
     */
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 返回dialog的view对象
     */
    public View getDialogView() {
        return dialogView;
    }

    /**
     * 返回dialog对象
     */
    public Dialog getDialogObj() {
        return dialog;
    }

    /**
     * 设置dialog的宽高信息,单位px
     * 注意：不推荐用该方法，由于标注是按照IOS标准标的像素，如果直接传递像素，在安卓设备上会产生较严重的偏差
     */
    public void setLayoutByPx(final int width, final int height) {
        final Window window = dialog.getWindow();
        if (null != window) {
            Display display = window.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            final int windowWidth = metrics.widthPixels;

            if (height != 0) {  //如果不为0，则指定LL的高度填充父窗体，也就是填满指定的高度值,避免出现内容小于指定高度时，内容底部显示白色块
                LinearLayout ll_root_dialog = dialogBinding.llRootAntDialog;
                ViewGroup.LayoutParams layoutParams = ll_root_dialog.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                ll_root_dialog.setLayoutParams(layoutParams);
                ll_root_dialog.setGravity(Gravity.CENTER);
            }

            dialogView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                           int oldRight, int oldBottom) {
                    int finalWidth = width <= 0 ? (int) (windowWidth * 0.76) : width;
                    int finalHeight = height <= 0 ? FrameLayout.LayoutParams.WRAP_CONTENT : width;
                    LogUtils.e("宽高", finalWidth + "/" + finalHeight);
                    window.setLayout(finalWidth, finalHeight);
                    window.setGravity(Gravity.CENTER);

                }
            });
        }
    }

    /**
     * 设置dialog的宽高信息，单位dp
     * 推荐使用这种，先将标注图上的px 按照2：1 转成dp,然后调用该方法
     */
    public void setLayoutByDp(final int width, final int height) {
        final Window window = dialog.getWindow();
        if (null != window) {
            Display display = window.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            final int windowWidth = metrics.widthPixels;

            if (height != 0) {      //如果不为0，则指定LL的高度填充父窗体，也就是填满指定的高度值,避免出现内容小于指定高度时，内容底部显示白色块
                LinearLayout ll_root_dialog = dialogBinding.llRootAntDialog;
                ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) ll_root_dialog.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                ll_root_dialog.setLayoutParams(layoutParams);
                ll_root_dialog.setGravity(Gravity.CENTER);
            }

            dialogView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                           int oldRight, int oldBottom) {
                    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                    int widthPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, metrics);
                    int heightPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, metrics);
                    int finalWidth = widthPx <= 0 ? (int) (windowWidth * 0.76) : widthPx;
                    int finalHeight = heightPx <= 0 ? FrameLayout.LayoutParams.WRAP_CONTENT : heightPx;
                    LogUtils.e("宽高", widthPx + "/" + heightPx);
                    window.setLayout(finalWidth, finalHeight);
                }
            });
        }
    }

    /**
     * 设置dialog的宽高信息，无单位
     * 也推荐使用这种，按照比率设置宽高
     *
     * @param widthRate  内容区域占屏幕宽度的多少，取值（0，1]
     * @param heightRate 内容区域占屏幕高度的多少,取值 （0，1]
     */
    public void setLayoutByRate(final float widthRate, final float heightRate) {
        final Window window = dialog.getWindow();
        if (null != window) {
            Display display = window.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            final int windowWidth = metrics.widthPixels;
            final int windowHeight = metrics.heightPixels;

            if (heightRate != 0) {      //如果不为0，则指定LL的高度填充父窗体，也就是填满指定的高度值,避免出现内容小于指定高度时，内容底部显示白色块
                LinearLayout ll_root_dialog = dialogBinding.llRootAntDialog;
                ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) ll_root_dialog.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                ll_root_dialog.setLayoutParams(layoutParams);
                ll_root_dialog.setGravity(Gravity.CENTER);
            }

            dialogView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                           int oldRight, int oldBottom) {
                    int finalWidth = widthRate <= 0 ? (int) (windowWidth * 0.76) : (int) (windowWidth * widthRate);
                    int finalHeight = heightRate <= 0 ? FrameLayout.LayoutParams.WRAP_CONTENT : (int) (windowHeight *
                            heightRate);
                    LogUtils.e("宽高", finalWidth + "/" + finalHeight);
                    window.setLayout(finalWidth, finalHeight);
                }
            });
        }
    }

    /**
     * 设置dialog的背景--传入资源id
     */
    public void setBackGroundDrawableResource(int drawableResId) {
        Window window = dialog.getWindow();
        if (null != window) {
            if (0 == drawableResId) {
                drawableResId = R.drawable.shape_bk_rect_cornor_white;
            }
            window.setBackgroundDrawableResource(drawableResId);
        }
    }

    /**
     * 设置背景图--传入drawable对象
     */
    public void setBackGroundDrawable(Drawable drawable) {
        Window window = dialog.getWindow();
        if (null != window) {
            if (null == drawable) {
                drawable = context.getResources().getDrawable(R.drawable.shape_bk_rect_cornor_white);
            }
            window.setBackgroundDrawable(drawable);
        }
    }

    /**
     * 设置背景图--根据传入的color值生成对应填充色的圆角背景图
     *
     * @param colorInt      色值
     * @param conorRadiusPx 圆角半径,单位PX
     */
    public void setBackGroundDrawable(
            @ColorInt
                    int colorInt, int conorRadiusPx) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(colorInt);
        drawable.setCornerRadius(conorRadiusPx);
        drawable.setShape(GradientDrawable.RECTANGLE);

        Window window = dialog.getWindow();
        if (null != window) {
            window.setBackgroundDrawable(drawable);
        }

    }

    /**
     * 设置确定按钮的问题及其点击事件,
     * 传入的事件监听为null时，会关闭dialog
     */
    public void setPositiveButton(String des, final AntDialogClickListener clickListener) {
        dialogBinding.setIsConfirmBtShow(true);
        dialogBinding.tvConfirmBT.setText(des);
        dialogBinding.tvConfirmBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clickListener) {
                    clickListener.onClick(v);
                }
                dismissDialog();
            }
        });
    }

    public void setPositiveButton(int strResId, final AntDialogClickListener clickListener) {
        dialogBinding.setIsConfirmBtShow(true);
        dialogBinding.tvConfirmBT.setText(context.getResources().getString(strResId));
        dialogBinding.tvConfirmBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clickListener) {
                    clickListener.onClick(v);
                }
                dismissDialog();
            }
        });
    }

    /**
     * 取消按钮的点击事件
     */
    public void setNegativeButton(String des, final AntDialogClickListener clickListener) {
        dialogBinding.setIsCancleBtShow(true);
        dialogBinding.tvCancleBT.setText(des);
        dialogBinding.tvCancleBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clickListener) {
                    clickListener.onClick(v);
                }
                dismissDialog();
            }
        });
    }

    public void setNegativeButton(int strResId, final AntDialogClickListener clickListener) {
        dialogBinding.setIsCancleBtShow(true);
        dialogBinding.tvCancleBT.setText(context.getResources().getString(strResId));
        dialogBinding.tvCancleBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != clickListener) {
                    clickListener.onClick(v);
                }
                dismissDialog();
            }
        });
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        dialogBinding.setIsTitleShow(true);
        dialogBinding.tvTitle.setText(title);
    }

    public void setTitle(int strResId) {
        dialogBinding.setIsTitleShow(true);
        dialogBinding.tvTitle.setText(context.getResources().getString(strResId));
    }

    /**
     * 设置消息体
     */
    public void setMessage(String message) {
        dialogBinding.setIsMsgShow(true);
        dialogBinding.tvMsg.setText(message);
    }

    public void setMessage(int strResId) {
        dialogBinding.setIsMsgShow(true);
        dialogBinding.tvMsg.setText(context.getResources().getString(strResId));
    }

    /**
     * 更改消息主体的颜色值
     */
    public void setMessageTextColor(
            @ColorInt
                    int textColor) {
        dialogBinding.tvMsg.setTextColor(textColor);
    }

    public void setMessageTextColor(ColorStateList colors) {
        if (colors != null) {
            dialogBinding.tvMsg.setTextColor(colors);
        }
    }


    /**
     * 设置确认按钮的文字颜色
     */
    public void setPositiveButtonTextColor(
            @ColorInt
                    int color) {
        dialogBinding.tvConfirmBT.setTextColor(color);
    }

    public void setPositiveButtonTextColor(ColorStateList colorStateList) {
        dialogBinding.tvConfirmBT.setTextColor(colorStateList);
    }

    /**
     * 设置取消按钮的字体颜色
     */
    public void setNegativeButtonTextColor(
            @ColorInt
                    int color) {
        dialogBinding.tvCancleBT.setTextColor(color);
    }

    public void setNegativeButtonTextColor(ColorStateList colorStateList) {
        dialogBinding.tvCancleBT.setTextColor(colorStateList);
    }

    /**
     * 更改消息主体文字的大小
     */
    public void setMessageTextSize(int sizePx) {
        if (sizePx <= 0) {
            sizePx = 14;
        }
        dialogBinding.tvMsg.setTextSize(sizePx);
    }

    /**
     * 修改Dialog阴影区域的灰度百分比
     * <p>
     * 取值 0-1.
     */
    public void setDimAmount(float rate) {
        Window window = dialog.getWindow();
        if (null != window) {
            if (rate < 0) {
                rate = 0;
            } else if (rate > 1) {
                rate = 1;
            }
            window.setDimAmount(rate);
        }
    }

    /**
     * 点击非内容区域是否可以关闭
     */
    public void setCancelable(boolean bool) {
        dialog.setCancelable(bool);
    }

    /**
     * 对外暴露点击事件的自定义接口
     */
    public interface AntDialogClickListener {
        void onClick(View view);
    }
}