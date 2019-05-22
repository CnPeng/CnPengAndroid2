package com.cnpeng.b_27_suspendRv;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cnpeng.utils.LogUtils;
import com.google.android.material.appbar.AppBarLayout;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/12/12:上午10:18
 * <p>
 * 说明：自定义的AppBarLayout.Behavior，解决使用 CoordinatorLayout + AppBarLayout + Rv 实现顶部悬浮条时，下拉不具有Fling惯性效果的问题。
 * * ATTENTION 注意：如果需要改名字或者挪到其他包中，必须全局搜索使用的位置，同时更改，否则，可能会出现下拉不具有Fling惯性的情况
 */

public final class CustomFlingBehavior extends AppBarLayout.Behavior {
    private static final int TOP_CHILD_FLING_THRESHOLD = 3;
    private boolean isPositive;

    public CustomFlingBehavior() {
    }

    public CustomFlingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX,
                                 float velocityY, boolean consumed) {
        if (velocityY > 0 && !isPositive || velocityY < 0 && isPositive) {
            velocityY = velocityY * -1;
        }

        if (target instanceof SwipeRefreshLayout && velocityY < 0) {
            target = ((SwipeRefreshLayout) target).getChildAt(0);
        }
        if (target instanceof RecyclerView && velocityY < 0) {
            final RecyclerView recyclerView = (RecyclerView) target;
            //            final View firstChild = recyclerView.getChildAt(0);
            //            final int childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild);
            //            consumed = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                int firstCompletedVisibleItem = ((LinearLayoutManager) layoutManager)
                        .findFirstCompletelyVisibleItemPosition();
                LogUtils.e("第一条完整可见条目索引", firstCompletedVisibleItem + "/");
                consumed = 0 != firstCompletedVisibleItem;
            }
        }
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy,
                                  int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        isPositive = dy > 0;
    }
}
