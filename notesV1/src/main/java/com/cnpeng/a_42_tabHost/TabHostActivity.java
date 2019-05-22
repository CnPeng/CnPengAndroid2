package com.cnpeng.a_42_tabHost;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.a_40_NumberPicker.NumberPickerActivity;

/**
 *  作者：CnPeng   时间：2017/3/13:上午8:58
 * <p>
 * 说明：使用TabHost模拟通话记录选项卡界面
 */


public class TabHostActivity extends TabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabhost);

        init();
    }

    private void init() {
        TabHost tabhost = getTabHost();

        //newTabSpec 中传入的tag是为了调用getTag(tag)方法时使用，类似于Map集合中的键名
        TabHost.TabSpec tab1 = tabhost.newTabSpec("tab1").setContent(R.id.tab1).setIndicator("未接电话");
        tabhost.addTab(tab1);

        //虽然给Indicator设置了drawable，但并不好使
        TabHost.TabSpec tab2 = tabhost.newTabSpec("tab2").setContent(R.id.tab2).setIndicator("已接电话", getResources()
                .getDrawable(R.drawable.icon));
        tabhost.addTab(tab2);

        //setContent 方法中可以直接传递一个intent，该intent指向某个activity
        Intent intent = new Intent(this, NumberPickerActivity.class);
        TabHost.TabSpec tab3 = tabhost.newTabSpec("页面3").setContent(intent).setIndicator("数值选择界面");
        tabhost.addTab(tab3);
    }
}