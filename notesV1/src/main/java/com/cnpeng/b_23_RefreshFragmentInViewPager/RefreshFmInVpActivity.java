package com.cnpeng.b_23_RefreshFragmentInViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.ActivityRefreshFmInVpBinding;
import com.google.android.material.tabs.TabLayout;

import static com.cnpeng.b_23_RefreshFragmentInViewPager.Fragment_VpItem.FLAG_BROADCAST_TO_UPDATE;
import static com.cnpeng.b_23_RefreshFragmentInViewPager.Fragment_VpItem.KEY_POSITION;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/9/20:上午11:16
 * <p>
 * 说明：ViewPager 中包含多个Fragment 对象 , 当一个Fragment 对象中的数据发生变化时，同时刷新另外几个Fragment中的数据。
 */

public class RefreshFmInVpActivity extends AppCompatActivity {

    private ActivityRefreshFmInVpBinding binding;
    private LocalBroadcastManager        localBroadCastManager;
    private UpdateDataReceiver           receiver;
    private VpAdapter_RefreshFmInVp      vpAdapter;
    private ViewPager                    viewPager;

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_refresh_fm_in_vp);

        initViewPager();
        initLocalBroadCastReceiver();

    }

    private void initViewPager() {
        viewPager = binding.viewPagerRefreshFmInVp;
        TabLayout tabLayout = binding.tablayoutTitleRefreshFmInVp;

        tabLayout.setupWithViewPager(viewPager);    //TabLayout与ViewPager 绑定

        String[] titles = {"第一页", "第二页", "第三页", "第四页"};

        FragmentManager fragemntManager = getSupportFragmentManager();
        vpAdapter = new VpAdapter_RefreshFmInVp(fragemntManager, titles);
        viewPager.setAdapter(vpAdapter);
    }

    private void initLocalBroadCastReceiver() {
        localBroadCastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter(FLAG_BROADCAST_TO_UPDATE);

        receiver = new UpdateDataReceiver();
        localBroadCastManager.registerReceiver(receiver, intentFilter);
    }

    private class UpdateDataReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int position = intent.getIntExtra(KEY_POSITION, 0);
            int curPageIndex=viewPager.getCurrentItem();
            vpAdapter.refreshMofifiedPageData(position,curPageIndex);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        localBroadCastManager.unregisterReceiver(receiver);
    }
}
