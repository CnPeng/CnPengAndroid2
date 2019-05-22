package com.cnpeng.b_25_clickSpanAndItemClick;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.ClickSpanBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/11/16:下午8:30
 * <p>
 * 说明：解决ListView条目中的TextView设置 clickSpan 后，LV无法响应条目点击事件的情况
 * <p>
 * 1、当条目中有ClickSpan 超文本时，不能通过 setOnItemClickListener 设置条目事件，这样条目事件不响应，只能再Adapter或者 xml 中设置
 * 2、按照 1 中的方式，虽然解决了不触发条目点击的事件，但是会重复响应--TODO 等待解决
 */

public class ClickSpanAndItemClickActivity extends AppCompatActivity {

    private ClickSpanBinding binding;

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.actiivty_clickspan_and_itemclick);

        initListView();

//        initClipBoardManager();
    }

//    private void initClipBoardManager() {
//        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
//            @Override
//            public void onPrimaryClipChanged() {
//                LogUtils.e("剪切板发生变化","变化");
//            }
//        });
//    }

    private void initListView() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            names.add("张三" + i + "——————————————————————————随便输入几个名字");
        }
        ClickSpanLvAdapter adapter = new ClickSpanLvAdapter(names);
        binding.lvClickSpanAndItemClick.setAdapter(adapter);


        //由于列表中的TextView中的内容为SpannableString，并为TextView设置 setMovementMethod ，导致setOnItemClick根本不被触发！！！
        //所以必须从Adapter中为条目布局和textView 设置点击事件
        //binding.lvClickSpanAndItemClick.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //    @Override
        //    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //        Toast.makeText(ClickSpanAndItemClickActivity.this, "列表条目被点击了", Toast.LENGTH_SHORT).show();
        //    }
        //});
    }
}
