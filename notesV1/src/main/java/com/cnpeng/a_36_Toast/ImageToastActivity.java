package com.cnpeng.a_36_Toast;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/2/27:上午10:50
 * <p>
 * 说明：展示带有图片的Toast，自定义吐司布局时，主要依靠setView 方法
 * <p>
 * 关于 负的margin值的注意事项 查看 activity_imagetoast22222222222.xml
 */

public class ImageToastActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagetoast);

        Button button = (Button) findViewById(R.id.bt_imageToast);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_imageToast:

                //自定义Toast 方式1 ，直接setView
                ////填充view，官方guide说根布局必须设置id为custom_toast_container，且inflate的第二个参数必须按照这个模式写，实际测试无所谓
                //// View toastView = getLayoutInflater().inflate(R.layout.imagetoast, (ViewGroup)findViewById(R.id
                //// .custom_toast_container));

                //// 填充view
                //View toastView = getLayoutInflater().inflate(R.layout.imagetoast, null);

                //Toast toast = new Toast(getApplicationContext());           //构造方法创建吐司对象
                //toast.setDuration(Toast.LENGTH_SHORT);                      //设置吐司时长
                ////toast.setGravity(Gravity.BOTTOM | Gravity.RIGHT, 100, 300); //更改吐司的展示位置（默认的位置就挺好）
                //toast.setView(toastView);                                   //设置吐司的新view
                //toast.show();                                               //展示吐司

                //自定义Toast方式2 ： 直接修改原生的toast布局（效果同setView时，view只是一个TextView的情况）
                Toast toast = Toast.makeText(ImageToastActivity.this, "直接改写原生的Toast布局", Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toastView.setBackgroundResource(R.drawable.shape_bk_imagetoast);
                toastView.setPadding(20, 5, 20, 5);

                TextView tv = (TextView) toastView.findViewById(android.R.id.message);
                tv.setTextSize(16);
                tv.setTextColor(Color.BLUE);
                tv.setGravity(Gravity.CENTER);
                tv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_launcher, 0, 0, 0);
                tv.setCompoundDrawablePadding(20);
                toast.show();

                break;
        }
    }
}
