package com.yang.potato.potato.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yang.potato.potato.R;

import java.util.List;



/**
 * Created by potato on 2018/5/3.
 */

public class UploadImgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context mContext;
    public UploadImgAdapter(Context context, @Nullable List<String> data) {
        super(R.layout.item_img, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Glide.with(mContext).load(item).into((ImageView) helper.getView(R.id.img_item));
        Glide.with(mContext).load(item).into((ImageView) helper.getView(R.id.img_item));
    }
}
