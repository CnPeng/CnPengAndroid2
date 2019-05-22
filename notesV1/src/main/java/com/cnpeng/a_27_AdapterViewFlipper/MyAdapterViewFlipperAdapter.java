package com.cnpeng.a_27_AdapterViewFlipper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * Created by CnPeng on 2017/1/22.
 * <p>
 * AdapterViewFlipper 的adapter
 */

public class MyAdapterViewFlipperAdapter extends BaseAdapter {
    Context context;
    int[]   pics;

    public MyAdapterViewFlipperAdapter(Context context, int[] pics) {
        this.context = context;
        this.pics = pics;
    }

    @Override
    public int getCount() {
        return pics.length;
    }

    @Override
    public Object getItem(int position) {
        return pics[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterViewFlipperHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_adapterviewflipper, null);
            holder = new AdapterViewFlipperHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_adapterViewFlipper);
            convertView.setTag(holder);
        } else {
            holder = (AdapterViewFlipperHolder) convertView.getTag();
        }

        holder.imageView.setImageResource(pics[position]);
        return convertView;
    }

    class AdapterViewFlipperHolder {
        ImageView imageView;
    }
}
