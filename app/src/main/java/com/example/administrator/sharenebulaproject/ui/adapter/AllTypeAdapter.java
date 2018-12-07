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

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class AllTypeAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<TitleNewsBean> list;

    public AllTypeAdapter(Context context, List<TitleNewsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_news_type, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        TitleNewsBean titleNewsBean = list.get(position);
        TextView type_new = holder.itemView.findViewById(R.id.type_new);
        type_new.setText(new StringBuffer().append("+ ").append(titleNewsBean.getContent()));

        type_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addCheckClickListener != null)
                    addCheckClickListener.onAddCheckClickListener(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    public interface AddCheckClickListener {
        void onAddCheckClickListener(int position);
    }

    private AddCheckClickListener addCheckClickListener;

    public void setAddCheckClickListener(AddCheckClickListener addCheckClickListener) {
        this.addCheckClickListener = addCheckClickListener;
    }

}
