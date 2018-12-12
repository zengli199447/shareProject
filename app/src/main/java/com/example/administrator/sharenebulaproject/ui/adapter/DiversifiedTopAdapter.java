package com.example.administrator.sharenebulaproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.HotAllDataBean;
import com.example.administrator.sharenebulaproject.model.bean.RefreshDateBean;
import com.example.administrator.sharenebulaproject.ui.holder.MyViewHolder;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CalendarBuilder;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/8/13.
 */

public class DiversifiedTopAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private String TAG = getClass().getSimpleName();

    private Context context;
    private List<HotAllDataBean.Result.topNews> topNews;
    private final int height;

    public DiversifiedTopAdapter(Context context, List<HotAllDataBean.Result.topNews> theArticleList) {
        this.context = context;
        topNews = theArticleList;
        height = SystemUtil.dp2px(context, 40);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_type, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        HotAllDataBean.Result.topNews topNews = this.topNews.get(position);
        int itemViewType = getItemViewType(position);

        View top = holder.itemView.findViewById(R.id.top);

        View layout_after_refresh = holder.itemView.findViewById(R.id.layout_after_refresh);
        TextView after_refresh = holder.itemView.findViewById(R.id.after_refresh);

        View line_placeholder = holder.itemView.findViewById(R.id.line_placeholder);
        View bottom_line = holder.itemView.findViewById(R.id.bottom_line);

        RelativeLayout layout_only_img = holder.itemView.findViewById(R.id.layout_only_img);
        ImageView only_min_img = holder.itemView.findViewById(R.id.only_min_img);
        TextView only_img_title_content = holder.itemView.findViewById(R.id.only_img_title_content);

        LinearLayout layout_only_max_img_news = holder.itemView.findViewById(R.id.layout_only_max_img_news);
        TextView only_max_img_title_content = holder.itemView.findViewById(R.id.only_max_img_title_content);
        ImageView only_max_img = holder.itemView.findViewById(R.id.only_max_img);

        LinearLayout layout_multiple_img_news = holder.itemView.findViewById(R.id.layout_multiple_img_news);
        TextView multiple_img_title_content = holder.itemView.findViewById(R.id.multiple_img_title_content);
        ImageView min_img1 = holder.itemView.findViewById(R.id.min_img1);
        ImageView min_img2 = holder.itemView.findViewById(R.id.min_img2);
        ImageView min_img3 = holder.itemView.findViewById(R.id.min_img3);

        TextView form_to = holder.itemView.findViewById(R.id.form_to);
        TextView reading_count = holder.itemView.findViewById(R.id.reading_count);
        TextView starbean = holder.itemView.findViewById(R.id.starbean);
        TextView shares_check = holder.itemView.findViewById(R.id.shares_check);

        ViewGroup native_outer_view = holder.itemView.findViewById(R.id.native_outer_view);

        ImageView advertising_img = holder.itemView.findViewById(R.id.advertising_img);
        TextView advertising_title_content = holder.itemView.findViewById(R.id.advertising_title_content);

        refreshViewStatus(holder, topNews.getIfcanmoney(), topNews.getViewtype());

        top.setVisibility(View.VISIBLE);

        if (!topNews.getSource().equals(form_to.getText().toString()))
            form_to.setText(topNews.getSource());
        if (!new StringBuffer().append("阅读: ").append(topNews.getAmount_read()).toString().equals(reading_count.getText().toString()))
            reading_count.setText(new StringBuffer().append("阅读: ").append(topNews.getAmount_read()).toString());
        if (!new StringBuffer().append("奖励池: ").append(topNews.getStarbean()).append("星豆").toString().equals(starbean.getText().toString()))
            starbean.setText(new StringBuffer().append("奖励池: ").append(topNews.getStarbean()).append("星豆").toString());

        switch (topNews.getViewtype()) {
            case 1:
                String[] split = topNews.getListimg().split(",");
                Glide.with(context).load(SystemUtil.JudgeUrl(split[0])).skipMemoryCache(false).error(R.drawable.banner_off).into(only_min_img);
                only_img_title_content.setText(topNews.getTitle());
                break;
            case 2:
                ViewGroup.LayoutParams minImg1layoutParams = min_img1.getLayoutParams();
                int width = DataClass.WINDOWS_WIDTH - 30;
                minImg1layoutParams.width = SystemUtil.dp2px(context, width / 3);
                min_img1.setLayoutParams(minImg1layoutParams);
                min_img2.setLayoutParams(minImg1layoutParams);
                min_img3.setLayoutParams(minImg1layoutParams);

                String[] splitMultiple = topNews.getListimg().split(",");
                Glide.with(context).load(SystemUtil.JudgeUrl(splitMultiple[0])).skipMemoryCache(false).error(R.drawable.banner_off).into(min_img1);
                Glide.with(context).load(SystemUtil.JudgeUrl(splitMultiple[1])).skipMemoryCache(false).error(R.drawable.banner_off).into(min_img2);
                Glide.with(context).load(SystemUtil.JudgeUrl(splitMultiple[2])).skipMemoryCache(false).error(R.drawable.banner_off).into(min_img3);

                multiple_img_title_content.setText(topNews.getTitle());
                break;
            case 3:
                String[] splitMax = topNews.getListimg().split(",");
                Glide.with(context).load(SystemUtil.JudgeUrl(splitMax[0])).skipMemoryCache(false).error(R.drawable.banner_off).into(only_max_img);
                only_max_img_title_content.setText(topNews.getTitle());
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topItemkListener != null)
                    topItemkListener.onTopItemClick(position);
            }
        });


    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return topNews.size() == 0 ? 0 : topNews.size();
    }

    private void refreshViewStatus(MyViewHolder holder, int shareStatus, int viewType) {
        switch (viewType) {
            case 1:
                holder.itemView.findViewById(R.id.layout_only_img).setVisibility(View.VISIBLE);
                holder.itemView.findViewById(R.id.layout_only_max_img_news).setVisibility(View.GONE);
                holder.itemView.findViewById(R.id.layout_multiple_img_news).setVisibility(View.GONE);
                break;
            case 2:
                holder.itemView.findViewById(R.id.layout_only_img).setVisibility(View.GONE);
                holder.itemView.findViewById(R.id.layout_only_max_img_news).setVisibility(View.VISIBLE);
                holder.itemView.findViewById(R.id.layout_multiple_img_news).setVisibility(View.GONE);
                break;
            case 3:
                holder.itemView.findViewById(R.id.layout_only_img).setVisibility(View.GONE);
                holder.itemView.findViewById(R.id.layout_only_max_img_news).setVisibility(View.GONE);
                holder.itemView.findViewById(R.id.layout_multiple_img_news).setVisibility(View.VISIBLE);
                break;
        }
        if (shareStatus == 1) {
            holder.itemView.findViewById(R.id.shares_check).setVisibility(View.VISIBLE);
        } else {
            holder.itemView.findViewById(R.id.shares_check).setVisibility(View.GONE);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface TopItemListener {
        void onTopItemClick(int i);
    }

    public TopItemListener topItemkListener;

    public void setOnTopItemkListener(TopItemListener topItemkListener) {
        this.topItemkListener = topItemkListener;
    }

}
