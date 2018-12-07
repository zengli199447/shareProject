package com.example.administrator.sharenebulaproject.ui.activity.certification;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.MineInfoNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UserInfoBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.utils.CardIdValidationUtils;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.example.administrator.sharenebulaproject.widget.MultipartBuilder;
import com.example.administrator.sharenebulaproject.widget.PickerViewBuilder;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/16.
 * 实名认证
 */

public class RealNameSystemActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.input_name)
    TextView input_name;
    @BindView(R.id.input_gender)
    TextView input_gender;
    @BindView(R.id.input_birthday)
    TextView input_birthday;
    @BindView(R.id.input_id_card)
    TextView input_id_card;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about)
    TextView title_about;

    private int[] textView = {R.id.name, R.id.gender, R.id.birthday, R.id.id_card, R.id.input_name, R.id.input_gender, R.id.input_birthday, R.id.input_id_card};

    private boolean upDataStatus;
    private ShowDialog instance;
    private PickerViewBuilder pickerViewBuilder;
    private ProgressDialog progressDialog;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.INPUT_NAME:
                input_name.setText(commonevent.getTemp_str());
                break;
            case EventCode.INPUT_ID_CARD:
                if (CardIdValidationUtils.cardIdValidation(toastUtil, commonevent.getTemp_str()))
                    input_id_card.setText(commonevent.getTemp_str());
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_real_name_system;
    }

    @Override
    protected void initClass() {
        instance = ShowDialog.getInstance();
        pickerViewBuilder = new PickerViewBuilder(this);
        progressDialog = instance.showProgressStatus(this, getString(R.string.progress_commit));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        title_about.setText(getString(R.string.the_editor));
        title_name.setText(getString(R.string.real_name_system));
        input_name.setText(UserInfoBean.name);
        input_gender.setText(UserInfoBean.sex);
        input_birthday.setText(UserInfoBean.brithday);
        input_id_card.setText(UserInfoBean.idcard);
        InputStatus();
    }

    @Override
    protected void initListener() {
        input_name.setOnClickListener(this);
        input_gender.setOnClickListener(this);
        input_birthday.setOnClickListener(this);
        input_id_card.setOnClickListener(this);
        img_btn_black.setOnClickListener(this);
        title_about.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.input_name:
                upDataUserContent(EventCode.INPUT_NAME, getString(R.string.input_name), input_name.getText().toString());
                break;
            case R.id.input_gender:
                if (upDataStatus)
                    pickerViewBuilder.GenderPickView(input_gender);
                break;
            case R.id.input_birthday:
                if (upDataStatus)
                    pickerViewBuilder.showTimePickView(input_birthday, false);
                break;
            case R.id.input_id_card:
                upDataUserContent(EventCode.INPUT_ID_CARD, getString(R.string.input_id_card), input_id_card.getText().toString());
                break;
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.title_about:
                if (!upDataStatus) {
                    title_about.setText(getString(R.string.commit));
                    toastUtil.showToast("进入编辑修改模式");
                } else {
                    title_about.setText(getString(R.string.the_editor));
                    if (input_name.getText().toString().isEmpty() || input_gender.getText().toString().isEmpty() || input_birthday.getText().toString().isEmpty() || input_id_card.getText().toString().isEmpty()) {
                        toastUtil.showToast(getString(R.string.empty_exception));
                    } else {
                        progressDialog.show();
                        commitUpData();
                    }
                }
                upDataStatus = !upDataStatus;
                InputStatus();
                break;
        }
    }

    private void upDataUserContent(int status, String title, String content) {
        if (upDataStatus) {
            instance.showGeneralEditInput(this, status, title, content);
        }
    }

    private void InputStatus() {
        for (int i = 0; i < textView.length; i++) {
            if (upDataStatus) {
                ((TextView) findViewById(textView[i])).setTextColor(getResources().getColor(R.color.black));
            } else {
                ((TextView) findViewById(textView[i])).setTextColor(getResources().getColor(R.color.gray_light));
            }
        }
    }

    //更新用户资料
    private void commitUpData() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.REALNAME_INFO_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("name", input_name.getText().toString());
        linkedHashMap.put("sex", input_gender.getText().toString());
        linkedHashMap.put("brithday", input_birthday.getText().toString());
        linkedHashMap.put("idcard", input_id_card.getText().toString());
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatus(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        Log.e(TAG, "UpLoadStatusNetBean : " + upLoadStatusNetBean.toString());
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

//            http://xfx.027perfect.com/api/api.mingfa.php?version=v1&vars={"action":"realname_info_set", "userid":"1", "name":"1", "sex":"1", "brithday":"1", "idcard":"1"}


}
