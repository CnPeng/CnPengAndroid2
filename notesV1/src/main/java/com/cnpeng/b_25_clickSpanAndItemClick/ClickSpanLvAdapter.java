package com.cnpeng.b_25_clickSpanAndItemClick;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.ItemSimpleadapterBinding;

import java.util.List;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/11/16:下午8:37
 * <p>
 * 说明：ClickSpanAndItemClickActivity 界面中的LV使用的适配器
 */

class ClickSpanLvAdapter extends BaseAdapter {

    private final List<String> names;

    public ClickSpanLvAdapter(List<String> names) {
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemSimpleadapterBinding binding;


        if (null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            binding = DataBindingUtil.inflate(inflater, R.layout.item_simpleadapter, null, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ItemSimpleadapterBinding) convertView.getTag();
        }

        ClickSpanAndItemClickHandler handler = new ClickSpanAndItemClickHandler(parent.getContext());
        binding.setHandler(handler);

        String name = names.get(position);
        SpannableString spannableString = new SpannableString(name);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(widget.getContext(), "关键字被点击了", Toast.LENGTH_SHORT).show();
            }
        }, 0, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        binding.setContent(spannableString);
        binding.tvName.setMovementMethod(LinkMovementMethod.getInstance());
        //        binding.tvName.setMovementMethod(LinkMovementClickMethod.getInstance());
        
        //        //事件会重复 
        //        View.OnClickListener clickListener = new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                Toast.makeText(v.getContext(), "条目被点击了", Toast.LENGTH_SHORT).show();
        //            }
        //        };
        //        binding.tvName.setOnClickListener(clickListener);
        //        binding.llParent.setOnClickListener(clickListener);

        return convertView;
    }

}
