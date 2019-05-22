package com.cnpeng.a_32_ViewSwitcher;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * Created by CnPeng on 2017/2/4.
 * <p>
 * ViewSwitcher的基本使用--手动添加view并实现切换
 * <p>
 * ViewSwitcher 虽然继承自 FrameLayout ，但是，在xml中添加view的时候，却是先添加的会显示在上面，后添加的显示在下面， 这个属性与FrameLayout
 * 相反，FrameLayout是后添加的会覆盖先添加的
 */

public class ViewSwitcherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewswitcher);

        init();
    }

    private void init() {
        Button bt_one = (Button) findViewById(R.id.bt_one);
        Button bt_two = (Button) findViewById(R.id.bt_two);
        final ViewSwitcher viewSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher01);

        Animation inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation outAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        viewSwitcher.setInAnimation(inAnimation);
        viewSwitcher.setOutAnimation(outAnimation);

        bt_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                viewSwitcher.showPrevious();
                viewSwitcher.setDisplayedChild(0);
            }
        });

        bt_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                viewSwitcher.showNext();
                viewSwitcher.setDisplayedChild(1);
            }
        });
    }

}
