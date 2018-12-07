package com.example.administrator.sharenebulaproject.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.DailyNetBean;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/8/13.
 */

public class DailyRecyclerViewAdapter extends RecyclerView.Adapter<DailyRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<DailyNetBean.ResultBean.NewsBean> TheArticleList;
    private DailyNetBean.ResultBean.NewsBean newsBean;

    public DailyRecyclerViewAdapter(Context context, List<DailyNetBean.ResultBean.NewsBean> theArticleList) {
        this.context = context;
        TheArticleList = theArticleList;
    }

    @Override
    public int getItemViewType(int position) {
        int Type = 0;
//        if (position == TheArticleList.size()) {
//            Type = 7;
//        } else {
        DailyNetBean.ResultBean.NewsBean newsBean = TheArticleList.get(position);
        switch (Integer.valueOf(newsBean.getViewtype())) {
            case 0:
                if (Integer.valueOf(newsBean.getIfcanmoney()) == 0) {
                    Type = 1;
                } else {
                    Type = 2;
                }
                break;
            case 1:
                if (Integer.valueOf(newsBean.getIfcanmoney()) == 0) {
                    Type = 1;
                } else {
                    Type = 2;
                }
                break;
            case 2:
                if (Integer.valueOf(newsBean.getIfcanmoney()) == 0) {
                    Type = 3;
                } else {
                    Type = 4;
                }
                break;
            case 3:
                if (Integer.valueOf(newsBean.getIfcanmoney()) == 0) {
                    Type = 5;
                } else {
                    Type = 6;
                }
                break;
        }
//        }
        return Type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_page_type_1_off, parent, false);
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_view, parent, false);
                break;
        }
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (position == TheArticleList.size()) {
            return;
        }
        newsBean = TheArticleList.get(position);
        holder.title_content.setText(newsBean.getTitle());
        holder.time_no.setText(newsBean.getCreatedate());
        holder.amount_read_no.setText(new StringBuffer().append("阅读: ").append(newsBean.getAmount_read()).toString());
        String MinImg = newsBean.getListimg();
        if (holder.only_min_img != null) {
            String[] split = newsBean.getListimg().split(",");
            Glide.with(context).load(new StringBuffer().append(DataClass.FileUrl).append(split[0]).toString()).error(R.drawable.banner_off).into(holder.only_min_img);
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
                Glide.with(context).load(new StringBuffer().append(DataClass.FileUrl).append(split[j]).toString()).error(R.drawable.banner_off).into(views[j]);
            }
        }
        String MaxImg = newsBean.getListimg();
        if (holder.only_max_img != null) {
            String[] split = newsBean.getListimg().split(",");
            Glide.with(context).load(new StringBuffer().append(DataClass.FileUrl).append(split[0]).toString()).error(R.drawable.banner_off).into(holder.only_max_img);
        }

        if (Integer.valueOf(newsBean.getIfcanmoney()) == 1) {
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
                        if (onCheckListener != null) {
                            onCheckListener.onClick(position);
                        }
                    }
                });
        }

        holder.layout_news_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.e("DailyRecyclerViewAdapter", "newsBean.getNewsid() : " + newsBean.getNewsid());
                if (onItemCheckListener != null)
                    onItemCheckListener.onItemClick(position);
            }
        });

        if (holder.progress_bar != null)
            holder.progress_bar.setBackgroundColor(context.getResources().getColor(R.color.blue));

        if (position == 0) {
            holder.line_placeholder.setVisibility(View.VISIBLE);
        } else {
            holder.line_placeholder.setVisibility(View.GONE);
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

        }
    }

    public interface onCheckListener {
        void onClick(int i);
    }

    public interface onItemCheckListener {
        void onItemClick(int i);
    }

    public onCheckListener onCheckListener;

    public onItemCheckListener onItemCheckListener;

    public void setOnCheckListener(onCheckListener onCheckListener) {
        this.onCheckListener = onCheckListener;
    }

    public void setOnItemCheckListener(onItemCheckListener onItemCheckListener) {
        this.onItemCheckListener = onItemCheckListener;
    }

}
