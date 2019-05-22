package com.cnpeng.b_27_suspendRv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.ItemSuspendrvBinding;

import java.util.List;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/12/8:上午10:21
 * <p>
 * 说明：具有顶部悬浮条的 RV 的适配器
 */

public class SuspendRvAdapter extends RecyclerView.Adapter<SuspendRvAdapter.SuspendRvHolder> {

    private final List<String>       mContents;
    private final SuspendRvActivity2 mSuspendRvActivity2;

    public SuspendRvAdapter(SuspendRvActivity2 suspendRvActivity2, List<String> contents) {
        mSuspendRvActivity2 = suspendRvActivity2;
        mContents = contents;
    }


    @Override
    public SuspendRvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mSuspendRvActivity2);
        ItemSuspendrvBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_suspendrv, parent, false);
        SuspendRvHolder holder = new SuspendRvHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(SuspendRvHolder holder, int position) {
        ItemSuspendrvBinding binding = holder.getBinding();
        binding.tvSpinnerContent.setText(mContents.get(position));
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }


    class SuspendRvHolder extends RecyclerView.ViewHolder {
        ItemSuspendrvBinding mBinding;

        public SuspendRvHolder(View itemView) {
            super(itemView);
        }

        public ItemSuspendrvBinding getBinding() {
            return mBinding;
        }

        public void setBinding(ItemSuspendrvBinding binding) {
            mBinding = binding;
        }
    }
}
