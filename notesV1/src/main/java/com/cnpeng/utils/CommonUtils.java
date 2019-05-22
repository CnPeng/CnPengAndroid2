package com.cnpeng.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

/**
 * Created by CnPeng on 2017/1/6.
 * <p>
 * 通用工具类
 */

public class CommonUtils {

    /**
     * 自定义开启Activity的方法
     *
     * @param context       上下文
     * @param activityClass 要开启的activity.class
     */
    public static void mStartActivity(Context context, Class activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }

    /**
     * 自定义开启Activity的方法，携带数据
     *
     * @param context       上下文
     * @param activityClass 要开启的activity.class
     * @param bundle        要传递的数据
     * @param key           传递数据时使用的键名
     */
    public static void mStartActivity(Context context, Class activityClass, Bundle bundle, String key) {
        Intent intent = new Intent(context, activityClass);
        intent.putExtra(key, bundle);
        context.startActivity(intent);
    }

    //    /**
    //     * 自定义开启Activity的方法，携带数据
    //     *
    //     * @param activity      当前activity类
    //     * @param activityClass 要开启的activity.class
    //     * @param bundle        要传递的数据
    //     * @param key           传递数据时使用的键名
    //     * @param requestCode   请求码
    //     */
    //    public static void mStartActivityForResult(Activity activity, Class activityClass, Bundle bundle, String key,
    //                                               int requestCode) {
    //        Intent intent = new Intent(activity, activityClass);
    //        intent.putExtra(key, bundle);
    //        activity.startActivityForResult(intent,requestCode);
    //    }


    //    /**
    //     * 备选颜色的数组
    //     */
    //    public static int[] colors  = {R.color.blue_14a5ef, R.color.green_02bd89, R.color.red_f6584f, R.color
    //            .orange_eb9e21};
    //    /**
    //     * 备选颜色的数组2
    //     */
    //    public static int[] colors2 = {R.color.blue_14a5ef, R.color.green_02bd89, R.color.red_f6584f, R.color
    //            .orange_eb9e21, R.color.blue_14a5ef, R.color.orange_eb9e21, R.color.red_f6584f};

    /**
     * 获取完全随机的颜色
     *
     * @return int行的rgb颜色值
     */
    public static int randomColor() {
        Random random = new Random();
        int red = random.nextInt(220);//0-190	,如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        int green = random.nextInt(220);//0-190, 值越大，生成的颜色越鲜艳
        int blue = random.nextInt(220);//0-190
        return Color.rgb(red, green, blue);//使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
    }


    /**
     * 获取指定颜色中的随机色
     *
     * @return int型颜色值的ID
     */
    public static int getRandomColor(int[] colors) {
        //        int[] colors = {R.color.blue_14a5ef, R.color.red_f6584f, R.color.green_02bd89, R.color.orange_ea9e21};
        Random random = new Random();
        int position = random.nextInt(colors.length);     //生成<4 的随机数, 不包含4 ,具体取值 0-3

        return colors[position];    //返回指定颜色中的随机色的ID
    }

    /**
     * 生成圆角图片--有边线无填充色，对应的xml中的shape标签 (代码创建shape)
     *
     * @param radiu 单位是px
     * @param rgb   需要是 ContextCompat.getColor（）得到的int
     * @return 返回drawable类型的图片
     */
    public static Drawable generateStrokeDrawable(int rgb, float radiu) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);//设置为矩形，默认就是矩形
        //        drawable.setColor(rgb);  //传入的是一个int 类型的RGB颜色值,这是设置背景颜色
        drawable.setCornerRadius(radiu);
        drawable.setStroke(2, rgb);     //边框
        return drawable;
    }

    /**
     * 生成圆角图片，只有填充色，没有边线
     */
    public static Drawable generateSolid_RadiusDrawable(int solidColor, float radiu) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);//设置为矩形，默认就是矩形
        drawable.setColor(solidColor);  //传入的是一个int 类型的RGB颜色值,这是设置背景颜色
        drawable.setCornerRadius(radiu);
        return drawable;
    }

    /**
     * 自定义方法生成左侧矩形右侧半圆的图片，对应的xml中的shape标签
     *
     * @param rgb    int型颜色
     * @param radius 半径的数组 8个参数 如{8,8,16,16,8,8,16,16},两个一组,按顺序分别控制 左上角,右上角,右下角,左下角的弧度
     * @return 返回drawable类型的图片
     */
    public static Drawable generateHalfCircleDrawable(int rgb, float[] radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);//设置为矩形，默认就是矩形
        //        drawable.setColor(rgb);  //传入的是一个int 类型的RGB颜色值,这是设置背景颜色
        drawable.setCornerRadii(radius);
        drawable.setStroke(1, rgb);     //边框
        return drawable;
    }

    /**
     * 自定义方法生成左侧矩形右侧半圆的并带有填充色的¬图片，对应的xml中的shape标签
     *
     * @param rgb    int型颜色
     * @param radius 半径的数组
     * @return 返回drawable类型的图片
     */
    public static Drawable generateHalfCircleWithSolidDrawable(int rgb, float[] radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);//设置为矩形，默认就是矩形
        drawable.setColor(rgb);  //传入的是一个int 类型的RGB颜色值,这是设置背景颜色
        drawable.setCornerRadii(radius);
        //        drawable.setStroke(1, rgb);     //边框
        return drawable;
    }


    //    /**
    //     * 自定义方法生成圆形的图片，对应的xml中的shape标签
    //     *
    //     * @param rgb   int型颜色
    //     * @param radiu 半径
    //     * @return 返回drawable类型的图片
    //     */
    //    public static Drawable generateCircleDrawable(int rgb, float radiu) {
    //        GradientDrawable drawable = new GradientDrawable();
    //        drawable.setShape(GradientDrawable.OVAL);//椭圆
    //        drawable.setColor(ContextCompat.getColor(AntLinkApplication.getAppContext(), R.color.background_color));
    //        //传入的是一个int 类型的RGB颜色值,这是设置背景颜色
    //        drawable.setCornerRadius(radiu);
    //        drawable.setStroke(1, rgb);     //边框
    //        return drawable;
    //    }
    //
    //    public static Drawable generateCircleDrawable() {
    //        GradientDrawable drawable = new GradientDrawable();
    //        drawable.setShape(GradientDrawable.OVAL);//椭圆
    //        drawable.setSize(32, 32);
    //        drawable.setColor(ContextCompat.getColor(AntLinkApplication.getAppContext(), R.color.major_ffc000));
    //        return drawable;
    //    }

    /**
     * Set集合转换成字符串
     *
     * @param list 需要转换的list集合
     * @return 返回字符串
     */
    public static String List2String(List<String> list) {
        String result = "";//最终的拼接结果,初始化为""

        if (list == null) {
            return result;
        }
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            if (i == 0) {       //如果是第一个元素，不拼接逗号
                result = str;
            } else {
                result = result + "," + str;
            }
            //            Log.e("abd", "拼接集合元素：" + result);
        }
        return result;
    }

    //每个字符都加上双引号,因为数据库查询时,如果是字符串,必须加双引号,否则查询不出来
    public static String List2StringStr(List<String> list) {
        String result = "";//最终的拼接结果,初始化为""

        if (list == null) {
            return result;
        }
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            if (i == 0) {       //如果是第一个元素，不拼接逗号
                result = "\"" + str + "\"";
            } else {
                result = result + "," + "\"" + str + "\"";
            }
            //            Log.e("abd", "拼接集合元素：" + result);
        }
        return result;
    }

    /**
     * sqlite 用where in查询 排序
     *
     * @param list       需要排序的list
     * @param columnName 排序的列的名字
     * @param orderType  升序降序
     */
    public static String ListForQuery(List<String> list, String columnName, String orderType) {
        String result = "";//最终的拼接结果,初始化为""
        if (list == null) {
            return result;
        }
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            String orderStr = columnName + "=" + str + " " + orderType;
            if (i == 0) {       //如果是第一个元素，不拼接逗号
                result = orderStr;
            } else {
                result = result + "," + orderStr;
            }
        }
        return result;
    }

    public static String ListForQueryStr(List<String> list, String columnName, String orderType) {
        String result = "";//最终的拼接结果,初始化为""
        if (list == null) {
            return result;
        }
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            String orderStr = columnName + "=\"" + str + "\" " + orderType;
            if (i == 0) {       //如果是第一个元素，不拼接逗号
                result = orderStr;
            } else {
                result = result + "," + orderStr;
            }
        }
        return result;
    }

    /**
     * 字符串转集合 将从服务器获取到的个性标签的字符串通过正则切割转成数组，然后转成集合
     *
     * @param str 从服务器获取到的个性标签组成的字符串
     * @return 已有的个性标签的List集合
     */
    public static List<String> String2List(String str) {
        List<String> list = new ArrayList<>();  //预定义集合
        String[] lables = str.split(",");       //以逗号为正则进行切割得到字符串数组
        Collections.addAll(list, lables);
        return list;
    }

    /**
     * dp转PX
     *
     * @param context 上下文
     * @param dpValue 要转换的dp
     * @return 转换后的px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * PX 转dp
     *
     * @param context 上下文
     * @param pxValue 要转换的px
     * @return 转换后的dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将毫秒值转成 年月日时分秒的格式
     *
     * @param date 毫秒值
     * @return 格式化后的时间字符串
     */
    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH:mm：ss");
        if (date != null) {
            return format.format(date);
        } else {    //TODO 在获取文档的时候，没有获取时间
            return "";
        }
    }

    /**
     * 170627 获取状态栏的高度 单位PX
     *
     * @param context 传入上下文
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId != 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    //    /**
    //     * 获取设备id
    //     */
    //    public static String getDeviceId() {
    //        return Settings.Secure.getString(AntLinkApplication.getAppContext().getContentResolver(), Settings.Secure
    //                .ANDROID_ID);
    //    }

    /**
     * 实现沉浸式状态栏，适用于5.0及以上版本
     * 想让哪一部分的背景延伸到状态栏，就在其节点中设置 fitSystemWindow = true。 clipToPadding=true
     *
     * @param activity 要设置为沉浸式的界面所在的activity
     * @param colorId  状态栏需要展示的颜色（如果有标题栏传入标题栏的颜色，没有标题栏则用透明色--传0）
     */
    public static void setImmersionStatusBar(Activity activity, int colorId) {
        //状态栏沉浸式的实现
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 全透明实现(清除原有的TRANSLUCENT flag，设置ui全屏，增加更改状态栏的flag,更改状态栏颜色)
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
                    .SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //手动设置状态栏的颜色为标题栏的颜色
            int statusBarColorID = android.R.color.transparent;
            if (colorId != 0) {
                statusBarColorID = colorId;
            }
            window.setStatusBarColor(activity.getResources().getColor(statusBarColorID));
        }
    }

    /**
     * 沉浸式状态栏的实现，适用于4.4以上版本
     */
    public static void setImmersionStatusBar2(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams
                    .FLAG_TRANSLUCENT_NAVIGATION);
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams
                    .FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;

        //方式2
        // WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * 指定字符串中某部分带有下划线且具有点击事件
     *
     * @param str             源字符串
     * @param start           从哪开始设置下划线或点击事件
     * @param end             到哪结束设置下划线或点击事件
     * @param colorInt        下划线及其对应文字的颜色
     * @param isShowUnderLine 是否展示下划线
     */
    public static SpannableString getSpannableStrWithUnderLine(String str, int start, int end, int colorInt,
                                                               boolean isShowUnderLine) {
        SpannableString spannableString = new SpannableString(str);

        if (isShowUnderLine) {
            spannableString.setSpan(new UnderlineSpan(), start, end, SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        spannableString.setSpan(new ForegroundColorSpan(colorInt), start, end, SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /**
     * 获取状态栏高度
     */
    //    public static int getStatusBarHeight() {
    //        Class<?> c;
    //        Object obj;
    //        Field field;
    //        int x, sbar = 0;
    //        try {
    //            c = Class.forName("com.android.internal.R$dimen");
    //            obj = c.newInstance();
    //            field = c.getField("status_bar_height");
    //            x = Integer.parseInt(field.get(obj).toString());
    //            sbar = AntLinkApplication.getAppContext().getResources().getDimensionPixelSize(x);
    //        } catch (Exception e1) {
    //            e1.printStackTrace();
    //        }
    //        return sbar;
    //    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param data  需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(Double data, int scale) {
        if (scale >= 0) {
            BigDecimal b = null == data ? new BigDecimal("0.0") : new BigDecimal(Double.toString(data));
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else {
            return Math.round(data);
        }
    }

//    /**
//     * 作者：CnPeng
//     * 时间：2018/7/9 下午8:56
//     * 功用：绘制一个shape图片
//     * 说明：
//     *
//     * @param shape          {@link GradientDrawable.RECTANGLE}等
//     * @param solidColorRes  填充线的颜色，R.color.xxx
//     * @param radius         半径的数组 8个参数 如{8,8,16,16,8,8,16,16},两个一组,按顺序分别控制 左上角,右上角,右下角,左下角的弧度
//     * @param strokeWidth    边线的宽度
//     * @param strokeColorRes 边线的颜色 R.color.xxx
//     */
//    public static Drawable getShapeDrawable(@ColorRes int shape, int solidColorRes, float[] radius, int strokeWidth, @ColorRes int strokeColorRes) {
//        Context context = AntLinkApplication.getAppContext();
//        GradientDrawable drawable = new GradientDrawable();
//        drawable.setShape(shape);
//        drawable.setColor(ContextCompat.getColor(context, solidColorRes));
//        drawable.setCornerRadii(radius);
//        drawable.setStroke(strokeWidth, ContextCompat.getColor(context, strokeColorRes));
//        return drawable;
//    }

    /**
     * 作者：CnPeng
     * 时间：2018/7/19 下午9:04
     * 功用：获取不带圆角的LayerDrawable——即 xml 中的layerList
     * 说明：
     *
     * @param bgColorInt 底层/背景颜色色值。格式 0xfff000。非该格式的可以使用Color.parseColor("#ffc000")、或 getResources().getColor(colorRes)进行格式化
     * @param fgColorInt 上层/前景颜色色值
     * @param layerInset 上层图片相对于下层图片的缩进。该数组必须有四个元素！
     */
    public static LayerDrawable getRectLayerDrawable(@ColorInt int bgColorInt, @ColorInt int fgColorInt, int[] layerInset) {

        if (null == layerInset) {
            layerInset = new int[]{0, 0, 0, 0};
        }

        // TODO: CnPeng 2018/7/19 下午9:18 后期补充数组元素不足四个或者超过四个的处理

        RectShape rectShape = new RectShape();
        ShapeDrawable shapeDrawableBG = new ShapeDrawable(rectShape);
        shapeDrawableBG.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawableBG.getPaint().setColor(bgColorInt);

        ShapeDrawable shapeDrawableFG = new ShapeDrawable(rectShape);
        shapeDrawableFG.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawableFG.getPaint().setColor(fgColorInt);

        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{shapeDrawableBG, shapeDrawableFG});
        layerDrawable.setLayerInset(1, layerInset[0], layerInset[1], layerInset[2], layerInset[3]);

        return layerDrawable;
    }

    /**
     * 作者：CnPeng
     * 时间：2018/7/19 下午9:30
     * 功用：获取StateListDrawable
     * 说明：
     * 当给View、TextView、ImageView、ViewGroup等类型的默认没有按下事件的控件添加StateListDrawable时，控件需要设置上click事件，否则按下效果不起作用
     *
     * @param statedColorInt 格式：0xffcccccc
     */
    public static StateListDrawable getStateListDrawable(@ColorInt int statedColorInt, @ColorInt int normalColorInt) {

        StateListDrawable stateListDrawable = new StateListDrawable();

        int[] stated = new int[]{android.R.attr.state_pressed};
        Drawable statedDrawable = getShapeDrawable(statedColorInt);
        stateListDrawable.addState(stated, statedDrawable);

        int[] stateNormal = new int[]{};
        Drawable normalDrawable = getShapeDrawable(normalColorInt);
        stateListDrawable.addState(stateNormal, normalDrawable);

        return stateListDrawable;
    }

    /**
     * 作者：CnPeng
     * 时间：2018/7/19 下午9:37
     * 功用：根据传入的颜色值获取一个直角矩形shape
     * 说明： // TODO: CnPeng 2018/7/19 下午9:40 补充一下 RoundRectShape的创建，使用它创建圆角图片
     */
    public static ShapeDrawable getShapeDrawable(@ColorInt int colorInt) {
        RectShape rectShape = new RectShape();
        ShapeDrawable shapeDrawable = new ShapeDrawable(rectShape);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setColor(colorInt);
        return shapeDrawable;
    }

    public static int getMax(int a, int b) {
        return a > b ? a : b;
    }

    /**
     * 利用android提供的原生工具类实现转换
     */
    public int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
                .getDisplayMetrics());
    }

}
