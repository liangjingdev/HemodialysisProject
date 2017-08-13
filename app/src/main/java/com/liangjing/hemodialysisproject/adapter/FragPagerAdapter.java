package com.liangjing.hemodialysisproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by liangjing on 2017/8/11.
 * <p>
 * function:TabLayout+ViewPager+Fragment(需要FragmentPagerAdapter)--切换Fragment
 */

public class FragPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragmentList;//Fragment集合
    private ArrayList<String> mTitleList;//TabLayout在对应的Fragment下显示的Title的集合

    public FragPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList, ArrayList<String> titleList) {
        super(fm);
        this.mFragmentList = fragmentList;
        this.mTitleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
