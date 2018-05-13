package com.yang.potato.potato.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yang.potato.potato.R;
import com.yang.potato.potato.entity.User;

import java.util.List;

/**
 * Created by potato on 2018/5/4.
 */

public class FollowListAdapter extends BaseQuickAdapter<User, BaseViewHolder> {

    private Context mContext;

    public FollowListAdapter(@Nullable List<User> data, Context mContext) {
        super(R.layout.item_follow, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {
        Glide.with(mContext).load(item.getHeadImg()).into((ImageView) helper.getView(R.id.item_icon));
        helper.setText(R.id.tv_name, item.getNickName());
    }
}
