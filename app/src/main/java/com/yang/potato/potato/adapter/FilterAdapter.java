package com.yang.potato.potato.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yang.potato.potato.R;

import java.util.List;

/**
 * Created by potato on 2018/5/4.
 */

public class FilterAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public FilterAdapter(@Nullable List<String> data) {
        super(R.layout.item_tv,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv, item);
    }
}
