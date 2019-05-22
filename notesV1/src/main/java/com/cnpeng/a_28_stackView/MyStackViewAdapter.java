package com.cnpeng.a_28_stackView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by CnPeng on 2017/1/23.
 * <p>
 * 自定义StackView的适配器
 */

public class MyStackViewAdapter extends BaseAdapter {
    Context context;
    int[]   pics;

    public MyStackViewAdapter(Context context, int[] pics) {
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

        ImageView iv_StackView = new ImageView(context);

        //        View.MeasureSpec 
        iv_StackView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT));

        iv_StackView.setScaleType(ImageView.ScaleType.FIT_XY);
        iv_StackView.setImageResource(pics[position]);
        return iv_StackView;
    }


    //        @Override
    //        public View getView(int position, View convertView, ViewGroup parent) {
    //            StackViewHolder holder;
    //            if (convertView == null) {
    //                convertView = LayoutInflater.from(context).inflate(R.layout.item_stackview, null);
    //                holder = new StackViewHolder();
    //                holder.iv_StackView = (ImageView) convertView.findViewById(R.id.iv_svItem);
    //               
    //                convertView.setTag(holder);
    //            } else {
    //                holder = (StackViewHolder) convertView.getTag();
    //            }
    //    
    //            holder.iv_StackView.setImageResource(pics[position]);
    //            return convertView;
    //        }
    //    
    //        class StackViewHolder {
    //            ImageView iv_StackView;
    //        }
}
