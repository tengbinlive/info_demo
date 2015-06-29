package com.touyan.investment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.touyan.investment.AbsFragment;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<AbsFragment> fragments;

    public MainViewPagerAdapter(FragmentManager fm, ArrayList<AbsFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public Fragment getItem(int num) {
        AbsFragment fragment = fragments.get(num);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    public void refresh(int position) {
        fragments.get(position).scrollToTop();
    }

}