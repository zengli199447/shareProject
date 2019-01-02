package com.example.administrator.sharenebulaproject.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.global.MyApplication;
import com.example.administrator.sharenebulaproject.model.bean.LoginInfoBean;
import com.example.administrator.sharenebulaproject.model.bean.MineInfoNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sharenebulaproject.model.bean.ValidationCodeNet;
import com.example.administrator.sharenebulaproject.model.db.entity.LoginUserInfo;
import com.example.administrator.sharenebulaproject.model.db.entity.ThirdPartyLoginStatusInfo;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.activity.about.PublicWebActivity;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
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
 * Created by Administrator on 2018/8/11.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, UmShareListenerBuilder.onCompleteListener, UmShareListenerBuilder.onNotReachListener {

    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.edit_phone_number)
    EditText edit_phone_number;
    @BindView(R.id.edit_validation)
    EditText edit_validation;
    @BindView(R.id.validation)
    TextView validation;
    @BindView(R.id.edit_invitation)
    EditText edit_invitation;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.prompt)
    TextView prompt;
    @BindView(R.id.wechat)
    View wechat;
    @BindView(R.id.qq)
    View qq;

    private int ss;
    private boolean status;
    private ShowDialog showDialog;
    private ProgressDialog progressDialog;
    private UmShareListenerBuilder umShareListenerBuilder;
    private String openid_external;
    private String name_external;
    private String gender_external;
    private String image_url_external;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (validation != null)
                        validation.setText(new StringBuffer().append(ss).append("秒").toString());
                    break;
                case 1:
                    if (validation != null) {
                        validation.setText(getString(R.string.get_validation));
                        validation.setTextColor(getResources().getColor(R.color.black_overlay));
                        ss = getResources().getInteger(R.integer.validation_interval);
                        status = !status;
                    }
                    break;
                case 2:
                    progressDialog.dismiss();
//                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                    finish();
                    break;
            }
        }
    };
    private String validationCode;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.COMMIET_IMG:
                image_url_external = commonevent.getTemp_str();
//                if (queryValidationStatus()) {
//                    showDialog.showVerificationInputDialog(this, EventCode.VERIFICATION_CODE, edit_invitation.getText().toString());
//                } else {
                initNetDataWork(DataClass.LOGINTYPE);
//                }
                break;
            case EventCode.VERIFICATION_CODE:
//                edit_invitation.setText(commonevent.getTemp_str());
//                initNetDataWork(DataClass.LOGINTYPE);
//                insertValidationStatus(false);
                initInvitationCodeNetDataWork(commonevent.getTemp_str());
                break;
            case EventCode.DISMISS:
//                initNetDataWork(DataClass.LOGINTYPE);
//                insertValidationStatus(true);
                finish();
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initClass() {
        showDialog = ShowDialog.getInstance();
        progressDialog = showDialog.showProgressStatus(this, getString(R.string.progress));
        umShareListenerBuilder = new UmShareListenerBuilder(this, toastUtil);
    }

    @Override
    protected void initData() {
        ss = getResources().getInteger(R.integer.validation_interval);
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.login));
        SystemUtil.textMagicTool(this, prompt, getString(R.string.prompt_first), getString(R.string.prompt_last), R.dimen.dp12, R.dimen.dp12, R.color.gray, R.color.blue);
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        validation.setOnClickListener(this);
        login.setOnClickListener(this);
        prompt.setOnClickListener(this);
        wechat.setOnClickListener(this);
        qq.setOnClickListener(this);
        umShareListenerBuilder.setOnCompleteListener(this);
        umShareListenerBuilder.setOnNotReachListener(this);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.validation:
                if (SystemUtil.isPhotoNumberLegal(edit_phone_number.getText().toString())) {
                    if (!status) {
                        VerificationCodeCheck(edit_phone_number.getText().toString(), DataClass.LOGIN_);
                        validation.setTextColor(getResources().getColor(R.color.gray_light));
                        status = true;
                        ssOrRun();
                    }
                } else {
                    toastUtil.showToast(getString(R.string.input_exception));
                }
                break;
            case R.id.login:
                if (EditVerdict(edit_phone_number.getText().toString(), edit_validation.getText().toString())) {
                    progressDialog.show();
                    DataClass.LOGINTYPE = 1;
                    initNetDataWork(DataClass.LOGINTYPE);
                }
                break;
            case R.id.prompt:
                Intent intent = new Intent(this, PublicWebActivity.class);
                intent.setFlags(EventCode.TEXT_WEB);
                intent.putExtra("value", String.valueOf(DataClass.INFOTYPE_USE_AGREEMENT));
                startActivity(intent);
//                showDialog.showPromptDialog(this);
                break;
            case R.id.wechat:
                progressDialog.show();
                DataClass.LOGINTYPE = 3;
//                umShareListenerBuilder.deleteOauth(1);
                umShareListenerBuilder.initUmLogin(1);
                break;
            case R.id.qq:
                progressDialog.show();
                DataClass.LOGINTYPE = 2;
//                umShareListenerBuilder.deleteOauth(0);
                umShareListenerBuilder.initUmLogin(0);
                break;
        }
    }

    @Override
    public void comlete(Map<String, String> data) {
        if (data != null) {
            switch (DataClass.LOGINTYPE) {
                case 2:
                    openid_external = data.get("openid");
                    name_external = data.get("screen_name");
                    gender_external = data.get("gender");
                    image_url_external = data.get("profile_image_url");
                    break;
                case 3:
                    openid_external = data.get("unionid");
                    name_external = data.get("screen_name");
                    gender_external = data.get("gender");
                    image_url_external = data.get("profile_image_url");
                    break;
            }
            LogUtil.e(TAG, "openid_external :" + openid_external);
            LogUtil.e(TAG, "name_external :" + name_external);
            LogUtil.e(TAG, "gender_external :" + gender_external);
            LogUtil.e(TAG, "image_url_external :" + image_url_external);
//            new MultipartBuilder().downloadImg(this, image_url_external);
            initNetDataWork(DataClass.LOGINTYPE);
        } else {
            LogUtil.e(TAG, "用户信息为空");
            progressDialog.dismiss();
        }
    }

    @Override
    public void notReach() {
        progressDialog.dismiss();
    }

    //过滤
    private boolean EditVerdict(String phoneNumber, String validation) {
        boolean status = true;
        if (phoneNumber.isEmpty() || validation.isEmpty()) {
            status = false;
            toastUtil.showToast(getString(R.string.empty_exception));
        } else if (!SystemUtil.isValidation(validationCode, validation)) {
            status = false;
            toastUtil.showToast(getString(R.string.validation_exception));
        } else if (!SystemUtil.isPhotoNumberLegal(phoneNumber)) {
            status = false;
            toastUtil.showToast(getString(R.string.phone_number_exception));
        }
//        else if (!SystemUtil.isValidation(invitation)) {
//            status = false;
//            toastUtil.showToast(getString(R.string.invitation_exception));
//        }
        return status;
    }

    //验证间隔读秒
    private void ssOrRun() {
        MyApplication.executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(999);
                    handler.sendEmptyMessage(0);
                    ss--;
                    if (ss > 0) {
                        ssOrRun();
                    } else {
                        if (status) {
                            handler.sendEmptyMessage(1);
                        }
                    }
                } catch (Exception e) {
                    LogUtil.e(TAG, "Exception : " + e.toString());
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean queryValidationStatus() {
        ThirdPartyLoginStatusInfo thirdPartyLoginStatusInfo = dataManager.queryThirdPartyLoginStatusInfo(openid_external);
        if (thirdPartyLoginStatusInfo == null) {
            dataManager.insertThirdPartyLoginStatusInfo(new ThirdPartyLoginStatusInfo(openid_external, status));
            return true;
        } else {
            boolean validationStatus = thirdPartyLoginStatusInfo.getValidationStatus();
            return validationStatus;
        }
    }

    private void insertValidationStatus(boolean status) {
        dataManager.insertThirdPartyLoginStatusInfo(new ThirdPartyLoginStatusInfo(openid_external, status));
    }

    //验证码校验
    private void VerificationCodeCheck(String phoneNumber, int type) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.GET_CODE);
        linkedHashMap.put("phone", phoneNumber);
        linkedHashMap.put("type", type);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.ValidationCodeNetData(map)
                .compose(RxUtil.<ValidationCodeNet>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<ValidationCodeNet>(toastUtil) {
                    @Override
                    public void onNext(ValidationCodeNet validationCodeNet) {
                        if (validationCodeNet.getStatus() == 1) {
                            validationCode = validationCodeNet.getResult().getSmscode();
                            edit_validation.setText("");
                        } else {
                            toastUtil.showToast(validationCodeNet.getMessage());
                        }
                    }
                }));
    }

    //登陆
    private void initNetDataWork(int Type) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.LOGIN);
        linkedHashMap.put("logintype", DataClass.LOGINTYPE);
        switch (Type) {
            case 1:
                linkedHashMap.put("phone", edit_phone_number.getText().toString());
                break;
            case 2:
                linkedHashMap.put("qq", openid_external);
                linkedHashMap.put("qqname", name_external);
                linkedHashMap.put("photo", image_url_external);
                break;
            case 3:
                linkedHashMap.put("weixin", openid_external);
                linkedHashMap.put("weixinname", name_external);
                linkedHashMap.put("photo", image_url_external);
                break;
        }
        linkedHashMap.put("invitationcode", edit_invitation.getText().toString());
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.fetchLogin(map)
                .compose(RxUtil.<LoginInfoBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<LoginInfoBean>(toastUtil) {
                    @Override
                    public void onNext(LoginInfoBean loginInfoBean) {
                        Log.e(TAG, "loginInfoBean : " + loginInfoBean.toString());
                        Log.e(TAG, "loginInfoBean.getResult() : " + loginInfoBean.getResult());
                        Log.e(TAG, "loginInfoBean.getStatus() : " + loginInfoBean.getStatus());
                        if (loginInfoBean.getStatus() == 1) {
                            LoginInfoBean.ResultBean result = loginInfoBean.getResult();
                            dataManager.insertLoginUserInfo(new LoginUserInfo("admin", result.getUserid(),result.getToken()));
                            DataClass.initUserInfo(result);
                            RxBus.getDefault().post(new CommonEvent(EventCode.REFRESH_BRANCH));
                            RxBus.getDefault().post(new CommonEvent(EventCode.USERID_REFRESH, loginInfoBean.getResult().getUserid()));
                            LogUtil.e(TAG, "result.getIsinvitationcode() : " + result.getIsinvitationcode());
                            if (DataClass.LOGINTYPE != 1 && "1".equals(result.getIsinvitationcode())) {
                                showDialog.showVerificationInputDialog(LoginActivity.this, EventCode.VERIFICATION_CODE, edit_invitation.getText().toString());
                            } else {
                                finish();
                            }
                        } else {
                            toastUtil.showToast(loginInfoBean.getMessage());
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                    }
                }));
    }

    //提交邀请码
    private void initInvitationCodeNetDataWork(String invitation) {
        progressDialog.show();
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.BIND_INVITATIONCODE);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("invitationcode", invitation);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatus(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            toastUtil.showToast("邀请码提交成功");
                        } else {
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                        progressDialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                        progressDialog.dismiss();
                        finish();
                    }
                }));
    }

//    http://xfx.027perfect.com/api/api.mingfa.php?version=v1&vars={"action":"my_center_get","userid":"1"}


}
