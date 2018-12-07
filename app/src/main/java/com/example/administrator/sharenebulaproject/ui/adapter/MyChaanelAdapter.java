package com.example.administrator.sharenebulaproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.ui.holder.MyViewHolder;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MyChaanelAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Object> list;

    public MyChaanelAdapter(Context context, List<Object> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chaanel_type, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

}
