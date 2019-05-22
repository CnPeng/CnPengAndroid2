package com.cnpeng.b_10_AndroidDrawables;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/5/11:上午8:56
 * <p>
 * 说明：TrasitionDrawable的使用.
 * An extension of LayerDrawables that is intended to cross-fade between the first and second
 * layer. To start the transition, call startTransition(int). To display just the first layer, call resetTransition().
 * <p>
 * It can be defined in an XML file with the <transition> element. Each Drawable in the transition is defined in a
 * nested <item>. For more information, see the guide to Drawable Resources.
 * <p>
 * TransitionDrawable 是 LayerDrawable的子类，只负责管理两层 drawable , 并且在这两层drawable 切换的时候提供了透明度渐变的动画。具体可以参考下面的示例代码：
 * <p>
 * 示例代码： 第一张图是初始的关灯状态， 点击 “开灯” 按钮时获取 TransitionDrawable 对象 并开启动画，startTransition( int
 * )方法中传入的是动画执行的时长。然后就会有一个透明度渐变的过程，如图2 ，直到最后展示成图3 状态。点击“关灯” 时，调用reverseTrasition( int ) 会再从开灯状态渐变到关灯状态。
 */

public class TransitionDrawableActivity extends AppCompatActivity implements View.OnClickListener {

    private TransitionDrawable transitionDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transitiondrawable);

        ImageView iv_lampOnAndOff = (ImageView) findViewById(R.id.iv_lampOnAndOff);
        transitionDrawable = (TransitionDrawable) iv_lampOnAndOff.getDrawable();

        Button lampOn = (Button) findViewById(R.id.bt_lampOn);
        Button lampOff = (Button) findViewById(R.id.bt_lampOff);

        lampOn.setOnClickListener(this);
        lampOff.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_lampOn:
                transitionDrawable.startTransition(3000);   //关灯到开灯的状态展示透明渐变动画
                break;
            case R.id.bt_lampOff:
                //transitionDrawable.reverseTransition(3000);   //开灯到关灯的状态展示透明渐变动画
                transitionDrawable.resetTransition();   //开灯到关灯的状态不展示渐变动画，直接切换到关灯状态
                break;
        }
    }
}
