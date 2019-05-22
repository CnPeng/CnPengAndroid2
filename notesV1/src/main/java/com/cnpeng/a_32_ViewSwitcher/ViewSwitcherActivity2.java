package com.cnpeng.a_32_ViewSwitcher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CnPeng on 2017/2/7.
 * <p>
 * 使用ViewSwitcher仿写安卓Lanucher界面
 */

public class ViewSwitcherActivity2 extends AppCompatActivity implements View.OnClickListener {

    private ViewSwitcher viewSwitcher;
    private List<App> appList    = new ArrayList<>();      //模拟的全部app集合
    private int       eveCount   = 12;                     //每页展示的数据量
    private int       curPageNum = -1;                     //记录当前显示的是第几页(第几页的索引)
    private int            totalPage;                      //记录总共需要多少页，默认为0
    private Animation      inAnimation;
    private Animation      outAnimation;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewswitcher2);

        init();
    }

    private void init() {
        viewSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher02);
        Button bt_pre = (Button) findViewById(R.id.bt_pre);
        Button bt_next = (Button) findViewById(R.id.bt_next);
        bt_pre.setOnClickListener(this);
        bt_next.setOnClickListener(this);

        inflater = LayoutInflater.from(this);

        // 为ViewSwitcher填充一个GridView作为子View       
        viewSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return inflater.inflate(R.layout.slidelauncher, null);  //填充一个GridView作为ViewSwitcher的子vew
            }
        });

        // 模拟数据，循环添加45个对象       
        for (int i = 0; i < 49; i++) {
            App app = new App();
            app.name = "应用" + i;
            app.icon = R.drawable.icon;
            appList.add(app);
        }

        //计算总共需要展示多少页
        totalPage = appList.size() % eveCount == 0 ? appList.size() / eveCount : appList.size() / eveCount + 1;

        //准备动画
        inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        outAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        viewSwitcher.setInAnimation(inAnimation);
        viewSwitcher.setOutAnimation(outAnimation);

        //默认展示第一页，索引为0
        next();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_pre:   //显示前一个
                previous();
                break;
            case R.id.bt_next:  //显示下一个
                next();
                break;

        }
    }

    /**
     * 展示上一页内容
     */
    private void previous() {
        if (curPageNum > 0) {
            curPageNum--;
            ((GridView) (viewSwitcher).getNextView()).setAdapter(adapter);

            viewSwitcher.showPrevious();
        }
    }

    /**
     * 展示下一页的数据
     */
    private void next() {
        if (curPageNum < totalPage - 1) {   //因为显示下一页的时候，curPageNum要自增加一，所以需要在判断时-1
            curPageNum++;

            //准备下一页的内容：强转ViewSwitvher为GridView.然后获取GridView的nextView ,最后给这个nextView 设置adapter
            ((GridView) (viewSwitcher).getNextView()).setAdapter(adapter);

            //显示下一页内容
            viewSwitcher.showNext();
        }
    }

    /**
     * 内部类表示要展示的数据信息
     */
    private class App {
        String name;    //应用岷城
        int    icon;    //应用图标对应的id
    }

    //定制适配器（如果直接使用的是SimpleAdapter的话，每个页面显示的内容都是一样的；要想控制每页显示的内容不同，只能通过定制BaseAdapter）
    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() { //每一页要展示的数量
            if (curPageNum == totalPage - 1 && appList.size() % eveCount != 0) {  //curPageNum 表示索引，是从0开始的，所以要减一
                return appList.size() % eveCount;
            }
            return eveCount;
        }

        @Override
        public Object getItem(int position) {   //每一页中position位置要展示的条目内容
            return appList.get(curPageNum * eveCount + position);
        }

        @Override
        public long getItemId(int position) {   //每一页中position位置展示内容在集合中的id
            return curPageNum * eveCount + position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) { //配置条目具体数据
            Holder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_gv, null);
                holder = new Holder();
                holder.tv_name = (TextView) convertView.findViewById(R.id.tv_gv_vs);
                holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_gv_vs);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            holder.tv_name.setText(appList.get(curPageNum * eveCount + position).name);
            holder.iv_icon.setImageResource(appList.get(curPageNum * eveCount + position).icon);
            return convertView;
        }

        class Holder {   //适配器中定义holder
            TextView  tv_name;
            ImageView iv_icon;
        }
    };
}
