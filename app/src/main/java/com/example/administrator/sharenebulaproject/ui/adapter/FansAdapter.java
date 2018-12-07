package com.example.administrator.sharenebulaproject.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.model.bean.FansNetBean;
import com.example.administrator.sharenebulaproject.model.bean.VipBenefitNetBean;
import com.example.administrator.sharenebulaproject.widget.CommonViewHolder;

import java.util.List;


/**
 * Created by Administrator on 2018/8/17.
 */

public class FansAdapter extends BaseAdapter {

    private Context context;
    private List<FansNetBean.ResultBean.DetaillistBean> detaillist;
    private CommonViewHolder holder;

    public FansAdapter(Context context, List<FansNetBean.ResultBean.DetaillistBean> detaillist) {
        this.context = context;
        this.detaillist = detaillist;
    }

    @Override
    public int getCount() {
        return detaillist.size();
    }

    @Override
    public Object getItem(int i) {
        return detaillist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FansNetBean.ResultBean.DetaillistBean detaillistBean = detaillist.get(i);
        holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.layout_item_select_type);
        TextView content = holder.getTv(R.id.content);
        TextView content_more = holder.getTv(R.id.content_more);
        content.setText(detaillistBean.getLevelname());
        content_more.setText(new StringBuffer().append(detaillistBean.getFansamount()).append(context.getResources().getString(R.string.people)).toString());
        return holder.convertView;
    }

}
