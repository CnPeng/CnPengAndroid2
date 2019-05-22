package com.cnpeng.a_33_ImageSwitcher;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CnPeng on 2017/2/9.
 * <p>
 * ImageSwitcher的基本使用--使用ImageSwitcher实现带动画的图片浏览器
 */

public class ImageSwitcherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageswitcher);

        init();
    }

    private void init() {

        //模拟数据
        final int[] images = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R
                .drawable.f};

        final List<Map<String, Object>> imageList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("imageId", images[i]);
            imageList.add(map);
        }

        //获取控件，并设置数据
        final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher_1);
        GridView gridView = (GridView) findViewById(R.id.gv_all);

        SimpleAdapter adapter = new SimpleAdapter(this, imageList, R.layout.item_gv, new String[]{"imageId"}, new 
                int[]{R.id.iv_gv_vs});
        gridView.setAdapter(adapter);   //使用GridView展示小图

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(ImageSwitcherActivity.this);
                ImageSwitcher.LayoutParams params = new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams
                        .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(params);

                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                return imageView;
            }
        });

        //条目点击
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imageSwitcher.setImageResource(images[position]);
            }
        });

        //条目选中和条目点击差不多
        gridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageSwitcher.setImageResource(images[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
