package com.cnpeng.b_04_suspendAndListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.ItemLvSuspendBinding;

import java.util.List;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/3/22:上午8:49
 * <p>
 * 说明：带有悬浮条的页面中LV的适配器
 */
public class SuspendLvAdapter extends BaseAdapter {
    private Context              context;
    private List<String>         list;
    private ItemLvSuspendBinding binding;

    public SuspendLvAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * 自定义方法--数据发生改变时调用该方法通知数据更新
     *
     * @param newlist 变更后的集合
     */
    public void notifyDataChanged(List<String> newlist) {
        this.list = newlist;
        super.notifyDataSetChanged();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(context);
            binding = DataBindingUtil.inflate(inflater, R.layout.item_lv_suspend, parent, false);
            holder.setBinding(binding);

            convertView = binding.getRoot();    //获取view
            convertView.setTag(holder);         //设置tag
        } else {
            holder = (Holder) convertView.getTag();
            binding = holder.getBinding();
        }

        //填充数据
        binding.tvItemSuspendLv.setText(list.get(position));

        return convertView;
    }

    private class Holder {
        ItemLvSuspendBinding binding;

        public void setBinding(ItemLvSuspendBinding binding) {
            this.binding = binding;
        }

        public ItemLvSuspendBinding getBinding() {
            return binding;
        }
    }
}
