package com.yang.potato.potato.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yang.potato.potato.R;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.entity.AlbumInfo;
import com.yang.potato.potato.utils.ImageLoder;

import java.util.List;

import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;

/**
 * Created by potato on 2018/5/1.
 */

public class AlbumInfoAdapter extends BaseQuickAdapter<Album, BaseViewHolder> {

    public AlbumInfoAdapter(@Nullable List<Album> data) {
        super(R.layout.item_home, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Album item) {
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        int width = screenWidth / 2;
        Glide.with(mContext).load(item.getImg()).override(width, SIZE_ORIGINAL).fitCenter().into((ImageView) helper.getView(R.id.img_item_home));
        //ImageLoder.loadImg(mContext, item.getImg(), (ImageView) helper.getView(R.id.img_item_home));
        helper.setText(R.id.tv_item_home_title, item.getTitle());
        //helper.setText(R.id.tv_item_home_title, item.getInfo());
        //helper.setText(R.id.tv_item_home_nick, item.getTitle());
        helper.setText(R.id.tv_praise, item.getZan() +"");
        ImageLoder.loadImg(mContext, item.getHeadImg(), (ImageView) helper.getView(R.id.img_item_home_icon));
        helper.setText(R.id.tv_item_home_nick, item.getNickName());
    }
}
