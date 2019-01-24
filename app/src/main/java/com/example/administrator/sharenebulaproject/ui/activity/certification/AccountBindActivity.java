package com.example.administrator.sharenebulaproject.ui.activity.certification;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.BusObject;
import com.example.administrator.sharenebulaproject.model.bean.HotAllDataBean;
import com.example.administrator.sharenebulaproject.model.bean.LoginInfoBean;
import com.example.administrator.sharenebulaproject.model.bean.SettlementAccountNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UserInfoBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.ui.view.CustomPayPopupWindow;
import com.example.administrator.sharenebulaproject.utils.AESCryptUtil;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.EventObject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/16.
 * 账户绑定
 */

public class AccountBindActivity extends BaseActivity implements View.OnClickListener, CustomPayPopupWindow.OnItemClickListener, CustomPayPopupWindow.OnInputPassWordListener {

    private CustomPayPopupWindow customPayPopupWindow;

    @BindView(R.id.layout_bg_first)
    RelativeLayout layout_bg_first;
    @BindView(R.id.zhifubao_pay_content)
    TextView zhifubao_pay_content;
    @BindView(R.id.add_zhifubao_pay)
    View add_zhifubao_pay;
    @BindView(R.id.bottom_text_first)
    TextView bottom_text_first;

    @BindView(R.id.layout_bg_center)
    RelativeLayout layout_bg_center;
    @BindView(R.id.bank_pay_icon)
    View bank_pay_icon;
    @BindView(R.id.bank_type)
    TextView bank_type;
    @BindView(R.id.bank_pay_content)
    TextView bank_pay_content;
    @BindView(R.id.add_bank_pay)
    View add_bank_pay;
    @BindView(R.id.bottom_text_center)
    TextView bottom_text_center;

    @BindView(R.id.layout_bg_last)
    RelativeLayout layout_bg_last;
    @BindView(R.id.wechat_content)
    TextView wechat_pay_content;
    @BindView(R.id.add_wechat_pay)
    View add_wechat_pay;
    @BindView(R.id.bottom_text_last)
    TextView bottom_text_last;

    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.title_name)
    TextView title_name;

    private boolean unBindStatus;
    private List<SettlementAccountNetBean.ResultBean.MoneyaccountBean> moneyaccount;
    private String wchatMoneyaccountid = "";
    private String zhifubaoMoneyaccountid = "";
    private String bankMoneyaccountid = "";
    private int selectIndex = 0;
    private ProgressDialog progressDialog;
    private int flags;
    private String moneyaccountId = "";

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_account_bind;
    }

    @Override
    protected void initClass() {
        customPayPopupWindow = new CustomPayPopupWindow(this);
        progressDialog = ShowDialog.getInstance().showProgressStatus(this, getString(R.string.progress_commit));

    }

    @Override
    protected void initData() {
        progressDialog.show();
//        initNetDataWork();
        flags = getIntent().getFlags();
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.bind_account));
    }

    @Override
    protected void initListener() {
        add_zhifubao_pay.setOnClickListener(this);
        add_bank_pay.setOnClickListener(this);
        add_wechat_pay.setOnClickListener(this);
        customPayPopupWindow.setOnItemClickListener(this);
        customPayPopupWindow.setOnInputPassWordListener(this);
        layout_bg_first.setOnClickListener(this);
        layout_bg_center.setOnClickListener(this);
        layout_bg_last.setOnClickListener(this);
        img_btn_black.setOnClickListener(this);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_zhifubao_pay:
                Intent paymentZhiFuBaoAboutIntent = new Intent(this, GeneralActivity.class);
                paymentZhiFuBaoAboutIntent.setFlags(EventCode.ZHIFUBAO_PAY);
                startActivity(paymentZhiFuBaoAboutIntent);
                break;
            case R.id.add_bank_pay:
                Intent paymentBankAboutIntent = new Intent(this, GeneralActivity.class);
                paymentBankAboutIntent.setFlags(EventCode.BANK_PAY);
                startActivity(paymentBankAboutIntent);
                break;
            case R.id.add_wechat_pay:
                Intent paymentWechatAboutIntent = new Intent(this, GeneralActivity.class);
                paymentWechatAboutIntent.setFlags(EventCode.WECHAT_PAY);
                startActivity(paymentWechatAboutIntent);
                break;
            case R.id.layout_bg_first:
                popStatus(zhifubaoMoneyaccountid.isEmpty(), 0, zhifubaoMoneyaccountid);
                break;
            case R.id.layout_bg_center:
                popStatus(bankMoneyaccountid.isEmpty(), 1, bankMoneyaccountid);
                break;
            case R.id.layout_bg_last:
                popStatus(wchatMoneyaccountid.isEmpty(), 2, wchatMoneyaccountid);
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        initNetDataWork();
        super.onResume();
    }

    private void popStatus(boolean status, int type, String id) {
        if (!status) {
            if (flags == EventCode.SELECT_TYPE) {
                RxBus.getDefault().post(new CommonEvent(EventCode.SELECT_TYPE, new BusObject(type, id)));
                finish();
            } else if (flags == EventCode.ACCOUNT_BIND) {
                selectIndex = type;
                customPayPopupWindow.setUnBindStatus(true);
                customPayPopupWindow.showAtLocation(findViewById(R.id.layout_bg_last), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        }
    }

    @Override
    public void setOnItemClick(View v) {
        switch (v.getId()) {
            case R.id.pop_pay_title_text:
//                toastUtil.showToast("解除绑定");
              /*  if (UserInfoBean.traderspwd.isEmpty()) {
                    toastUtil.showToast(getString(R.string.passwor_empty_exception));
                } else {
                    customPayPopupWindow.dismiss();
                    customPayPopupWindow.setUnBindStatus(false);
                    customPayPopupWindow.showAtLocation(findViewById(R.id.layout_bg_last), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
                }*/
                progressDialog.show();
                initUnBindNetDataWork(moneyaccountId, "");
                break;
            case R.id.forget_password:
                Intent intent = new Intent(this, TradePasswordActivity.class);
                intent.setFlags(EventCode.FORGETPASSWORD);
                startActivity(intent);
                customPayPopupWindow.dismiss();
                break;
        }
    }

    @Override
    public void setInputPassWord(String payPassWord) {
        if (UserInfoBean.traderspwd.equals(SystemUtil.string2MD5(payPassWord))) {
            switch (selectIndex) {
                case 0:
                    moneyaccountId = zhifubaoMoneyaccountid;
                    break;
                case 1:
                    moneyaccountId = bankMoneyaccountid;
                    break;
                case 2:
                    moneyaccountId = wchatMoneyaccountid;
                    break;
            }
            progressDialog.show();
            initUnBindNetDataWork(moneyaccountId, payPassWord);
        } else {
            toastUtil.showToast(getString(R.string.trade_password_exception));
        }
    }

    //解析账户绑定数据
    private void DataParsing() {
        for (SettlementAccountNetBean.ResultBean.MoneyaccountBean m : moneyaccount) {
            initPayContentView(m);
        }
    }

    //绑定状态
    private void initPayContentView(SettlementAccountNetBean.ResultBean.MoneyaccountBean moneyaccountBean) {
        if (moneyaccountBean.getAccount_type().equals("银行卡")) {
            bankMoneyaccountid = moneyaccountBean.getMoneyaccountid();
            new SystemUtil().textMagicTool(this, bank_type, moneyaccountBean.getAccount_type()
                    , moneyaccountBean.getAccount_bank(), R.dimen.dp16, R.dimen.dp12, R.color.gray_light, R.color.gray_light);
            bank_pay_content.setText(new StringBuffer().append(moneyaccountBean.getAccount_code()).append("        ").append("(").append(moneyaccountBean.getAccount_main()).append(")"));
            layout_bg_center.setBackground(getResources().getDrawable(R.drawable.account_bind_wechat_bank_bg));
            bottom_text_center.setVisibility(View.INVISIBLE);
            add_bank_pay.setVisibility(View.GONE);
        } else if (moneyaccountBean.getAccount_type().equals("支付宝")) {
            zhifubaoMoneyaccountid = moneyaccountBean.getMoneyaccountid();
            zhifubao_pay_content.setText(new StringBuffer().append(moneyaccountBean.getAccount_code()).append("        ").append("(").append(moneyaccountBean.getAccount_main()).append(")"));
            layout_bg_first.setBackground(getResources().getDrawable(R.drawable.account_bind_zhifubao_bg));
            bottom_text_first.setVisibility(View.INVISIBLE);
            add_zhifubao_pay.setVisibility(View.GONE);
        } else if (moneyaccountBean.getAccount_type().equals("微信")) {
            wchatMoneyaccountid = moneyaccountBean.getMoneyaccountid();
            wechat_pay_content.setText(new StringBuffer().append(moneyaccountBean.getAccount_code()).append("        ").append("(").append(moneyaccountBean.getAccount_main()).append(")"));
            layout_bg_last.setBackground(getResources().getDrawable(R.drawable.account_bind_wechat_bank_bg));
            bottom_text_last.setVisibility(View.INVISIBLE);
            add_wechat_pay.setVisibility(View.GONE);
        }
    }

    //获取结算账户列表
    private void initNetDataWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.MONEY_ACCOUNT_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.SettlementAccountNetData(map)
                .compose(RxUtil.<SettlementAccountNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<SettlementAccountNetBean>(toastUtil) {
                    @Override
                    public void onNext(SettlementAccountNetBean settlementAccountNetBean) {
                        Log.e(TAG, "settlementAccountNetBean : " + settlementAccountNetBean.toString());
                        if (settlementAccountNetBean.getStatus() == 1) {
                            moneyaccount = settlementAccountNetBean.getResult().getMoneyaccount();
                            DataParsing();
                        }else {
                            toastUtil.showToast(settlementAccountNetBean.getMessage());
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        progressDialog.dismiss();
                        super.onError(e);
                    }
                }));
    }

    //结算账户 解绑
    private void initUnBindNetDataWork(String moneyaccountid, String traderspwd) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.MONEY_ACCOUNT_DELETE_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("moneyaccountid", zhifubaoMoneyaccountid);
        linkedHashMap.put("traderspwd", traderspwd);
        final  String toJson =  AESCryptUtil.encrypt(new Gson().toJson(linkedHashMap));
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatus(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        Log.e(TAG, "upLoadStatusNetBean : " + upLoadStatusNetBean.toString());
                        if (upLoadStatusNetBean.getStatus() == 1) {
//                            initNetDataWork();
                            progressDialog.dismiss();
                            finish();
                        } else {
                            progressDialog.dismiss();
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                        progressDialog.dismiss();
                    }
                }));
    }

//                http://xfx.027perfect.com/api/api.mingfa.php?version=v1&vars={"action":"money_account_delete_set", "moneyaccountid":"18", "traderspwd":"123456"}


}
