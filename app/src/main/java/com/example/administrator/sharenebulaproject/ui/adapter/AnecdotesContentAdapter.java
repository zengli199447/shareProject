package com.example.administrator.sharenebulaproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobads.BaiduNativeAdPlacement;
import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.model.bean.TheArticleDetailsNetBean;
import com.example.administrator.sharenebulaproject.ui.holder.MyViewHolder;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.AdvertisingBuilder;
import com.example.administrator.sharenebulaproject.widget.CommonViewHolder;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/5.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class AnecdotesContentAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Object> list;

    public AnecdotesContentAdapter(Context context, List<Object> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        Object object = list.get(position);
        if (object instanceof BaiduNativeAdPlacement) {
            type = 3;
        } else if (object instanceof TheArticleDetailsNetBean.ResultBean.DetailBean.ContentBean) {
            TheArticleDetailsNetBean.ResultBean.DetailBean.ContentBean contentBean = (TheArticleDetailsNetBean.ResultBean.DetailBean.ContentBean) object;
            switch (Integer.valueOf(contentBean.getReftype())) {
                case 1:
                    type = 1;
                    break;
                case 2:
                    type = 2;
                    break;
            }
        }
        return type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.item_anecdotes_img_content, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(context).inflate(R.layout.item_anecdotes_text_content, parent, false);
                break;
            case 3:
                view = LayoutInflater.from(context).inflate(R.layout.feed_h5_item_placement, null);
                break;
        }

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Object object = list.get(position);
        switch (getItemViewType(position)) {
            case 1:
                ImageView img_content = holder.itemView.findViewById(R.id.img_content);
                Glide.with(context).load(SystemUtil.JudgeUrl(((TheArticleDetailsNetBean.ResultBean.DetailBean.ContentBean) object).getReftxt())).error(R.drawable.banner_off).into(img_content);
                break;
            case 2:
                TextView text_content = holder.itemView.findViewById(R.id.text_content);
                text_content.setText(((TheArticleDetailsNetBean.ResultBean.DetailBean.ContentBean) object).getReftxt());
                break;
            case 3:
                RelativeLayout native_outer_view = holder.itemView.findViewById(R.id.native_outer_view);
                AdvertisingBuilder.RecycleAdvertising(context, native_outer_view, object);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

}
