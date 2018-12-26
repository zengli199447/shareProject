package com.example.administrator.sharenebulaproject.ui.activity.certification;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.AccountBindNetBean;
import com.example.administrator.sharenebulaproject.model.bean.IncomeBenefitNetBean;
import com.example.administrator.sharenebulaproject.model.bean.SettlementAccountNetBean;
import com.example.administrator.sharenebulaproject.model.bean.TextExplainsNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.activity.LoginActivity;
import com.example.administrator.sharenebulaproject.ui.activity.PersonageActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.UpGradeVipActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.VipBenefitActivity;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.ui.view.BandCardEditText;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.example.administrator.sharenebulaproject.widget.UmShareListenerBuilder;
import com.google.gson.Gson;
import com.tencent.smtt.sdk.WebView;

import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/16.
 * 通用Tools
 */

public class GeneralActivity extends BaseActivity implements View.OnClickListener, BandCardEditText.BankCardListener {

    @BindView(R.id.layout_text_input)
    LinearLayout layout_text_input;
    @BindView(R.id.layout_bank_text_input)
    LinearLayout layout_bank_text_input;

    @BindView(R.id.first_text_hint)
    TextView first_text_hint;
    @BindView(R.id.input_first_text)
    TextView input_first_text;

    @BindView(R.id.last_text_hint)
    TextView last_text_hint;
    @BindView(R.id.input_last_text)
    TextView input_last_text;

    @BindView(R.id.edit_bank_account)
    BandCardEditText edit_bank_account;
    @BindView(R.id.belongs_to_the_bank)
    TextView belongs_to_the_bank;
    @BindView(R.id.input_name_of_cardholder)
    TextView input_name_of_cardholder;

    @BindView(R.id.commit)
    TextView commit;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.title_name)
    TextView title_name;

    @BindView(R.id.web_layout)
    FrameLayout web_layout;
    @BindView(R.id.web_view)
    WebView web_view;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.layout_setting)
    LinearLayout layout_setting;
    @BindView(R.id.layout_personal_data)
    RelativeLayout layout_personal_data;
    @BindView(R.id.layout_change_the_password)
    RelativeLayout layout_change_the_password;
    @BindView(R.id.login_out)
    TextView login_out;

    @BindView(R.id.layout_income_benefit)
    LinearLayout layout_income_benefit;
    @BindView(R.id.reading_share_reward)
    TextView reading_share_reward;
    @BindView(R.id.level_of_increasing_returns)
    TextView level_of_increasing_returns;
    @BindView(R.id.team_management_commission)
    TextView team_management_commission;
    @BindView(R.id.advertising_investment_promotion_award)
    TextView advertising_investment_promotion_award;
    @BindView(R.id.platform_incentive_activities)
    TextView platform_incentive_activities;
    @BindView(R.id.license_performance_income)
    TextView license_performance_income;

    private int flags;
    private ShowDialog instance;
    //    private Integer value;
    private ProgressDialog progressDialog;
    private UmShareListenerBuilder umShareListenerBuilder;
    private int text[] = {R.id.reading_share_reward, R.id.level_of_increasing_returns, R.id.team_management_commission, R.id.advertising_investment_promotion_award, R.id.platform_incentive_activities, R.id.license_performance_income};

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.FIRSH_TEXT_INPUT:
                input_first_text.setText(commonevent.getTemp_str());
                break;
            case EventCode.LAST_TEXT_INPUT:
                input_last_text.setText(commonevent.getTemp_str());
                break;
            case EventCode.INPUT_NAME_OF_CARDHOLDER:
                input_name_of_cardholder.setText(commonevent.getTemp_str());
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_general;
    }

    @Override
    protected void initClass() {
        instance = ShowDialog.getInstance();
        flags = getIntent().getFlags();
        progressDialog = instance.showProgressStatus(this, getString(R.string.progress));
    }

    @Override
    protected void initData() {
        switch (flags) {
            case EventCode.GENERAL_WEB:
//                value = Integer.valueOf(getIntent().getStringExtra("value"));
//                initNetTextDataWork(value);
                break;
        }
    }

    @Override
    protected void initView() {
        switch (flags) {
            case EventCode.WECHAT_PAY:
                layout_text_input.setVisibility(View.VISIBLE);
                first_text_hint.setText(getString(R.string.wechat_account));
                last_text_hint.setText(getString(R.string.the_cardholder));
                title_name.setText(getString(R.string.bind_wechat_account));
                break;
            case EventCode.BANK_PAY:
                layout_bank_text_input.setVisibility(View.VISIBLE);
                title_name.setText(getString(R.string.bind_bank_account));
                break;
            case EventCode.ZHIFUBAO_PAY:
                layout_text_input.setVisibility(View.VISIBLE);
                first_text_hint.setText(getString(R.string.zhifubao_account));
                last_text_hint.setText(getString(R.string.the_cardholder));
                title_name.setText(getString(R.string.bind_zhifubao_account));
                break;
            case EventCode.GENERAL_WEB:

                break;
            case EventCode.SETTING:
                commit.setVisibility(View.GONE);
                title_name.setText(getString(R.string.setting));
                layout_setting.setVisibility(View.VISIBLE);
                umShareListenerBuilder = new UmShareListenerBuilder(this, toastUtil);
                break;
            case EventCode.INCOME_BENEFIT:
                commit.setVisibility(View.GONE);
                title_name.setText(getString(R.string.income_benefit));
                layout_income_benefit.setVisibility(View.VISIBLE);
                initNetIncomeBenefitWork();
                break;
        }
    }

    @Override
    protected void initListener() {
        edit_bank_account.setBankCardListener(this);
        commit.setOnClickListener(this);
        input_first_text.setOnClickListener(this);
        input_last_text.setOnClickListener(this);
        input_name_of_cardholder.setOnClickListener(this);
        img_btn_black.setOnClickListener(this);
        layout_personal_data.setOnClickListener(this);
        layout_change_the_password.setOnClickListener(this);
        login_out.setOnClickListener(this);
        for (int i = 0; i < text.length; i++) {
            findViewById(text[i]).setOnClickListener(this);
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commit:
                EditVerdict();
                break;
            case R.id.input_first_text:
                instance.showGeneralEditInput(this, EventCode.FIRSH_TEXT_INPUT, getString(R.string.input_account), input_first_text.getText().toString());
                break;
            case R.id.input_last_text:
                instance.showGeneralEditInput(this, EventCode.LAST_TEXT_INPUT, getString(R.string.the_cardholder), input_last_text.getText().toString());
                break;
            case R.id.input_name_of_cardholder:
                instance.showGeneralEditInput(this, EventCode.INPUT_NAME_OF_CARDHOLDER, getString(R.string.input_name_of_cardholder), input_name_of_cardholder.getText().toString());
                break;
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.layout_personal_data:
                startActivity(new Intent(this, PersonageActivity.class));
                break;
            case R.id.layout_change_the_password:
                Intent intent = new Intent(this, TradePasswordActivity.class);
                intent.setFlags(EventCode.TRADEPASSWORD);
                startActivity(intent);
                break;
            case R.id.login_out:
                //需要清空本地登陆信息，以游客方式登陆
                DataClass.USERID = "";
                DataClass.SELECT = "http://xfx.027perfect.com/pf_wx.php?act=oneday&do=display&userid_share=";
                if (DataClass.LOGINTYPE != 0)
                    umShareListenerBuilder.deleteOauth(DataClass.LOGINTYPE);
                dataManager.deleteLoginUserInfo("admin");
                RxBus.getDefault().post(new CommonEvent(EventCode.LOGIN_OUT));
                finish();
                break;
            case R.id.reading_share_reward:
                startOpenInterests(reading_share_reward.getText().toString());
                break;
            case R.id.level_of_increasing_returns:
                startOpenInterests(level_of_increasing_returns.getText().toString());
                break;
            case R.id.team_management_commission:
                startOpenInterests(team_management_commission.getText().toString());
                break;
            case R.id.advertising_investment_promotion_award:
                startOpenInterests(advertising_investment_promotion_award.getText().toString());
                break;
            case R.id.platform_incentive_activities:
                startOpenInterests(platform_incentive_activities.getText().toString());
                break;
            case R.id.license_performance_income:
                startOpenInterests(license_performance_income.getText().toString());
                break;
        }
    }

    @Override
    public void success(String name) {
        LogUtil.e(TAG, "name : " + name);
        belongs_to_the_bank.setText(name);
    }

    @Override
    public void failure() {
        belongs_to_the_bank.setText("未查到相关银行");
    }

    private void startOpenInterests(String text) {
        if (!text.equals("待开通")) {
            Intent levelPermissionsIntent = new Intent(this, VipBenefitActivity.class);
            levelPermissionsIntent.setFlags(0);
            startActivity(levelPermissionsIntent);
        } else {
            Intent upGrade_VipIntent = new Intent(this, UpGradeVipActivity.class);
            upGrade_VipIntent.setFlags(0);
            startActivity(upGrade_VipIntent);
        }
    }

    private void EditVerdict() {
        switch (flags) {
            case EventCode.WECHAT_PAY:
                if (!input_first_text.getText().toString().isEmpty() && !input_last_text.getText().toString().isEmpty()) {
                    initNetDataWork(input_last_text.getText().toString(), input_first_text.getText().toString(), "", "微信");
                } else {
                    toastUtil.showToast(getString(R.string.empty_exception));
                }
            case EventCode.ZHIFUBAO_PAY:
                if (!input_first_text.getText().toString().isEmpty() && !input_last_text.getText().toString().isEmpty()) {
                    initNetDataWork(input_last_text.getText().toString(), input_first_text.getText().toString(), "", "支付宝");
                } else {
                    toastUtil.showToast(getString(R.string.empty_exception));
                }
                break;
            case EventCode.BANK_PAY:
                if (!input_name_of_cardholder.getText().toString().isEmpty() && !edit_bank_account.getText().toString().isEmpty() && !belongs_to_the_bank.getText().toString().isEmpty()) {
                    initNetDataWork(input_name_of_cardholder.getText().toString(), edit_bank_account.getText().toString(), belongs_to_the_bank.getText().toString(), "银行卡");
                } else {
                    toastUtil.showToast(getString(R.string.empty_exception));
                }
                break;
        }
    }

    //提交绑定
    private void initNetDataWork(String account_main, String account_code, String account_bank, String account_type) {
        LogUtil.e(TAG, "account_main : " + account_main);
        LogUtil.e(TAG, "account_code : " + account_code);
        LogUtil.e(TAG, "account_bank : " + account_bank);
        LogUtil.e(TAG, "account_type : " + account_type);
        progressDialog.show();
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.MONEY_ACCOUNT_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("account_type", account_type); //"类型"
        linkedHashMap.put("account_main", account_main); //"所属人"
        linkedHashMap.put("account_code", account_code);//"账户"
        linkedHashMap.put("account_bank", account_bank); //"银行卡类型"
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatus(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        LogUtil.e(TAG, "UpLoadStatusNetBean : " + upLoadStatusNetBean.toString());
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            toastUtil.showToast("绑定成功");
                            finish();
                        } else {
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                        progressDialog.dismiss();
                    }
                }));
    }

    //收入权益
    private void initNetIncomeBenefitWork() {
        progressDialog.show();
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.MY_INTERESTS_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getIncomeBenefitNetBean(map)
                .compose(RxUtil.<IncomeBenefitNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<IncomeBenefitNetBean>(toastUtil) {
                    @Override
                    public void onNext(IncomeBenefitNetBean incomeBenefitNetBean) {
                        LogUtil.e(TAG, "IncomeBenefitNetBean : " + incomeBenefitNetBean.toString());
                        if (incomeBenefitNetBean.getStatus() == 1) {
                            IncomeBenefitNetBean.ResultBean result = incomeBenefitNetBean.getResult();
                            OpenStatus(result.getRights1(), reading_share_reward);
                            level_of_increasing_returns.setText(result.getRights2());
                            OpenStatus(result.getRights3(), team_management_commission);
                            OpenStatus(result.getRights4(), advertising_investment_promotion_award);
                            OpenStatus(result.getRights5(), platform_incentive_activities);
                            OpenStatus(result.getRights6(), license_performance_income);
                        }else {
                            toastUtil.showToast(incomeBenefitNetBean.getMessage());
                        }
                        progressDialog.dismiss();
                    }
                }));
    }

    private void OpenStatus(String content, TextView view) {
        if ("0".equals(content)) {
            view.setText("待开通");
            view.setTextColor(getResources().getColor(R.color.blue));
        } else {
            view.setText("已开通");
            view.setTextColor(getResources().getColor(R.color.black_overlay));
        }
    }


}
