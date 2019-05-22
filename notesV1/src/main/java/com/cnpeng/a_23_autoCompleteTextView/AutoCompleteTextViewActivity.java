package com.cnpeng.a_23_autoCompleteTextView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * Created by CnPeng on 2017/1/20.
 * <p>
 * AutoCompleteTextView、MultiAutoCompleteTextView 的使用--带有下拉提示框的EditText
 */

public class AutoCompleteTextViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocompletetv);

        String[] names = new String[]{"z张三1", "z张三2", "z张三3", "z张三4", "z李四", "z王五", "z赵六"};

        AutoCompleteTextView autoCompleteTextView01 = (AutoCompleteTextView) findViewById(R.id.actv_01);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_arrayadapter1, names);
        autoCompleteTextView01.setAdapter(adapter);

        MultiAutoCompleteTextView mactv01 = (MultiAutoCompleteTextView) findViewById(R.id.mactv_01);
        mactv01.setAdapter(adapter);
        //MultiAutoCompleteTextView 必须设置分隔符才可以弹出popup.这句代码表示设置逗号为分隔符，分割多个被选中的值，被选中的值可以重复
        mactv01.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}
