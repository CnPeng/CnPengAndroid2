package com.cnpeng.utils_stu;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by yxj on 16/11/23.
 * 171201 CnPeng 该内容直接拷贝自当前项目
 * shouldShowRequestPermissionRationale第一次请求权限是false，选中了不再提醒后也是false,不是第一次，并且没选中不再提醒，是true
 */

public class PermissionUtils {
    private static final String PREFS_FILE_NAME             = "preference_permission";
    private static final String PREFS_FIRST_TIME_KEY        = "is_app_launched_first_time";
    public static final  int    CODE_RECORD_AUDIO           = 0;
    public static final  int    CODE_GET_ACCOUNTS           = 1;
    public static final  int    CODE_READ_PHONE_STATE       = 2;
    public static final  int    CODE_CALL_PHONE             = 3;
    public static final  int    CODE_CAMERA                 = 4;
    public static final  int    CODE_ACCESS_FINE_LOCATION   = 5;
    public static final  int    CODE_ACCESS_COARSE_LOCATION = 6;
    public static final  int    CODE_READ_EXTERNAL_STORAGE  = 7;
    public static final  int    CODE_WRITE_EXTERNAL_STORAGE = 8;
    public static final  int    CODE_MULTI_PERMISSION       = 100;

    public static final String PERMISSION_RECORD_AUDIO           = Manifest.permission.RECORD_AUDIO;
    public static final String PERMISSION_GET_ACCOUNTS           = Manifest.permission.GET_ACCOUNTS;
    public static final String PERMISSION_READ_PHONE_STATE       = Manifest.permission.READ_PHONE_STATE;
    public static final String PERMISSION_CALL_PHONE             = Manifest.permission.CALL_PHONE;
    public static final String PERMISSION_CAMERA                 = Manifest.permission.CAMERA;
    public static final String PERMISSION_ACCESS_FINE_LOCATION   = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PERMISSION_READ_EXTERNAL_STORAGE  = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private static final String[] Permissions = {PERMISSION_RECORD_AUDIO, PERMISSION_GET_ACCOUNTS, 
            PERMISSION_READ_PHONE_STATE, PERMISSION_CALL_PHONE, PERMISSION_CAMERA, PERMISSION_ACCESS_FINE_LOCATION, 
            PERMISSION_ACCESS_COARSE_LOCATION, PERMISSION_READ_EXTERNAL_STORAGE, PERMISSION_WRITE_EXTERNAL_STORAGE};

    private static final String[] PermissionsDesc = {"在设置-应用-中开启录音权限,以正常使用聊天等功能", "在设置-应用-中开启用户权限," + "以正常使用功能", 
            "在设置-应用-中开启读取手机状态权限,以正常使用功能", "在设置-应用-中开启打电话状态权限,以正常使用功能", "在设置-应用-中开启相机状态权限," + "以正常使用聊天、扫描二维码等功能", 
            "在设置-应用-中开启位置权限,以正常使用附近同学功能", "在设置-应用-中开启位置权限," + "以正常使用附近同学功能", "在设置-应用-中开启存储空间权限," + "以正常使用功能", 
            "在设置-应用-中开启存储空间权限,以正常使用功能"};
    private static boolean shouldShowRationale;

    // preference utility methods
    private static boolean getApplicationLaunchedFirstTime(Activity activity, int requestCode) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        return sharedPreferences.getBoolean(PREFS_FIRST_TIME_KEY + requestCode, true);
    }

    private static void setApplicationLaunchedFirstTime(Activity activity, int requestCode) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREFS_FIRST_TIME_KEY + requestCode, false);
        editor.commit();
    }


    public interface PermissionGrant {
        void onPermissionGranted(int permissonCode);
    }

    public static boolean lackStoragePermissions(Activity activity) {//判断是不是有内存权限
        if (Build.VERSION.SDK_INT < 23) {
            return false;
        }
        return ActivityCompat.checkSelfPermission(activity, PERMISSION_WRITE_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED;
    }

    public static boolean lackRecordAudioPermissions(Activity activity) {//判断是不是有内存权限
        if (Build.VERSION.SDK_INT < 23) {
            return false;
        }
        return ActivityCompat.checkSelfPermission(activity, PERMISSION_RECORD_AUDIO) != PackageManager
                .PERMISSION_GRANTED;
    }

    public static boolean lackCameraPermissions(Activity activity) {//判断是不是有内存权限
        if (Build.VERSION.SDK_INT < 23) {
            return false;
        }
        return ActivityCompat.checkSelfPermission(activity, PERMISSION_CAMERA) != PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 请求权限
     *
     * @param activity        所处的Activity界面
     * @param permissionCode  权限代码的数组。该类中的那一堆 int 常量，如 CODE_CAMERA 代表拍照权限
     * @param permissionGrant 授权情况处理类
     * @param finish          请求完权限之后是否需要关闭页面
     */
    public static void requestPermission(final Activity activity, final int permissionCode,
                                         PermissionGrant permissionGrant, final boolean finish) {
        if (activity == null) {
            return;
        }

        if (permissionCode < 0 || permissionCode >= Permissions.length) {
            return;
        }

        final String requestPermission = Permissions[permissionCode];

        //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
        // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang.RuntimeException: Unknown 
        // exception code: 1 msg null)，
        if (Build.VERSION.SDK_INT < 23) {
            permissionGrant.onPermissionGranted(permissionCode);
            return;
        }

        int checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, requestPermission)) {
                shouldShowRationale(activity, permissionCode, requestPermission);
            } else {
                if (getApplicationLaunchedFirstTime(activity, permissionCode)) {//第一次获取权限，显示dialog
                    setApplicationLaunchedFirstTime(activity, permissionCode);  //  ** DON'T FORGET THIS **
                    shouldShowRationale(activity, permissionCode, requestPermission);
                } else {
                    showPersimissonDialog(activity, permissionCode, finish);
                }
            }
        } else {
            permissionGrant.onPermissionGranted(permissionCode);
        }
    }

    /**
     * 请求权限
     *
     * @param activity        所处的Activity界面
     * @param permissionCodes 权限代码的数组。该类中的那一堆 int 常量，如 CODE_CAMERA 代表拍照权限
     * @param permissionGrant 授权情况处理类
     * @param finish          请求完权限之后是否需要关闭页面
     */
    public static void requestPermissions(final Activity activity, final int[] permissionCodes,
                                          PermissionGrant permissionGrant, final boolean finish) {
        if (null == activity || null == permissionCodes || permissionCodes.length == 0) {
            return;
        }

        int requestCode = permissionCodes[permissionCodes.length - 1];
        //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
        // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang.RuntimeException: Unknown 
        // exception code: 1 msg null)，
        if (Build.VERSION.SDK_INT < 23) {   //以最后一个权限编号作为请求码
            permissionGrant.onPermissionGranted(requestCode);
            return;
        }

        int permissionsStatus = PackageManager.PERMISSION_GRANTED;
        final String[] permissions_requested = new String[permissionCodes.length];
        for (int i = 0; i < permissionCodes.length; i++) {
            int permissionNum = permissionCodes[i];

            if (permissionNum < 0 || permissionNum >= Permissions.length) {
                return;
            }

            permissions_requested[i] = Permissions[permissionNum];
            int curPermissionStatus = ActivityCompat.checkSelfPermission(activity, permissions_requested[i]);
            permissionsStatus = permissionsStatus | curPermissionStatus;    //位或运算

            shouldShowRationale = shouldShowRationale & ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    permissions_requested[i]);
        }

        //一次申请一组权限，只要其中有一个之前未被允许，则全部重新申请
        if (permissionsStatus != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRationale) {
                shouldShowRationale2(activity, requestCode, permissions_requested);
            } else {
                if (getApplicationLaunchedFirstTime(activity, requestCode)) {//第一次获取权限，显示dialog
                    setApplicationLaunchedFirstTime(activity, requestCode);  //  ** DON'T FORGET THIS **
                    shouldShowRationale2(activity, requestCode, permissions_requested);
                } else {
                    showPersimissonDialog(activity, requestCode, finish);
                }
            }
        } else {
            permissionGrant.onPermissionGranted(requestCode);
        }
    }

    private static void showPersimissonDialog(final Activity activity, final int permissionCode, final boolean finish) {
        new AlertDialog.Builder(activity).setCancelable(false).setTitle("权限申请").setMessage
                (PermissionsDesc[permissionCode]).setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openSettingActivity(activity);
                if (finish) {
                    activity.finish();
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (finish) {
                    activity.finish();
                }
            }
        }).create().show();
    }

    private static void shouldShowRationale(final Activity activity, final int requestCode,
                                            final String requestPermission) {
        //第一个参数 activity，第二个参数 权限描述的数组，第三个是 一个 int 的区间值 
        ActivityCompat.requestPermissions(activity, new String[]{requestPermission}, requestCode);
    }

    private static void shouldShowRationale2(final Activity activity, final int requestCode,
                                             final String[] permissions_requested) {
        //第一个参数 activity，第二个参数 权限描述的数组，第三个是 一个 int 的区间值 
        ActivityCompat.requestPermissions(activity, permissions_requested, requestCode);
    }

    /**
     * @param requestCode Need consistent with requestPermission
     */
    public static void requestPermissionsResult(final Activity activity, final int requestCode,
                                                @NonNull
                                                        String[] permissions,
                                                @NonNull
                                                        int[] grantResults, PermissionGrant permissionGrant,
                                                final boolean finish) {
        if (activity == null) {
            return;
        }
        if (requestCode < 0 || requestCode >= Permissions.length) {
            Toast.makeText(activity, "illegal requestCode:" + requestCode, Toast.LENGTH_SHORT).show();
            return;
        }
        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionGrant.onPermissionGranted(requestCode);
        } else {
            new AlertDialog.Builder(activity).setCancelable(false).setTitle("权限申请").setMessage
                    (PermissionsDesc[requestCode]).setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    openSettingActivity(activity);
                    if (finish) {
                        activity.finish();
                    }
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (finish) {
                        activity.finish();
                    }
                }
            }).create().show();
        }
    }

    private static void openSettingActivity(final Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
    }


    /**
     * 判断摄像头是否可用 * 主要针对6.0 之前的版本，现在主要是依靠try...catch... 报错信息，感觉不太好， * 以后有更好的方法的话可适当替换 * * @return
     */
    public static boolean PermissionToolBefore23() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            // setParameters 是针对魅族MX5 做的。MX5 通过Camera.open() 拿到的Camera
            // 对象不为null
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            canUse = false;
        }
        if (mCamera != null) {
            mCamera.release();
        }
        return canUse;
    }
}
