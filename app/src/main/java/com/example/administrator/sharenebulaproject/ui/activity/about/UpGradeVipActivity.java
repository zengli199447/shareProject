package com.example.administrator.sharenebulaproject.ui.activity.about;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.AppKeyConfig;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.AlPayNetBean;
import com.example.administrator.sharenebulaproject.model.bean.TextExplainsNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpgradeNetBean;
import com.example.administrator.sharenebulaproject.model.bean.WechatPayNetBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.adapter.UpGradeVipAdapter;
import com.example.administrator.sharenebulaproject.ui.dialog.PayStatusDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.ui.view.CustomPayInputPopupWindow;
import com.example.administrator.sharenebulaproject.ui.view.ImageSlideshow;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.example.administrator.sharenebulaproject.widget.PayBuilder;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/21.
 * 升级会员/推广合作
 */

public class UpGradeVipActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, CustomPayInputPopupWindow.OnItemClickListener, PopupWindow.OnDismissListener {

    @BindView(R.id.upgrade_list)
    ListView upgrade_list;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.title_bg)
    ImageView title_bg;
    @BindView(R.id.title_about_img)
    View title_about_img;

    @BindView(R.id.layout_generalize)
    LinearLayout layout_generalize;
    @BindView(R.id.input_the_contact)
    TextView input_the_contact;
    @BindView(R.id.input_contact_phone_number)
    TextView input_contact_phone_number;
    @BindView(R.id.commite)
    TextView commite;
    @BindView(R.id.instructions)
    TextView instructions;

    private List<UpgradeNetBean.ResultBean.LevelBean> levelBeanList;
    private CustomPayInputPopupWindow customPayInputPopupWindow;
    private String payType = "";
    private String levelconfigid;
    private ProgressDialog progressDialog;
    private int flags;
    private PayBuilder payBuilder;
    private ShowDialog instance;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.INPUT_THE_CONTACT:
                input_the_contact.setText(commonevent.getTemp_str());
                break;
            case EventCode.INPUT_CONTACT_PHONE_NUMBER:
                input_contact_phone_number.setText(commonevent.getTemp_str());
                break;
            case EventCode.PAY_RETURN:
                instance.showPayStatusDialog(this, commonevent.getTemp_value());
                RxBus.getDefault().post(new CommonEvent(EventCode.REFRESH_USERINFO));
                initNetDataWork();
//                finish();
                break;
            case EventCode.FINISH_TASK:
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
        return R.layout.activity_upgrade_vip;
    }

    @Override
    protected void initClass() {
        flags = getIntent().getFlags();
        instance = ShowDialog.getInstance();
        payBuilder = new PayBuilder(this, toastUtil);
        customPayInputPopupWindow = new CustomPayInputPopupWindow(this);
        progressDialog = instance.showProgressStatus(this, getString(R.string.progress));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        flags = intent.getFlags();
    }

    @Override
    protected void initData() {
        progressDialog.show();
        switch (flags) {
            case 0:
                initNetDataWork();
                break;
            case 1:
                initNetTextDataWork(DataClass.PROMOTION_COOPERATION);
                break;
        }
    }

    @Override
    protected void initView() {
        switch (flags) {
            case 0:
                title_name.setText(getString(R.string.upgrade_vip));
                upgrade_list.setVisibility(View.VISIBLE);
                title_about_img.setBackground(getResources().getDrawable(R.drawable.why_icon));
                break;
            case 1:
                title_name.setText(getString(R.string.generalize));
//                title_bg.setBackground(getResources().getDrawable(R.drawable.upgrade_title_bg));
                layout_generalize.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void initListener() {
        upgrade_list.setOnItemClickListener(this);
        customPayInputPopupWindow.setOnItemClickListener(this);
        customPayInputPopupWindow.setOnDismissListener(this);
        img_btn_black.setOnClickListener(this);
        input_the_contact.setOnClickListener(this);
        input_contact_phone_number.setOnClickListener(this);
        commite.setOnClickListener(this);
        title_about_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.input_the_contact:
                ShowDialog.getInstance().showGeneralEditInput(this, EventCode.INPUT_THE_CONTACT, getString(R.string.input_the_contact), input_the_contact.getText().toString());
                break;
            case R.id.input_contact_phone_number:
                ShowDialog.getInstance().showGeneralEditInput(this, EventCode.INPUT_CONTACT_PHONE_NUMBER, getString(R.string.input_contact_phone_number), input_contact_phone_number.getText().toString());
                break;
            case R.id.commite:
                if (SystemUtil.isPhotoNumberLegal(input_contact_phone_number.getText().toString()) && !input_the_contact.getText().toString().isEmpty()) {
                    initNetGeneralizeCommit(input_the_contact.getText().toString(), input_contact_phone_number.getText().toString());
                } else {
                    toastUtil.showToast(getString(R.string.input_exception));
                }
                break;
            case R.id.title_about_img:
                Intent levelPermissionsIntent = new Intent(this, VipBenefitActivity.class);
                levelPermissionsIntent.setFlags(0);
                startActivity(levelPermissionsIntent);
                break;
        }
    }

    @Override
    public void setOnItemClick(View v) {
        switch (v.getId()) {
            case R.id.wechat_pay:
                payType = "微信支付";
                customPayInputPopupWindow.setSelectIndex(v.getId());
                break;
            case R.id.zhifubao_pay:
                payType = "支付宝支付";
                customPayInputPopupWindow.setSelectIndex(v.getId());
                break;
            case R.id.bank_pay:
                payType = "网银支付";
                customPayInputPopupWindow.setSelectIndex(v.getId());
                break;
            case R.id.commit_pay:
//                toastUtil.showToast("payType : " + payType);
                if (!payType.isEmpty()) {
                    if (payType.equals("支付宝支付")) {
                        initZhiFuBaoNetCommit(levelconfigid, payType);
                    } else {
                        initWeChatNetCommit(levelconfigid, payType);
                    }
                    customPayInputPopupWindow.dismiss();
                } else {
                    toastUtil.showToast(getString(R.string.select_pay));
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        UpgradeNetBean.ResultBean.LevelBean levelBean = levelBeanList.get(i);
        if (levelBean.getIfcanclick().equals("1")) {
            levelconfigid = levelBean.getLevelconfigid();
            customPayInputPopupWindow.setTitlePayNumber(levelBean.getLevelprice());
            customPayInputPopupWindow.showAtLocation(findViewById(R.id.upgrade_list), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
            SystemUtil.windowToDark(this);
        } else {
            toastUtil.showToast(getString(R.string.no_check_exception));
        }
    }

    private void initAdapter() {
        UpGradeVipAdapter upGradeVipAdapter = new UpGradeVipAdapter(this, levelBeanList);
        upgrade_list.setAdapter(upGradeVipAdapter);
        upGradeVipAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDismiss() {
        SystemUtil.windowToLight(this);
    }

    //获取升级信息
    private void initNetDataWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.UPVIP_INIT_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getUpgradeNetBean(map)
                .compose(RxUtil.<UpgradeNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpgradeNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpgradeNetBean upgradeNetBean) {
                        if (upgradeNetBean.getStatus() == 1) {
                            Log.e(TAG, "upgradeNetBean : " + upgradeNetBean.toString());
                            UpgradeNetBean.ResultBean result = upgradeNetBean.getResult();
                            levelBeanList = result.getLevel();
                            Glide.with(UpGradeVipActivity.this).load(new StringBuffer().append(DataClass.FileUrl).append(result.getConfig().getPhoto()).toString()).centerCrop().error(R.drawable.banner_off).into(title_bg);
                            initAdapter();
                        } else {
                            toastUtil.showToast(upgradeNetBean.getMessage());
                        }
                        progressDialog.dismiss();
                    }
                }));
    }

    //提交升级(支付宝)
    private void initZhiFuBaoNetCommit(String levelconfigid, String paytype) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SERVICE_OPT_GETORDER_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("levelconfigid", levelconfigid);
        linkedHashMap.put("paytype", paytype);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getAlPayNetBean(map)
                .compose(RxUtil.<AlPayNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<AlPayNetBean>(toastUtil) {
                    @Override
                    public void onNext(AlPayNetBean alPayNetBean) {
                        Log.e(TAG, "AlPayNetBean : " + alPayNetBean.toString());
                        if (alPayNetBean.getStatus() == 1) {
                            AlPayNetBean.PrepayinfoBean prepayinfo = alPayNetBean.getPrepayinfo();
                            payBuilder.doAlipay(getString(R.string.upgrade_vip),
                                    getString(R.string.upgrade_vip),
                                    prepayinfo.getOrderCode(),
                                    prepayinfo.getUrl_notify(),
                                    prepayinfo.getPartner(),
                                    prepayinfo.getSeller(),
                                    prepayinfo.getPrivate_key(),
                                    prepayinfo.getTotalmoney());
                        } else {
                            toastUtil.showToast(alPayNetBean.getMessage());
                        }
                    }
                }));
    }

    //提交推广合作
    private void initNetGeneralizeCommit(String cooperationname, String phone) {
        progressDialog.ShowDiaLog(getString(R.string.progress_commit));
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.COOPERATION_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("cooperationname", cooperationname);
        linkedHashMap.put("phone", phone);
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
                        } else {
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                        progressDialog.dismiss();
                    }
                }));
    }

    //获取文本
    private void initNetTextDataWork(int infotype) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.TEXTINFO_GET);
        linkedHashMap.put("infotype", infotype);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getTextExplainsNetBean(map)
                .compose(RxUtil.<TextExplainsNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<TextExplainsNetBean>(toastUtil) {
                    @Override
                    public void onNext(TextExplainsNetBean textExplainsNetBean) {
                        if (textExplainsNetBean.getStatus() == 1) {
                            Log.e(TAG, "TextExplainsNetBean : " + textExplainsNetBean.toString());
                            TextExplainsNetBean.ResultBean result = textExplainsNetBean.getResult();
                            String content = result.getContent();
                            String photo = result.getPhoto();
                            LogUtil.e(TAG, "photo : " + photo);
//                        new SystemUtil().textMagicTool(UpGradeVipActivity.this, instructions, getString(R.string.instructions), content, R.dimen.dp14, R.dimen.dp12, R.color.black, R.color.gray_light,"");
                            instructions.setText(content);
                            Glide.with(UpGradeVipActivity.this).load(new StringBuffer().append(DataClass.FileUrl).append(photo).toString()).error(R.drawable.user_photo_icon).into(title_bg);
                        } else {
                            toastUtil.showToast(textExplainsNetBean.getMessage());
                        }
                        progressDialog.dismiss();
                    }
                }));
    }

    //提交升级(微信)
    private void initWeChatNetCommit(String levelconfigid, String paytype) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SERVICE_OPT_GETORDER_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("levelconfigid", levelconfigid);
        linkedHashMap.put("paytype", paytype);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getWechatPayNetBean(map)
                .compose(RxUtil.<WechatPayNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<WechatPayNetBean>(toastUtil) {
                    @Override
                    public void onNext(WechatPayNetBean wechatPayNetBean) {
                        Log.e(TAG, "WechatPayNetBean : " + wechatPayNetBean.toString());
                        if (wechatPayNetBean.getStatus() == 1) {
                            payBuilder.formatWxPayContent(wechatPayNetBean.getPrepayinfo());
                        } else {
                            toastUtil.showToast(wechatPayNetBean.getMessage());
                        }
                    }
                }));
    }


}
