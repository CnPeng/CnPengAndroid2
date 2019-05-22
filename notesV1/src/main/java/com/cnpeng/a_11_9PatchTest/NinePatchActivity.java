package com.cnpeng.a_11_9PatchTest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * Created by CnPeng on 2017/1/16. 点九图的基本使用， 9Patch
 * 
 * 左侧 决定垂直缩放区域
 * 上侧 决定水平方向缩放区域
 * 左侧和上侧的交汇区域 在两个方向都可以缩放
 * 
 * 右侧和下侧共同决定文字内容的缩放区域
 */

public class NinePatchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ninepatch);
    }
}
