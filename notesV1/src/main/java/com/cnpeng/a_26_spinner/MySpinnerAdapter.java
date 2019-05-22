package com.cnpeng.a_26_spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * Created by CnPeng on 2017/1/22.
 * <p>
 * 自定义Adapter
 */

public class MySpinnerAdapter extends BaseAdapter {
    Context  context;
    String[] books;

    public MySpinnerAdapter(Context context, String[] books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.length;
    }

    @Override
    public Object getItem(int position) {
        return books[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpinnerHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner, null);
            holder = new SpinnerHolder();
            holder.tvSpinnerContent = (TextView) convertView.findViewById(R.id.tv_spinnerContent);
            convertView.setTag(holder);
        } else {
            holder = (SpinnerHolder) convertView.getTag();
        }

        holder.tvSpinnerContent.setText(books[position]);
        return convertView;
    }

    class SpinnerHolder {
        TextView tvSpinnerContent;
    }
}
