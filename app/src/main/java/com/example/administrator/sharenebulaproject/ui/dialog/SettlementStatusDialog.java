package com.example.administrator.sharenebulaproject.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseDialog;
import com.example.administrator.sharenebulaproject.ui.activity.about.SettlementLogActivity;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/11.
 * 结算状态
 */

public class SettlementStatusDialog extends BaseDialog implements View.OnClickListener{

    private Context context;
    private int status;
    @BindView(R.id.settlement_img)
    ImageView settlement_img;
    @BindView(R.id.query_log)
    TextView query_log;
    @BindView(R.id.commit)
    TextView commit;

    protected SettlementStatusDialog(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_settlement;
    }

    @Override
    protected void initClass() {
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        query_log.setOnClickListener(this);
        commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.query_log:
                Intent settlementLogIntent = new Intent(context, SettlementLogActivity.class);
                settlementLogIntent.setFlags(0);
                context.startActivity(settlementLogIntent);
                dismiss();
                break;
            case R.id.commit:
                dismiss();
                break;
        }
    }

    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        SystemUtil.windowToLight(context);
    }
}
