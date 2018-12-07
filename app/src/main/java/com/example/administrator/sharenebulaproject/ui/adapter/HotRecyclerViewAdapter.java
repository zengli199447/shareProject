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
import com.example.administrator.sharenebulaproject.model.bean.HotAllDataBean;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * Created by Administrator on 2018/8/13.
 */

public class HotRecyclerViewAdapter extends RecyclerView.Adapter<HotRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<HotAllDataBean.Result.newsBean> TheArticleList;
    private HotAllDataBean.Result.newsBean newsBean;

    // 普通布局
    private final int TYPE_ITEM = 1;
    // 脚布局
    private final int TYPE_FOOTER = 2;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;

    // 正在加载
    public final int LOADING = 1;

    boolean scrolling = true;

    public HotRecyclerViewAdapter(Context context, List<HotAllDataBean.Result.newsBean> theArticleList) {
        this.context = context;
        TheArticleList = theArticleList;
    }

    @Override
    public int getItemViewType(int position) {
        int Type = 0;
        if (position == TheArticleList.size()) {
            Type = 7;
        } else {
            HotAllDataBean.Result.newsBean newsBean = TheArticleList.get(position);
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
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_view, parent, false);
                break;
            case 8:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_refresh_view, parent, false);
                break;
            default:
                throw new InvalidParameterException();
        }
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == TheArticleList.size()) {
            switch (loadState) {
                case LOADING: // 正在加载
                    holder.progress_bar.setVisibility(View.VISIBLE);
                    break;
                case LOADING_COMPLETE:
                    // 加载完成
                    holder.progress_bar.setVisibility(View.INVISIBLE);
                    break;
                case LOADING_END:
                    // 加载到底
                    holder.progress_bar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        } else {
            newsBean = TheArticleList.get(position);
            holder.title_content.setText(newsBean.getTitle());
            holder.time_no.setText(newsBean.getSource());
            holder.amount_read_no.setText(new StringBuffer().append("阅读: ").append(newsBean.getAmount_read()).toString());
            String MinImg = newsBean.getListimg();
            if (holder.only_min_img != null) {
                String[] split = newsBean.getListimg().split(",");
                if (scrolling) {
                    Glide.with(context).load(SystemUtil.JudgeUrl(split[0])).error(R.drawable.banner_off).into(holder.only_min_img);
                } else {
                    Glide.with(context).load(R.drawable.banner_off).into(holder.only_min_img);
                }
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
                    if (scrolling) {
                        Glide.with(context).load(SystemUtil.JudgeUrl(split[j])).error(R.drawable.banner_off).into(views[j]);
                    } else {
                        Glide.with(context).load(R.drawable.banner_off).into(views[j]);
                    }
                }
            }
            String MaxImg = newsBean.getListimg();
            if (holder.only_max_img != null) {
                String[] split = newsBean.getListimg().split(",");
                if (scrolling) {
                    Glide.with(context).load(SystemUtil.JudgeUrl(split[0])).error(R.drawable.banner_off).into(holder.only_max_img);
                } else {
                    Glide.with(context).load(R.drawable.banner_off).into(holder.only_max_img);
                }
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


            if (position == TheArticleList.size() - 1) {
                holder.bottom_line.setVisibility(View.GONE);
            } else {
                holder.bottom_line.setVisibility(View.VISIBLE);
            }

        }

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return TheArticleList.size() == 0 ? 0 : TheArticleList.size() + 1;
    }

    public void setScrolling(boolean scrolling) {
        this.scrolling = scrolling;
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

        }
    }

    public interface onCheckListener {
        void onClick(int i);
    }

    public interface onItemCheckListener {
        void onItemClick(int i);

        void refreshItemClick();
    }

    public onCheckListener onCheckListener;

    public onItemCheckListener onItemCheckListener;

    public void setOnCheckListener(onCheckListener onCheckListener) {
        this.onCheckListener = onCheckListener;
    }

    public void setOnItemCheckListener(onItemCheckListener onItemCheckListener) {
        this.onItemCheckListener = onItemCheckListener;
    }

    /**
     * 设置上拉加载状态 * * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

}
