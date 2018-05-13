package com.yang.potato.potato.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yang.potato.potato.fragment.MineScollFragment;

import java.util.List;

/**
 * Created by potato on 2018/4/30.
 */

public class MineFragmentAdapter extends FragmentStatePagerAdapter {

    private List<String> titles;

    public MineFragmentAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        this.titles = titles;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = MineScollFragment.newInstance(position+"", null);
        return fragment;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
