package com.cnpeng.b_20_shortCut;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.ShortCutBinding;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/8/15:下午5:07
 * <p>
 * 说明：快捷键的实现，区分8.0之前和8.0之后
 * <p>
 * API25==》8.0
 * <p>
 * 8.0之后快捷方式的展现形式：
 * static ShortCuts       静态模式
 * dynamic ShortCuts      动态模式
 * pinding ShortCusts     图钉模式
 * widgets                小控件模式
 * <p>
 * 8.0 之前的快捷方式展现形式：
 * 静态模式（通过广播实现）
 * <p>
 * 8.0（含）之后广播模式废弃
 * 8.0之后创建的静态快捷方式在右下角会有应用小图标，用来确认该快捷方式对应的界面属于哪个APP
 */

public class ShortCutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShortCutBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_shortcut);
        ShortCutHandler handler = new ShortCutHandler(ShortCutActivity.this);
        binding.setHandler(handler);

        binding.tvScroll.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
