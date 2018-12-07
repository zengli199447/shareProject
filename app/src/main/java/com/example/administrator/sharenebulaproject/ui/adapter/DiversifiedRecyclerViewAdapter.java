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

import com.baidu.mobad.feeds.RequestParameters;
import com.baidu.mobads.BaiduNativeAdPlacement;
import com.baidu.mobads.BaiduNativeH5AdView;
import com.baidu.mobads.BaiduNativeH5AdViewManager;
import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
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

public class DiversifiedRecyclerViewAdapter extends RecyclerView.Adapter<DiversifiedRecyclerViewAdapter.ViewHolder> {

    private String TAG = getClass().getSimpleName();

    private Context context;
    private List<Object> TheArticleList;
    private int indexRefreshStatus;
    private long refreshTime;
    private final int height;

    public DiversifiedRecyclerViewAdapter(Context context, List<Object> theArticleList) {
        this.context = context;
        TheArticleList = theArticleList;
        height = SystemUtil.dp2px(context, 40);
    }

    @Override
    public int getItemViewType(int position) {
        int Type = 0;
        Object object = TheArticleList.get(position);
        if (object instanceof BaiduNativeAdPlacement) {
            Type = 8;
        } else if (object instanceof RefreshDateBean) {
            Type = 7;
        } else if (object instanceof SelfBuiltAdvertisingBean) {
            Type = 9;
        } else if (object instanceof HotAllDataBean.Result.newsBean) {
            HotAllDataBean.Result.newsBean newsBean = (HotAllDataBean.Result.newsBean) object;
            switch (newsBean.getViewtype()) {
                case 0:
                    if (newsBean.getIfcanmoney() == 0) {
                        Type = 1;
                    } else {
                        Type = 2;
                    }
                    break;
                case 1:
                    if (newsBean.getIfcanmoney() == 0) {
                        Type = 1;
                    } else {
                        Type = 2;
                    }
                    break;
                case 2:
                    if (newsBean.getIfcanmoney() == 0) {
                        Type = 3;
                    } else {
                        Type = 4;
                    }
                    break;
                case 3:
                    if (newsBean.getIfcanmoney() == 0) {
                        Type = 5;
                    } else {
                        Type = 6;
                    }
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_page_type_1_off, parent, false);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_page_type_1_off, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_page_type_1_on, parent, false);
                break;
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_page_type_2_off, parent, false);
                break;
            case 4:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_page_type_2_on, parent, false);
                break;
            case 5:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_page_type_3_off, parent, false);
                break;
            case 6:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_page_type_3_on, parent, false);
                break;
            case 7:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_refresh_view, parent, false);
                break;
            case 8:
                view = LayoutInflater.from(context).inflate(R.layout.feed_h5_item_placement, null);
                break;
            case 9:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_self_built_advertising, parent, false);
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
        int viewType = getItemViewType(position);
        if (viewType == 7) {
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
        } else if (viewType == 8) {
            LogUtil.e(TAG, "广告位");
            AdvertisingBuilder.RecycleAdvertising(context, holder.native_outer_view, object);
        } else if (viewType == 9) {
            SelfBuiltAdvertisingBean selfBuiltAdvertisingBean = (SelfBuiltAdvertisingBean) object;
            Glide.with(context).load(SystemUtil.JudgeUrl(selfBuiltAdvertisingBean.getImg())).error(R.drawable.banner_off).into(holder.advertising_img);
            holder.advertising_title_content.setText(selfBuiltAdvertisingBean.getTitle());
            holder.advertising_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemCheckListener != null)
                        onItemCheckListener.onItemClick(position);
                }
            });
        } else if (object instanceof HotAllDataBean.Result.newsBean) {
            final HotAllDataBean.Result.newsBean newsBean = (HotAllDataBean.Result.newsBean) object;
            holder.title_content.setText(newsBean.getTitle());
            holder.time_no.setText(newsBean.getSource());
            holder.amount_read_no.setText(new StringBuffer().append("阅读: ").append(newsBean.getAmount_read()).toString());
            String MinImg = newsBean.getListimg();
            if (holder.only_min_img != null && "1".equals(newsBean.getListimg())) {
                String[] split = newsBean.getListimg().split(",");
                Glide.with(context).load(SystemUtil.JudgeUrl(split[0])).error(R.drawable.banner_off).into(holder.only_min_img);
            }
            if (holder.min_img1 != null) {
                ImageView views[] = {holder.min_img1, holder.min_img2, holder.min_img3};
                ViewGroup.LayoutParams minImg1layoutParams = holder.min_img1.getLayoutParams();
                ViewGroup.LayoutParams minImg2layoutParams = holder.min_img2.getLayoutParams();
                ViewGroup.LayoutParams minImg3layoutParams = holder.min_img3.getLayoutParams();
                int width = DataClass.WINDOWS_WIDTH - 30;
                minImg1layoutParams.width = SystemUtil.dp2px(context, width / 3);
                minImg2layoutParams.width = SystemUtil.dp2px(context, width / 3);
                minImg3layoutParams.width = SystemUtil.dp2px(context, width / 3);

                holder.min_img1.setLayoutParams(minImg1layoutParams);
                holder.min_img2.setLayoutParams(minImg2layoutParams);
                holder.min_img3.setLayoutParams(minImg3layoutParams);

                String[] split = newsBean.getListimg().split(",");
                for (int j = 0; j < split.length; j++) {
                    Glide.with(context).load(SystemUtil.JudgeUrl(split[j])).error(R.drawable.banner_off).into(views[j]);
                }
            }
            String MaxImg = newsBean.getListimg();
            if (holder.only_max_img != null && "1".equals(newsBean.getListimg())) {
                String[] split = newsBean.getListimg().split(",");
                Glide.with(context).load(SystemUtil.JudgeUrl(split[0])).error(R.drawable.banner_off).into(holder.only_max_img);
            }
            if (newsBean.getIfcanmoney() == 1) {
                if (holder.starbean_no != null)
                    holder.starbean_no.setText(new StringBuffer().append("奖励池: ").append(newsBean.getStarbean()).append("星豆").toString());
                if (holder.shares_no != null)
                    holder.shares_no.setText(new StringBuffer().append("分享: ").append(newsBean.getAmount_share()).append("人").toString());

                if (Integer.valueOf(newsBean.getStarbean()) > 0) {
                    if (holder.shares_check_no != null) {
                        holder.shares_check_no.setTextColor(context.getResources().getColor(R.color.fatigue_red));
                        holder.shares_check_no.setBackground(context.getResources().getDrawable(R.drawable.corners_hollow_text_red));
                        Drawable img = context.getResources().getDrawable(R.drawable.share_no_icon);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        holder.shares_check_no.setCompoundDrawables(null, null, img, null);
                    }
                } else {
                    if (holder.shares_check_no != null) {
                        holder.shares_check_no.setTextColor(context.getResources().getColor(R.color.gray_light));
                        holder.shares_check_no.setBackground(context.getResources().getDrawable(R.drawable.corners_hollow_text_gray));
                        Drawable img = context.getResources().getDrawable(R.drawable.share_off_icon);
                        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                        holder.shares_check_no.setCompoundDrawables(null, null, img, null);
                    }
                }
                if (holder.shares_check_no != null)
                    holder.shares_check_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (onCheckListener != null)
                                onCheckListener.onClick(position);
                        }
                    });
            }

            if (holder.layout_news_item != null)
                holder.layout_news_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LogUtil.e("DailyRecyclerViewAdapter", "newsBean.getNewsid() : " + newsBean.getNewsid());
                        if (onItemCheckListener != null)
                            onItemCheckListener.onItemClick(position);
                    }
                });

            if ((position + 1) < TheArticleList.size() && !(TheArticleList.get(position + 1) instanceof HotAllDataBean.Result.newsBean)) {
                holder.bottom_line.setVisibility(View.GONE);
            } else if (position != TheArticleList.size() - 1) {
                holder.bottom_line.setVisibility(View.VISIBLE);
            } else {
                holder.bottom_line.setVisibility(View.GONE);
            }

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

    public static class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView title_content;
        private final TextView time_no;
        private final TextView amount_read_no;
        private final ImageView only_min_img;
        private final ImageView min_img1;
        private final ImageView min_img2;
        private final ImageView min_img3;
        private final ImageView only_max_img;
        private final TextView starbean_no;
        private final TextView shares_no;
        private final TextView shares_check_no;
        private final RelativeLayout progress_bar;
        private final LinearLayout layout_news_item;
        private final View line_placeholder;
        private final TextView after_refresh;
        private final View bottom_line;
        private final RelativeLayout layout_after_refresh;
        private final RelativeLayout native_outer_view;
        private final ImageView advertising_img;
        private final TextView advertising_title_content;

        public ViewHolder(View itemView) {
            super(itemView);
            title_content = (TextView) itemView.findViewById(R.id.title_content);
            time_no = (TextView) itemView.findViewById(R.id.time_no);
            amount_read_no = (TextView) itemView.findViewById(R.id.amount_read_no);

            only_min_img = (ImageView) itemView.findViewById(R.id.only_min_img);

            min_img1 = (ImageView) itemView.findViewById(R.id.min_img1);
            min_img2 = (ImageView) itemView.findViewById(R.id.min_img2);
            min_img3 = (ImageView) itemView.findViewById(R.id.min_img3);

            only_max_img = (ImageView) itemView.findViewById(R.id.only_max_img);

            starbean_no = (TextView) itemView.findViewById(R.id.starbean_no);
            shares_no = (TextView) itemView.findViewById(R.id.shares_no);
            shares_check_no = (TextView) itemView.findViewById(R.id.shares_check_no);

            progress_bar = (RelativeLayout) itemView.findViewById(R.id.progress_bar);

            layout_news_item = (LinearLayout) itemView.findViewById(R.id.layout_news_item);

            line_placeholder = (View) itemView.findViewById(R.id.line_placeholder);

            after_refresh = (TextView) itemView.findViewById(R.id.after_refresh);

            bottom_line = itemView.findViewById(R.id.bottom_line);

            layout_after_refresh = itemView.findViewById(R.id.layout_after_refresh);

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
