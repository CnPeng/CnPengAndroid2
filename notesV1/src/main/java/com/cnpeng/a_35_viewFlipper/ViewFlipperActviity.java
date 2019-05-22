package com.cnpeng.a_35_viewFlipper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/2/27:上午9:51
 * <p>
 * 说明：ViewFlipper的基本使用，ViewFlipper类似AdapterViewFlipper。 区别在于 ViewFlipper中的view需要addView()
 * 手动添加或直接在布局文件中写入；AdapterViewFlipper中的view是通过adapter实现的
 */

public class ViewFlipperActviity extends AppCompatActivity implements View.OnClickListener {

    private Button      bt_lt;
    private Button      bt_gt;
    private ViewFlipper viewFlipper;
    private Button      bt_auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewflipper);

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper_01);

        //  给viewFlipper添加view的方式1 ，直接代码中addView。如果同时在代码中和xml中添加了view,那么会先显示xml中的
        //添加一个imageview，注意，先添加的会显示在上面
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.pikaqiu);
        viewFlipper.addView(imageView);

        //添加第二个textView
        TextView textView = new TextView(this);
        textView.setText("ViewFlipper的使用");
        viewFlipper.addView(textView);

        bt_lt = (Button) findViewById(R.id.bt_showPre_vf);
        bt_lt.setOnClickListener(this);

        bt_gt = (Button) findViewById(R.id.bt_showNext_vf);
        bt_gt.setOnClickListener(this);

        bt_auto = (Button) findViewById(R.id.bt_auto_vf);
        bt_auto.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_showPre_vf:
                viewFlipper.showPrevious();
                viewFlipper.stopFlipping();
                break;
            case R.id.bt_showNext_vf:
                viewFlipper.showNext();
                viewFlipper.stopFlipping();
                break;
            case R.id.bt_auto_vf:
                viewFlipper.startFlipping();
                break;
        }
    }
}
