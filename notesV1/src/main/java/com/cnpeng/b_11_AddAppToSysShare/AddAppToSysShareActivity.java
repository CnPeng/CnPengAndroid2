package com.cnpeng.b_11_AddAppToSysShare;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

import java.util.ArrayList;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/5/11:下午8:11
 * <p>
 * 说明：将应用加入系统分享界面，并处理分享过来的数据-
 */

public class AddAppToSysShareActivity extends AppCompatActivity {

    private TextView  tv_title;
    private EditText  tv_link;
    private ImageView iv_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_app_to_sysshare);


        tv_title = (TextView) findViewById(R.id.tv_title_addToSysShare);
        tv_link = (EditText) findViewById(R.id.tv_link_addToSysShare);
        iv_share = (ImageView) findViewById(R.id.iv_addToSysShare);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (action != null && action.equals(Intent.ACTION_SEND) && type != null) {
            if (type.equals("text/plain")) {
                handleSendText(intent);
            } else if (type.startsWith("image/")) {
                handleSendImage(intent);
            }
        } else if (action != null && action.equals(Intent.ACTION_SEND_MULTIPLE) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultiImage(intent);
            }
        }
    }

    private void handleSendText(Intent intent) {
        String title = intent.getStringExtra(Intent.EXTRA_SUBJECT);
        String shareText = intent.getStringExtra(Intent.EXTRA_TEXT);
        //        Map<String, String> stringMap = getContent(intent);
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }

        if (!TextUtils.isEmpty(shareText)) {
            SpannableString spannableString = new SpannableString("网页链接");
            spannableString.setSpan(new URLSpan(shareText), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 网络
            tv_link.setText(spannableString);
            tv_link.setMovementMethod(LinkMovementMethod.getInstance());    //点击之后跳转，不设置该方法点击后无响应
        }
    }

    private void handleSendImage(Intent intent) {
        Uri shareUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (shareUri != null) {
            //处理获取的图片  展示到iv中
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(shareUri.toString());
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
    }

    private void handleSendMultiImage(Intent intent) {
        ArrayList<Uri> uris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (uris != null && uris.size() > 0) {
            //处理获取到的多图  ==
        }
    }
}
