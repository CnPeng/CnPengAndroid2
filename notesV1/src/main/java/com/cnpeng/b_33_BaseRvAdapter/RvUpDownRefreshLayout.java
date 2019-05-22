package com.cnpeng.b_33_BaseRvAdapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.Serializable;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;


/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/12/12:下午15:35
 * <p>
 * 说明：RecyclerView上拉下拉的刷新控件，主要借鉴了 ListView 的 UpDownRefreshLayout
 * 使用方法说明：
 * 1、如果 RecyclerView 是该布局的第一子View，只需要让该布局所在的界面（activity或者Fragment）实现 下拉接口OnDownRefreshListener、上拉接口OnUpLoadingListener，
 * 然后就可以实现下拉刷新和上拉加载更多的场景。需要注意的是，在请求服务器过程中需要动态的通过 setLoadingMore()、setDownRefreshing()更新上拉和下拉的加载状态，
 * 上拉或者下拉完毕传入false，开始上拉/下拉传入true。
 * <p>
 * 2、如果RecyclerView不是第一子view，则通过 bindRvToRefreshLayout(),将二者绑定.其他事项同1中说明
 */
public class RvUpDownRefreshLayout extends SwipeRefreshLayout implements Serializable {

    //源码中的相关定义
    private static final int   DEFAULT_CIRCLE_TARGET = 64;
    private static final int   CIRCLE_DIAMETER       = 40;
    private static final float SHADOW_RADIUS         = 3.5f;

    private int                 mDownY;
    private int                 mCircleImageViewHeight;
    private float               mDensity;
    private RecyclerView        mRv;
    private OnUpLoadingListener mOnUpLoadingListener;
    private int                 mDownX;
    /**
     * 是否正在执行加载更多的操作
     */
    private boolean             isLoadingMore = false;
    /**
     * 最小移动距离
     */
    private int                 mTouchSlop;

    private boolean mIsAtBottom;
    private int     mLastY;


    public RvUpDownRefreshLayout(Context context) {
        super(context);
        initRefreshLayout(context);
    }

    public RvUpDownRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRefreshLayout(context);
    }

    /**
     * 如果Rv不是RecyclerView的第一个子View，使用该方法将RecyclerView与刷新控件绑定，
     */
    public void bindRvToRefreshLayout(RecyclerView recyclerView) {
        this.mRv = recyclerView;
        mRv.setOnScrollListener(new RvScrollListener());
    }

    /**
     * 初始化刷新控件
     */
    private void initRefreshLayout(Context pContext) {
        //计算圆形滚动条的高度
        mDensity = pContext.getResources().getDisplayMetrics().density;
        int shadowRadius = (int) (mDensity * SHADOW_RADIUS);
        int diameter = (int) (CIRCLE_DIAMETER * mDensity);
        mCircleImageViewHeight = diameter + shadowRadius * 2;
        mTouchSlop = ViewConfiguration.get(pContext).getScaledTouchSlop();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //实例化RecyclerView
        if (mRv == null) {
            int chilCount = getChildCount();
            if (chilCount > 0) {
                View childView = getChildAt(0);
                if (childView instanceof RecyclerView) {
                    mRv = (RecyclerView) childView;
                    //给RecyclerView设置滚动监听事件
                    //mRv.clearOnScrollListeners();
                    //mRv.addOnScrollListener(mRvScrollListener);
                    mRv.setOnScrollListener(new RvScrollListener());
                }
            }
        } else {
            //mRv.clearOnScrollListeners();
            //mRv.addOnScrollListener(mRvScrollListener);
            mRv.setOnScrollListener(new RvScrollListener());
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //170915 终于解了banner侧滑触发下拉的情况，使用onInterceptTouchEvent 替代 disPatchTouchEvent ，然后左右划不拦截事件，垂直时交由其父类处理
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownY = (int) ev.getY();
                mDownX = (int) ev.getX();

                if (null != mRv) {
                    //解决RV的0索引条目未展示出来时会触发下拉的情况。
                    RecyclerView.LayoutManager layoutManager = mRv.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        int firstCompletelyVisibleItem = ((LinearLayoutManager) layoutManager)
                                .findFirstCompletelyVisibleItemPosition();
                        //                        LogUtils.e("触摸时第一条完整可见的条目",firstCompletelyVisibleItem+"");
                        if (firstCompletelyVisibleItem > 0) {
                            return false;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int curX = (int) ev.getX();
                int curY = (int) ev.getY();
                int absX = Math.abs(curX - mDownX);
                int absY = Math.abs(curY - mDownY);
                //LogUtils.e("移动的绝对值", absX + "/" + absY);

                if (absX > absY) {
                    //左右划的时候不拦截，直接传递给子View
                    return false;
                } else {
                    if (curY - mDownY < 0 && absY > mTouchSlop && mIsAtBottom) {
                        if (!isLoadingMore && null != mOnUpLoadingListener) {
                            mOnUpLoadingListener.releaseToLoadMore();
                        }
                    } else {
                        if (!isLoadingMore && null != mOnUpLoadingListener) {
                            mOnUpLoadingListener.clearUpLoadHint();
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mLastY = (int) ev.getY();
                //                LogUtils.e("触摸位置", mDownY + "/" + lastY);
                if (mLastY - mDownY < 0 && Math.abs(mLastY - mDownY) > mTouchSlop && mIsAtBottom) {
                    if (!isLoadingMore) {
                        //                        LogUtils.e("触摸上拉", "TRUE");
                        isLoadingMore = true;
                        loadUpData();
                    }
                }

                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 加载上拉的数据
     */

    private void loadUpData() {
        if (mOnUpLoadingListener != null) {
            mOnUpLoadingListener.onUpLoading();
        }
    }

    /**
     * 是否只进行上拉而不允许下拉
     * 如果为true，下拉无效果
     *
     * @param isOnlyUpLoading true 只能上拉，false 既能上拉也能下拉，默认false
     */
    public void setOnlyUpLoading(boolean isOnlyUpLoading) {
        if (isOnlyUpLoading) {
            setProgressViewOffset(false, -mCircleImageViewHeight, -mCircleImageViewHeight);
        } else {
            setProgressViewOffset(false, -mCircleImageViewHeight, (int) (DEFAULT_CIRCLE_TARGET * mDensity - mCircleImageViewHeight));
        }
    }

    /**
     * 是否正在下拉刷新
     */
    public boolean isDownRefreshing() {
        return isRefreshing();
    }

    /**
     * 更新下拉状态
     */
    public void setDownRefreshing(boolean pIsDownRefreshing) {
        //        if (!pIsDownRefreshing) {
        setRefreshing(pIsDownRefreshing);
        //        }
    }

    /**
     * 是否正在执行上拉加载更多的操作
     */
    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    /**
     * 更新上拉状态。true 正在上拉，false 上拉完成
     */
    public void setLoadingMore(boolean loadingMore) {
        isLoadingMore = loadingMore;
    }

    /**
     * 设置上拉刷新监听事件
     */
    public void setOnUpRefreshListener(OnUpLoadingListener pListener) {
        mOnUpLoadingListener = pListener;
    }

    /**
     * 设置下拉刷新监听事件
     */
    public void setOnDownRefreshListener(OnDownRefreshListener pListener) {
        setOnRefreshListener(pListener);
    }

    class RvScrollListener extends RecyclerView.OnScrollListener {
        int firstVisibleItem, visibleItemCount, totalItemCount, lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (SCROLL_STATE_IDLE == newState) {
                if (mIsAtBottom && mLastY - mDownY < 0 && Math.abs(mLastY - mDownY) > mTouchSlop) {
                    if (!isLoadingMore) {
                        //                        LogUtils.e("滚动上拉", "TRUE");
                        isLoadingMore = true;
                        loadUpData();
                    }
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisibleItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

                //                LogUtils.e("可见的条目信息：", "visibleItemCount：" + visibleItemCount + "/totalItemCount："
                // + totalItemCount +
                //                        "/firstVisibleItem：" + firstVisibleItem + "/lastVisibleItem：" +
                // lastVisibleItem);

                mIsAtBottom = (totalItemCount - visibleItemCount) <= firstVisibleItem;
            }
        }
    }

    /**
     * 上拉刷新的监听接口
     */
    public interface OnUpLoadingListener {
        /**
         * 执行加载更多的操作
         */
        void onUpLoading();

        /**
         * 释放加载更多，主要是为了通知外部更新脚布局
         */
        void releaseToLoadMore();

        /**
         * 清除上拉加载更多的提示--用户触摸时，先上拉在下拉可能就会导致上拉提示语一直存在的情况，所以，当用户下拉时，给出该方法清除提示
         */
        void clearUpLoadHint();
    }

    /**
     * 下拉刷新的监听接口,
     * 因为实现了 SwipeRefreshLayout 的 OnRefreshListener, 所以，不需要再单独监听下拉事件
     */
    public interface OnDownRefreshListener extends OnRefreshListener {
    }
}
