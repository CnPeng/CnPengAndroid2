package com.cnpeng.b_33_BaseRvAdapter;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/12/26:下午2:26
 * <p>
 * 说明:我的关注、我的粉丝、TA的关注、TA的粉丝、共同关注 页面的适配器
 */
public class RvAdapter_MyFollow {
//        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

//    private final Context        mContext;
//    private final LayoutInflater mInflater;
//
//    /**
//     * 脚布局类型
//     */
//    private final int TYPE_FOOTER        = 0;
//    /**
//     * 用户条目类型
//     */
//    private final int TYPE_CONTENT       = 1;
//    /**
//     * 共同关注条目类型--这个有点特殊，在展示 TA的关注 时，顶部的 共同关注标题、共同关注用户列表、TA的全部关注标题 三者共同作为一个条目。
//     */
//    private final int TYPE_COMMON_FOLLOW = 2;
//
//    /**
//     * 松手加载更多
//     */
//    public static final String STATUS_RELEASE_TO_LOAD = "releaseToLoadMore";
//    /**
//     * 正在上拉加载更多
//     */
//    public static final String STATUS_LOADING_MORE    = "loadingMore";
//    /**
//     * 上拉加载完毕
//     */
//    public static final String STATUS_LOADING_OVER    = "loadedOver";
//
//    /**
//     * 上拉加载完毕--没有更多
//     */
//    public static final String STATUS_NO_MORE = "noMore";
//
//    /**
//     * 清除上拉提示
//     */
//    public static final String STATUS_CLEAR_HINT = "clearUpLoadHint";
//
//    private List<Object> mUserList         = new ArrayList<>();
//    private String       mCurLoadingStatus = "";
//
//    private String mTargetUid;
//
//    /**
//     * 当前是哪个页面在使用该适配器：我的关注、我的粉丝、
//     */
//    private String mPageType;
//
//    /**
//     * 在 TA的关注 界面中，有 共同关注 条目，如果共同关注超过3个，会在右上角展示一个 更多 的按钮，点击这个更多按钮的时候会展示全部共同关注，此时，
//     * 需要使用这个 targetUid ,即 当前正在被查看关注信息的 用户的uid。
//     *
//     * @param context  上下文
//     * @param pageType 页面类型
//     */
//    public RvAdapter_MyFollow(Context context, String pageType, String targetUid) {
//        mContext = context;
//        mInflater = LayoutInflater.from(mContext);
//        mPageType = pageType;
//        mTargetUid = targetUid;
//    }
//
//    public void setDataToView(List<Object> userList) {
//        if (null != userList) {
//            mUserList.clear();
//            mUserList.addAll(userList);
//            notifyDataSetChanged();
//        }
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (TYPE_FOOTER == viewType) {
//            FooterRvLoadingBinding footerBinding = DataBindingUtil.inflate(mInflater, R.layout.footer_rv_loading,
//                    parent, false);
//            FooterHolder footerHolder = new FooterHolder(footerBinding.getRoot());
//            footerHolder.setFooterBinding(footerBinding);
//            return footerHolder;
//        } else if (TYPE_COMMON_FOLLOW == viewType) {
//            // 初始化共同关注条目--含两个标题，一个列表。
//            TopCommonFollowBinding commonFollowBinding = DataBindingUtil.inflate(mInflater, R.layout
//                    .top_common_follow, parent, false);
//            TopCommonFollowHolder topCommonFollowHolder = new TopCommonFollowHolder(commonFollowBinding.getRoot());
//            topCommonFollowHolder.setCommonFollowBinding(commonFollowBinding);
//            return topCommonFollowHolder;
//        } else {
//            //普通关注条目，后面带有 关注、已关注的功能按钮
//            ItemMyFollowBinding itemBinding = DataBindingUtil.inflate(mInflater, R.layout.item_my_follow, parent,
//                    false);
//
//            FollowItemHolder followHolder = new FollowItemHolder(itemBinding.getRoot());
//            followHolder.setItemBinding(itemBinding);
//            return followHolder;
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//
//        if (holder instanceof FooterHolder) {
//            initFooterView(holder, position);
//        } else if (holder instanceof TopCommonFollowHolder) {
//            initTopCommonFollowView(holder);
//        } else {
//            //if (holder instanceof FollowItemHolder) {
//            initFollowItemView((FollowItemHolder) holder, position);
//            //}
//        }
//    }
//
//    /**
//     * 初始化条目view
//     */
//    private void initFollowItemView(FollowItemHolder holder, int position) {
//        ItemMyFollowBinding itemBinding = holder.getItemBinding();
//        final ReplyBean_MyFollowUser userBean = (ReplyBean_MyFollowUser) mUserList.get(position);
//        itemBinding.setUserBean(userBean);
//
//        FollowHandler handler = new FollowHandler(mContext, userBean.getUid(), mPageType);
//        itemBinding.setHandler(handler);
//
//        if (!FollowOrFollowerActivity.TYPE_ALL_COMMON_FOLLOW.equals(mPageType)) {
//            //共同关注页面中，不需要展示关注状态，而且服务器也没有返回关注状态，所以，共同关注页面不做关注状态同步的处理
//            UserFollowInfoUtil baseInfoUtil = UserFollowInfoUtil.getInstance();
//            UserFollowInfoEntity baseInfoEntity = baseInfoUtil.getUserBaseInfoFromCache(userBean.getUid());
//            if (null == baseInfoEntity) {
//                baseInfoEntity = new UserFollowInfoEntity();
//            }
//            baseInfoEntity.setFollowStatus(userBean.getFollowStatus());
//            baseInfoEntity.setUserLevel(userBean.getAuthLevel());
//            baseInfoEntity.setUserUniversityName(userBean.getUniversityName());
//            baseInfoEntity.setUserSex(userBean.getSex());
//            baseInfoEntity.setUserUID(userBean.getUid());
//            baseInfoEntity.setUserName(CommonUtils.getRemark(userBean.getUid(), userBean.getuName()));
//
//            baseInfoUtil.setUserBaseInfoToCache(userBean.getUid(), baseInfoEntity);
//            itemBinding.setUserBaseInfo(baseInfoEntity);
//        }
//
//        if (FollowOrFollowerActivity.TYPE_ALL_COMMON_FOLLOW.equals(mPageType)) {
//            //如果是共同关注页面，则隐层 最右侧功能按钮
//            itemBinding.setHideFuncBt(true);
//        } else {
//            //如果列表中出现当前登录用户，不展示 关注/已关注 的按钮
//            String curUid = UserManager.getInstance().getCurUid();
//            if (curUid.equals(userBean.getUid())) {
//                itemBinding.setHideFuncBt(true);
//            } else {
//                itemBinding.setHideFuncBt(false);
//            }
//        }
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return mUserList.size() == 0 ? 0 : mUserList.size() + 1;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        int itemType = TYPE_CONTENT;
//        if (position == getItemCount() - 1) {
//            itemType = TYPE_FOOTER;
//        } else if (mUserList.get(position) instanceof ReplyBean_CommonFollow) {
//            itemType = TYPE_COMMON_FOLLOW;
//        }
//        return itemType;
//    }
//
//    /**
//     * 我关注的  条目Holder
//     */
//    class FollowItemHolder extends RecyclerView.ViewHolder {
//        private ItemMyFollowBinding itemBinding;
//
//        ItemMyFollowBinding getItemBinding() {
//            return itemBinding;
//        }
//
//        void setItemBinding(ItemMyFollowBinding itemBinding) {
//            this.itemBinding = itemBinding;
//        }
//
//        FollowItemHolder(View itemView) {
//            super(itemView);
//        }
//    }
//
//    /**
//     * 我关注的 脚步局的Holder
//     */
//    class FooterHolder extends RecyclerView.ViewHolder {
//        private FooterRvLoadingBinding mFooterBinding;
//
//        public FooterHolder(View itemView) {
//            super(itemView);
//        }
//
//        public void setFooterBinding(FooterRvLoadingBinding footerBinding) {
//            mFooterBinding = footerBinding;
//        }
//
//        public FooterRvLoadingBinding getFooterBinding() {
//            return mFooterBinding;
//        }
//    }
//
//
//    /**
//     * 初始化脚布局
//     */
//    private void initFooterView(RecyclerView.ViewHolder holder, int position) {
//        FooterRvLoadingBinding binding = ((FooterHolder) holder).getFooterBinding();
//
//        switch (mCurLoadingStatus) {
//            case STATUS_RELEASE_TO_LOAD:
//                binding.pbLoading.setVisibility(View.INVISIBLE);
//                binding.tvLoadState.setText(R.string.releaseToLoadMore);
//                break;
//            case STATUS_LOADING_MORE:
//                //正在加载更多
//                binding.pbLoading.setVisibility(View.VISIBLE);
//                binding.tvLoadState.setText(R.string.isLoadingMore);
//                break;
//            case STATUS_NO_MORE:
//                //加载完成没有更多
//                binding.pbLoading.setVisibility(View.INVISIBLE);
//                binding.tvLoadState.setText(R.string.loadOverAndNoMore);
//                break;
//            case STATUS_CLEAR_HINT:
//                binding.pbLoading.setVisibility(View.INVISIBLE);
//                binding.tvLoadState.setText("");
//                break;
//            default:
//                //                binding.getRoot().setVisibility(View.GONE);
//                break;
//        }
//    }
//
//
//    /**
//     * TA的关注页面--共同关注的Holder。包含两个标题，一个用户列表
//     */
//    class TopCommonFollowHolder extends RecyclerView.ViewHolder {
//        TopCommonFollowBinding commonFollowBinding;
//
//        public TopCommonFollowHolder(View itemView) {
//            super(itemView);
//        }
//
//        public TopCommonFollowBinding getCommonFollowBinding() {
//            return commonFollowBinding;
//        }
//
//        public void setCommonFollowBinding(TopCommonFollowBinding commonFollowBinding) {
//            this.commonFollowBinding = commonFollowBinding;
//        }
//    }
//
//    /**
//     * 初始化共同关注视图。含两个标题，一个列表
//     * 共同关注的数据始终放在集合的 0 索引出，所以不用传递position
//     */
//    private void initTopCommonFollowView(RecyclerView.ViewHolder holder) {
//        //TODO 初始化共同关注的视图，
//        TopCommonFollowBinding commonFollowBinding = ((TopCommonFollowHolder) holder).getCommonFollowBinding();
//        ReplyBean_CommonFollow commonFollowBean = (ReplyBean_CommonFollow) mUserList.get(0);
//        commonFollowBinding.setIsMore(commonFollowBean.getIsMore());
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
//        commonFollowBinding.rvCommonFollow.setLayoutManager(layoutManager);
//
//        RvAdapter_TopCommonFollow adapter = new RvAdapter_TopCommonFollow(mContext, commonFollowBean
//                .getCommonFollowList());
//        commonFollowBinding.rvCommonFollow.setAdapter(adapter);
//
//        FollowHandler handler = new FollowHandler(mContext, mTargetUid, FollowOrFollowerActivity
//                .TYPE_HIS_FOLLOW_COMMON);
//        commonFollowBinding.setHandler(handler);
//
//    }
//
//    /**
//     * 更新加载更多的状态
//     *
//     * @param status 状态：正在加载更多、没有更多数据了
//     */
//    public void updateLoadMoreStatus(String status) {
//        mCurLoadingStatus = status;
//        notifyItemChanged(getItemCount() - 1);
//        //        notifyDataSetChanged();
//    }
}
