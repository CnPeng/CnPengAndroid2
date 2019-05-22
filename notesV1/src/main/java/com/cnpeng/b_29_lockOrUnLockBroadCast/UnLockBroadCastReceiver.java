package com.cnpeng.b_29_lockOrUnLockBroadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2018/1/23:下午3:59
 * <p>
 * 说明：屏幕解锁的广播接收器。屏幕锁屏，然后解锁，就会走该广播.
 * 
 * 注意：如果注册之后，解锁不走该广播，则需要将直接其挪到应用包下。
 */

public class UnLockBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (null!=intent&&Intent.ACTION_USER_PRESENT.equals(intent.getAction())){
            Toast.makeText(context, "屏幕解锁了", Toast.LENGTH_SHORT).show();
        }
    }
}
