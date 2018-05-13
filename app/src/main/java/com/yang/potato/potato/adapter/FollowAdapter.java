package com.yang.potato.potato.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yang.potato.potato.R;
import com.yang.potato.potato.entity.Album;
import com.yang.potato.potato.utils.ImageLoder;
import com.yang.potato.potato.utils.Utils;

import java.util.List;

/**
 * Created by potato on 2018/5/3.
 */

public class FollowAdapter extends BaseQuickAdapter<Album, BaseViewHolder> {
    private Context mContext;

    public FollowAdapter(Context context, @Nullable List<Album> data) {
        super(R.layout.layout_item_follow, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Album item) {
        ImageLoder.loadImg(mContext, item.getHeadImg(), (ImageView) helper.getView(R.id.img_item_follow_icon));
        helper.setText(R.id.tv_item_follow_name, item.getNickName());
        helper.setText(R.id.tv_item_follow_time, Utils.getTime(item.getCreateTime(),"yyyy-MM-dd"));
        helper.setText(R.id.tv_item_follow_title, item.getTitle());
        helper.setText(R.id.tv_item_follow_info, item.getInfo());
        helper.setText(R.id.tv_item_follow_number, "共"+item.getPhotos().size()+"图");
        if(item.getPhotos().size() == 1){
            //helper.setVisible(, false);
            helper.getView(R.id.lin).setVisibility(View.GONE);
            helper.setVisible(R.id.img_item_follow_img, true);
            ImageLoder.loadImg(mContext, item.getPhotos().get(0).getImgUrl(), (ImageView) helper.getView(R.id.img_item_follow_img));
        }else{
            helper.setVisible(R.id.lin, true);
            helper.getView(R.id.img_item_follow_img).setVisibility(View.GONE);
            //helper.setVisible(R.id.img_item_follow_img, false);
            for(int i = 0; i < item.getPhotos().size(); i++){
                if(i == 0)
                    ImageLoder.loadImg(mContext, item.getPhotos().get(0).getImgUrl(), (ImageView) helper.getView(R.id.img_item_follow_img1));
                if(i == 1)
                    ImageLoder.loadImg(mContext, item.getPhotos().get(1).getImgUrl(), (ImageView) helper.getView(R.id.img_item_follow_img2));
                if(i == 2)
                    ImageLoder.loadImg(mContext, item.getPhotos().get(2).getImgUrl(), (ImageView) helper.getView(R.id.img_item_follow_img3));

            }
            if(item.getPhotos().size() == 2){
                helper.getView(R.id.img_item_follow_img3).setVisibility(View.INVISIBLE);
            }
        }
    }
}
