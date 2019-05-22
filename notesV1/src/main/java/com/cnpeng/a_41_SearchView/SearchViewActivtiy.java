package com.cnpeng.a_41_SearchView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/3/1:上午10:02
 * <p>
 * 说明：SearchView 的使用
 */

public class SearchViewActivtiy extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchview);

        //获取控件
        final ListView listView = (ListView) findViewById(R.id.lv_searchView);
        final SearchView searchView = (SearchView) findViewById(R.id.searchView_01);
        final String[] books = getResources().getStringArray(R.array.books);

        //适配器对象要加泛型
        final ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, books);
        listView.setAdapter(arrayAdapter);
        //启用listView的文本过滤（该方法继承自ABSListView）,只有实现了Filterable接口的adapter才可以使用这个方法。
        // ArrayAdapter和SimpleAdapter本身就实现了这个Filterable接口，而BaseAdapter并没有实现这个接口
        listView.setTextFilterEnabled(true);
        //listView条目点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //条目点击之后将文本传递给输入框，第二个参数表示是否直接搜索
                searchView.setQuery(books[position], false);
            }
        });


        //设置searchView
        searchView.setSubmitButtonEnabled(true);    //启用右侧的提交搜索按钮
        //设置搜索监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {    //提交搜索的时候调用
                //此处执行具体的搜索逻辑
                Toast.makeText(SearchViewActivtiy.this, "您要搜索的内容是" + query, Toast.LENGTH_SHORT).show();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {  //输入框获取焦点--用户准备输入的时候
                //如果输入框中的内容为空清除过滤器，如果不为空将内容设置给过滤器
                if (TextUtils.isEmpty(newText)) {
                    // listView.clearTextFilter();   //使用这种输入内容后，屏幕中央会有大大的黑色提示文本，丑！
                    arrayAdapter.getFilter().filter(null);  //使用这种则没有黑色提示文本
                } else {
                    //listView.setFilterText(newText); //使用这种输入内容后，屏幕中央会有大大的黑色提示文本，丑！
                    arrayAdapter.getFilter().filter(newText);   //使用这种则没有黑色提示文本
                }
                return true;    //返回true表示事件由自己处理，false由外部处理
            }
        });
        
        //根据资源标识符获取searchView中的输入控件,并更改背景(取消底部那根灰色的线)只有这种方式能拿到控件
        LinearLayout ll_edit = (LinearLayout) searchView.findViewById(searchView.getContext().getResources()
                .getIdentifier("android:id/search_plate", null, null));
        ll_edit.setBackgroundColor(Color.WHITE);

        //获取searchView中的提交按钮控件，并更改背景（取消底部那根灰色的线）
        LinearLayout ll_submit = (LinearLayout) searchView.findViewById(searchView.getContext().getResources()
                .getIdentifier("android:id/submit_area", null, null));
        ll_submit.setBackgroundColor(Color.WHITE);

        //获取SearchView中的提交按钮控件，并更改按钮图标
        ImageView iv_goBtn = (ImageView) searchView.findViewById(searchView.getContext().getResources().getIdentifier
                ("android:id/search_go_btn", null, null));
        ViewGroup.LayoutParams layoutParams = iv_goBtn.getLayoutParams();    //获取其布局参数
        layoutParams.width = 100;     //指定宽度
        iv_goBtn.setLayoutParams(layoutParams);
        iv_goBtn.setPadding(0, 0, 0, 0);   //取消paddin值
        iv_goBtn.setImageResource(R.drawable.pikaqiu);  //更改按钮图标
    }
}
