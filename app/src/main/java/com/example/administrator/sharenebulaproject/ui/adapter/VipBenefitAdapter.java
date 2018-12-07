package com.example.administrator.sharenebulaproject.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.UpgradeNetBean;
import com.example.administrator.sharenebulaproject.model.bean.VipBenefitNetBean;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonViewHolder;

import java.util.List;


/**
 * Created by Administrator on 2018/8/17.
 */

public class VipBenefitAdapter extends BaseAdapter {

    private Context context;
    private List<VipBenefitNetBean.ResultBean.LevelremarkBean> levelremark;
    private CommonViewHolder holder;

    public VipBenefitAdapter(Context context, List<VipBenefitNetBean.ResultBean.LevelremarkBean> levelremark) {
        this.context = context;
        this.levelremark = levelremark;
    }

    @Override
    public int getCount() {
        return levelremark.size();
    }

    @Override
    public Object getItem(int i) {
        return levelremark.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VipBenefitNetBean.ResultBean.LevelremarkBean levelremarkBean = levelremark.get(i);
        holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_vip_benefit);
        TextView vip_benefit_text = holder.getTv(R.id.vip_benefit_text);
        TextView vip_benefit_text_content = holder.getTv(R.id.vip_benefit_text_content);
        vip_benefit_text.setText(levelremarkBean.getLevelname());
        vip_benefit_text_content.setText(levelremarkBean.getRemark());
        return holder.convertView;
    }


}
