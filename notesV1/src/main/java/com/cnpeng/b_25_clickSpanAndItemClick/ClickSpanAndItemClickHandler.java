package com.cnpeng.b_25_clickSpanAndItemClick;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/11/16:下午10:14
 * <p>
 * 说明：帮助类，必须是public的，否则收绑定会报错
 */

public class ClickSpanAndItemClickHandler {

    private final Context context;

    public ClickSpanAndItemClickHandler(Context context) {
        this.context = context;
    }

    public void itemClick(View view) {
                Toast.makeText(context, "条目被点击了", Toast.LENGTH_SHORT).show();
//        CommonUtils.mStartActivity(context, MainActivity.class);
    }
}
