package com.example.administrator.sharenebulaproject.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobads.BaiduNativeAdPlacement;
import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.global.AppKeyConfig;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.HotAllDataBean;
import com.example.administrator.sharenebulaproject.model.bean.RefreshDateBean;
import com.example.administrator.sharenebulaproject.model.bean.SelfBuiltAdvertisingBean;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.AdvertisingBuilder;
import com.example.administrator.sharenebulaproject.widget.CalendarBuilder;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/8/13.
 */

public class DiversifiedAdapter extends RecyclerView.Adapter<DiversifiedAdapter.ViewHolder> {

    private String TAG = getClass().getSimpleName();

    private Context context;
    private List<Object> TheArticleList;
    private int indexRefreshStatus;
    private long refreshTime;
    private final int height;

    public DiversifiedAdapter(Context context, List<Object> theArticleList) {
        this.context = context;
        TheArticleList = theArticleList;
        height = SystemUtil.dp2px(context, 40);
    }

    @Override
    public int getItemViewType(int position) {
        int Type = -1;
        Object object = TheArticleList.get(position);
        if (object instanceof RefreshDateBean) {
            Type = 1;
        } else if (object instanceof HotAllDataBean.Result.newsBean) {
            HotAllDataBean.Result.newsBean newsBean = (HotAllDataBean.Result.newsBean) object;
            switch (newsBean.getType()) {
                case 0:
                    Type = 0;
                    break;
                case 2:
                    Type = 2;
                    break;
            }
        }
        return Type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_type, parent, false);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_refresh_view, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(context).inflate(R.layout.feed_h5_item_placement, null);
                break;
            default:
                throw new InvalidParameterException();
        }
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Object object = TheArticleList.get(position);
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case 0:
                HotAllDataBean.Result.newsBean newsBean = (HotAllDataBean.Result.newsBean) object;
                refreshViewStatus(holder, newsBean.getIfcanmoney(), newsBean.getViewtype());
                if (newsBean.getType() == 0) {
                    if (!newsBean.getSource().equals(holder.form_to.getText().toString()))
                        holder.form_to.setText(newsBean.getSource());
                    if (!new StringBuffer().append("阅读: ").append(newsBean.getAmount_read()).toString().equals(holder.reading_count.getText().toString()))
                        holder.reading_count.setText(new StringBuffer().append("阅读: ").append(newsBean.getAmount_read()).toString());
                    if (!new StringBuffer().append("奖励池: ").append(newsBean.getStarbean()).append("星豆").toString().equals(holder.starbean.getText().toString()))
                        holder.starbean.setText(new StringBuffer().append("奖励池: ").append(newsBean.getStarbean()).append("星豆").toString());
                }
                switch (newsBean.getType()) {
                    case 0:
                        switch (newsBean.getViewtype()) {
                            case 1:
                                String[] split = newsBean.getListimg().split(",");
                                Glide.with(context).load(SystemUtil.JudgeUrl(split[0])).error(R.drawable.banner_off).into(holder.only_min_img);
                                holder.only_img_title_content.setText(newsBean.getTitle());
                                break;
                            case 2:
                                ViewGroup.LayoutParams minImg1layoutParams = holder.min_img1.getLayoutParams();
                                int width = DataClass.WINDOWS_WIDTH - 30;
                                minImg1layoutParams.width = SystemUtil.dp2px(context, width / 3);
                                holder.min_img1.setLayoutParams(minImg1layoutParams);
                                holder.min_img2.setLayoutParams(minImg1layoutParams);
                                holder.min_img3.setLayoutParams(minImg1layoutParams);

                                String[] splitMultiple = newsBean.getListimg().split(",");
                                Glide.with(context).load(SystemUtil.JudgeUrl(splitMultiple[0])).error(R.drawable.banner_off).into(holder.min_img1);
                                Glide.with(context).load(SystemUtil.JudgeUrl(splitMultiple[1])).error(R.drawable.banner_off).into(holder.min_img2);
                                Glide.with(context).load(SystemUtil.JudgeUrl(splitMultiple[2])).error(R.drawable.banner_off).into(holder.min_img3);

                                holder.multiple_img_title_content.setText(newsBean.getTitle());
                                break;
                            case 3:
                                String[] splitMax = newsBean.getListimg().split(",");
                                Glide.with(context).load(SystemUtil.JudgeUrl(splitMax[0])).error(R.drawable.banner_off).into(holder.only_max_img);
                                holder.only_max_img_title_content.setText(newsBean.getTitle());
                                break;
                        }
                        break;
                    case 1:
                        String[] split = newsBean.getListimg().split(",");
                        Glide.with(context).load(SystemUtil.JudgeUrl(split[0])).error(R.drawable.banner_off).into(holder.advertising_img);
                        holder.advertising_title_content.setText(newsBean.getTitle());
                        holder.advertising_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (onItemCheckListener != null)
                                    onItemCheckListener.onItemClick(position);
                            }
                        });
                        break;
                }

                break;
            case 1:
                RefreshDateBean refreshDateBean = (RefreshDateBean) object;
                ViewGroup.LayoutParams layoutParams = holder.layout_after_refresh.getLayoutParams();
                if (refreshDateBean.isStatus()) {
                    layoutParams.height = height;
                    holder.layout_after_refresh.setLayoutParams(layoutParams);
                    String timeDifference = CalendarBuilder.getTimeDifference(refreshTime, new Date().getTime());
                    holder.after_refresh.setText(new StringBuffer().append(timeDifference).append("前看到这里   点击刷新"));
                } else {
                    layoutParams.height = 0;
                    holder.layout_after_refresh.setLayoutParams(layoutParams);
                }
                holder.layout_after_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemCheckListener != null)
                            onItemCheckListener.onRefreshItemClick();
                    }
                });
                break;
            case 2:
                BaiduNativeAdPlacement placement = new BaiduNativeAdPlacement();
                placement.setSessionId(1); // 设置页面id，不同listview不同，从1开始的正整数，可选
                placement.setPositionId(1); // 设置广告在页面的楼层，从1开始的正整数，可选
                placement.setApId(AppKeyConfig.ADVERTISING_BAIDU_ADVERTISING_RECYCLER_ID);
                AdvertisingBuilder.RecycleAdvertising(context, holder.native_outer_view, placement);
                break;

        }

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return TheArticleList.size() == 0 ? 0 : TheArticleList.size();
    }

    private void refreshViewStatus(ViewHolder holder, int shareStatus, int viewType) {
        switch (viewType) {
            case 1:
                holder.layout_only_img.setVisibility(View.VISIBLE);
                holder.layout_only_max_img_news.setVisibility(View.GONE);
                holder.layout_multiple_img_news.setVisibility(View.GONE);
                break;
            case 2:
                holder.layout_only_img.setVisibility(View.GONE);
                holder.layout_only_max_img_news.setVisibility(View.VISIBLE);
                holder.layout_multiple_img_news.setVisibility(View.GONE);
                break;
            case 3:
                holder.layout_only_img.setVisibility(View.GONE);
                holder.layout_only_max_img_news.setVisibility(View.GONE);
                holder.layout_multiple_img_news.setVisibility(View.VISIBLE);
                break;
        }
        if (shareStatus == 1) {
            holder.shares_check.setVisibility(View.VISIBLE);
        } else {
            holder.shares_check.setVisibility(View.GONE);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View layout_after_refresh;
        private final TextView after_refresh;
        private final View line_placeholder;
        private final View bottom_line;
        private final LinearLayout layout_only_img;
        private final ImageView only_min_img;
        private final TextView only_img_title_content;
        private final LinearLayout layout_only_max_img_news;
        private final TextView only_max_img_title_content;
        private final ImageView only_max_img;
        private final LinearLayout layout_multiple_img_news;
        private final TextView multiple_img_title_content;
        private final ImageView min_img1;
        private final ImageView min_img2;
        private final ImageView min_img3;
        private final TextView form_to;
        private final TextView reading_count;
        private final TextView starbean;
        private final TextView shares_check;
        private final RelativeLayout native_outer_view;
        private final ImageView advertising_img;
        private final TextView advertising_title_content;

        public ViewHolder(View itemView) {
            super(itemView);
            layout_after_refresh = itemView.findViewById(R.id.layout_after_refresh);
            after_refresh = itemView.findViewById(R.id.after_refresh);

            line_placeholder = itemView.findViewById(R.id.line_placeholder);
            bottom_line = itemView.findViewById(R.id.bottom_line);

            layout_only_img = itemView.findViewById(R.id.layout_only_img);
            only_min_img = itemView.findViewById(R.id.only_min_img);
            only_img_title_content = itemView.findViewById(R.id.only_img_title_content);

            layout_only_max_img_news = itemView.findViewById(R.id.layout_only_max_img_news);
            only_max_img_title_content = itemView.findViewById(R.id.only_max_img_title_content);
            only_max_img = itemView.findViewById(R.id.only_max_img);

            layout_multiple_img_news = itemView.findViewById(R.id.layout_multiple_img_news);
            multiple_img_title_content = itemView.findViewById(R.id.multiple_img_title_content);
            min_img1 = itemView.findViewById(R.id.min_img1);
            min_img2 = itemView.findViewById(R.id.min_img2);
            min_img3 = itemView.findViewById(R.id.min_img3);

            form_to = itemView.findViewById(R.id.form_to);
            reading_count = itemView.findViewById(R.id.reading_count);
            starbean = itemView.findViewById(R.id.starbean);
            shares_check = itemView.findViewById(R.id.shares_check);

            native_outer_view = itemView.findViewById(R.id.native_outer_view);

            advertising_img = itemView.findViewById(R.id.advertising_img);
            advertising_title_content = itemView.findViewById(R.id.advertising_title_content);

        }
    }

    public interface onCheckListener {
        void onClick(int i);
    }

    public interface onItemCheckListener {
        void onItemClick(int i);

        void onRefreshItemClick();
    }

    public onCheckListener onCheckListener;

    public onItemCheckListener onItemCheckListener;

    public void setOnCheckListener(onCheckListener onCheckListener) {
        this.onCheckListener = onCheckListener;
    }

    public void setOnItemCheckListener(onItemCheckListener onItemCheckListener) {
        this.onItemCheckListener = onItemCheckListener;
    }

    public void refreshViewStatus(int indexRefreshStatus, long refreshTime) {
        this.indexRefreshStatus = indexRefreshStatus;
        this.refreshTime = refreshTime;
    }

}
