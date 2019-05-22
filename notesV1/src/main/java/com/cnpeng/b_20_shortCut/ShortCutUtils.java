package com.cnpeng.b_20_shortCut;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.widget.Toast;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/8/17:下午3:55
 * <p>
 * 说明：用广播的形式创建快捷方式，并实现快捷方式的 删除 和 修改
 * <p>
 * TODO 修改快捷方式、非原生系统的launcher如何判断快捷方式是否存在
 * TODO 8.0之后快捷方式的实现方式
 */

public class ShortCutUtils {


    /**
     * 对外暴露的创建快捷方式的方法
     *
     * @param context 上下文
     * @param tClass  为哪个activity 页面创建的快捷方式
     */
    public static void createShortCut(final Context context, Class<?> tClass, Intent shortCutIntent) {
        if (!isShortCutExist(context)) {     //如果没有则创建
            createShortCut_broadCast(context, tClass, shortCutIntent);
        } else {        //如果已经有了，则给出提示
            Toast.makeText(context, "快捷方式已经创建", Toast.LENGTH_SHORT).show();
        }

        //FIXME ：本来考虑创建成功之后延时一小会之后给出一个创建成功的提示，但是实际测试并不好使，等了好长时间之后也没有对应的提示，
        //FIXME: 没想明白为什么，是不是因为使用Timer定时器的时候没用Handler？
    }


    /**
     * 创建快捷方式的核心代码
     */
    private static void createShortCut_broadCast(Context context, Class<?> tClass, Intent shortCutIntent) {


        //发送要创建快捷方式时需要的intent
        Intent broadCastIntent = new Intent();
        //broadCastIntent.setAction(Intent.ACTION_CREATE_SHORTCUT); //这个不好使！！！
        broadCastIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");  //
        broadCastIntent.putExtra("duplicate", false);                                //不允许重复创建（有时会不生效）
        broadCastIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortCutIntent);
        //将快捷方式对应的页面与APP绑定，实现卸载APP时同步删除快捷方式
        broadCastIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "广播方式的快捷方式");     //快捷方式名称
        Parcelable iconRes = Intent.ShortcutIconResource.fromContext(context, R.drawable.daomeixiong);
        broadCastIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
        context.sendBroadcast(broadCastIntent);
    }

    /**
     * 快捷方式的信息存储在 data/data/com.android.launcherX 目录下的 databases 数据库 的 favorites 表中，以 contentprovider 的形式
     * 对外暴露，我们获取的时候就需要使用 ContentResolver 去获取并解析。
     * <p>
     * 关于 databases 的上级目录，在 android 原生系统中 2.2(对应API8 ) 之前是 com.android.launcher;4.4 （API 19 ）是launcher2。
     * 19之后是launcher3。而在非原生系统中，则不确定，具体需要根据 launcher 界面的包名来确定。
     */
    private static boolean isShortCutExist(Context context) {
        String authority;        //Uri的主机名，从上层源码packages/apps/Launcher2/AndroidManifest.xml 中的provider 节点查看
        boolean isShortCutExist = false;    //是否有快捷方式，默认没有
        String shecema = "content://";     //Uri的schema 
        String table = "/favorites";       //存储快捷键信息的表。在确定某个provider对应的表时可以从provider的CRUD方法中找线索，也可以直接网上搜索

        int APIVersion = getAPIVersion();
        if (APIVersion < 8) {   //2.2之前是launcher,目前的APP基本已经不再支持2.2之前的版本，所以该判断实际可以废弃
            authority = "com.android.launcher.settings";
        } else if (APIVersion < 19) {
            authority = "com.android.launcher2.settings";
        } else {     //19以及之后的版本中在上层源码中 luancher2 和 launcher3 并存，但是在数据库文件中 只有 com.android.launcher3 包下才有数据。截止到API 25 
            authority = "com.android.launcher3.settings";
        }

        Uri uri = Uri.parse(shecema + authority + table);

        ContentResolver contentResolver = context.getContentResolver();

        //通过sqlite3查看favorites 表中的字段，然后判断是否有该title，如果有表示已经创建。ATTENTION 此处不能只判title,可能有重复，最好再加上intent，icnoSource
        Cursor cursor = contentResolver.query(uri, new String[]{"title"}, "title=?", new String[]{"广播方式的快捷方式"}, null);

        if (null != cursor && cursor.getCount() > 0) {  //不为空据
            isShortCutExist = true;
            cursor.close();
        }

        return isShortCutExist;
    }

    /**
     * 获取当前手机系统的API版本号
     */
    public static int getAPIVersion() {
        return Build.VERSION.SDK_INT;
    }


    /**
     * 删除快捷方式
     * <p>
     * FIXME 并不好使，无法删除，即便是原生系统也删除不了，为啥？？？？？
     */
    public static void deleteShortCut(Context context, Class<?> tClass, Intent shortCutIntent) {
        if (null != shortCutIntent) {
            Intent deleteIntent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
            deleteIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "广播方式的快捷方式");     //快捷方式名称
            deleteIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortCutIntent);   //删除时的shortCutIntent必须和创建时的一致,否则无法删除
            deleteIntent.putExtra("duplicate", true);
            context.sendBroadcast(deleteIntent);
        }
    }
}
