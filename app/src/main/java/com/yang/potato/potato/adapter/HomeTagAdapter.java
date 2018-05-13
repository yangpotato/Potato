package com.yang.potato.potato.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yang.potato.potato.entity.Tag;
import com.yang.potato.potato.fragment.RecycleFragment;

import java.util.List;

/**
 * Created by potato on 2018/4/30.
 */

public class HomeTagAdapter extends FragmentStatePagerAdapter {
    private List<Tag> tags;

    public HomeTagAdapter(FragmentManager fm, List<Tag> tags) {
        super(fm);
        this.tags = tags;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = RecycleFragment.newInstance(position+"", null);
        return fragment;
    }

    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tags.get(position).getTag();
    }
}
