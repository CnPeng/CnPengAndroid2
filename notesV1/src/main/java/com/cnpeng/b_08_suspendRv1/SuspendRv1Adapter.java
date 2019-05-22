package com.cnpeng.b_08_suspendRv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cnpeng.cnpeng_demos2017_01.R;

import java.util.List;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/4/12:下午7:54
 * <p>
 * 说明：RecyclerView 的适配器
 */

class SuspendRv1Adapter extends RecyclerView.Adapter<CusHolder> {
    private List<String> items;
    private Context      context;

    public SuspendRv1Adapter(List<String> items, Context context) {
        this.items = items;
        this.context = context;
    }

    /**
     * 更新数据的方法
     */
    public void setNewList(List<String> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @Override
    public CusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //在填充条目布局的时候，如果不填入 parent 条目宽度无法填满布局，即便给条目设置了 matchParent
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_rv_cltbvprv, parent, false);
        CusHolder holder = new CusHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(CusHolder holder, int position) {
        holder.tv_rvItem.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class CusHolder extends RecyclerView.ViewHolder {
    TextView tv_rvItem;

    public CusHolder(View itemView) {
        super(itemView);
        tv_rvItem = (TextView) itemView.findViewById(R.id.tv_rvItem);
    }
}
