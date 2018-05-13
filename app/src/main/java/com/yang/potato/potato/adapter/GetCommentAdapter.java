package com.yang.potato.potato.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yang.potato.potato.R;
import com.yang.potato.potato.entity.GetComment;

import java.util.List;

/**
 * Created by potato on 2018/5/5.
 */

public class GetCommentAdapter extends BaseQuickAdapter<GetComment, BaseViewHolder> {
    private Context mContext;
    public GetCommentAdapter(@Nullable List<GetComment> data, Context mContext) {
        super(R.layout.item_get_comment, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, GetComment item) {
        Glide.with(mContext).load(item.getHeadImg()).into((ImageView) helper.getView(R.id.icon));
        helper.setText(R.id.tv_name, item.getNickName());
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_info, item.getInfo());

    }
}
