package com.cnpeng.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/12/5:下午2:11
 * <p>
 * 说明：动态权限申请工具类 < 当前只在Activity中使用过，无异常。FM 中暂未测试 >
 * <p>
 * 注意，处理权限被拒绝有两种方式，
 * （1）一种是在拒绝之后直接给出提示，提示用户拒绝之后将不能使用XX功能，如果是这种方式的话，就使用一个参数的构造，然后直接判断权限是否全都被允许，
 * 如果全都被允许直接执行相关事件；如果有权限未被允许则申请权限。
 * （2）一种是拒绝之后不给提示，然后下次进入时先检测是否有被拒绝的，如果有则展示dialog，这种情况就需要调用两个参数的构造，并调用
 * showRequestReasonOrHandlePermissionEvent()方法，由该方法决定是否需要展示dialog，如果不需要，则执行外部通过PermissionGrantedFactory
 * 传递的事件。
 */

public class DynamicPermissionTool {
    private Context                  context;
    private PermissionGrantedFactory permissionGrantedFactory;

    public String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public String[] deniedHints = {"没有相机权限将不能拍照", "没有存储设备的读写权限将不能存储照片到本地"};

    /**
     * 如果不需要判断是否之前被拒绝过，调用该构造
     */
    public DynamicPermissionTool(Context context) {
        this.context = context;
    }

    /**
     * 如果需要判断之前是否被拒绝过，调用该构造，
     *
     * @param context                  context
     * @param permissionGrantedFactory permissionGrantedFactory
     */
    public DynamicPermissionTool(Context context, PermissionGrantedFactory permissionGrantedFactory) {
        this.context = context;
        this.permissionGrantedFactory = permissionGrantedFactory;
    }

    /**
     * 检查单个权限是否已经被允许
     *
     * @param permission 要申请的权限
     */
    public boolean checkCurPermissionStatus(String permission) {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, permission);
    }

    /**
     * 检查一组权限的授权状态。
     *
     * @param permissions 权限数组
     * @return 权限的状态数组
     */
    public boolean[] checkCurPermissionsStatus(String[] permissions) {
        if (null != permissions && permissions.length > 0) {
            boolean[] permissionsStatus = new boolean[permissions.length];

            for (int i = 0; i < permissions.length; i++) {
                boolean permissionStatus = checkCurPermissionStatus(permissions[i]);
                permissionsStatus[i] = permissionStatus;
            }
            return permissionsStatus;
        } else {
            throw new IllegalArgumentException("参数不能为空，且必须有元素");
        }
    }

    /**
     * 获取被拒绝的权限
     *
     * @param permissions 要申请的全部权限
     */
    public String[] getDeniedPermissions(String[] permissions) {
        if (null != permissions && permissions.length > 0) {
            List<String> deniedPermissionList = new ArrayList<>();

            boolean[] permissionsStatus = checkCurPermissionsStatus(permissions);
            for (int i = 0; i < permissions.length; i++) {
                if (!permissionsStatus[i]) {
                    deniedPermissionList.add(permissions[i]);
                }
            }

            String[] deniedPermissions = new String[deniedPermissionList.size()];
            return deniedPermissionList.toArray(deniedPermissions);
        } else {
            throw new IllegalArgumentException("参数不能为空，且必须有元素");
        }
    }

    /**
     * 获取被拒绝的权限对应的提示文本数组
     *
     * @param permissions 要申请的全部权限
     * @param hints       权限被拒绝时的提示文本
     */
    public String[] getDeniedHint(String[] permissions, String[] hints) {
        if (null == permissions || null == hints || permissions.length == 0 || hints.length == 0 || permissions
                .length != hints.length) {
            throw new IllegalArgumentException("参数不能为空、必须有元素，且两个参数的长度必须一致");
        } else {
            List<String> deniedHintList = new ArrayList<>();

            boolean[] permissionsStatus = checkCurPermissionsStatus(permissions);
            for (int i = 0; i < permissions.length; i++) {
                if (!permissionsStatus[i]) {
                    deniedHintList.add(hints[i]);
                }
            }

            String[] deniedPermissions = new String[deniedHintList.size()];
            return deniedHintList.toArray(deniedPermissions);
        }
    }

    /**
     * 获取被拒绝的权限对应的提示文本字符串
     *
     * @param permissions 要申请的全部权限
     * @param hints       权限被拒绝时的提示文本
     */
    public String getDeniedHintStr(String[] permissions, String[] hints) {
        if (null == permissions || null == hints || permissions.length == 0 || hints.length == 0 || permissions
                .length != hints.length) {
            throw new IllegalArgumentException("参数不能为空、必须有元素，且两个参数的长度必须一致");
        } else {
            StringBuilder hintStr = new StringBuilder();
            boolean[] permissionsStatus = checkCurPermissionsStatus(permissions);
            for (int i = 0; i < permissions.length; i++) {
                if (!permissionsStatus[i]) {
                    hintStr.append(hints[i]).append("\n");
                }
            }
            return hintStr.toString();
        }
    }

    /**
     * 是否所有权限都已经被允许
     *
     * @param permissions 申请的权限
     * @return true 全被允许，false 有没有被允许的权限
     */
    public boolean isAllPermissionGranted(String[] permissions) {
        return getDeniedPermissions(permissions).length == 0;
    }

    /**
     * 是否所有权限都已经被允许
     *
     * @param grantResults 权限申请的结果
     * @return true 全被允许，false 有没有被允许的权限
     */
    public boolean isAllPermissionGranted(int[] grantResults) {
        int isAllGranted = PackageManager.PERMISSION_GRANTED;
        for (int grantResult : grantResults) {
            isAllGranted = isAllGranted | grantResult;
        }
        return isAllGranted == 0;
    }

    /**
     * 请求权限
     *
     * @param activity    Activity
     * @param permissions 权限
     * @param requestCode 请求码
     */
    public void requestNecessaryPermissions(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }


    /**
     * 检测之前是否已经拒绝过。如果已经拒绝过，那么再次请求权限的时候就需要给出原因
     *
     * @param activity    activity
     * @param permissions 请求的权限
     */
    public boolean shouldShowRequestReason(Activity activity, String[] permissions) {
        boolean showReason = false;
        for (int i = 0; i < permissions.length; i++) {
            showReason = showReason | ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i]);
        }
        return showReason;
    }

    /**
     * 展示被拒绝的弹窗
     */
    public void showDeniedDialog(final Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setNegativeButton("就不给你权限", null);
        builder.setPositiveButton("我要重新开启权限", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openSysSettingPage(context);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 打开系统设置界面
     */
    private void openSysSettingPage(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * 如果需要判断之前是否被拒绝过，则创建该类的实例，然后将具体的事件处理放在该类的方法中
     */
    public interface PermissionGrantedFactory {
        /**
         * 处理事件或者请求权限：如果权限已经被允许，执行事件，否则请求权限
         */
        void handleEventOrRequestPermission();
    }

    /**
     * 判断是否需要展示为什么二次请求权限，如果不需要执行相应的操作
     * <p>
     * 该方法中首先会检测之前是否被拒绝过，如果已经被拒绝过则展示为什么需要再次申请这个权限，并引导用户去设置中开启权限。
     *
     * @param activity    activity
     * @param permissions 请求的权限
     * @param hints       权限被拒绝时的提示
     */
    public void showRequestReasonOrHandlePermissionEvent(Activity activity, String[] permissions, String[] hints) {
        boolean hadBeanDenied = shouldShowRequestReason(activity, permissions);
        if (hadBeanDenied) {
            showDeniedDialog(activity, getDeniedHintStr(permissions, hints));
        } else {
            if (null != permissionGrantedFactory) {
                permissionGrantedFactory.handleEventOrRequestPermission();
            }
        }
    }
}
