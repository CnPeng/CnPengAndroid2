package com.cnpeng.b_33_BaseRvAdapter;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.ActivityBaseRvAdapterBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：CnPeng
 * 时间：2018/6/8
 * 功用：
 * 其他：
 */
public class BaseRvAdapterActivity extends FragmentActivity implements BaseRvAdapter.OnLoadingMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private ActivityBaseRvAdapterBinding mBinding;
    private BaseRvAdapterActivity        mActivity;
    private MyRvAdapter                  mRvAdapter;

    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_base_rv_adapter);
        mActivity = this;
        initRv();
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        mBinding.refreshLayout.setOnRefreshListener(this);
    }

    private void initRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        mBinding.rv.setLayoutManager(layoutManager);

        addDataToList(1);

        //构造适配器
        mRvAdapter = new MyRvAdapter(mActivity, mBinding.rv);
        //设置数据并通知界面更新
        mRvAdapter.setData(mList);
        //禁用脚布局
        //        mRvAdapter.enableFooterView(false);
        mRvAdapter.enableFooterView(true);

        mBinding.rv.setAdapter(mRvAdapter);
        TextView textView = new TextView(mActivity);
        textView.setText("这是头布局");
        mRvAdapter.setHeaderView(textView);

        mRvAdapter.setLoadingMoreListener(this);
    }

    private void addDataToList(int start) {
        int end = start + 15;
        for (int i = start; i < end; i++) {
            mList.add(i + ".js (29)\n");
        }
        ;
    }

    @Override
    public void onLoadingMore() {

        if (mList.size() >= 150) {
            //不再执行加载操作
            mRvAdapter.setLoadingStatus(mRvAdapter.STATUS_NO_MORE);
            Toast.makeText(mActivity, "已经没有更多数据了", Toast.LENGTH_SHORT).show();

        } else {
            mRvAdapter.setLoadingStatus(mRvAdapter.STATUS_LOADING);

            //模拟上拉加载
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    addDataToList(mList.size()+1);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRvAdapter.setData(mList);
                            mRvAdapter.setLoadingStatus(mRvAdapter.STATUS_OVER);
                            Toast.makeText(mActivity, "上拉完成", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }, 1000);
        }
    }

    @Override
    public void onRefresh() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    mList.add(0, i + "下拉");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRvAdapter.setData(mList);
                        mRvAdapter.setLoadingStatus(mRvAdapter.STATUS_OVER);
                        Toast.makeText(mActivity, "下拉完成", Toast.LENGTH_SHORT).show();
                        mBinding.refreshLayout.setRefreshing(false);
                    }
                });
            }
        }, 1000);
    }
}
