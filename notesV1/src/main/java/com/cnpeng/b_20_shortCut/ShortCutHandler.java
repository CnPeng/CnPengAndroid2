package com.cnpeng.b_20_shortCut;

import android.content.Intent;
import android.view.View;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/8/15:下午8:37
 * <p>
 * 说明：创建快捷方式时使用的方法
 */

public class ShortCutHandler {
    private final ShortCutActivity shortCutActivity;
    private final Intent           shortCutIntent;

    public ShortCutHandler(ShortCutActivity shortCutActivity) {
        this.shortCutActivity = shortCutActivity;

        //快捷方式跳转到对应页面时使用的intent
        //不要用ACTION_MAIN，用MAIN时极易出现快捷方式的图标和APP图标一致的情况，且无法跳转到指定页面
        shortCutIntent = new Intent(Intent.ACTION_VIEW);
        shortCutIntent.addCategory(Intent.CATEGORY_LAUNCHER);   //不加category页面打不开
        //shortCutIntent.setClassName(getApplicationContext(), tClass.getName());// 效果同上面的setClass
        shortCutIntent.setClass(shortCutActivity, shortCutActivity.getClass());   //第一个是上下文，第二个是快捷方式对应的页面
        shortCutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //shortCutIntent.putExtra("whereIsFrom", "ShortCutActivity");  //该数据实际测试并不会被传递
    }

    /**
     * 使用广播的形式创建快捷方式.
     * <p>
     * 该方式在7.0之后废弃无效
     */
    public void createShortCut(View view) {
        ShortCutUtils.createShortCut(shortCutActivity, shortCutActivity.getClass(), shortCutIntent);
    }

    public void deleteShortCut(View view) {
        ShortCutUtils.deleteShortCut(shortCutActivity, shortCutActivity.getClass(), shortCutIntent);
    }
}
