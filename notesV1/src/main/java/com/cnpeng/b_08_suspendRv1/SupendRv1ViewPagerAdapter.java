package com.cnpeng.b_08_suspendRv1;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * 作者：CnPeng
 * <p>
 * 时间：2017/4/12:下午6:22
 * <p>
 * 说明：ViewPager 的适配器
 */

class SupendRv1ViewPagerAdapter extends FragmentPagerAdapter {

    private final String[] titles;

    public SupendRv1ViewPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return SuspendRv1Fragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
