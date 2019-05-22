package com.cnpeng.b_05_alertDialog_dismiss;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.b_04_suspendAndListView.SuspendAndListViewActivity2;
import com.cnpeng.utils.CommonUtils;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/4/10:下午8:23
 * <p>
 * 说明：自定义AlertDialog的View,点击确定按钮后跳转到下一个页面，并关闭当前页。
 * <p>
 * 需要注意的是，关闭当前页之前需要关闭Alertdialog，否则会导致actvity溢出。溢出的错误信息如下： 并且在关闭dialog的时候，只有通过show()
 * 方法获取到的dialog对象才可以调用dismiss并实现关闭；用create方法得到的对象则不行
 * <p>
 * 溢出的错误信息如下：（ERROR级别） android.view.WindowLeaked: Activity com.cnpeng.b_05_alertDialog_dismiss
 * .DissmissAlertDialog
 * has leaked window com.android.internal.policy.impl.PhoneWindow$DecorView{1a1d1b07 V.E..... R......D 0,0-684,238} that
 * was originally added here
 */

public class   DissmissAlertDialog extends AppCompatActivity implements View.OnClickListener {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dismiss_alertdialog);

        Button bt_showDialog = (Button) findViewById(R.id.bt_showAlertDialog);
        bt_showDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        View customDialogView = getLayoutInflater().inflate(R.layout.custom_alertdialog, null);
        builder.setView(customDialogView);
        //        dialog = builder.create();    //这种还是会报溢出
        //        builder.show();
        dialog = builder.show();    //这样则不会溢出

        Button bt_left = (Button) customDialogView.findViewById(R.id.bt_left);
        Button bt_right = (Button) customDialogView.findViewById(R.id.bt_right);

        bt_left.setOnClickListener(this);
        bt_right.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_left:  //关闭当前页
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                finish();
                break;
            case R.id.bt_right: //关闭当前页面，并跳转到其他页面
                CommonUtils.mStartActivity(this, SuspendAndListViewActivity2.class);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                finish();
                break;
        }
    }
}
