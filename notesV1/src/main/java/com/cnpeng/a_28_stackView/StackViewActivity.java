package com.cnpeng.a_28_stackView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.StackView;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CnPeng on 2017/1/23.
 * <p>
 * StackView展示叠加图
 */

public class StackViewActivity extends AppCompatActivity implements View.OnClickListener {

    private StackView stackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stackview);

        init();
    }

    private void init() {

        //这里偷懒使用了同一张图片，实际使用时用是根据需要随意设置的
        int[] pics = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};

        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < pics.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("pic", pics[i]);
            items.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item_stackview, new String[]{"pic"}, new 
                int[]{R.id.iv_svItem});

        stackView = (StackView) findViewById(R.id.stackView01);

        stackView.setAdapter(adapter);

        Button btPre = (Button) findViewById(R.id.bt_pre_sv);
        Button btNext = (Button) findViewById(R.id.bt_next_sv);

        btNext.setOnClickListener(this);
        btPre.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_pre_sv:
                stackView.showPrevious();
                break;
            case R.id.bt_next_sv:
                stackView.showNext();
                break;
        }
    }
}
