package com.cnpeng.b_33_BaseRvAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.FooterRvBinding;
import com.cnpeng.utils.LogUtils;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * 作者：CnPeng
 * 时间：2018/6/14
 * 功用：RecyclerView 的 Adapter 基类。
 * 其他：
 * 1、附带了上拉加载的功能处理，下拉加载通过SwipeRefreshLayout实现.
 * 2、暂时只支持 LinearLayoutManager
 * // TODO: CnPeng 2018/6/15 上午9:07 考虑下拉时头布局的封装
 * // TODO: CnPeng 2018/6/15 下午4:10 考虑如何兼容 项目中的 RvUpDownRefreshLayout
 */
public abstract class BaseRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * 正在加载
     */
    public final String STATUS_LOADING         = "loadingMore";
    /**
     * 加载完毕——没有更多
     */
    public final String STATUS_NO_MORE         = "noMore";
    /**
     * 加载完毕——有新的数据
     */
    public final String STATUS_OVER            = "loadingOver";
    /**
     * 释放加载更多，当最后一条完整可见条目为脚布局，并且用户还在下拉的时候触发
     */
    public final String STATUS_RELEASE_TO_LOAD = "releaseToLoad";

    private final int    ITEM_TYPE_HEADER = 0;
    private final int    ITEM_TYPE_FOOTER = 1;
    private final String TAG              = "BaseRvAdapter";
    private       String mLoadingStatus   = "";

    private OnLoadingMoreListener mLoadingMoreListener;
    private Context               mContext;
    private RecyclerView          mRv;
    /**
     * 是否正在执行加载更多的操作，可以避免上次请求未结束时重复发送请求的情况。内部处理之后，上拉监听中的onLoadingMore()中就不需要再做判断
     */
    private boolean               mIsLoadingMore;
    private boolean               mIsAtBottom;
    private int                   mLastY;
    private int                   mDownY;
    private int                   mDownX;
    private int                   mTouchSlop;
    private boolean               mFooterEnable;
    private View                  mHeaderView;

    /**
     * 基类的构造方法，子类的构造中必须传递这两个参数给该基类
     *
     * @param context      用来激活默认脚布局、获取触摸时的最小反馈量
     * @param recyclerView 用来监听和处理触摸和滚动事件，从而触发监听器中的上拉加载事件
     */
    public BaseRvAdapter(Context context, RecyclerView recyclerView) {
        // ATTENTION CnPeng 2018/6/15 上午8:55  子类的构造方法必须在此基础上扩展,context 和 recyclerView是BaseRvAdapter必须的
        mContext = context;
        mRv = recyclerView;

        //触摸时最小的响应距离
        mTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();


        // TODO: CnPeng 2018/6/14 下午9:48 如何确保最后一条完全可见的时候才上拉加载呢？
        initRvScrollListener();
        initRvTouchListener();
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    /**
     * 作者：CnPeng
     * 时间：2018/7/9 下午3:00
     * 功用：设置头布局
     * 说明：只允许添加一个头布局
     */
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        if (null != mHeaderView) {
            notifyItemChanged(0);
        }
    }

    /**
     * 作者：CnPeng
     * 时间：2018/7/9 下午2:59
     * 功用：获取头布局的数量
     * 说明：
     */
    public int getHeaderViewCount() {
        return null != mHeaderView ? 1 : 0;
    }

    /**
     * 只有在开启了脚布局之后才去触发这个状态更新的操作
     */
    public void setLoadingStatus(String loadingStatus) {
        if (mFooterEnable) {
            mLoadingStatus = loadingStatus;
            switch (mLoadingStatus) {
                case STATUS_LOADING:
                    mIsLoadingMore = true;
                    break;
                case STATUS_NO_MORE:
                case STATUS_OVER:
                case STATUS_RELEASE_TO_LOAD:
                default:
                    mIsLoadingMore = false;
                    break;
            }
            notifyItemChanged(getItemCount() - 1);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        switch (viewType) {
            case ITEM_TYPE_FOOTER:
                // TODO: CnPeng 2018/6/15 上午9:35 考虑如何由外部动态的配置脚布局view
                FooterRvBinding footerBinding = DataBindingUtil.inflate(inflater, R.layout.footer_rv, parent, false);
                View footerView = footerBinding.getRoot();
                FooterHolder footerHolder = new FooterHolder(footerView);
                footerHolder.setBinding(footerBinding);
                return footerHolder;
            case ITEM_TYPE_HEADER:
                return onCreateHeaderHolder(parent);
            default:
                return onCreateContentHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof FooterHolder) {
            updateFooterView((FooterHolder) holder);
        } else if (holder instanceof HeaderHolder) {
            if (null != mHeaderView) {
                HeaderHolder headerHolder = (HeaderHolder) holder;
                LinearLayout headerRoot = headerHolder.getHeaderView();
                if (0 != headerRoot.getChildCount()) {
                    //只允许设置一个头布局
                    headerRoot.removeAllViews();
                }
                headerRoot.addView(mHeaderView);
            }
        } else {
            // CnPeng 2018/7/9 下午3:01 由于增加了一个头布局，所以，传递给外部时position需要减1
            onBindContentHolder(holder, position - 1);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            return ITEM_TYPE_HEADER;
        } else {
            if (mFooterEnable) {
                if (position == getItemCount() - 1) {
                    return ITEM_TYPE_FOOTER;
                } else {
                    //此处做判断，防止子类定义的条目类型与该基类中定义的头布局和脚布局冲突
                    int contentType = getContentItemType(position - 1);
                    if (ITEM_TYPE_HEADER == contentType || ITEM_TYPE_FOOTER == contentType) {
                        new Exception("BaseRvAdapter:该条目类型已经在基类中定义为头布局/脚布局，不能子类重复定义").printStackTrace();
                    }
                    return getContentItemType(position - 1);
                }
            } else {
                //CnPeng 2018/7/9 下午3:02 由于添加了一个头布局，所以，传递具体位置到外部时需要-1
                return getContentItemType(position - 1);
            }
        }
    }

    @Override
    public int getItemCount() {
        //如果没有开启脚布局，数量+1，加的是头布局；如果开启了脚布局，数量再+1;
        return mFooterEnable ? getContentCount() + 1 + 1 : getContentCount() + 1;
    }

    /**
     * 作者：CnPeng
     * 时间：2018/7/9 下午2:21
     * 功用：头布局只预留一个空的viewGroup,具体由外部设置
     * 说明：
     */
    private RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        HeaderHolder headerHolder = new HeaderHolder(linearLayout);
        return headerHolder;
    }

    /**
     * 获取内容的数量
     */
    abstract int getContentCount();

    /**
     * 获取条目类型：0 和 1 已经被头布局和脚布局占用，不用重复定义
     */
    abstract int getContentItemType(int position);

    /**
     * 绑定内容视图
     */
    abstract void onBindContentHolder(RecyclerView.ViewHolder holder, int position);

    /**
     * 创建内容hodler
     */
    abstract RecyclerView.ViewHolder onCreateContentHolder(ViewGroup parent, int viewType);

    private void updateFooterView(FooterHolder holder) {
        switch (mLoadingStatus) {
            case STATUS_LOADING:
                //正在加载——展示进度和文本
                holder.mBinding.progressBar.setVisibility(View.VISIBLE);
                holder.mBinding.tvLoadingHint.setVisibility(View.VISIBLE);
                holder.mBinding.tvLoadingHint.setText("正在加载。。。");
                break;
            case STATUS_NO_MORE:
                //加载完毕——没有更多
                holder.mBinding.progressBar.setVisibility(View.GONE);
                holder.mBinding.tvLoadingHint.setVisibility(View.VISIBLE);
                holder.mBinding.tvLoadingHint.setText("没有更多数据了。。。");
                break;
            case STATUS_RELEASE_TO_LOAD:
                //加载完毕——还有更多数据
                holder.mBinding.progressBar.setVisibility(View.GONE);
                holder.mBinding.tvLoadingHint.setVisibility(View.VISIBLE);
                holder.mBinding.tvLoadingHint.setText("释放后开始加载。。。");
                break;
            case STATUS_OVER:
            default:
                //加载完毕——还有更多数据
                holder.mBinding.progressBar.setVisibility(View.GONE);
                holder.mBinding.tvLoadingHint.setVisibility(View.VISIBLE);
                holder.mBinding.tvLoadingHint.setText("上拉加载更多。。。");
                break;
        }
    }

    private void initRvScrollListener() {
        mRv.setOnScrollListener(new RecyclerView.OnScrollListener() {

            int firstVisibleItem, visibleItemCount, totalItemCount, lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (SCROLL_STATE_IDLE == newState) {
                    if (mIsAtBottom && mLastY - mDownY < 0 && Math.abs(mLastY - mDownY) > mTouchSlop) {
                        if (!mIsLoadingMore || null != mLoadingMoreListener) {
                            mIsLoadingMore = true;
                            mLoadingMoreListener.onLoadingMore();
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
                    //                    lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                    mIsAtBottom = (totalItemCount - visibleItemCount) <= firstVisibleItem;
                }
            }
        });
    }

    private void initRvTouchListener() {

        mRv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                int action = ev.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mDownY = (int) ev.getY();
                        mDownX = (int) ev.getX();

                        if (null != mRv) {
                            //解决RV的0索引条目未展示出来时会触发下拉的情况。
                            RecyclerView.LayoutManager layoutManager = mRv.getLayoutManager();
                            if (layoutManager instanceof LinearLayoutManager) {
                                int firstCompletelyVisibleItem = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
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
                        LogUtils.i(TAG, "移动的绝对值：" + absX + "/" + absY);

                        if (absX > absY) {
                            //左右划的时候不拦截，直接传递给子View
                            return false;
                        } else {
                            if (curY - mDownY < 0 && absY > mTouchSlop && mIsAtBottom) {
                                if (!mIsLoadingMore && null != mLoadingMoreListener) {
                                    //                                    mLoadingMoreListener.releaseToLoadMore();
                                    setLoadingStatus(STATUS_RELEASE_TO_LOAD);
                                }
                            } else {
                                if (!mIsLoadingMore && null != mLoadingMoreListener) {
                                    //                                    mLoadingMoreListener.clearUpLoadHint();
                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        mLastY = (int) ev.getY();
                        if (mLastY - mDownY < 0 && Math.abs(mLastY - mDownY) > mTouchSlop && mIsAtBottom) {
                            if (!mIsLoadingMore && null != mLoadingMoreListener) {
                                mIsLoadingMore = true;
                                mLoadingMoreListener.onLoadingMore();
                            }
                        }

                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    public OnLoadingMoreListener getLoadingMoreListener() {
        return mLoadingMoreListener;
    }

    public void setLoadingMoreListener(OnLoadingMoreListener loadingMoreListener) {
        mLoadingMoreListener = loadingMoreListener;
    }

    /**
     * 是否允许展示脚布局。
     *
     * @param enable true 展示脚布局，false 不允许展示脚布局
     */
    public void enableFooterView(boolean enable) {
        mFooterEnable = enable;
    }

    /**
     * 作者：CnPeng
     * 时间：2018/7/9 下午2:03
     * 功用：脚布局的Holder
     * 说明：在事件项目中使用的时候，如果不定义为static 的 ，在 onBindViewHolder 中 判断 holder instanceOf FooterHolder 时会报错：“illegal generic type of instanceof”
     */
    private static class FooterHolder extends RecyclerView.ViewHolder {
        private FooterRvBinding mBinding;

        public FooterHolder(View itemView) {
            super(itemView);
        }

        public void setBinding(FooterRvBinding binding) {
            mBinding = binding;
        }
    }

    private static class HeaderHolder extends RecyclerView.ViewHolder {

        private LinearLayout mHeaderView;

        public HeaderHolder(LinearLayout headerView) {
            super(headerView);
            mHeaderView = headerView;
        }

        public LinearLayout getHeaderView() {
            return mHeaderView;
        }

        public void setHeaderView(LinearLayout headerView) {
            mHeaderView = headerView;
        }
    }

    public interface OnLoadingMoreListener {
        void onLoadingMore();
    }
}
