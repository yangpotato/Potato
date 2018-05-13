package com.yang.potato.potato.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yang.potato.potato.R;
import com.yang.potato.potato.entity.AlbumInfo;

import java.util.List;

/**
 * Created by potato on 2018/5/1.
 */

public class TagAdapter extends BaseQuickAdapter<AlbumInfo.TagsBean, BaseViewHolder> {

    public TagAdapter(@Nullable List<AlbumInfo.TagsBean> data) {
        super(R.layout.item_tag, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AlbumInfo.TagsBean item) {
        helper.setText(R.id.tv, "#"+item.getTag());
    }
}
