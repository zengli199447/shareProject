package com.example.administrator.sharenebulaproject.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadUserInfoNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UserInfoBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.activity.tools.CameraActivity;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.ui.view.CircleImageView;
import com.example.administrator.sharenebulaproject.ui.view.CustomMediaFilePopupWindow;
import com.example.administrator.sharenebulaproject.utils.AESCryptUtil;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.example.administrator.sharenebulaproject.widget.MatisseBuilder;
import com.example.administrator.sharenebulaproject.widget.MultipartBuilder;
import com.example.administrator.sharenebulaproject.widget.PickerViewBuilder;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;


/**
 * Created by Administrator on 2018/8/11.
 */

public class PersonageActivity extends BaseActivity implements View.OnClickListener, CustomMediaFilePopupWindow.OnItemClickListener, PopupWindow.OnDismissListener, MatisseBuilder.selectMediaListrner {

    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about)
    TextView title_about;
    @BindView(R.id.user_heart)
    CircleImageView user_heart;
    @BindView(R.id.input_nick_name)
    TextView input_nick_name;
    @BindView(R.id.input_gender)
    TextView input_gender;
    @BindView(R.id.input_city)
    TextView input_city;
    @BindView(R.id.input_professional)
    TextView input_professional;
    @BindView(R.id.input_expected_income)
    TextView input_expected_income;

    private ShowDialog instance;
    private boolean upDataStatus;
    private PickerViewBuilder pickerViewBuilder;
    private CustomMediaFilePopupWindow customMediaFilePopupWindow;
    private String picturePath = "";
    private boolean upStatus = false;
    private ProgressDialog progressDialog;
    private MatisseBuilder matisseBuilder;
    private boolean textStatus = false;

    private int[] textView = {R.id.user_heart_text, R.id.nick_name, R.id.gender, R.id.city, R.id.professional, R.id.expected_income
            , R.id.input_nick_name, R.id.input_gender, R.id.input_city, R.id.input_professional, R.id.input_expected_income};

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.INPUT_NICK_NAME:
                input_nick_name.setText(commonevent.getTemp_str());
                break;
            case EventCode.INPUT_PROFESSIONAL:
                input_professional.setText(commonevent.getTemp_str());
                break;
            case EventCode.INPUT_EXPECTED_INCOME:
                input_expected_income.setText(new StringBuffer().append(commonevent.getTemp_str()).append(getString(R.string.the_measuring_unit)).toString());
                break;
            case EventCode.OPENMATISSE:
                MatisseBuilder.getInstence().singlePictureSelect(this);

                break;
            case EventCode.COMMIET_IMG:
                if (commonevent.getTemp_str().isEmpty()) {
                    toastUtil.showToast("图片提交失败");
                } else {
                    initNetDataWork(commonevent.getTemp_str());
                }
                break;
        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_personage;
    }

    @Override
    protected void initClass() {
        instance = ShowDialog.getInstance();
        pickerViewBuilder = new PickerViewBuilder(this);
        customMediaFilePopupWindow = new CustomMediaFilePopupWindow(this);
        progressDialog = instance.showProgressStatus(this, getString(R.string.progress_commit));
        matisseBuilder = MatisseBuilder.getInstence();
    }

    @Override
    protected void initData() {
        pickerViewBuilder.initJsonData();

    }

    @Override
    protected void initView() {
        title_about.setText(getString(R.string.the_editor));
        title_name.setText(getString(R.string.mine_content));
        Glide.with(this).load(new StringBuffer().append(DataClass.FileUrl).append(UserInfoBean.photo).toString()).centerCrop().error(R.drawable.banner_off).into(user_heart);
        input_nick_name.setText(UserInfoBean.secondname);
        input_gender.setText(UserInfoBean.sex);
        input_city.setText(new StringBuffer().append(UserInfoBean.province).append(" - ").append(UserInfoBean.city));
        input_professional.setText(UserInfoBean.job);
        input_expected_income.setText(UserInfoBean.moneyin);
        InputStatus();
    }

    @Override
    protected void initListener() {
        user_heart.setOnClickListener(this);
        img_btn_black.setOnClickListener(this);
        title_about.setOnClickListener(this);
        input_nick_name.setOnClickListener(this);
        input_professional.setOnClickListener(this);
        input_expected_income.setOnClickListener(this);
        user_heart.setOnClickListener(this);
        input_gender.setOnClickListener(this);
        input_city.setOnClickListener(this);
        customMediaFilePopupWindow.setOnItemClickListener(this);
        customMediaFilePopupWindow.setOnDismissListener(this);
        matisseBuilder.setSelectMediaListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_about:
                if (!upDataStatus) {
                    title_about.setText(getString(R.string.commit));
                    toastUtil.showToast("进入编辑修改模式");
                } else {
                    title_about.setText(getString(R.string.the_editor));
                    commitUpData();
                }
                upDataStatus = !upDataStatus;
                InputStatus();
                break;
            case R.id.input_nick_name:
                upDataUserContent(EventCode.INPUT_NICK_NAME, getString(R.string.input_nick_name), input_nick_name.getText().toString());
                break;
            case R.id.input_professional:
                upDataUserContent(EventCode.INPUT_PROFESSIONAL, getString(R.string.input_professional), input_professional.getText().toString());
                break;
            case R.id.input_expected_income:
                upDataUserContent(EventCode.INPUT_EXPECTED_INCOME, getString(R.string.input_expected_income), input_expected_income.getText().toString());
                break;
            case R.id.input_gender:
                if (upDataStatus)
                    pickerViewBuilder.GenderPickView(input_gender);
                break;
            case R.id.input_city:
                if (upDataStatus)
                    pickerViewBuilder.showOptionsPickerView(input_city);
                break;
            case R.id.user_heart:
                if (upDataStatus) {
                    customMediaFilePopupWindow.showAtLocation(input_expected_income, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    SystemUtil.windowToDark(this);
                }
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    @Override
    public void setOnItemClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_select:
                MatisseBuilder.getInstence().singlePictureSelect(this);
                break;
            case R.id.id_btn_take_photo:
                OpenCamera();
                break;
            case R.id.id_btn_cancelo:
                break;
        }
        customMediaFilePopupWindow.dismiss();
    }

    private void upDataUserContent(int status, String title, String content) {
        if (upDataStatus) {
            instance.showGeneralEditInput(this, status, title, content);
        }
    }

    //提交更新数据
    private void commitUpData() {
//        if (input_nick_name.getText().toString().isEmpty() || input_gender.getText().toString().isEmpty() || input_city.getText().toString().isEmpty() || input_professional.getText().toString().isEmpty() || input_expected_income.getText().toString().isEmpty()) {
//            toastUtil.showToast(getString(R.string.empty_exception));
//        } else {
            progressDialog.show();
            if (picturePath.isEmpty()) {
                initNetDataWork(UserInfoBean.photo);
            } else {
                new MultipartBuilder().commitFile(picturePath, this);
            }
//        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EventCode.PICTURE && resultCode == RESULT_OK) {
            picturePath = data.getStringExtra("picturePath");
            LogUtil.e(TAG, "picturePath : " + picturePath);
            Glide.with(this).load(picturePath).centerCrop().error(R.drawable.banner_off).into(user_heart);
        }
    }

    @Override
    public void selectMediaListener(String path) {
        Log.e("Matisse", "mSelected: " + new StringBuffer().append("file://").append(path).toString());
        picturePath = path;
        Glide.with(this).load(path).centerCrop().error(R.drawable.banner_off).into(user_heart);
    }


    //开启相机
    public void OpenCamera() {
        startActivityForResult(new Intent(this, CameraActivity.class), EventCode.PICTURE);
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
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

    //更新个人信息
    private void initNetDataWork(String photoUrl) {
        String[] split = input_city.getText().toString().split("-");
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.USER_INFO_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("photo", photoUrl);
        linkedHashMap.put("secondname", input_nick_name.getText());
        linkedHashMap.put("province", split[0]);
        linkedHashMap.put("city", split[1]);
        linkedHashMap.put("sex", input_gender.getText());
        linkedHashMap.put("job", input_professional.getText());
        linkedHashMap.put("moneyin", input_expected_income.getText());
        String toJson =  AESCryptUtil.encrypt(new Gson().toJson(linkedHashMap));
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getUpLoadUserInfoNetBean(map)
                .compose(RxUtil.<UpLoadUserInfoNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadUserInfoNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadUserInfoNetBean upLoadUserInfoNetBean) {
                        Log.e(TAG, "upLoadStatusNetBean : " + upLoadUserInfoNetBean.toString());
                        if (upLoadUserInfoNetBean.getStatus() == 1) {
                            toastUtil.showToast("提交成功");
                            RxBus.getDefault().post(new CommonEvent(EventCode.REFRESH_USERINFO));
                            finish();
                        } else {
                            toastUtil.showToast(upLoadUserInfoNetBean.getMessage());
                        }
                        progressDialog.dismiss();
                    }
                }));
    }


//        http://xfx.027perfect.com/api/api.mingfa.php?version=v1&vars={"action":"user_info_set",userid":"1",photo":"1234567890123",secondname":"胡戈",province":"东城",city":"北京",sex":"男",job":"演员",moneyin":"1000"}

}
