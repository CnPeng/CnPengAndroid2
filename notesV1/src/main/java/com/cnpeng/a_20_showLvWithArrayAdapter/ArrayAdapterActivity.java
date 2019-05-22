package com.cnpeng.a_20_showLvWithArrayAdapter;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * Created by CnPeng on 2017/1/19.
 * <p>
 * 使用ArrayAdapter展示LV内容
 */

public class ArrayAdapterActivity extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrayadapter);

        String []names=new String[]{"张三","李四","王五","赵六"};
        
        ListView lv_ArrayAdapter1= (ListView) findViewById(R.id.lv_arrayAdapter1);
        ArrayAdapter adapter1=new ArrayAdapter(this,R.layout.item_arrayadapter1,names);
        lv_ArrayAdapter1.setAdapter(adapter1);

        ListView lv_ArrayAdapter2= (ListView) findViewById(R.id.lv_arrayAdapter2);
        ArrayAdapter adapter2=new ArrayAdapter(this,R.layout.item_arrayadapter2,names);
        lv_ArrayAdapter2.setAdapter(adapter2);
    }
}
