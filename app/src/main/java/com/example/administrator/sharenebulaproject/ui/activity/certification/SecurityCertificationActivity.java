package com.example.administrator.sharenebulaproject.ui.activity.certification;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.LoginInfoBean;
import com.example.administrator.sharenebulaproject.model.bean.MineInfoNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UserInfoBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.utils.AESCryptUtil;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.example.administrator.sharenebulaproject.widget.MultipartBuilder;
import com.example.administrator.sharenebulaproject.widget.UmShareListenerBuilder;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/16.
 * 安全认证中心
 */

public class SecurityCertificationActivity extends BaseActivity implements View.OnClickListener, UmShareListenerBuilder.onCompleteListener {

    @BindView(R.id.certification)
    TextView certification;
    @BindView(R.id.trade_password)
    TextView trade_password;
    @BindView(R.id.settlement_account_binding)
    TextView settlement_account_binding;
    @BindView(R.id.phone_number_binding)
    TextView phone_number_binding;
    @BindView(R.id.wechat_binding)
    TextView wechat_binding;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.title_name)
    TextView title_name;
    private UmShareListenerBuilder umShareListenerBuilder;


    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.REFRESH_BRANCH:
                initViewStatus();
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_security_certification;
    }

    @Override
    protected void initClass() {
        umShareListenerBuilder = new UmShareListenerBuilder(this, toastUtil);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.safety_certification));
        initViewStatus();
    }

    @Override
    protected void initListener() {
        certification.setOnClickListener(this);
        trade_password.setOnClickListener(this);
        settlement_account_binding.setOnClickListener(this);
        phone_number_binding.setOnClickListener(this);
        wechat_binding.setOnClickListener(this);
        img_btn_black.setOnClickListener(this);
        umShareListenerBuilder.setOnCompleteListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViewStatus();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.certification:
                startActivity(new Intent(this, RealNameSystemActivity.class));
                break;
            case R.id.trade_password:
                Intent intent = new Intent(this, TradePasswordActivity.class);
                if ("0".equals(UserInfoBean.ifpwdcheck)) {
                    intent.setFlags(EventCode.TRADEPASSWORD);
                } else {
                    intent.setFlags(EventCode.FORGETPASSWORD);
                }
                startActivity(intent);
                break;
            case R.id.settlement_account_binding:
                Intent accountBindIntent = new Intent(this, AccountBindActivity.class);
                accountBindIntent.setFlags(EventCode.ACCOUNT_BIND);
                startActivity(accountBindIntent);
                break;
            case R.id.phone_number_binding:
                Intent phoneAboutIntent = new Intent(this, PhoneNumberBindActivity.class);
                LogUtil.e(TAG, "LoginInfoBean.Result.phone.isEmpty() : " + UserInfoBean.phone);
                LogUtil.e(TAG, "UserInfoBean.ifphonecheck : " + UserInfoBean.ifphonecheck);
                if ("1".equals(UserInfoBean.ifphonecheck)) {  // 以及用户资料绑定是否为空  这里未判断
                    phoneAboutIntent.setFlags(EventCode.UPPHONENUMBER);
                } else {
                    phoneAboutIntent.setFlags(EventCode.PHONEBING);
                }
                startActivity(phoneAboutIntent);
                break;
            case R.id.wechat_binding:
                umShareListenerBuilder.initUmLogin(1);
                break;
            case R.id.img_btn_black:
                finish();
                break;

        }

    }

    private void initViewStatus() {
        try {
            UpdataViewStatus(Integer.valueOf(UserInfoBean.ifrealnamecheck), certification, true);
            UpdataViewStatus(Integer.valueOf(UserInfoBean.ifpwdcheck), trade_password, false);
            UpdataViewStatus(Integer.valueOf(UserInfoBean.ifaccountcheck), settlement_account_binding, false);
            UpdataViewStatus(Integer.valueOf(UserInfoBean.ifphonecheck), phone_number_binding, false);
            UpdataViewStatus(Integer.valueOf(UserInfoBean.ifwxcheck), wechat_binding, false);
        } catch (Exception e) {
            toastUtil.showToast(getString(R.string.exception));
        }
    }

    private void UpdataViewStatus(int status, TextView view, boolean type) {
        if (status == 0) {
            view.setTextColor(getResources().getColor(R.color.blue_min));
            if (type) {
                view.setText(R.string.certification_off);
            } else {
                view.setText(R.string.complete_off);
            }
        } else {
            view.setTextColor(getResources().getColor(R.color.black_overlay));
            if (type) {
                view.setText(R.string.certification_no);
            } else {
                view.setText(R.string.complete);
            }
        }
    }

    @Override
    public void comlete(Map<String, String> data) {
        if (data != null) {
            String openid_external = data.get("unionid");
            initNetDataWork(openid_external);
        } else {
            LogUtil.e(TAG, "用户信息为空");
        }
    }

    //获取个人信息
    private void initNetDataWork(String unionid) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.WEIXIN_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("weixin", unionid);
        String toJson =  AESCryptUtil.encrypt(new Gson().toJson(linkedHashMap));
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatus(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        Log.e(TAG, "UpLoadStatusNetBean : " + upLoadStatusNetBean.toString());
                        if (upLoadStatusNetBean.getStatus() ==1 ){
                            toastUtil.showToast("绑定成功");
                            RxBus.getDefault().post(new CommonEvent(EventCode.REFRESH_USERINFO));
                        }else {
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }


}
