package com.yang.potato.potato.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yang.potato.potato.R;
import com.yang.potato.potato.entity.Tag;

import java.util.List;

/**
 * Created by potato on 2018/5/3.
 */

public class SelectTagAdapter extends BaseQuickAdapter<Tag, BaseViewHolder> {

    public SelectTagAdapter(@Nullable List<Tag> data) {
        super(R.layout.item_select_tag, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Tag item) {
        helper.setText(R.id.tv_tag, item.getTag());
    }
}
