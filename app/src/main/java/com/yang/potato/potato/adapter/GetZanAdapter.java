package com.yang.potato.potato.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yang.potato.potato.R;
import com.yang.potato.potato.entity.CollectionZan;
import com.yang.potato.potato.utils.ImageLoder;

import java.util.List;

/**
 * Created by potato on 2018/5/3.
 */

public class GetZanAdapter extends BaseQuickAdapter<CollectionZan, BaseViewHolder> {
    private Context mContext;

    public GetZanAdapter(Context context, @Nullable List<CollectionZan> data) {
        super(R.layout.item_img, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionZan item) {
        ImageLoder.loadImg(mContext, item.getImg(), (ImageView) helper.getView(R.id.img_item));
    }
}
