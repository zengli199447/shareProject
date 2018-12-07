package com.example.administrator.sharenebulaproject.ui.activity.certification;

import android.content.Intent;
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
import com.example.administrator.sharenebulaproject.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sharenebulaproject.model.bean.ValidationCodeNet;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.activity.HomeActivity;
import com.example.administrator.sharenebulaproject.ui.activity.LoginActivity;
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
 * 交易密码设置
 */

public class TradePasswordActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.edit_account)
    EditText edit_account;
    @BindView(R.id.edit_validation)
    EditText edit_validation;
    @BindView(R.id.validation)
    TextView validation;
    @BindView(R.id.edit_pass_word)
    EditText edit_pass_word;
    @BindView(R.id.edit_confirm_pass_word)
    EditText edit_confirm_pass_word;
    @BindView(R.id.commit)
    TextView commit;
    @BindView(R.id.prompt)
    TextView prompt;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.title_name)
    TextView title_name;

    private int ss;
    private boolean status;

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
    private int flags;
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
        return R.layout.activity_trade_password;
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
        if (flags == EventCode.TRADEPASSWORD) {
            title_name.setText(getString(R.string.trade_password));
        } else if (flags == EventCode.FORGETPASSWORD) {
            title_name.setText(getString(R.string.forget_password));
        }
        SystemUtil.textMagicTool(this, prompt, getString(R.string.app_name), getString(R.string.prompt_last_about), R.dimen.dp16, R.dimen.dp12, R.color.blue, R.color.gray);

    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        validation.setOnClickListener(this);
        commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.validation:
                if (SystemUtil.isPhotoNumberLegal(edit_account.getText().toString())) {
                    if (!status) {
                        VerificationCodeCheck(edit_account.getText().toString(), DataClass.TRADE_PASSWORD);
                        validation.setTextColor(getResources().getColor(R.color.gray_light));
                        status = true;
                        ssOrRun();
                    }
                } else {
                    toastUtil.showToast(getString(R.string.input_exception));
                }
                break;
            case R.id.commit:
                if (EditVerdict(edit_account.getText().toString(), edit_validation.getText().toString(), edit_pass_word.getText().toString(), edit_confirm_pass_word.getText().toString()))
                    if (edit_pass_word.getText().toString().equals(edit_confirm_pass_word.getText().toString())) {
                        progressDialog.show();
                        initNetDataWork(edit_confirm_pass_word.getText().toString());
                    } else {
                        toastUtil.showToast(getString(R.string.equals_exception));
                    }
                break;
        }
    }

    //过滤
    private boolean EditVerdict(String account, String validation, String pass_word, String confirm_pass_word) {
        boolean status = true;
        if (account.isEmpty() || validation.isEmpty() || pass_word.isEmpty() || confirm_pass_word.isEmpty()) {
            status = false;
            toastUtil.showToast(getString(R.string.empty_exception));
        } else if (!SystemUtil.isValidation(validationCode, validation)) {
            status = false;
            toastUtil.showToast(getString(R.string.validation_exception));
        } else if (!SystemUtil.isPhotoNumberLegal(account)) {
            status = false;
            toastUtil.showToast(getString(R.string.phone_number_exception));
        } else if (!SystemUtil.isPwdLegal(pass_word)) {
            status = false;
            toastUtil.showToast(getString(R.string.pass_word_exception));
        } else if (!SystemUtil.isPwdLegal(confirm_pass_word)) {
            status = false;
            toastUtil.showToast(getString(R.string.pass_word_exception));
        } else if (!pass_word.equals(confirm_pass_word)) {
            status = false;
            toastUtil.showToast(getString(R.string.equals_exception));
        }
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

    //提交
    private void initNetDataWork(String passWord) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.TRANSACTION_PWD_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("traderspwd", passWord);
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
                }));
    }

    // http://xfx.027perfect.com/api/api.mingfa.php?version=v1&vars={"action":"get_code", "phone":"17762598059", "type":"2"}


}
