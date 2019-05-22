package com.cnpeng.b_02_DrawerLayoutAndImmersion;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.google.android.material.tabs.TabLayout;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/3/3:下午4:10
 * <p>
 * 说明： 给DrawerLayout的侧边栏实现沉浸式
 */

public class DrawerLayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawerlayout);

        //最终方案,设置状态栏透明的最终方案
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 全透明实现
            //getWindow.setStatusBarColor(Color.TRANSPARENT)
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
                    .SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        init();
    }

    private void init() {
        //将toolBar作为ActionBar使用
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        //DrawerLayout
        DrawerLayout draweLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        //让侧滑栏包含标题栏
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, draweLayout, toolbar, R.string.app_name, R
                .string.app_name);
        draweLayout.addDrawerListener(toggle);
        toggle.syncState(); //同步状态

        // 直接设置ToolBar 的fitSystem...属性出问题时使用这种，使用这种方式时需要给ToolBar 外面套一层相对布局
        //  //获得状态栏高度
        //  int resourceId= getResources().getIdentifier("status_bar_height", "dimen", "android");
        //  int stateBarHeight =  getResources().getDimensionPixelSize(resourceId);
        //  //设置margin
        //  RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
        //  layoutParams.setMargins(0, stateBarHeight, 0, 0);
        //  toolbar.setLayoutParams(layoutParams);

        TabLayout tablayout = (TabLayout) findViewById(R.id.tableLayout);
        for (int i = 0; i < 6; i++) {
            // 创建Tab的同时设置tab描述文字以及tab的tag，并添加到tablayout中 . Tab只能用在代码中，不能用在xml          
            tablayout.addTab(tablayout.newTab().setText("代码中定义的" + i + "号标签").setTag(i));
        }

    }
}
