package com.example.administrator.sharenebulaproject.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.model.bean.SettlementAccountNetBean;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonViewHolder;

import java.util.List;


/**
 * Created by Administrator on 2018/8/17.
 */

public class AccountBindAdapter extends BaseAdapter {

    private Context context;
    private List<SettlementAccountNetBean.ResultBean.MoneyaccountBean> settlementAccountNetList;
    private CommonViewHolder holder;

    public AccountBindAdapter(Context context, List<SettlementAccountNetBean.ResultBean.MoneyaccountBean> settlementAccountNetList) {
        this.context = context;
        this.settlementAccountNetList = settlementAccountNetList;
    }

    @Override
    public int getCount() {
        return settlementAccountNetList.size();
    }

    @Override
    public Object getItem(int i) {
        return settlementAccountNetList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SettlementAccountNetBean.ResultBean.MoneyaccountBean moneyaccountBean = settlementAccountNetList.get(i);
        holder = CommonViewHolder.createCVH(view, viewGroup, R.layout.item_account_bind);
        RelativeLayout layout_bg = (RelativeLayout) holder.getView(R.id.layout_bg);
        View pay_icon = holder.getView(R.id.pay_icon);
        TextView bank_type = holder.getTv(R.id.bank_type);
        TextView pay_content = holder.getTv(R.id.pay_content);
        View add_pay = holder.getView(R.id.add_pay);
        TextView bottom_text = holder.getTv(R.id.bottom_text);

        if (moneyaccountBean.getAccount_type().equals("储蓄卡")) {
            pay_icon.setBackground(context.getResources().getDrawable(R.drawable.bank_pay));
            new SystemUtil().textMagicTool(context,bank_type,moneyaccountBean.getAccount_type()
                    ,moneyaccountBean.getAccount_bank(),R.dimen.dp16, R.dimen.dp12,R.color.gray_light_text,R.color.gray_light_text);
        } else if (moneyaccountBean.getAccount_type().equals("支付宝")) {
            pay_icon.setBackground(context.getResources().getDrawable(R.drawable.zhifubao_pay));
            bank_type.setText(moneyaccountBean.getAccount_type());
        } else if (moneyaccountBean.getAccount_type().equals("微信")) {
            pay_icon.setBackground(context.getResources().getDrawable(R.drawable.wechat_pay));
            bank_type.setText(moneyaccountBean.getAccount_type());
        }
        return holder.convertView;
    }



}
