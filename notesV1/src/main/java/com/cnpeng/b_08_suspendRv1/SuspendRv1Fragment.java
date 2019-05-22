package com.cnpeng.b_08_suspendRv1;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cnpeng.cnpeng_demos2017_01.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/4/12:下午6:42
 * <p>
 * 说明：每个Tab 标题对应的内容。该界面需要使用RV列表。考虑，如果实现 RV的 上拉加载更多
 */

public class SuspendRv1Fragment extends Fragment {

    private int               index;     //当前被选中的标题索引
    private View              view;      //Fragment 中的视图view
    private List<String>      items;
    private SuspendRv1Adapter rvAdapter;


    public static Fragment getInstance(int position) {
        SuspendRv1Fragment fragment = new SuspendRv1Fragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        index = getArguments().getInt("index"); //获取传递过来的索引
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable
                                     ViewGroup container, Bundle savedInstanceState) {
        //  if (null != view) {     //防止重复创建
        //      ViewGroup parent = (ViewGroup) view.getParent();
        //      if (parent != null) {
        //          parent.removeView(view);
        //      }
        // }

        items = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            items.add(index + "——" + (char) i + "----------------------------------------");
        }

        view = inflater.inflate(R.layout.fm_cltbvprv, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_clTbVpRv);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());

        //        com.cnpeng.utils.DividerItemDecoration mDividerItemDecoration = new com.cnpeng
        //                .cnpeng_demos2017_01.utils.DividerItemDecoration(getActivity(), 1);

        recyclerView.addItemDecoration(mDividerItemDecoration);

        recyclerView.setLayoutManager(mLayoutManager);

        rvAdapter = new SuspendRv1Adapter(items, getActivity());
        recyclerView.setAdapter(rvAdapter);

        initPull2Refresh(view);
        return view;
    }

    /**
     * 初始化下拉刷新监听
     */
    private void initPull2Refresh(View fragmentView) {
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.srl_ClTbVpRv);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //模拟更新数据，并关闭刷新
                for (int i = 0; i < 10; i++) {
                    items.add("下拉出来的数据" + i);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);    //关闭刷新
                    }
                }, 2500);

                rvAdapter.setNewList(items);
            }
        });
    }
}
