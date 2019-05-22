package com.cnpeng.b_09_TextureView;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.TextureView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/4/28:下午3:13
 * <p>
 * 说明：拍照预览界面
 */

public class TextureViewActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {

    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //先初始化TextureView ,然后将其作为view设置给该界面
        TextureView textureView = new TextureView(this);
        textureView.setSurfaceTextureListener(this);    //设置监听

        textureView.setAlpha(1.0f);     //控制透明度
        textureView.setRotation(90.0f); //控制旋转

        setContentView(textureView);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        camera = Camera.open(0);
        try {
            camera.setPreviewTexture(surface);  //设置预览监听
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();  //开启预览

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        camera.stopPreview();      //关闭预览
        camera.release();       //释放对象
        return true;
        //        return false;     //此处返回true ,表示事件已经处理
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
