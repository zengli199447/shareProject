package com.example.administrator.sharenebulaproject.ui.activity.about;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.BusObject;
import com.example.administrator.sharenebulaproject.model.bean.LoginInfoBean;
import com.example.administrator.sharenebulaproject.model.bean.SettlementNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UserInfoBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.activity.certification.AccountBindActivity;
import com.example.administrator.sharenebulaproject.ui.activity.certification.RealNameSystemActivity;
import com.example.administrator.sharenebulaproject.ui.activity.certification.SecurityCertificationActivity;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.utils.AESCryptUtil;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/21.
 * 结算
 */

public class SettlementActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.pay_type)
    TextView pay_type;
    @BindView(R.id.select_pay_type)
    ImageView select_pay_type;
    @BindView(R.id.edit_settlement_number)
    EditText edit_settlement_number;
    @BindView(R.id.balance_and_value)
    TextView balance_and_value;
    @BindView(R.id.settlement)
    TextView settlement;
    @BindView(R.id.prompt)
    TextView prompt;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;

    private String payId = "";
    private List<SettlementNetBean.ResultBean.MoneyaccountBean> moneyaccount;
    private SettlementNetBean.ResultBean result;
    private ProgressDialog progressDialog;
    private ShowDialog instance;


    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) { //更新 payId
            case EventCode.SELECT_TYPE:
                BusObject busObject = commonevent.getBusObject();
                payId = busObject.getStringValue();
                setPayType(busObject.getIntValue());
                break;
            case EventCode.REALNAME_CHECK:
                startActivity(new Intent(this, SecurityCertificationActivity.class));
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_settlement;
    }

    @Override
    protected void initClass() {
        instance = ShowDialog.getInstance();
        progressDialog = instance.showProgressStatus(this, getString(R.string.progress));
    }

    @Override
    protected void initData() {
        progressDialog.show();
        initNetDataWork();
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.settlement));
        if ("0".equals(UserInfoBean.ifrealnamecheck) || "0".equals(UserInfoBean.ifphonecheck)) {
            instance.showGeneralConfirmDialog(this,EventCode.REALNAME_CHECK,getString(R.string.warm_prompt_if_realname_check_));
        }
    }

    @Override
    protected void initListener() {
        select_pay_type.setOnClickListener(this);
        settlement.setOnClickListener(this);
        img_btn_black.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_pay_type:
                Intent accountBindIntent = new Intent(this, AccountBindActivity.class);
                accountBindIntent.setFlags(EventCode.SELECT_TYPE);
                startActivity(accountBindIntent);
                break;
            case R.id.settlement:
                if (edit_settlement_number.getText().toString().isEmpty() || Integer.valueOf(edit_settlement_number.getText().toString()) < Integer.valueOf(result.getMinbean()) || payId.isEmpty()) {
                    toastUtil.showToast(getString(R.string.settlement_exception));
                } else {
                    if (Integer.valueOf(edit_settlement_number.getText().toString()) > Integer.valueOf(result.getStarbeantotal())) {
                        toastUtil.showToast(getString(R.string.not_enough_exception));
                    } else {
                        if (SystemUtil.WhetherTheDivisible(toastUtil, Integer.valueOf(edit_settlement_number.getText().toString()), Integer.valueOf(result.getMoney2bean()))) {
                            progressDialog.ShowDiaLog(getString(R.string.progress_commit));
                            initNetCommite(edit_settlement_number.getText().toString());
                        }
                    }
                }
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    //View更新
    private void initLayoutView() {
        moneyaccount = result.getMoneyaccount();
        if (moneyaccount.size() > 0) {
            payId = moneyaccount.get(0).getMoneyaccountid();
            setPayType(0);
        }
        new SystemUtil().textMagicTool(this, balance_and_value,
                new StringBuffer().append("账户余额 : ").append(result.getStarbeantotal()).append("星豆").toString(),
                new StringBuffer().append("当前结算价值 : ").append("1元/").append(result.getMoney2bean()).append("星豆").toString(),
                R.dimen.dp13, R.dimen.dp13, R.color.black_overlay, R.color.fatigue_red);
        edit_settlement_number.setHint(new StringBuffer().append("数量不低于").append(result.getMinbean()).append("星豆"));
        new SystemUtil().textMagicTool(this, prompt, "提示 : \n", new StringBuffer().append("1.账户星豆超过").append(result.getMinbean()).append("星豆才能进行结算\n").append("2.结算价值会根据市场而变动\n").append("3.结算资金会在3个工作日之内到达结算账户").toString(), R.dimen.dp16, R.dimen.dp13, R.color.black, R.color.black_overlay);
    }

    //选择账户类型
    private void setPayType(int type) {
        Drawable drawableLeft = null;
        LogUtil.e(TAG, "payId : " + payId);
        switch (type) {
            case 0:
                drawableLeft = getResources().getDrawable(R.drawable.zhifubao_pay);
                pay_type.setText(getString(R.string.zhifubao_pay));
                break;
            case 1:
                drawableLeft = getResources().getDrawable(R.drawable.bank_pay);
                pay_type.setText(getString(R.string.bank_pay));
                break;
            case 2:
                drawableLeft = getResources().getDrawable(R.drawable.wechat_pay);
                pay_type.setText(getString(R.string.wechat_pay));
                break;
        }
        pay_type.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
        pay_type.setCompoundDrawablePadding(SystemUtil.dp2px(this, 5));
    }

    //获取结算信息
    private void initNetDataWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SETTLEACCOUNTS_INIT_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getSettlementNetBean(map)
                .compose(RxUtil.<SettlementNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<SettlementNetBean>(toastUtil) {
                    @Override
                    public void onNext(SettlementNetBean settlementNetBean) {
                        Log.e(TAG, "SettlementNetBean : " + settlementNetBean.toString());
                        result = settlementNetBean.getResult();
                        initLayoutView();
                        progressDialog.dismiss();
                    }
                }));
    }

    //结算提交
    private void initNetCommite(String optmoney) {
        progressDialog.show();
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SETTLEACCOUNTS_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("moneyaccountid", payId);
        linkedHashMap.put("optmoney", optmoney);
        String toJson =  AESCryptUtil.encrypt(new Gson().toJson(linkedHashMap));
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatus(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        Log.e(TAG, "UpLoadStatusNetBean : " + upLoadStatusNetBean.toString());
                        progressDialog.dismiss();
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            RxBus.getDefault().post(new CommonEvent(EventCode.REFRESH_USERINFO));
                            instance.showSettlementStatusDialog(SettlementActivity.this);
                            initNetDataWork();
                        } else {
                            instance.showPayStatusDialog(SettlementActivity.this, 4);
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                    }
                }));
    }


}
