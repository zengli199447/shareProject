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
import com.example.administrator.sharenebulaproject.ui.holder.MyViewHolder;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.AdvertisingBuilder;
import com.example.administrator.sharenebulaproject.widget.CalendarBuilder;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/8/13.
 */

public class DiversifiedAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private String TAG = getClass().getSimpleName();

    private Context context;
    private List<Object> TheArticleList;
    private List<NativeExpressADView> nativeExpressADViewList;
    private int indexRefreshStatus;
    private long refreshTime;
    private final int height;
    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap = new HashMap<>();

    public DiversifiedAdapter(Context context, List<Object> theArticleList, List<NativeExpressADView> nativeExpressADViewList) {
        this.context = context;
        this.nativeExpressADViewList = nativeExpressADViewList;
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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Object object = TheArticleList.get(position);
        int itemViewType = getItemViewType(position);

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


        switch (itemViewType) {
            case 0:
                HotAllDataBean.Result.newsBean newsBean = (HotAllDataBean.Result.newsBean) object;
                refreshViewStatus(holder, newsBean.getIfcanmoney(), newsBean.getViewtype());
                if (newsBean.getType() == 0) {
//                    if (!newsBean.getSource().equals(form_to.getText().toString()))
                        form_to.setText(newsBean.getSource());
//                    if (!new StringBuffer().append("阅读: ").append(newsBean.getAmount_read()).toString().equals(reading_count.getText().toString()))
                        reading_count.setText(new StringBuffer().append("阅读: ").append(newsBean.getAmount_read()).toString());
//                    if (!new StringBuffer().append("奖励池: ").append(newsBean.getStarbean()).append("星豆").toString().equals(starbean.getText().toString()))
                        starbean.setText(new StringBuffer().append("奖励池: ").append(newsBean.getStarbean()).append("星豆").toString());
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemCheckListener != null)
                            onItemCheckListener.onItemClick(position);
                    }
                });

                switch (newsBean.getType()) {
                    case 0:
                        switch (newsBean.getViewtype()) {
                            case 1:
                                String[] split = newsBean.getListimg().split(",");
                                Glide.with(context).load(SystemUtil.JudgeUrl(split[0])).skipMemoryCache(false).error(R.drawable.banner_off).into(only_min_img);
                                only_img_title_content.setText(newsBean.getTitle());
                                break;
                            case 2:
                                ViewGroup.LayoutParams minImg1layoutParams = min_img1.getLayoutParams();
                                int width = DataClass.WINDOWS_WIDTH - 30;
                                minImg1layoutParams.width = SystemUtil.dp2px(context, width / 3);
                                min_img1.setLayoutParams(minImg1layoutParams);
                                min_img2.setLayoutParams(minImg1layoutParams);
                                min_img3.setLayoutParams(minImg1layoutParams);

                                String[] splitMultiple = newsBean.getListimg().split(",");
                                Glide.with(context).load(SystemUtil.JudgeUrl(splitMultiple[0])).skipMemoryCache(false).error(R.drawable.banner_off).into(min_img1);
                                Glide.with(context).load(SystemUtil.JudgeUrl(splitMultiple[1])).skipMemoryCache(false).error(R.drawable.banner_off).into(min_img2);
                                Glide.with(context).load(SystemUtil.JudgeUrl(splitMultiple[2])).skipMemoryCache(false).error(R.drawable.banner_off).into(min_img3);

                                multiple_img_title_content.setText(newsBean.getTitle());
                                break;
                            case 3:
                                String[] splitMax = newsBean.getListimg().split(",");
                                Glide.with(context).load(SystemUtil.JudgeUrl(splitMax[0])).skipMemoryCache(false).error(R.drawable.banner_off).into(only_max_img);
                                only_max_img_title_content.setText(newsBean.getTitle());
                                break;
                        }
                        break;
                    case 1:
                        String[] split = newsBean.getListimg().split(",");
                        Glide.with(context).load(SystemUtil.JudgeUrl(split[0])).skipMemoryCache(false).error(R.drawable.banner_off).into(advertising_img);
                        advertising_title_content.setText(newsBean.getTitle());
                        break;
                }

                break;
            case 1:
                RefreshDateBean refreshDateBean = (RefreshDateBean) object;
                ViewGroup.LayoutParams layoutParams = layout_after_refresh.getLayoutParams();
                if (refreshDateBean.isStatus()) {
                    layoutParams.height = height;
                    layout_after_refresh.setLayoutParams(layoutParams);
                    String timeDifference = CalendarBuilder.getTimeDifference(refreshTime, new Date().getTime());
                    after_refresh.setText(new StringBuffer().append(timeDifference).append("前看到这里   点击刷新"));
                } else {
                    layoutParams.height = 0;
                    layout_after_refresh.setLayoutParams(layoutParams);
                }
                layout_after_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemCheckListener != null)
                            onItemCheckListener.onRefreshItemClick();
                    }
                });
                break;
            case 2:
//                BaiduNativeAdPlacement placement = new BaiduNativeAdPlacement();
//                placement.setSessionId(1); // 设置页面id，不同listview不同，从1开始的正整数，可选
//                placement.setPositionId(1); // 设置广告在页面的楼层，从1开始的正整数，可选
//                placement.setApId(AppKeyConfig.ADVERTISING_BAIDU_ADVERTISING_RECYCLER_ID);
//                AdvertisingBuilder.RecycleAdvertising(context, native_outer_view, placement);
                try {
                    native_outer_view.removeAllViews();
                    NativeExpressADView nativeExpressADView = nativeExpressADViewList.get(position);
                    native_outer_view.addView(nativeExpressADView);
                    mAdViewPositionMap.put(nativeExpressADView, position);
                    nativeExpressADView.render();
                } catch (Exception e) {
                    LogUtil.e(TAG, "Exception : " + e.toString());
                }
                break;

        }
    }

    // 移除NativeExpressADView的时候是一条一条移除的
    public void removeADView(NativeExpressADView adView) {
//        int position = mAdViewPositionMap.get(adView);
//        nativeExpressADViewList.remove(position);
//        notifyItemRemoved(position); // position为adView在当前列表中的位置
//        notifyItemRangeChanged(0, TheArticleList.size() - 1);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return TheArticleList.size() == 0 ? 0 : TheArticleList.size();
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

    //添加数据
    public void addData(int position, Object object) {
        TheArticleList.add(position, object);
        notifyItemInserted(position);
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