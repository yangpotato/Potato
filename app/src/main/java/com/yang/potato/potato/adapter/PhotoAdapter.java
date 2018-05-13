package com.yang.potato.potato.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yang.potato.potato.R;
import com.yang.potato.potato.entity.AlbumInfo;
import com.yang.potato.potato.utils.ImageLoder;

import java.util.List;

/**
 * Created by potato on 2018/5/1.
 */

public class PhotoAdapter extends BaseQuickAdapter<AlbumInfo.PhotosBean, BaseViewHolder> {
    private Context mContext;

    public PhotoAdapter(Context context, @Nullable List<AlbumInfo.PhotosBean> data) {
        super(R.layout.layout_item_img, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AlbumInfo.PhotosBean item) {
        ImageLoder.loadImg(mContext, item.getImgUrl(), (ImageView) helper.getView(R.id.img));
    }
}
