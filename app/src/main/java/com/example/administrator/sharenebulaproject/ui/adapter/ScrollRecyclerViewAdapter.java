package com.example.administrator.sharenebulaproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.model.bean.TopNetBean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/13.
 */

public class ScrollRecyclerViewAdapter extends RecyclerView.Adapter<ScrollRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<TopNetBean.ResultBean.LogsBean> logsBeen;

    public ScrollRecyclerViewAdapter(Context context, List<TopNetBean.ResultBean.LogsBean> logsBeen) {
        this.context = context;
        this.logsBeen = logsBeen;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TopNetBean.ResultBean.LogsBean logsBean = logsBeen.get(position % logsBeen.size());
        holder.text.setText(new StringBuffer().append(logsBean.getCreatedate()).append("  ").append(logsBean.getRemark()).toString());
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return logsBeen == null ? 0 : Integer.MAX_VALUE;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView text;


        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);


        }
    }


}
