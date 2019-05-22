package com.cnpeng.b_23_RefreshFragmentInViewPager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/9/20:下午2:24
 * <p>
 * 说明：ListView的适配器
 */

class LvAdapter extends BaseAdapter {

    private List<String> list = new ArrayList<>();
    private final Context context;

    public LvAdapter(FragmentActivity activity) {
        context = activity;
    }

    public void setData(List<String> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //此处测试使用，不再使用holder了。实际项目中需要使用holder
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null);
        }

        final TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(list.get(position));

        //添加条目点击事件
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送本地广播，通知其他页面刷新数据
                sendLocalBroadCastToUpdate(position);
            }
        });

        return convertView;
    }

    /**
     * 发送本地广播
     */
    private void sendLocalBroadCastToUpdate(int position) {
        Intent intent = new Intent(Fragment_VpItem.FLAG_BROADCAST_TO_UPDATE);
        intent.putExtra(Fragment_VpItem.KEY_POSITION, position);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
