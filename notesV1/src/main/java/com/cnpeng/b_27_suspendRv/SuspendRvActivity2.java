package com.cnpeng.b_27_suspendRv;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.ActivitySuspendrvBinding;
import com.cnpeng.utils.LogUtils;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/12/8:上午10:06
 * <p>
 * 说明：使用 CoordinatorLayout 和 RecyclerView实现顶部悬浮，同时带有上拉和下拉操作
 * 重点要解决的问题就是： 让悬浮部分具有惯性滑动效果
 */

public class SuspendRvActivity2 extends AppCompatActivity {

    private ActivitySuspendrvBinding binding;

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SuspendRvActivity2.this, R.layout.activity_suspendrv);
        initRv();
    }

    private void initRv() {
        List<String> contents = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            contents.add("RV条目内容" + i);
        }
        SuspendRvAdapter adapter = new SuspendRvAdapter(SuspendRvActivity2.this, contents);
        binding.rvContentSuspend.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SuspendRvActivity2.this);
        binding.rvContentSuspend.setLayoutManager(layoutManager);

        initAppBarScrollListener();
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtils.e("刷新动画被关闭", "TRUE");
                binding.refreshLayout.setRefreshing(false); //关闭刷新动画
            }
        });
    }

    private void initAppBarScrollListener() {
        binding.appBarSuspendRv.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //LogUtils.e("顶部标题的高度：", binding.appBarSuspendRv.getHeight() + "____" + "垂直偏移量：" +verticalOffset);
                //
                //int goneHeadHeight = binding.headSuspendRvGone.getHeight();
                //
                ////使用这种方式，既能滚出去也能滚回来，但是在RV滚到顶端时会停住，再次下拉才能显示AppBar
                ////说明appBar 已经滚出去看不见了
                //if (goneHeadHeight + verticalOffset == 0) {
                //    LogUtils.e("appBar要滚出去的部分：", "滚出去了");
                //    //Toast.makeText(SuspendRvActivity2.this, "appBar滚出去了，展示标题栏的搜索按钮", Toast.LENGTH_SHORT).show();
                //}
                //
                //////此时应该先判断标题栏的搜索按钮的显示属性，如果当前可见，则隐藏。如果当前可见，不做操作
                //if (verticalOffset == 0) {
                //    LogUtils.e("appBar要滚出去的部分：", "滚回来了");
                //    //                    Toast.makeText(SuspendRvActivity2.this,"appBar滚回来了，隐藏标题栏的搜索按钮", Toast
                //    // .LENGTH_SHORT).show();
                //}

                if (verticalOffset == 0) {
                    binding.refreshLayout.setEnabled(true);
                } else {
                    if (!binding.refreshLayout.isRefreshing()) {
                        binding.refreshLayout.setEnabled(false);
                    }
                }
            }
        });


    }

    //private void initRvScrollListener() {
    //
    //    binding.rvContentSuspend.setOnScrollListener(new RecyclerView.OnScrollListener() {
    //        @Override
    //        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
    //            super.onScrollStateChanged(recyclerView, newState);
    //        }
    //
    //        @Override
    //        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    //            //这里的 x y 表示瞬间总的移动量，而不是具体的坐标值
    //            super.onScrolled(recyclerView, dx, dy);
    //
    //            //获取0索引的条目
    //            View view = recyclerView.getChildAt(0);
    //            int firstItemTop = (int) view.getTop();
    //            LogUtils.e("第一个条目的顶部坐标", "" + firstItemTop);
    //            if (0 == firstItemTop) {
    //                binding.headSuspendRvGone.scrollTo(0, 0);
    //            }
    //
    //        }
    //    });
    //}
}
