package com.cnpeng.b_33_BaseRvAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cnpeng.cnpeng_demos2017_01.R;
import com.cnpeng.cnpeng_demos2017_01.databinding.ItemRvBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：CnPeng
 * 时间：2018/6/15
 * 功用：
 * 其他：
 */
public class MyRvAdapter extends BaseRvAdapter {
    private Context      mContext;
    private List<String> mList;

    public MyRvAdapter(Context context, RecyclerView recyclerView) {
        super(context, recyclerView);
        mContext = context;
    }

    /**
     * 作者：CnPeng
     * 时间：2018/6/15 上午9:47
     * 功用：设置数据并更新界面
     * 说明：
     */
    public void setData(List<String> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    int getContentCount() {
        return mList.size();
    }

    @Override
    int getContentItemType(int position) {
        //注意：0 和 1 作为基类预留的头布局和脚布局，外部定义类型时不能再使用0和1
        return 2;
    }

    @Override
    void onBindContentHolder(RecyclerView.ViewHolder holder, int position) {
        // TODO: CnPeng 2018/6/14 下午12:06 这个三个position 和 position有什么区别呢？
        //        int realPosition = holder.getAdapterPosition();
        //        int realPosition2 = holder.getLayoutPosition();
        //        int oldPosition = holder.getOldPosition();

        if (holder instanceof ContentHolder) {
            String str = mList.get(position);

            ItemRvBinding mBinding = ((ContentHolder) holder).mBinding;
            mBinding.tv.setText(str);

            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            mBinding.innerRv.setLayoutManager(layoutManager);

            //            //构造适配器
            //            MyRvAdapter2 mRvAdapter = new MyRvAdapter2(mContext, mBinding.innerRv);
            //            //设置数据并通知界面更新
            //            mRvAdapter.setData(addDataToList(1));
            //            //            禁用脚布局
            //            mRvAdapter.enableFooterView(false);
            //
            //            mBinding.innerRv.setAdapter(mRvAdapter);
        }
    }

    @Override
    RecyclerView.ViewHolder onCreateContentHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        ItemRvBinding itemBinding = DataBindingUtil.inflate(inflater, R.layout.item_rv, parent, false);
        View itemView = itemBinding.getRoot();

        ContentHolder contentHolder = new ContentHolder(itemView);
        contentHolder.setBinding(itemBinding);

        return contentHolder;
    }

    private List<String> addDataToList(int start) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(i + ".js (29)\n" +
                    "07-05 20:55:32.224 " +
                    "\n12104-12104/com.zjelite.antlinkercampus\n E/Http请求回来的数据字符串是：:\n" +
                    " {\"RE\":0,\"Text\":\"\",\"Data\":\n" +
                    "{\"HasNew\":false,\"Hot\":[{\"AuthLevel\":3,\n \"GoodCount\":1,\"IsGood\":0,\"IsMany\":1,\"ReplyCount\":13,\"ReplyDesc\":\"不对，是看看长文定位\",\"ReplyID\":18753,\"ReplyMimes\":[],\"ReplyTime\":\"20180627212016\",\"ReplyUID\":\"DY0000082462\",\"SecondLevels\":[{\"ReplyAuthLevel\":3,\"ReplyDesc\":\"13\",\"ReplyID\":18767,\"ReplyMimes\":[],\"ReplyTime\":\"20180627212132\",\"ReplyUID\":\"DY0000082469\",\"ReplyUName\":\"Test28\",\"ReplyUniversityName\":\"青岛大学\",\"ReplyUsex\":\"M\",\"TargetID\":18753,\"TargetUID\":\"DY0000082462\",\"TargetUName\":\"5021DEV\",\"TopicID\":[]},{\"ReplyAuthLevel\":3,\"ReplyDesc\":\"12\",\"ReplyID\":18765,\"ReplyMimes\":[],\"ReplyTime\":\"20180627212121\",\"ReplyUID\":\"DY0000082469\",\"ReplyUName\":\"Test28\",\"ReplyUniversityName\":\"青岛大学\",\"ReplyUsex\":\"M\",\"TargetID\":18753,\"TargetUID\":\"DY0000082462\",\"TargetUName\":\"5021DEV\",\"TopicID\":[]},{\"ReplyAuthLevel\":3,\"ReplyDesc\":\"11\",\"ReplyID\":18764,\"ReplyMimes\":[],\"ReplyTime\":\"20180627212118\",\"ReplyUID\":\"DY0000082469\",\"ReplyUName\":\"Test28\",\"ReplyUniversityName\":\"青岛大学\",\"ReplyUsex\":\"M\",\"TargetID\":18753,\"TargetUID\":\"DY0000082462\",\"TargetUName\":\"5021DEV\",\"TopicID\":[]},{\"ReplyAuthLevel\":3,\"ReplyDesc\":\"10\",\"ReplyID\":18763,\"ReplyMimes\":[],\"ReplyTime\":\"20180627212115\",\"ReplyUID\":\"DY0000082469\",\"ReplyUName\":\"Test28\",\"ReplyUniversityName\":\"青岛大学\",\"ReplyUsex\":\"M\",\"TargetID\":18753,\"TargetUID\":\"DY0000082462\",\"TargetUName\":\"5021DEV\",\"TopicID\":[]},{\"ReplyAuthLevel\":3,\"ReplyDesc\":\"9\",\"ReplyID\":18762,\"ReplyMimes\":[],\"ReplyTime\":\"20180627212111\",\"ReplyUID\":\"DY0000082469\",\"ReplyUName\":\"Test28\",\"ReplyUniversityName\":\"青岛大学\",\"ReplyUsex\":\"M\",\"TargetID\":18753,\"TargetUID\":\"DY0000082462\",\"TargetUName\":\"5021DEV\",\"TopicID\":[]},{\"ReplyAuthLevel\":3,\"ReplyDesc\":\"8\",\"ReplyID\":18761,\"ReplyMimes\":[],\"ReplyTime\":\"20180627212107\",\"ReplyUID\":\"DY0000082469\",\"ReplyUName\":\"Test28\",\"ReplyUniversityName\":\"青岛大学\",\"ReplyUsex\":\"M\",\"TargetID\":18753,\"TargetUID\":\"DY0000082462\",\"TargetUName\":\"5021DEV\",\"TopicID\":[]}],\"TopicID\":[],\"UName\":\"5021DEV\",\"UniversityName\":\"青岛大学\",\"Usex\":\"M\"},{\"AuthLevel\":3,\"GoodCount\":1,\"IsGood\":0,\"IsMany\":1,\"ReplyCount\":9,\"ReplyDesc\":\"我们来看看一级评论\",\"ReplyID\":18752,\"ReplyMimes\":[],\"ReplyTime\":\"20180627212002\",\"ReplyUID\":\"DY0000082462\",\"SecondLevels\":[{\"ReplyAuthLevel\":3,\"ReplyDesc\":\"\\n我的父亲是个“兵”\\n2018.06.14 阅读 8763\\n光头强 \\n\\n父亲曾是一名老兵。退伍后，当过村支书，后辞职下海经商；当过“万元户”，后血亏回家务农；生病判过死缓，现相伴病患的母亲。生活的磨难似千钧重担，他依然秉持军人的作风，少言寡语，不屈不挠，坚强自信。——题记\\n\\n\\n听说，父亲当兵时干得很出色。因家里太穷，主动放弃提干的机会，回乡务农的。或许是曾经的磨炼和未圆的梦想，足留给他一生的“兵味”，也给予了我。\\n\\n1.\\n高考落榜的那年，“万元户”的父亲在生意上也血本无归。\\n我便无意补习复考，去了上海打工，想着早一点赚些钱补贴家用。\\n临行时，父亲试问了我一下，他的意思我懂。多年受他的影响，这个念头早已有之。\\n\\n两个月后，收到父亲发来的电报：征兵开始了，速归！\\n“回来啦！”车站，父亲简短的一句。\\n“嗯”！ \\n “走，回家，你妈在等着你” ！父亲一把顺过我的旅行包。\\n我知道，他在车站等我已有大半晌了。\\n\\n2.\\n年底征兵，曾经当过村支书的父亲对征兵工作流程自然很熟悉。\\n报名、体检、政审，父亲顶着烈日，带着我跑来跑去。\\n所幸，一切合格，顺利通过。\\n\\n还是那个车站。\\n“到部队后，听领导的话，好好\",\"ReplyID\":19017,\"ReplyMimes\":[],\"ReplyTime\":\"20180704182354\",\"ReplyUID\":\"DY0000082462\",\"ReplyUName\":\"5021DEV\",\"ReplyUniversityName\":\"青岛大学\",\"ReplyUsex\":\"M\",\"TargetID\":18752,\"TargetUID\":\"DY0000082469\",\"TargetUName\":\"Test28\",\"TopicID\":[]},{\"ReplyAuthLevel\":3,\"ReplyDesc\":\"1/8\",\"ReplyID\":18775,\"ReplyMimes\":[],\"ReplyTime\":\"2018");
        }
        return list;
    }

    public class ContentHolder extends RecyclerView.ViewHolder {
        private ItemRvBinding mBinding;

        public ContentHolder(View itemView) {
            super(itemView);
        }

        public void setBinding(ItemRvBinding binding) {
            mBinding = binding;
        }
    }
}
