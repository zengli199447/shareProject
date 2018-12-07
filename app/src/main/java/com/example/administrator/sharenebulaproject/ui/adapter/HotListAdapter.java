package com.example.administrator.sharenebulaproject.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.HotAllDataBean;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.widget.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/13.
 */

public class HotListAdapter extends BaseAdapter {

    private Context context;
    private List<HotAllDataBean.Result.newsBean> TheArticleList;

    public HotListAdapter(Context context, List<HotAllDataBean.Result.newsBean> theArticleList) {
        this.context = context;
        TheArticleList = theArticleList;
    }

    @Override
    public int getCount() {
        return TheArticleList.size();
    }

    @Override
    public Object getItem(int i) {
        return TheArticleList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        CommonViewHolder holder = null;
        final HotAllDataBean.Result.newsBean newsBean = TheArticleList.get(i);

        switch (newsBean.getViewtype()) {
            case 1:
                if (newsBean.getIfcanmoney() == 0) {
                    holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_hot_page_type_1_off);
                } else {
                    holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_hot_page_type_1_on);
                }
                break;
            case 2:
                if (newsBean.getIfcanmoney() == 0) {
                    holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_hot_page_type_2_off);
                } else {
                    holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_hot_page_type_2_on);
                }
                break;
            case 3:
                if (newsBean.getIfcanmoney() == 0) {
                    holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_hot_page_type_3_off);
                } else {
                    holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_hot_page_type_3_on);
                }
                break;
        }

        holder.getTv(R.id.title_content).setText(newsBean.getTitle());
        holder.getTv(R.id.time_no).setText(newsBean.getCreatedate());
        holder.getTv(R.id.amount_read_no).setText(new StringBuffer().append("阅读: ").append(newsBean.getAmount_read()).toString());

        String MinImg = newsBean.getListimg();
        if (holder.getIv(R.id.only_min_img) != null) {
            String[] split = newsBean.getListimg().split(",");
            Glide.with(context).load(new StringBuffer().append(DataClass.FileUrl).append(split[0]).toString()).centerCrop().error(R.drawable.banner_off).into(holder.getIv(R.id.only_min_img));
        }

        if (holder.getIv(R.id.min_img1) != null) {
            int views[] = {R.id.min_img1, R.id.min_img2, R.id.min_img3};
            String[] split = newsBean.getListimg().split(",");
            for (int j = 0; j < split.length; j++) {
                Glide.with(context).load(new StringBuffer().append(DataClass.FileUrl).append(split[j]).toString()).centerCrop().error(R.drawable.banner_off).into(holder.getIv(views[j]));
            }
        }

        String MaxImg = newsBean.getListimg();
        if (holder.getIv(R.id.only_max_img) != null){
            String[] split = newsBean.getListimg().split(",");
            Glide.with(context).load(new StringBuffer().append(DataClass.FileUrl).append(split[0]).toString()).centerCrop().error(R.drawable.banner_off).into(holder.getIv(R.id.only_max_img));
        }

        if (newsBean.getIfcanmoney() == 1) {
            holder.getTv(R.id.starbean_no).setText(new StringBuffer().append("奖励池: ").append(newsBean.getStarbean()).append("星豆").toString());
            holder.getTv(R.id.shares_no).setText(new StringBuffer().append("分享: ").append(newsBean.getAmount_share()).append("人").toString());
            holder.getTv(R.id.shares_check_no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCheckListener.onClick(newsBean.getIfcanmoney(), newsBean.getNewsid());
                }
            });
        }
        return holder.convertView;
    }

    public interface onCheckListener {
        void onClick(int i, int id);
    }

    public onCheckListener onCheckListener;

    public void setOnCheckListener(onCheckListener onCheckListener) {
        this.onCheckListener = onCheckListener;
    }

}
