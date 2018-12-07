package com.example.administrator.sharenebulaproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.model.bean.TitleNewsBean;
import com.example.administrator.sharenebulaproject.ui.holder.MyViewHolder;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class TitleNewsTypeAdapter extends RecyclerView.Adapter<MyViewHolder> {

    String TAG = getClass().getSimpleName();

    Context context;
    List<TitleNewsBean> list;

    public TitleNewsTypeAdapter(Context context, List<TitleNewsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news_title, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        TitleNewsBean titleNewsBean = list.get(position);
        RefreshView(holder, titleNewsBean);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newsTitleClickListener != null)
                    newsTitleClickListener.onNewsTitleClickListener(position);
            }
        });
    }

    public void RefreshView(MyViewHolder holder, TitleNewsBean titleNewsBean) {
        TextView text_news = holder.itemView.findViewById(R.id.text_news);
        LogUtil.e(TAG, "titleNewsBean.isStatus() : " + titleNewsBean.isStatus());
        if (titleNewsBean.isStatus()) {
            text_news.setTextColor(context.getResources().getColor(R.color.orange_red));
            text_news.setTextSize(14);
        } else {
            text_news.setTextColor(context.getResources().getColor(R.color.black));
            text_news.setTextSize(13);
        }
        text_news.setText(titleNewsBean.getContent());

    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    public interface NewsTitleClickListener {
        void onNewsTitleClickListener(int position);
    }

    private NewsTitleClickListener newsTitleClickListener;

    public void onNewsTitleClickListener(NewsTitleClickListener newsTitleClickListener) {
        this.newsTitleClickListener = newsTitleClickListener;
    }

}
