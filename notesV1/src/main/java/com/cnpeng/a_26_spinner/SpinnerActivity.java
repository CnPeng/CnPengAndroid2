package com.cnpeng.a_26_spinner;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * Created by CnPeng on 2017/1/21.
 * <p>
 * 使用Spinner展现下拉列表
 */

public class SpinnerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        //初始化数据，该数组是定义在res目录下的values目录中的arrays.xml中。正常可以直接定义新数组，这里主要演示如何获取res目录中的数据
        final String[] books = getResources().getStringArray(R.array.books);

        //1 Spinner 默认的情况下，展示出来的最终被选中的内容与 dropdown 
        // 中的条目采用同样的布局，如果想自定义dropDown的条目布局，使用arrayAdapter的setDropDownViewResource，
        Spinner spinner = (Spinner) findViewById(R.id.spinner_adapter);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, books);
        //该方法只针对ArrayAdapter有效，用来指定下拉列表条目的布局文件
        arrayAdapter.setDropDownViewResource(R.layout.item_arrayadapter1);
        spinner.setAdapter(arrayAdapter);

        //2 使用ArrayAdapter实现spinner。prompt属性只有在spinnerMode 为dialog时才起作用
        Spinner spinnser1 = (Spinner) findViewById(R.id.spinner_adapter1);
        spinnser1.setAdapter(arrayAdapter);
        // spinner.setPrompt("选择你喜欢的书");
        // spinnerMode 可以通过代码设置


        //3 使用自定义BaseAdapter封装数据
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner_adapter2);
        MySpinnerAdapter mySpinnerAdapter = new MySpinnerAdapter(this, books);
        spinner2.setAdapter(mySpinnerAdapter);

        //4 给spinner 的条目加入 点击/选中 事件
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SpinnerActivity.this, "你喜欢的图书是：" + books[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
