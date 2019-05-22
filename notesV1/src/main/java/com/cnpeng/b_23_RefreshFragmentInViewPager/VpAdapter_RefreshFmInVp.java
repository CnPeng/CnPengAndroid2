package com.cnpeng.b_23_RefreshFragmentInViewPager;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/9/20:上午11:41
 * <p>
 * 说明：ViewPager的适配器。重点是如何生成并获取TAG，然后根据TAG获取Fragment对象
 */

class VpAdapter_RefreshFmInVp extends FragmentPagerAdapter {


    private final String[]        titles;
    private final FragmentManager fragmentManager;
    List<String> tags = new ArrayList<>();

    public VpAdapter_RefreshFmInVp(FragmentManager fm, String[] titles) {
        super(fm);
        fragmentManager = fm;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment_VpItem.newInstance(position);   //多个页面的布局样式一致，所以需要根据position展示不同的内容
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int pageIndex) {
        //创建tag并存储到集合中
        String fragmentNameTag = makeFragmentName(container.getId(), pageIndex);
        if (!tags.contains(fragmentNameTag)) {
            tags.add(fragmentNameTag);
        }
        return super.instantiateItem(container, pageIndex);
    }

    /**
     * 创建Fragment的名称也就是tag,该方法源自 FragmentPagerAdapter的源码
     */
    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    /**
     * 刷新数据
     *
     * @param position     列表中被点击条目的位置
     * @param curPageIndex 当前正在展示的页面
     */
    public void refreshMofifiedPageData(int position, int curPageIndex) {
        for (int i = 0; i < tags.size(); i++) {
            Fragment_VpItem fragment = (Fragment_VpItem) fragmentManager.findFragmentByTag(tags.get(i));
            fragment.refreshData(position, curPageIndex);
        }
    }
}
