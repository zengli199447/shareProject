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

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.HotAllDataBean;
import com.example.administrator.sharenebulaproject.ui.holder.MyViewHolder;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * Created by Administrator on 2018/8/13.
 */

public class TopRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<HotAllDataBean.Result.topNews> TheArticleList;
    private HotAllDataBean.Result.topNews newsBean;


    public TopRecyclerViewAdapter(Context context, List<HotAllDataBean.Result.topNews> theArticleList) {
        this.context = context;
        TheArticleList = theArticleList;
    }

    @Override
    public int getItemViewType(int position) {
        int Type = 0;
        HotAllDataBean.Result.topNews newsBean = TheArticleList.get(position);
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
        return Type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
            default:
                throw new InvalidParameterException();
        }
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        newsBean = TheArticleList.get(position);

        TextView title_content =  holder.itemView.findViewById(R.id.title_content);
        TextView time_no =  holder.itemView.findViewById(R.id.time_no);
        TextView amount_read_no =  holder.itemView.findViewById(R.id.amount_read_no);

        ImageView only_min_img =  holder.itemView.findViewById(R.id.only_min_img);

        ImageView min_img1 =  holder.itemView.findViewById(R.id.min_img1);
        ImageView min_img2 =  holder.itemView.findViewById(R.id.min_img2);
        ImageView min_img3 =  holder.itemView.findViewById(R.id.min_img3);

        ImageView only_max_img =  holder.itemView.findViewById(R.id.only_max_img);

        TextView starbean_no =  holder.itemView.findViewById(R.id.starbean_no);
        TextView shares_no =  holder.itemView.findViewById(R.id.shares_no);
        TextView shares_check_no =  holder.itemView.findViewById(R.id.shares_check_no);

        RelativeLayout progress_bar =  holder.itemView.findViewById(R.id.progress_bar);

        LinearLayout layout_news_item =  holder.itemView.findViewById(R.id.layout_news_item);

        View line_placeholder =  holder.itemView.findViewById(R.id.line_placeholder);

        TextView after_refresh =  holder.itemView.findViewById(R.id.after_refresh);

        View bottom_line = holder.itemView.findViewById(R.id.bottom_line);

        TextView top = holder.itemView.findViewById(R.id.top);

        title_content.setText(newsBean.getTitle());
        time_no.setText(newsBean.getCreatedate());
        amount_read_no.setText(new StringBuffer().append("阅读: ").append(newsBean.getAmount_read()).toString());
        String MinImg = newsBean.getListimg();
        if (only_min_img != null) {
            String[] split = newsBean.getListimg().split(",");
            Glide.with(context).load(new StringBuffer().append(DataClass.FileUrl).append(split[0]).toString()).error(R.drawable.banner_off).into(only_min_img);
        }
        if (min_img1 != null) {
            ImageView views[] = {min_img1, min_img2, min_img3};
            ViewGroup.LayoutParams minImg1layoutParams = min_img1.getLayoutParams();
            ViewGroup.LayoutParams minImg2layoutParams = min_img2.getLayoutParams();
            ViewGroup.LayoutParams minImg3layoutParams = min_img3.getLayoutParams();
            int width = DataClass.WINDOWS_WIDTH - 30;
            minImg1layoutParams.width = SystemUtil.dp2px(context, width / 3);
            minImg2layoutParams.width = SystemUtil.dp2px(context, width / 3);
            minImg3layoutParams.width = SystemUtil.dp2px(context, width / 3);

            min_img1.setLayoutParams(minImg1layoutParams);
            min_img2.setLayoutParams(minImg2layoutParams);
            min_img3.setLayoutParams(minImg3layoutParams);

            String[] split = newsBean.getListimg().split(",");
            for (int j = 0; j < split.length; j++) {
                Glide.with(context).load(new StringBuffer().append(DataClass.FileUrl).append(split[j]).toString()).error(R.drawable.banner_off).into(views[j]);
            }
        }
        String MaxImg = newsBean.getListimg();
        if (only_max_img != null) {
            String[] split = newsBean.getListimg().split(",");
            Glide.with(context).load(new StringBuffer().append(DataClass.FileUrl).append(split[0]).toString()).error(R.drawable.banner_off).into(only_max_img);
        }
        if (newsBean.getIfcanmoney() == 1) {
            if (starbean_no != null)
                starbean_no.setText(new StringBuffer().append("奖励池: ").append(newsBean.getStarbean()).append("星豆").toString());
            if (shares_no != null)
                shares_no.setText(new StringBuffer().append("分享: ").append(newsBean.getAmount_share()).append("人").toString());

            if (Integer.valueOf(newsBean.getStarbean()) > 0) {
                if (shares_check_no != null) {
                    shares_check_no.setTextColor(context.getResources().getColor(R.color.fatigue_red));
                    shares_check_no.setBackground(context.getResources().getDrawable(R.drawable.corners_hollow_text_red));
                    Drawable img = context.getResources().getDrawable(R.drawable.share_no_icon);
                    img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                    shares_check_no.setCompoundDrawables(null, null, img, null);
                }
            } else {
                if (shares_check_no != null) {
                    shares_check_no.setTextColor(context.getResources().getColor(R.color.gray_light));
                    shares_check_no.setBackground(context.getResources().getDrawable(R.drawable.corners_hollow_text_gray));
                    Drawable img = context.getResources().getDrawable(R.drawable.share_off_icon);
                    img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                    shares_check_no.setCompoundDrawables(null, null, img, null);
                }
            }
            if (shares_check_no != null)
                shares_check_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onCheckListener != null)
                            onCheckListener.onClick(position);
                    }
                });
        }

        if (layout_news_item != null)
            layout_news_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogUtil.e("DailyRecyclerViewAdapter", "newsBean.getNewsid() : " + newsBean.getNewsid());
                    if (onItemCheckListener != null)
                        onItemCheckListener.onItemClick(position);
                }
            });


        if (position == TheArticleList.size() - 1) {
            bottom_line.setVisibility(View.GONE);
        } else {
            bottom_line.setVisibility(View.VISIBLE);
        }

        top.setVisibility(View.VISIBLE);

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
        public ViewHolder(View itemView) {
            super(itemView);



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
