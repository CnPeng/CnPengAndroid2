package com.cnpeng.b_21_listViewLocalRefresh;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.LocalRefreshLVBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/8/24:下午8:13
 * <p>
 * 说明：ListView 单条局部刷新的实现--点击之后更新局部条目
 */

public class ListViewLocalRefreshActivity extends AppCompatActivity {

    private LocalRefreshLVBinding    binding;
    private LvAdapter_LocalRefreshLv adapter;

    @Override
    protected void onCreate(
            @Nullable
                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lv_local_refresh);

        initListView();
        initLvITemClickEvent();
        setDataToLv();
    }

    private void initListView() {
        adapter = new LvAdapter_LocalRefreshLv(ListViewLocalRefreshActivity.this);
        binding.lvLocalRefresh.setAdapter(adapter);
    }

    private void setDataToLv() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            userList.add(new User("张三", i));
        }

        if (null != adapter) {
            adapter.setList(userList);
        }
    }

    private void initLvITemClickEvent() {
        binding.lvLocalRefresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User clickedUser = (User) parent.getAdapter().getItem(position); //获取被点击的bean

                clickedUser.setName(clickedUser.getName() + position);  //点击后更改数据

                updateSingleItemData(clickedUser);      //更新条目view
            }
        });
    }

    /**
     * 更新单条数据，由于LV的复用机制，所以需要获取第一条可见和最后一条可见，然后获取对应的bean，然后判断该bean是否与被点击的bean一致
     */
    private void updateSingleItemData(User clickedUser) {
        int firstVisiableIndex = binding.lvLocalRefresh.getFirstVisiblePosition();
        int lastVisiableIndex = binding.lvLocalRefresh.getLastVisiblePosition();

        for (int i = firstVisiableIndex, j = lastVisiableIndex; i <= j; i++) {
            User user = (User) binding.lvLocalRefresh.getItemAtPosition(i);  //获取 i 索引出的bean
            if (user.getId() == clickedUser.getId()) {  //如果数据一致
                //获取对应的条目view（由于复用机制，所以此处需要 i- firstVisiableIndex）
                View view = binding.lvLocalRefresh.getChildAt(i - firstVisiableIndex);

                adapter.updateChangedItemBean(i, clickedUser);//此处千万不要忘了更新适配器中的集合数据，否则adapter中的数据无变化view自然也不会变

                adapter.getView(i, view, binding.lvLocalRefresh);
            }
        }
    }
}
