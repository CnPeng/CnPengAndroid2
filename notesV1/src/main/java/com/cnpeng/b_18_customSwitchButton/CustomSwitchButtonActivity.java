package com.cnpeng.b_18_customSwitchButton;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.CusSwitchBtBinding;
import com.cnpeng.utils.LogUtils;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/8/3:上午8:58
 * <p>
 * 说明：自定义开关按钮的使用
 */

public class CustomSwitchButtonActivity extends AppCompatActivity {

    private CusSwitchBtBinding binding;

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customswitchbutton);

        init();
    }

    private void init() {
        final LogUtils.SpHelper_config spHelper = new LogUtils.SpHelper_config(this);
        boolean checkedStatus = spHelper.getCusSwitchBtStatus();
        LogUtils.e("之前的选中状态：", String.valueOf(checkedStatus));

        binding.cusSwitchBt.setIsChecked(checkedStatus);  //根据之前的选中状态初始化界面
        changeTextColor(checkedStatus);


        binding.cusSwitchBt.setOnCheckedChangeListener(new CustomSwitchButton.onCheckedChangeListener() {
            @Override
            public void onChange(boolean isChecked) {
                changeTextColor(isChecked);
                spHelper.saveCusSwitchBtStatus(isChecked);
            }
        });
    }

    private void changeTextColor(boolean isChecked) {
        if (isChecked) {
            binding.tvCusSwitchBtAct.setTextColor(Color.RED);
        } else {
            binding.tvCusSwitchBtAct.setTextColor(Color.BLACK);
        }
    }
}
