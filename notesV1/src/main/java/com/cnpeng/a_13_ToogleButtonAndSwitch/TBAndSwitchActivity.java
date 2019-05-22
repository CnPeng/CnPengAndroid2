package com.cnpeng.a_13_ToogleButtonAndSwitch;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * Created by CnPeng on 2017/1/17.
 * <p>
 * ToogleButton 和 Switch 的基本使用
 */

public class TBAndSwitchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbandswitch);

        final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.tb_);
        final Switch aSwitch = (Switch) findViewById(R.id.switch_01);
        final LinearLayout ll = (LinearLayout) findViewById(R.id.ll_TbAndSwitch);

        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ll.setOrientation(LinearLayout.VERTICAL);
                    aSwitch.setChecked(true);
                    toggleButton.setChecked(true);
                } else {
                    ll.setOrientation(LinearLayout.HORIZONTAL);
                    aSwitch.setChecked(false);
                    toggleButton.setChecked(false);
                }
            }
        };

        toggleButton.setOnCheckedChangeListener(listener);
        aSwitch.setOnCheckedChangeListener(listener);
    }
}
