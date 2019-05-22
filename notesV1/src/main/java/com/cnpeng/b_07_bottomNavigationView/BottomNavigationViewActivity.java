package com.cnpeng.b_07_bottomNavigationView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/4/11:下午8:32
 * <p>
 * 说明：安卓原生底部导航栏 BottomNavigationView 的基本使用示例
 * <p>
 * 使用时需要先引入 design 包
 * <p>
 * 底部Item的数量不能超过5 ，否则会报错，报错信息如下： java.lang.IllegalArgumentException: Maximum number of items supported by
 * BottomNavigationView is 5. Limit can be checked with BottomNavigationView#getMaxItemCount()
 * <p>
 * 当item超过3个时，未被选中的item 只显示图标，只有被选中的才会显示图标和文字；而3个及3个以内时图标和文字都会显示
 * <p>
 * 如果底部Item 想实现小红点，就无法使用BottomNavigationView 了 。/(ㄒoㄒ)/~~
 */

public class BottomNavigationViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_view);

        final TextView tv_whichItemSelected = (TextView) findViewById(R.id.tv_whichItemSelected);

        BottomNavigationView bnv_001 = (BottomNavigationView) findViewById(R.id.bnv_001);

        //为底部导航设置条目选中监听
        bnv_001.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(
                    @NonNull
                            MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        tv_whichItemSelected.setText(item.getTitle());
                        break;
                    case R.id.item2:
                        tv_whichItemSelected.setText(item.getTitle());
                        break;
                    case R.id.item3:
                        tv_whichItemSelected.setText(item.getTitle());
                        break;
                    case R.id.item4:
                        tv_whichItemSelected.setText(item.getTitle());
                        break;
                    case R.id.item5:
                        tv_whichItemSelected.setText(item.getTitle());
                        break;
                }
                
                return true;    //这里返回true，表示事件已经被处理。如果返回false，为了达到条目选中效果，还需要下面的代码 
                // item.setChecked(true);  不论点击了哪一个，都手动设置为选中状态true（该控件并没有默认实现)
                // 。如果不设置，只有第一个menu展示的时候是选中状态，其他的即便被点击选中了，图标和文字也不会做任何更改

            }
        });

        //默认选中底部导航栏中的第三个
        bnv_001.getMenu().getItem(2).setChecked(true);
    }
}
