package com.cnpeng.b_06_ObjectAnimator;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/4/11:下午2:16
 * <p>
 * 说明：本来是写属性动画来着。写着写着就写成了 如何去掉LV上方的控件。之所以没用头布局，是为了考虑当前项目中的广场列表界面
 * 广场列表界面中， 是 VP +TAB + FM ，为了保留VP的左右滑动，就所以写了这个DEMO。DEMO中虽然在滑动到顶部的时候会出现闪
 * 的状况，但是移植到项目中之后，完全不好使了。。。。。。。。。这真是简直了。。。。。
 */

public class ObjectAnimatorAndListViewActivity extends AppCompatActivity {

    private boolean      isLvTop;    //是否到了LV的最顶部
    private int          startY;
    private boolean      isTransUp;  //是否正在上移
    private LinearLayout ll_oaAndLvContainer;
    private Button       bt_titleAboveLv;
    private Button       bt_transUp;
    private ListView     lv_oaAndLv;
    private boolean      isTransDown;
    private int          curY;
    private boolean      isLastItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oa_and_lv);

        init();
    }

    private void init() {
        lv_oaAndLv = (ListView) findViewById(R.id.lv_oaAndLv);
        bt_titleAboveLv = (Button) findViewById(R.id.bt_titleAboveLv);
        ll_oaAndLvContainer = (LinearLayout) findViewById(R.id.ll_OaAndLvContainer);
        //添加滚动监听，判断是不是在最顶部
        lv_oaAndLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //如果是下移。到了LV最顶部，且bt_titleAboveLv 当前不可见，则直接下移动作，将其回复到原位
                if (firstVisibleItem == 0) {
                    View firsttemView = view.getChildAt(0);  //获取0索引条目
                    if (firsttemView != null) {
                        isLvTop = firsttemView.getTop() == 0;     //如果上面还有控件，需要获取控件的高度，跟控件去做对比
                        //最顶部下拉的方法
                        showSearcheHintView();
                    }
                }

                //是否到了最后一个条目（只判断是否是最后一个条目即可）
                isLastItem = totalItemCount == firstVisibleItem + visibleItemCount;
            }
        });

        //添加触摸监听，判断是上拉还是下拉
        lv_oaAndLv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:   //触摸起始点
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:   //触摸中
                        curY = (int) event.getY();
                        isTransUp = curY < startY;    //是否正在上移
                        isTransDown = curY > startY;   //是否正在下拉
                        showSearcheHintView();
                    case MotionEvent.ACTION_UP:     //抬手的触摸点
                        curY = (int) event.getY();
                        isTransUp = curY < startY;    //是否正在上移
                        isTransDown = curY > startY;   //是否正在下拉
                        showSearcheHintView();
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 如果已经到了最顶部，且正在下拉，则将移出去的内容再移回来
     */
    private void showSearcheHintView() {
        if (isLvTop && isTransDown) {   //如果在LV顶部，且正在执行下拉。整体下移,展示顶部控件
            if (View.GONE == bt_titleAboveLv.getVisibility()) {
                bt_titleAboveLv.setVisibility(View.VISIBLE);
                
//                //使用属性动画，虽然是将内容挪出去了，但是还占着位置啊！！！！！！！
//                ObjectAnimator showAnimator=ObjectAnimator.ofFloat(bt_titleAboveLv,"translationY",0,100);
//                showAnimator.setDuration(300);
//                showAnimator.start();
            }
        }

        //        if (isLvTop && isTransUp) {    //如果在LV顶端，且在执行上拉，则gone掉最顶部控件
        //如果在LV顶端，且在执行上拉，则gone掉最顶部控件 TODO还需要判断LV的高度是否高于窗体，如果小于窗体就不要拿掉控件了
        if (isTransUp && !isLastItem) {
            bt_titleAboveLv.setVisibility(View.GONE);
//            ObjectAnimator showAnimator=ObjectAnimator.ofFloat(bt_titleAboveLv,"translationY",0,-100);
//            showAnimator.setDuration(300);
//            showAnimator.start();
        }
    }
}
