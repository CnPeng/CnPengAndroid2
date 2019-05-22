package com.cnpeng.a_03_CreateViewWithoutXML;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by CnPeng on 2017/1/12.
 * <p>
 * 在读 疯狂安卓讲义的过程中的demo -- 完全使用代码创建一个简单布局
 */

public class CreateViewWithoutXMLActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //创建view
        LinearLayout layout = new LinearLayout(this);
        //设置view给当前activity
        setContentView(layout);

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(20, 50, 20, 50); //设置边距时，控件可以直接调用setPadding

        //ViewGroup中 定义常量 -1 表示MatchParent, -2 表示WrapContent
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);

        final TextView tv_content = new TextView(this);
        tv_content.setText("点击下面的按钮可以更改此处的文字");
        layoutParams.setMargins(0, 0, 0, 30);   //设置Margin时必须借助layoutParams
        tv_content.setLayoutParams(layoutParams);

        Button btn_changeText = new Button(this);
        //创建LayoutParams 的时候，如果知道当前控件的父控件是谁，那就创建谁的LayoutParams；如果不知道，就创建ViewGroup的
        btn_changeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT));
        btn_changeText.setText("点击按钮更改上面的文字");
        btn_changeText.setSoundEffectsEnabled(true);   //true启用音效,false 禁用音效
        btn_changeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_content.setText("按钮被点击了，文字被改变了");
            }
        });

        layout.addView(tv_content);
        layout.addView(btn_changeText);

    }
}
