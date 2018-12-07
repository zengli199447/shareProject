package com.example.administrator.sharenebulaproject.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.model.bean.QuarterTopBean;
import com.example.administrator.sharenebulaproject.widget.CommonViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/8/14.
 */

public class QuarterTopAdapter extends BaseAdapter {

    private Context context;
    private List<QuarterTopBean> quarterTopBeanArrayList;
    private CommonViewHolder holder;

    public QuarterTopAdapter(Context context, List<QuarterTopBean> quarterTopBeanArrayList) {
        this.context = context;
        this.quarterTopBeanArrayList = quarterTopBeanArrayList;
    }

    @Override
    public int getCount() {
        return quarterTopBeanArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return quarterTopBeanArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        QuarterTopBean quarterTopBean = quarterTopBeanArrayList.get(i);
        holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_quarter_top);
        Button button_index = (Button) holder.getView(R.id.button_index);
        TextView user = holder.getTv(R.id.user);
        TextView harvest = holder.getTv(R.id.harvest);
        int index = quarterTopBean.getIndex();
        if (index == 0) {
            button_index.setBackground(context.getResources().getDrawable(R.drawable.top_bg_1));
            button_index.setText("");
        } else if (index == 1) {
            button_index.setBackground(context.getResources().getDrawable(R.drawable.top_bg_2));
            button_index.setText("");
        } else if (index == 2) {
            button_index.setBackground(context.getResources().getDrawable(R.drawable.top_bg_3));
            button_index.setText("");
        } else {
            button_index.setBackground(context.getResources().getDrawable(R.drawable.transparent));
            button_index.setText(String.valueOf(i + 1));
        }

        user.setText(quarterTopBean.getUser());
        harvest.setText(quarterTopBean.getHarvest());

        return holder.convertView;
    }
}
