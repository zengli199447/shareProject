package com.example.administrator.sharenebulaproject.ui.activity.certification;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.global.MyApplication;
import com.example.administrator.sharenebulaproject.model.bean.PhoneBindNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sharenebulaproject.model.bean.ValidationCodeNet;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/16.
 * 手机绑定相关
 * 进入该界面前应该先判定登陆状态 ， 第三方登陆未绑定则要进入绑定  ， 否则 进入手机登陆修改绑定的状态
 */

public class PhoneNumberBindActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.edit_phone_text)
    EditText edit_phone_text;
    @BindView(R.id.edit_validation)
    EditText edit_validation;
    @BindView(R.id.validation)
    TextView validation;
    @BindView(R.id.commit)
    TextView commit;
    @BindView(R.id.prompt)
    TextView prompt;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.phone_text)
    TextView phone_text;

    private int flags;

    private int ss;
    private boolean status;
    private boolean bindType;

    private String oldPhoneNumber;
    private String newPhoneNumber;


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
            }
        }
    };
    private ProgressDialog progressDialog;
    private String validationCode;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_phone_number_bind;
    }

    @Override
    protected void initClass() {
        ShowDialog instance = ShowDialog.getInstance();
        progressDialog = instance.showProgressStatus(this, getString(R.string.progress_commit));
    }

    @Override
    protected void initData() {
        flags = getIntent().getFlags();
        ss = getResources().getInteger(R.integer.validation_interval);
    }

    @Override
    protected void initView() {
        switch (flags) {
            case EventCode.PHONEBING:
                title_name.setText(getString(R.string.phone_number_binding));
                break;
            case EventCode.UPPHONENUMBER:
                title_name.setText(getString(R.string.modify_the_binding));
                commit.setText(getString(R.string.next_step));
                phone_text.setText(getString(R.string.old_phone_number));
                break;
        }
        SystemUtil.textMagicTool(this, prompt, getString(R.string.app_name), getString(R.string.prompt_last_about), R.dimen.dp16, R.dimen.dp12, R.color.blue, R.color.gray);
    }

    @Override
    protected void initListener() {
        commit.setOnClickListener(this);
        validation.setOnClickListener(this);
        img_btn_black.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.validation:
                if (!status) {
                    switch (flags) {
                        case EventCode.PHONEBING:
                            if (SystemUtil.isPhotoNumberLegal(edit_phone_text.getText().toString())) {
                                switch (DataClass.LOGINTYPE) {
                                    case 1:
                                        VerificationCodeCheck(edit_phone_text.getText().toString(), DataClass.QQ_LOGIN);
                                        break;
                                    case 2:
                                        VerificationCodeCheck(edit_phone_text.getText().toString(), DataClass.WECHAT_LOGIN);
                                        break;
                                }
                                validation.setTextColor(getResources().getColor(R.color.gray_light));
                                status = true;
                                ssOrRun();
                            } else {
                                toastUtil.showToast(getString(R.string.phone_number_exception));
                            }
                            break;
                        case EventCode.UPPHONENUMBER:
                            if (!bindType) {
                                if (SystemUtil.isPhotoNumberLegal(edit_phone_text.getText().toString())) {
                                    VerificationCodeCheck(edit_phone_text.getText().toString(), DataClass.OLDPHONE);
                                    validation.setTextColor(getResources().getColor(R.color.gray_light));
                                    status = true;
                                    ssOrRun();
                                } else {
                                    toastUtil.showToast(getString(R.string.phone_number_exception));
                                }
                            } else {
                                if (SystemUtil.isPhotoNumberLegal(edit_phone_text.getText().toString())) {
                                    VerificationCodeCheck(edit_phone_text.getText().toString(), DataClass.NEWPHONE);
                                    validation.setTextColor(getResources().getColor(R.color.gray_light));
                                    status = true;
                                    ssOrRun();
                                } else {
                                    toastUtil.showToast(getString(R.string.phone_number_exception));
                                }
                            }
                            break;
                    }
                }
                break;
            case R.id.commit:
                switch (flags) {
                    case EventCode.PHONEBING:
                        if (EditVerdict(edit_phone_text.getText().toString(), edit_validation.getText().toString()))
                            initNetDataWork(DataClass.LOGINTYPE, edit_phone_text.getText().toString());
                        break;
                    case EventCode.UPPHONENUMBER:
                        if (EditVerdict(edit_phone_text.getText().toString(), edit_validation.getText().toString())) {
                            phone_text.setText(getString(R.string.new_phone_number));
                            if (!bindType) {
                                initNetDataWork(0, edit_phone_text.getText().toString());
                            } else {
                                initNetDataWorkMore(0, edit_phone_text.getText().toString());
                            }
                        }
                        break;
                }
                break;
        }
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
                    if (ss > 0 && !bindType) {
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

    //过滤
    private boolean EditVerdict(String phoneNumber, String validation) {
        boolean status = true;
        if (phoneNumber.isEmpty() || validation.isEmpty()) {
            status = false;
            toastUtil.showToast(getString(R.string.empty_exception));
        } else if (!SystemUtil.isValidation(validationCode, validation)) {
            status = false;
            toastUtil.showToast(getString(R.string.validation_exception));
        }
        return status;
    }

    //验证码校验
    private void VerificationCodeCheck(String phoneNumber, int code) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.GET_CODE);
        linkedHashMap.put("phone", phoneNumber);
        linkedHashMap.put("type", code);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.ValidationCodeNetData(map)
                .compose(RxUtil.<ValidationCodeNet>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<ValidationCodeNet>(toastUtil) {
                    @Override
                    public void onNext(ValidationCodeNet validationCodeNet) {
                        if (validationCodeNet.getStatus() ==1){
                            validationCode = validationCodeNet.getResult().getSmscode();
                            edit_validation.setText("");
                        }else {
                            toastUtil.showToast(validationCodeNet.getMessage());
                        }
                    }
                }));
    }

    //提交
    private void initNetDataWork(int status, String phoneNumber) {
        switch (flags) {
            case EventCode.PHONEBING:
                initNetDataWorkMore(status, phoneNumber);
//                toastUtil.showToast("bindPhoneNumber");
                break;
            case EventCode.UPPHONENUMBER:
                edit_phone_text.setText("");
                edit_validation.setText("");
                bindType = true;
                commit.setText(getString(R.string.commit));
                break;
        }
    }

    //更新手机号/绑定手机
    private void initNetDataWorkMore(int status, String phoneNumber) {
//        toastUtil.showToast("upPhoneNumber/bindPhone");
        progressDialog.show();
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        switch (status) {
            case 0:
                linkedHashMap.put("action", DataClass.NEW_PHONE_SET);
                linkedHashMap.put("userid", DataClass.USERID);
                linkedHashMap.put("newphone", phoneNumber);
                break;
            case 1:
                linkedHashMap.put("action", DataClass.PHONE_BIND_SET);
                linkedHashMap.put("userid", DataClass.USERID);
                linkedHashMap.put("phone", phoneNumber);
                linkedHashMap.put("thirdlogintype", 1);
                break;
            case 2:
                linkedHashMap.put("action", DataClass.PHONE_BIND_SET);
                linkedHashMap.put("userid", DataClass.USERID);
                linkedHashMap.put("phone", phoneNumber);
                linkedHashMap.put("thirdlogintype", 2);
                break;
        }
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
                            toastUtil.showToast("提交成功");
                            RxBus.getDefault().post(new CommonEvent(EventCode.REFRESH_USERINFO));
                            finish();
                        } else {
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        toastUtil.showToast("提交失败");
                        progressDialog.dismiss();
                    }
                }));
    }
// http://xfx.027perfect.com/api/api.mingfa.php?version=v1&vars={"action":"new_phone_set", "userid":"1", "newphone":"17762598059"}


}
