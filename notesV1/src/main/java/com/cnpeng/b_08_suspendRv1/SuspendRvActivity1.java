package com.cnpeng.b_08_suspendRv1;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.utils.LogUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/4/12:下午4:41
 * <p>
 * 说明：CoordinatorLayout + ViewPager + TabLayout + RecyclerView + AppBarLayout + CollapsingToolbarLayout
 * 整合以上控件实现，上滑时隐藏最顶部的内容，下拉LV到顶时展示隐藏的内容，并且能实现左右侧滑。
 * <p>
 * 注意：使用这种方式，在下拉时如果想让RV 到顶后再下拉 AppBar中的内容，那么不可避免的就是，在下拉的 Fling 时 ，会先在TabLayout 处停顿一下。
 * 如果在下拉的同时就下来 AppBar 和 Rv ，则不会产生卡顿，如果 AppBar中的内容比较少的时候，可以使用这种方式，即 scroll|enterAlways
 * <p>
 * TODO 下一步要实现的是：RV的上拉和下拉、考虑在展示标题栏的放大镜时加一个透明度渐变
 */

public class SuspendRvActivity1 extends AppCompatActivity {

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cltbvprv);

        init();
    }

    private void init() {
        //获取控件
        final TextView tv_searchBar = (TextView) findViewById(R.id.tv_search_clTbVpRv);
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_clTbVpRv);
        tabLayout = (TabLayout) findViewById(R.id.tb_clTbVpRv);

        //设置 顶部搜索框TV 的点击事件
        tv_searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SuspendRvActivity1.this, "顶部搜索栏被点击了", Toast.LENGTH_SHORT).show();
            }
        });

        //关联TB和VP
        tabLayout.setupWithViewPager(viewPager, true);


        //初始化 TB 的标题
        String[] tbTitles = {"最新", "精选", "关注"};

        //给VP添加适配器
        FragmentManager fm = getSupportFragmentManager();
        SupendRv1ViewPagerAdapter adapter = new SupendRv1ViewPagerAdapter(fm, tbTitles);
        viewPager.setAdapter(adapter);

        final AppBarLayout appBar_clTbVpRv = (AppBarLayout) findViewById(R.id.appBar_clTbVpRv);

        //当界AppBar 滚动的时候，动态监听其垂直滚动的距离。上移是负值，下移是正值。
        // 当appBar的高度+垂直偏移量=0 ，表示appBar已经移除界面了；当 垂直偏移量=0 ，表示appBar正在被展示
        //由于ViewPager 的预加载机制，初次进入页面时，会走两次这个监听
        appBar_clTbVpRv.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                LogUtils.e("顶部标题的高度：", appBar_clTbVpRv.getHeight() + "____" + "垂直偏移量：" + verticalOffset);

                TextView tv_titleBar = (TextView) findViewById(R.id.tv_titleBar_ClTbVpRv);

                int appBarHeight = appBar_clTbVpRv.getHeight();

                //使用这种方式的话，实现的效果是，初始的时候在顶部会有一个搜索提示框，上拉之后提示框滚出去就滚不回来了，然后在标题栏给出一个搜索放大镜
                if (appBarHeight + verticalOffset == 0) {    //说明appBar 已经滚出去看不见了
                    LogUtils.e("appBar滚出去了：", "展示标题栏的搜索按钮");
                    Toast.makeText(SuspendRvActivity1.this, "appBar滚出去了，展示标题栏的搜索按钮", Toast.LENGTH_SHORT).show();
                    tv_titleBar.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable
                            (android.R.drawable.ic_search_category_default), null);
                    appBar_clTbVpRv.removeAllViews();

                    appBar_clTbVpRv.removeOnOffsetChangedListener(this);    //达到条件后，移除监听
                }

                //使用这种方式，既能滚出去也能滚回来，但是在RV滚到顶端时会停住，再次下拉才能显示AppBar
                // if (appBarHeight + verticalOffset == 0) {    //说明appBar 已经滚出去看不见了
                //     LogUtils.e("appBar滚出去了：", "展示标题栏的搜索按钮");
                //     Toast.makeText(ClTbVpRvActivity.this, "appBar滚出去了，展示标题栏的搜索按钮", Toast.LENGTH_SHORT).show();
                //     tv_titleBar.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable
                //             (android.R.drawable.ic_search_category_default), null);
                //     appBar_clTbVpRv.removeAllViews();
                // }
                //
                // if (verticalOffset == 0) {  //此时应该先判断标题栏的搜索按钮的显示属性，如果当前可见，则隐藏。如果当前可见，不做操作
                //     LogUtils.e("appBar滚回来了：", "隐藏标题栏的搜索按钮");
                //     Toast.makeText(ClTbVpRvActivity.this, "appBar滚回来了，隐藏标题栏的搜索按钮", Toast.LENGTH_SHORT).show();
                //     Drawable[] drawables = tv_titleBar.getCompoundDrawables();
                //     if (drawables.length > 0) {
                //         tv_titleBar.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                //     }
                // }
            }
        });

        initTabLayoutTabBK();

    }

    private void initTabLayoutTabBK() {
        int tabs = tabLayout.getTabCount();
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        TextView textView = new TextView(SuspendRvActivity1.this);
        textView.setText("XXXXXX");
        tab.setCustomView(textView);
    }
}



