package com.cnpeng.b_04_suspendAndListView;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cnpeng.cnpeng_demos2017_01.R;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/3/15:下午2:30
 * <p>
 * 说明：上拉lv 的时候，将其中指定的条目固定在页面顶部，也就是条目悬浮效果
 * <p>
 * 这个项目中实现的是单一的条目悬浮，并不是通讯录中的那种多条目悬浮效果。
 * <p>
 * 在实现这种单一条目悬浮的效果时也可以使用CoordinaterLayout(布局协调者)实现，但是，使用布局协调者有两个问题，一个是上拉的时候会有卡顿的
 * 状况（在我司的项目中，我应用的时候得到的确是下拉卡顿），由于卡顿就无法展现fling效果；另外，如果顶部内容过多，不方便实现其中包含的滚动view的下拉刷新
 * <p>
 * 所以，还是使用这种LV实现。代码简单，而且方便实现下拉刷新的效果。使用LV实现的时候有两点注意： 1）
 * 必须在定义完lv之后再定义悬浮部分，也就是说把悬浮条目放在上面，否则lv滚动的时候条目会覆盖在悬浮层上，而且也能看到明显的悬浮条目切换过程
 * 2） 在给lv添加头布局的时候，需要悬浮展示的地方必须与要滚出去的部分分开添加，这样悬浮窗的切换才流畅！！！如果将悬浮的部分和滚出去的部分直接
 * 写在了同一个布局文件中，那么在切换悬浮窗的时候会有明显的视觉差异，能明显看出来悬浮部分是后来补上的
 * <p>
 * 其他需要注意： 这个示例demo中，是直接在布局文件中通过ListView的entries属性给lv设置了条目内容，并在activity代码中添加头布局，这种方式在4.1及之前的版本 中会崩溃，错误提示是：Cannot add
 * header view to list -- setAdapter has already been called.在添加头布局之前已经设置了适配器。 但是在4.4 及之后的版本中可以正常运行。
 */

public class SuspendAndListViewActivity extends AppCompatActivity {
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky);
        final TextView suspend_backup = (TextView) findViewById(R.id.tv_suspend_backup);

        lv = (ListView) findViewById(R.id.lv);
        View headerView1 = getLayoutInflater().inflate(R.layout.stick_header, null);//头部内容
        lv.addHeaderView(headerView1);//添加头部
        //注意，需要悬浮展示的地方必须与要滚出去的部分分开添加，这样悬浮窗的切换才流畅！！！
        // 如果将悬浮的部分和滚出去的部分直接写在了同一个布局文件中，那么在切换悬浮窗的时候会有明显的视觉差异，能明显看出来悬浮部分是后来补上的
        final View headerView2 = getLayoutInflater().inflate(R.layout.stick_action, null);//头部内容
        lv.addHeaderView(headerView2);//ListView条目中的悬浮部分 添加到头部

        final CharSequence text = ((TextView) headerView2.findViewById(R.id.tv_suspend)).getText();

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                //1 控制是否需要展示悬浮条
                if (firstVisibleItem >= 1) {    //因为加了两个头布局，所以从2 开始才是真正的数据内容
                    suspend_backup.setVisibility(View.VISIBLE);
                    suspend_backup.setText(text);   //滚动的过程中，动态设置TV的text
                } else {
                    suspend_backup.setVisibility(View.GONE);
                }
            }
        });
    }
}
