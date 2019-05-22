package com.cnpeng.a_34_textSwitcher;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/2/27:上午8:57
 * <p>
 * 说明：TextSwitcher的基本使用：TS和TV都可以用来展示文本内容，但是TS可以指定切换文本时的动画。
 */

public class TextSwitcherActvity extends AppCompatActivity {
    int clickNum;   //统计点击次数，同时用来控制展示第几个字符串
    String[] texts = new String[]{"还没有点击", "第一次", "第二次", "第三次", "第四次", "第五次","再点就从头开始"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textswitcher);

        final TextSwitcher textSwitcher = (TextSwitcher) findViewById(R.id.ts_01);

        //设置工厂，XXSwitcher必须要设置 factory
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(TextSwitcherActvity.this);
                textView.setTextColor(Color.BLUE);
                textView.setTextSize(40);
                return textView;
            }
        });

        //初始展示第0个
        textSwitcher.setText(texts[0]);

        //设置点击事件
        textSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSwitcher.setText(texts[++clickNum%texts.length]);
            }
        });
    }
}
