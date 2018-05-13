package com.yang.potato.potato.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yang.potato.potato.R;
import com.yang.potato.potato.entity.Video;
import com.yang.potato.potato.utils.Utils;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by potato on 2018/5/3.
 */

public class VideoAdapter extends BaseQuickAdapter<Video, BaseViewHolder> {
    private Context mContext;
    public VideoAdapter(Context context, @Nullable List<Video> data) {
        super(R.layout.item_video, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Video item) {
        helper.setText(R.id.tv_name, item.getNickName());
        helper.setText(R.id.tv_time, Utils.getTime(item.getCreateTime(), "MM-dd"));
        Glide.with(mContext).load(item.getHeadImg()).into((ImageView) helper.getView(R.id.tv_icon));
        JZVideoPlayerStandard jzVideoPlayerStandard = helper.getView(R.id.video_play);
        jzVideoPlayerStandard.setUp(item.getVideoUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, item.getTitle());
    }
}
