package com.yang.potato.potato.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yang.potato.potato.R;
import com.yang.potato.potato.entity.SelectZanByUserId;

import java.util.List;

/**
 * Created by potato on 2018/5/4.
 */

public class SelectZanByUserIdAdapter extends BaseQuickAdapter<SelectZanByUserId, BaseViewHolder> {
    private Context mContext;

    public SelectZanByUserIdAdapter(@Nullable List<SelectZanByUserId> data, Context mContext) {
        super(R.layout.item_select_zan, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, SelectZanByUserId item) {
        Glide.with(mContext).load(item.getHeadImg()).into((ImageView) helper.getView(R.id.icon));
        Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.img));
        helper.setText(R.id.tv_name, item.getNickName());
        helper.setText(R.id.tv_title, item.getTitle());
        if("0".equals(item.getType())){
            helper.setText(R.id.tv_dian, "点赞");
        }else{
            helper.setText(R.id.tv_dian, "收藏");
        }
    }
}
