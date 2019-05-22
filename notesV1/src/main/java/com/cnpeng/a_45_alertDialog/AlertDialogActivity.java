package com.cnpeng.a_45_alertDialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/3/16:上午8:30
 * <p>
 * 说明：不同风格的AlertDialog,普通对话框、普通列表对话框、单选列表对话框、多选列表对话框、自定义列表对话框、自定义view对话框
 */

public class AlertDialogActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertdialog);

        init();
    }

    private void init() {
        Button bt1 = (Button) findViewById(R.id.bt_1);
        Button bt2 = (Button) findViewById(R.id.bt_2);
        Button bt3 = (Button) findViewById(R.id.bt_3);
        Button bt4 = (Button) findViewById(R.id.bt_4);
        Button bt5 = (Button) findViewById(R.id.bt_5);
        Button bt6 = (Button) findViewById(R.id.bt_6);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_1:     //普通对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setMessage("普通对话框的内容");
                builder.setTitle("普通对话框的标题");
                builder.setIcon(R.drawable.pikaqiu);
                showPositivieButton(builder);
                showNegativeButton(builder);
                builder.create();   //等所有信息都设置完成之后再去create和show
                builder.show();
                break;
            case R.id.bt_2:     //普通列表项对话框，这种点击条目之后就会自定关闭对话框
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setCancelable(false);
                builder2.setTitle("普通列表对话框的标题");
                builder2.setIcon(R.drawable.pikaqiu);
                builder2.setItems(R.array.books, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialogActivity.this, "你点击了数组中的第" + which + "个条目", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
                showPositivieButton(builder2);
                showNegativeButton(builder2);
                builder2.create();   //等所有信息都设置完成之后再去create和show
                builder2.show();
                break;
            case R.id.bt_3:
                break;
            case R.id.bt_4:
                break;
            case R.id.bt_5:
                break;
            case R.id.bt_6:
                break;

        }
    }

    /**
     * 创建并展示一个确定按钮
     */
    private void showPositivieButton(AlertDialog.Builder builder) {
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AlertDialogActivity.this, "你点击le确定", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 创建并展示一个取消按钮
     */
    private void showNegativeButton(AlertDialog.Builder builder) {
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AlertDialogActivity.this, "你点击了取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 创建并展示一个取消按钮
     */
    private void showNeturalButton(AlertDialog.Builder builder) {
        builder.setNeutralButton("按钮", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AlertDialogActivity.this, "你点击了按钮", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
