package com.cnpeng.b_21_listViewLocalRefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.LocalRefreshLvItemBinding;
import com.cnpeng.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/8/24:下午8:23
 * <p>
 * 说明：局部刷新的ListView的适配器
 */

class LvAdapter_LocalRefreshLv extends BaseAdapter {

    private final Context                   context;
    private       LocalRefreshLvItemBinding binding;

    private List<User> userList = new ArrayList<>();

    public LvAdapter_LocalRefreshLv(Context context) {
        this.context = context;
    }

    /**
     * 更新全部集合数据并更新界面
     */
    public void setList(List<User> userList) {
        if (null != userList) {
            this.userList.clear();
            this.userList.addAll(userList);
        }
        notifyDataSetChanged();
    }

    /**
     * 只更新单条数据且不更新界面
     */
    public void updateChangedItemBean(int position, User newUserBean) {
        userList.set(position, newUserBean);
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //通过该log的打印，我们能确认单条更新的方法是生效的。notifyDataSetChanged会打印多次。
        LogUtils.e("被更新的条目索引是：", position + "");

        if (null == convertView) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_lv_local_refresh, parent,
                    false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (LocalRefreshLvItemBinding) convertView.getTag();
        }

        binding.setUserName(userList.get(position).getName());
        return convertView;
    }
}
