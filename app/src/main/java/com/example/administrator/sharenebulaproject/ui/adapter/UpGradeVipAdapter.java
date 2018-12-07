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
import com.example.administrator.sharenebulaproject.model.bean.SettlementAccountNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpgradeNetBean;
import com.example.administrator.sharenebulaproject.widget.CommonViewHolder;

import java.util.List;


/**
 * Created by Administrator on 2018/8/17.
 */

public class UpGradeVipAdapter extends BaseAdapter {

    private Context context;
    private List<UpgradeNetBean.ResultBean.LevelBean> levelBeanList;
    private CommonViewHolder holder;

    public UpGradeVipAdapter(Context context, List<UpgradeNetBean.ResultBean.LevelBean> levelBeanList) {
        this.context = context;
        this.levelBeanList = levelBeanList;
    }

    @Override
    public int getCount() {
        return levelBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return levelBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        UpgradeNetBean.ResultBean.LevelBean levelBean = levelBeanList.get(i);
        holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_up_grade);
        TextView vip_type = holder.getTv(R.id.vip_type);
        TextView up_grade_status = holder.getTv(R.id.up_grade_status);
        TextView up_grade_content = holder.getTv(R.id.up_grade_content);
        ImageView vip_type_img = holder.getIv(R.id.vip_type_img);
        up_grade_status.setText(levelBean.getBtntxt());
        vip_type.setText(levelBean.getLevelname());
        up_grade_content.setText(new StringBuffer().append(levelBean.getLevelprice()).append("/年"));
        Glide.with(context).load(new StringBuffer().append(DataClass.FileUrl).append(levelBean.getIcon()).toString()).centerCrop().error(R.drawable.banner_off).into(vip_type_img);
        if (levelBean.getIfcanclick().equals("1")){
            up_grade_status.setBackground(context.getResources().getDrawable(R.drawable.upgrade_text_bg));
        }else {
            up_grade_status.setBackground(context.getResources().getDrawable(R.drawable.up_grade_text_bg_off));
        }
        return holder.convertView;
    }


}
