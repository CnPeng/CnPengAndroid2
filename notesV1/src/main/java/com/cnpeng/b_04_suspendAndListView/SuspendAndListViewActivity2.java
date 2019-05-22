package com.cnpeng.b_04_suspendAndListView;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.ActivitySuspendListviewBinding;
import com.cnpeng.cnpeng_demos2017_01.databinding.FooterviewLvBinding;
import com.cnpeng.cnpeng_demos2017_01.databinding.HeaderContentBinding;
import com.cnpeng.cnpeng_demos2017_01.databinding.HeaderTitleBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/3/21:下午8:02
 * <p>
 * 说明：该示例中使用了数据绑定，但这不是重点。
 * <p>
 * 主要实现的功能： 使用纯listView实现单一的顶部悬浮条，并且实现该LV的上拉加载和下拉刷新
 */

public class SuspendAndListViewActivity2 extends AppCompatActivity {

    private ActivitySuspendListviewBinding binding;
    private HeaderContentBinding           headerContentBinding;
    private HeaderTitleBinding             headerTitleBinding;
    private SuspendLvAdapter               adapter;
    private List<String>                   list;        //要展示的数据集合
    private boolean                        isPullDown;  //是否正在下拉
    private boolean                        isUpLoad;    //是否正在上拉
    private boolean                        isTop;       //是否到了最顶部
    private boolean                        isBottom;    //是否到了最底部
    private boolean                        isRefreshing;//是否正在下拉刷新
    private boolean                        isUploading; //是否正在上拉加载更多
    private int                            startY;      //触摸屏幕时的起始Y坐标点
    private ListView                       listView;
    private LayoutInflater                 inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_suspend_listview);

        init();
    }

    private void init() {

        //初始隐藏悬浮条
        binding.setShowTitleBk(View.GONE);
        binding.setShowPB(View.GONE);

        //模拟数据
        list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add("初始数据值" + i);
        }

        //获取listview控件及填充器
        listView = binding.lvSuspendTitle;
        inflater = getLayoutInflater();

        //添加第一个头布局,一般用来展示帖子的详细内容
        headerContentBinding = DataBindingUtil.inflate(inflater, R.layout.header_content, null, false);
        listView.addHeaderView(headerContentBinding.getRoot());

        //添加第二个头布局，可用来展示帖子的评论数量
        headerTitleBinding = DataBindingUtil.inflate(inflater, R.layout.header_title, null, false);
        listView.addHeaderView(headerTitleBinding.getRoot());

        //添加完头布局之后再去设置适配器，否则可能会报错
        adapter = new SuspendLvAdapter(this, list);
        listView.setAdapter(adapter);

        //设置滚动监听，控制是否要展示悬浮条，并判断是否滚到了最顶端或最底端
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                //控制悬浮条的显示和隐藏
                int headerViewSize = listView.getHeaderViewsCount();    //获取头布局的数量
                if (firstVisibleItem >= headerViewSize - 1) {     //如果当前可见条目的索引不是头布局的索引
                    binding.setShowTitleBk(View.VISIBLE);
                } else {
                    binding.setShowTitleBk(View.GONE);
                }

                //判断是否到了最顶部（注意：进入页面的时候会走一次到顶）
                if (firstVisibleItem == 0) {
                    View firstHeaderView = view.getChildAt(0);
                    if (firstHeaderView != null) {
                        int firstHeaderViewTop = firstHeaderView.getTop();   //获取顶部坐标
                        //如果第一个view的top值为0，表示到了最顶部
                        isTop = firstHeaderViewTop == 0;

                        if (isTop) {    //注意：lv在初始化的时候也会走onScroll , 所以在初次进入界面的时候，也会谈一次"到顶了"的吐司提示
                            Toast.makeText(SuspendAndListViewActivity2.this, "到顶了", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                //是否到了最底部
                if (firstVisibleItem + visibleItemCount == totalItemCount) {    //如果true表示到了最后一个条目
                    View lastItemView = view.getChildAt(view.getChildCount() - 1);
                    if (lastItemView != null) {
                        int lastViewBottom = lastItemView.getBottom();
                        int viewHeight = view.getHeight();      //Lv 的整体高度
                        isBottom = viewHeight == lastViewBottom;
                        if (isBottom) {  //两个值一致表示到了最后一个条目的最底部
                            Toast.makeText(SuspendAndListViewActivity2.this, "到最底部了", Toast.LENGTH_SHORT).show();
                            loadMoreData();
                        }
                    }
                }
            }
        });

        //设置触摸监听，判断是上拉还是下拉
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:   //按下时记录起始坐标
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:   //移动中
                        break;
                    case MotionEvent.ACTION_UP:     //抬手时记录最终坐标
                        int curY = (int) event.getY();

                        isPullDown = curY > startY;
                        isUpLoad = curY < startY;
                        if (isPullDown) {
                            getLastestData();
                        }

                        if (isUpLoad) {
                            loadMoreData();
                        }
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 上拉加载更多数据
     */
    private void loadMoreData() {
        if (isUploading) {
            return;
        }
        if (isBottom && isUpLoad) {
            isUploading = true;
            //展示脚布局
            FooterviewLvBinding footerViewBinding = DataBindingUtil.inflate(inflater, R.layout.footerview_lv, null, 
                    false);
            final View footerView = footerViewBinding.getRoot();
            listView.addFooterView(footerView);

            //模拟加载数据 ==》这段代码是可以实现的
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //模拟加载数据
                    for (int i = 0; i < 10; i++) {
                        list.add("上拉加载出来的数据" + System.currentTimeMillis() + "----" + i);
                    }
                    isUploading = false;
                    isBottom = false; //实际项目中，根据网络请求回来的数据进行处理，如果请求回来的数据集合为空则不用置为false，如果请求回来的数据不为空，则置为false
                    listView.removeFooterView(footerView);  //必须放在这里。放在外面就不显示脚布局了
                }
            }, 5000);

            adapter.notifyDataChanged(list);   //这个通知更新必须放在外面，放里面就会报错：提示是否在非主线程更新了adapter或者未通知数据更新
        }
    }

    /**
     * 下拉刷新，获取最新数据
     */
    private void getLastestData() {
        if (isRefreshing) {     //防止当前刷新没执行完毕时再次请求
            return;
        }

        if (isTop) { //如果是下拉且在最顶部，执行刷新的逻辑
            isRefreshing = true;

            binding.setShowPB(View.VISIBLE);
            for (int i = 0; i < 10; i++) {
                list.add(0, "下拉出来的数据" + System.currentTimeMillis() + "/" + i);
            }

            //延时通知数据更新
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataChanged(list);    //但是这个通知更新写在这里就没问题呢？handler.postDelayed（）确实是在主线程啊？？？
                    binding.setShowPB(View.GONE);
                    isRefreshing = false;//
                }
            }, 1000);
        }
    }
}


