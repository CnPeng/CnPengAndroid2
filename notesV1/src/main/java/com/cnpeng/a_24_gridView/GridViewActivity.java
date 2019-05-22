package com.cnpeng.a_24_gridView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CnPeng on 2017/1/20.
 * <p>
 * GridView的基本使用
 */

public class GridViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);

        List<Map<String, Object>> items = new ArrayList<>();

        final int[] pics = new int[]{R.drawable.gongfuxiongmao, R.drawable.daomeixiong, R.drawable.xiaopohai, R
                .drawable.pikaqiu, R.drawable.act_attentioned, R.drawable.cus_switch, R.drawable.hot_green};

        for (int i = 0; i < pics.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("pic", pics[i]);
            item.put("name",pics[i]+"");
            items.add(item);
        }

        GridView gridView = (GridView) findViewById(R.id.gridview_01);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, items, R.layout.item_gridview, new String[]{"pic","name"}, new
                int[]{R.id.iv_gvItem,R.id.tv_item_gv_name});
        gridView.setAdapter(simpleAdapter);

        final ImageView imageView = (ImageView) findViewById(R.id.iv_gridview);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imageView.setImageResource(pics[position]);
            }
        });

    }
    
}
