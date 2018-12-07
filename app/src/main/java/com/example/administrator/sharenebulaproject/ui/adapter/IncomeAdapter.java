package com.example.administrator.sharenebulaproject.ui.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.model.bean.IncomeNetBean;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/8/23.
 */

public class IncomeAdapter extends BaseExpandableListAdapter {

    private SparseArray<ImageView> indicators = new SparseArray<>();
    private Context context;
    private List<IncomeNetBean.ResultBean.MoneyindetailBean> moneyindetail;
    private IncomeNetBean.ResultBean.MoneyindetailBean moneyindetailBean;

    public IncomeAdapter(Context context, List<IncomeNetBean.ResultBean.MoneyindetailBean> moneyindetail) {
        this.context = context;
        this.moneyindetail = moneyindetail;
    }

    @Override
    public int getGroupCount() {
        return moneyindetail.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return moneyindetail.get(i).getDetail().size();
    }

    @Override
    public Object getGroup(int i) {
        return moneyindetail.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return moneyindetail.get(i).getDetail().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        moneyindetailBean = moneyindetail.get(i);
        CommonViewHolder holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_income);
        TextView title_content = holder.getTv(R.id.title_content);
        ImageView btn_indicator = holder.getIv(R.id.btn_indicator);
        new SystemUtil().textMagicTool(context, title_content, moneyindetailBean.getTitle(), new StringBuffer().append("+").append(moneyindetailBean.getOptmoneytotal()).toString(), R.dimen.dp14, R.dimen.dp14, R.color.black_overlay, R.color.fatigue_orange_2, "          ");

        /**把位置和图标添加到Map*/
        indicators.put(i, btn_indicator);

        /**根据分组状态设置Indicator*/
        setIndicatorState(i, b);

        return holder.convertView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        IncomeNetBean.ResultBean.MoneyindetailBean moneyindetailBean = moneyindetail.get(i);
        List<IncomeNetBean.ResultBean.MoneyindetailBean.DetailBean> detail = moneyindetailBean.getDetail();
        IncomeNetBean.ResultBean.MoneyindetailBean.DetailBean detailBean = detail.get(i1);
        CommonViewHolder childHolder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_income_child);
        TextView content_child = childHolder.getTv(R.id.content_child);
        TextView floating_child = childHolder.getTv(R.id.floating_child);
        View line = childHolder.getView(R.id.line);
//        if (detail.size() - 1 == i1){
//            line.setVisibility(View.GONE);
//        }else {
//            line.setVisibility(View.VISIBLE);
//        }

        new SystemUtil().textMagicTool(context, content_child, detailBean.getRemark(), detailBean.getCreatedate(), R.dimen.dp13, R.dimen.dp12, R.color.black_overlay, R.color.gray_light_text, "     ");
        floating_child.setText(new StringBuffer().append("+").append(detailBean.getOptmoney()).toString());

        return childHolder.convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


    /**
     * 自定义按钮
     */
    public void setIndicatorState(int groupPosition, boolean isExpanded) {
        try {
            if (isExpanded) {
                indicators.get(groupPosition).setImageResource(R.drawable.btn_up);
            } else {
                indicators.get(groupPosition).setImageResource(R.drawable.btn_down);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
