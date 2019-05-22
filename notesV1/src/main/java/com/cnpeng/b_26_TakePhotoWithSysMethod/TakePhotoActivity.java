package com.cnpeng.b_26_TakePhotoWithSysMethod;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.cnpeng.cnpeng_demos2017_01.BuildConfig;
import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.ActivityTakephotoBinding;
import com.cnpeng.utils.DynamicPermissionTool;

import java.io.File;
import java.io.FileNotFoundException;

import static android.os.Environment.DIRECTORY_PICTURES;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/12/1:上午10:36
 * <p>
 * 说明：调用系统相机执行拍照操作
 * 主要知识点：
 * 1、调用系统相机执行拍照
 * 2、动态权限申请
 * 3、刷新相册
 * 4、开启APP对应的设置界面
 * 5、使用 FileProvider 解决7.0及以后系统中使用 Uri.fromUri() 获取URI之后调用相机崩溃的情况
 * 6、getExternalCacheDir()获取当前APP对应的缓存目录，使用该方式不用申请读写SD的权限
 * <p>
 * 注意：
 * 不同品牌的手机对拍照处理不一样，个别手机中即便我们没有指定存储路径，也会存储照片到默认的地址中。如：Galaxy Note4 调用系统相机执行拍照时，
 * 如果么有指定照片存储路径，则会存储在 DCIM/Camera 目录下，此时，即便不调用 刷新相册的方法，也会执行刷新操作。V3
 * 
 * 171205 封装动态权限请求工具类
 */
public class TakePhotoActivity extends AppCompatActivity {

    private ActivityTakephotoBinding binding;
    private File                     file;
    private       String takePhotoMode          = "";
    private final String MODE_TAKE_AND_SAVE     = "takePhotoAndSaveToLocal";
    private final String MODE_TAKE_AND_NOT_SAVE = "takePhotoAndNotSaveToLocal";
    private final int    REQUEST_CODE1          = 0;    //申请权限时的请求码
    private DynamicPermissionTool                          permissionTool;
    private DynamicPermissionTool.PermissionGrantedFactory factory;

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_takephoto);

        //permissionTool = new DynamicPermissionTool(TakePhotoActivity.this);

        factory = new DynamicPermissionTool.PermissionGrantedFactory() {
            @Override
            public void handleEventOrRequestPermission() {
                takePhotoOrRequestPermission();
            }
        };
        permissionTool = new DynamicPermissionTool(TakePhotoActivity.this, factory);

        initTakePhotoBtEvent();
    }

    private void initTakePhotoBtEvent() {

        binding.btTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoMode = MODE_TAKE_AND_SAVE;
                //handlePermissonEvent();
                permissionTool.showRequestReasonOrHandlePermissionEvent(TakePhotoActivity.this, permissionTool
                        .permissions, permissionTool.deniedHints);
            }
        });
        binding.btTakePhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoMode = MODE_TAKE_AND_NOT_SAVE;
                //handlePermissonEvent();
                permissionTool.showRequestReasonOrHandlePermissionEvent(TakePhotoActivity.this, permissionTool
                        .permissions, permissionTool.deniedHints);
            }
        });
    }

    private void takePhotoOrRequestPermission() {
        boolean isAllGranted = permissionTool.isAllPermissionGranted(permissionTool.permissions);

        if (isAllGranted) {
            openSysCameraView();
        } else {
            String[] deniedPermissions = permissionTool.getDeniedPermissions(permissionTool.permissions);
            permissionTool.requestNecessaryPermissions(TakePhotoActivity.this, deniedPermissions, REQUEST_CODE1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull
                                                   String[] permissions,
                                           @NonNull
                                                   int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE1:
                boolean isAllGranted = permissionTool.isAllPermissionGranted(grantResults);
                if (isAllGranted) {
                    openSysCameraView();
                } else {
                    //factory 不为空表示在执行点击事件时会先检测是否有权限被拒绝了，如果有则dialog的展示就放在检测完之后处理，所以此处不用重复展示
                    if (null == factory) {
                        String hint = permissionTool.getDeniedHintStr(permissionTool.permissions, permissionTool
                                .deniedHints);
                        permissionTool.showDeniedDialog(TakePhotoActivity.this, hint);
                    }
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 调用系统相机执行拍照
     * <p>
     * 这里使用的 getExternalCacheDir() 是系统为每个APP单独分配的缓存空间，返回一个绝对路径，API19以后使用该路径不需要申请权限，API19之前需
     * 要申请。该路径只对当前APP可用，其他APP不可访问，也就是说，如果我们将照片存储在这个路径，系统的媒体扫描器也无法检测到该路径的内容，也就无法
     * 实现 相册/图库 内容的刷新。APP卸载则该目录清空。getExternalFilesDir() 与此相同
     * <p>
     * If you saved your photo to the directory provided by getExternalFilesDir(), the media scanner cannot access the
     * files because they are private to your app.
     */
    private void openSysCameraView() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (MODE_TAKE_AND_SAVE.equals(takePhotoMode)) {
            //如果是拍照并存储，则需要指定在本地的存储路径，并需要获取拍照之后的结果
            //file = new File(getExternalCacheDir(), System.currentTimeMillis() + "temp.jpg");
            // file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + "temp" 
            //         + ".jpg");

            file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES), System
                    .currentTimeMillis() + "temp.jpg");
            Uri photoURI;
            photoURI = getPhotoUri(file);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        }
        startActivityForResult(intent, Activity.DEFAULT_KEYS_DIALER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode) {
            //读取存储在本地的图片作为背景，这个图的清晰度会比较高
            if (MODE_TAKE_AND_SAVE.equals(takePhotoMode)) {
                Uri photoUri = getPhotoUri(file);
                try {
                    Drawable drawable = Drawable.createFromStream(getContentResolver().openInputStream(photoUri), "");
                    binding.parentLayout.setBackground(drawable);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                updateGallery(photoUri);
            } else {
                //相机拍照后会返回一个intent给onActivityResult。 intent的extra部分包含一个编码过的Bitmap缩略图,但这个Bitmap会比较模糊
                Bundle bundle = data.getExtras();
                if (null != bundle) {
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    Drawable drawable = new BitmapDrawable(bitmap);
                    binding.parentLayout.setBackground(drawable);
                }
            }
        }
        //模式使用完之后记得要重置
        takePhotoMode = "";
    }


    /**
     * 发送广播更新相册，不更新的话，在相册中将无法查看到截取的图片
     * If you saved your photo to the directory provided by getExternalFilesDir(), the media scanner cannot access the
     * files because they are private to your app.
     */
    private void updateGallery(Uri photoURI) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, photoURI);
        sendBroadcast(intent);
    }

    private Uri getPhotoUri(File file) {
        Uri photoURI;
        if (Build.VERSION.SDK_INT > 21) {
            photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + "" + "" + ""
                    + ".fileprovider", file);
            //  Uri photoURI = FileProvider.getUriForFile(TakePhotoActivity.this,getPackageName()+ 
            // "" + ".fileprovider", file);     //这种方式也可以获取URI
        } else {
            photoURI = Uri.fromFile(file);
        }
        return photoURI;
    }
}
