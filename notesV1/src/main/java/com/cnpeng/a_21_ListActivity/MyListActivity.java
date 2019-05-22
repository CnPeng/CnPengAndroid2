package com.cnpeng.a_21_ListActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * Created by CnPeng on 2017/1/19.
 * <p>
 * ListActivity 的使用
 */

public class MyListActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] names = new String[]{"张三", "李四", "王五", "赵六"};

        //直接使用android原生的条目布局
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);

        //直接设置适配器就好，不需要setContentView 
        setListAdapter(adapter);
    }
}
