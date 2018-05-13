package com.yang.potato.potato.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yang.potato.potato.R;
import com.yang.potato.potato.entity.AlbumInfo;
import com.yang.potato.potato.utils.ImageLoder;
import com.yang.potato.potato.utils.Utils;

import java.util.List;

/**
 * Created by potato on 2018/5/1.
 */

public class CommentAdapter extends BaseQuickAdapter<AlbumInfo.CommentsBean, BaseViewHolder> {

    private Context mContext;

    public CommentAdapter(Context context, @Nullable List<AlbumInfo.CommentsBean> data) {
        super(R.layout.item_comment, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AlbumInfo.CommentsBean item) {
        ImageLoder.loadImg(mContext, item.getHeadImg(), (ImageView) helper.getView(R.id.img_item_comment_icon));
        helper.setText(R.id.tv_item_comment_name, item.getNickName());
        helper.setText(R.id.tv_item_comment_comment, item.getInfo());
        helper.setText(R.id.tv_time, Utils.getTime(item.getCreateTime(), "yyyy-MM-dd"));
    }
}
