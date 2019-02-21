package com.example.administrator.sharenebulaproject.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseFragment;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.LoginInfoBean;
import com.example.administrator.sharenebulaproject.model.bean.MineInfoNetBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.activity.LoginActivity;
import com.example.administrator.sharenebulaproject.ui.activity.PersonageActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.FansActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.GeneralVersionActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.ImageHeartActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.MyLevelActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.PublicWebActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.PushMessageActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.SettlementActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.SettlementLogActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.UpGradeVipActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.VipBenefitActivity;
import com.example.administrator.sharenebulaproject.ui.activity.certification.AccountBindActivity;
import com.example.administrator.sharenebulaproject.ui.activity.certification.GeneralActivity;
import com.example.administrator.sharenebulaproject.ui.activity.certification.SecurityCertificationActivity;
import com.example.administrator.sharenebulaproject.ui.activity.tools.QrCodeActivity;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.ui.view.CircleImageView;
import com.example.administrator.sharenebulaproject.utils.AESCryptUtil;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/11.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.user_heart)
    CircleImageView user_heart;
    @BindView(R.id.nick_name)
    TextView nick_name;
    @BindView(R.id.vip_level)
    TextView vip_level;
    @BindView(R.id.invite_code)
    TextView invite_code;
    @BindView(R.id.balance_outstanding)
    TextView balance_outstanding;

    @BindView(R.id.btn_right)
    ImageView btn_right;
    @BindView(R.id.layout_message)
    FrameLayout layout_message;
    @BindView(R.id.message_statistical)
    TextView message_statistical;
    @BindView(R.id.upgrade_vip)
    TextView upgrade_vip;

    @BindView(R.id.settlement)
    TextView settlement;

    @BindView(R.id.settlement_account)
    TextView settlement_account;
    @BindView(R.id.settlement_content)
    TextView settlement_content;
    @BindView(R.id.settlement_log)
    TextView settlement_log;

    @BindView(R.id.mine_level)
    TextView mine_level;
    @BindView(R.id.level_modle)
    TextView level_modle;
    @BindView(R.id.level_permissions)
    TextView level_permissions;
    @BindView(R.id.mine_upgrade_vip)
    TextView mine_upgrade_vip;

    @BindView(R.id.invitation)
    TextView invitation;
    @BindView(R.id.posters)
    TextView posters;
    @BindView(R.id.fans)
    TextView fans;
    @BindView(R.id.security)
    TextView security;
    @BindView(R.id.help_center)
    TextView help_center;
    @BindView(R.id.customer_service)
    TextView customer_service;
    @BindView(R.id.generalize)
    TextView generalize;
    @BindView(R.id.setting_up)
    TextView setting_up;
    @BindView(R.id.layout_user)
    LinearLayout layout_user;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refresh_layout;
    private ProgressDialog progressDialog;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initClass() {
        progressDialog = ShowDialog.getInstance().showProgressStatus(getActivity(), getString(R.string.progress));
        refresh_layout.setProgressViewOffset(true, -20, 100);
        refresh_layout.setColorSchemeResources(R.color.blue);
    }

    @Override
    protected void initData() {
        if (progressDialog != null){
            progressDialog.show();
            initNetDataWork();
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        layout_message.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        upgrade_vip.setOnClickListener(this);
        settlement.setOnClickListener(this);
        settlement_account.setOnClickListener(this);
        settlement_content.setOnClickListener(this);
        settlement_log.setOnClickListener(this);
        mine_level.setOnClickListener(this);
        level_modle.setOnClickListener(this);
        level_permissions.setOnClickListener(this);
        mine_upgrade_vip.setOnClickListener(this);
        invitation.setOnClickListener(this);
        posters.setOnClickListener(this);
        fans.setOnClickListener(this);
        security.setOnClickListener(this);
        help_center.setOnClickListener(this);
        customer_service.setOnClickListener(this);
        generalize.setOnClickListener(this);
        setting_up.setOnClickListener(this);
        user_heart.setOnClickListener(this);
        layout_user.setOnClickListener(this);
        refresh_layout.setOnRefreshListener(this);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.REFRESH_BRANCH:
                if (progressDialog != null)
                    progressDialog.show();
                initNetDataWork();
                break;
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_message:
//                toastUtil.showToast("消息");
                Intent messageIntent = new Intent(getActivity(), PushMessageActivity.class);
                getActivity().startActivity(messageIntent);
                message_statistical.setVisibility(View.GONE);
                break;
            case R.id.layout_user:
            case R.id.btn_right:
                getActivity().startActivity(new Intent(getActivity(), PersonageActivity.class));
//                toastUtil.showToast("right");
                break;
            case R.id.upgrade_vip:
//                toastUtil.showToast("升级Vip");
                Intent upGrade_VipIntent = new Intent(getActivity(), UpGradeVipActivity.class);
                upGrade_VipIntent.setFlags(0);
                getActivity().startActivity(upGrade_VipIntent);
                break;
            case R.id.settlement:
//                toastUtil.showToast("结算");
                getActivity().startActivity(new Intent(getActivity(), SettlementActivity.class));
                break;
            case R.id.settlement_account:
//                toastUtil.showToast("结算账户");
                Intent settlementAccountIntent = new Intent(getActivity(), AccountBindActivity.class);
                settlementAccountIntent.setFlags(EventCode.ACCOUNT_BIND);
                startActivity(settlementAccountIntent);
                break;
            case R.id.settlement_content:
//                toastUtil.showToast("结算说明");
                Intent instructionsIntent = new Intent(getActivity(), PublicWebActivity.class);
                instructionsIntent.setFlags(EventCode.TEXT_WEB);
                instructionsIntent.putExtra("value", String.valueOf(DataClass.BILLING_INSTRUCTIONS));
                startActivity(instructionsIntent);
                break;
            case R.id.settlement_log:
//                toastUtil.showToast("结算记录");
                Intent settlementLogIntent = new Intent(getActivity(), SettlementLogActivity.class);
                settlementLogIntent.setFlags(0);
                getActivity().startActivity(settlementLogIntent);
                break;
            case R.id.mine_level:
//                toastUtil.showToast("我的等级");
                getActivity().startActivity(new Intent(getActivity(), MyLevelActivity.class));
                break;
            case R.id.level_modle:
//                toastUtil.showToast("等级制度");
                Intent levelModleIntent = new Intent(getActivity(), PublicWebActivity.class);
                levelModleIntent.setFlags(EventCode.TEXT_WEB);
                levelModleIntent.putExtra("value", String.valueOf(DataClass.HIERARCHY));
                startActivity(levelModleIntent);
                break;
            case R.id.level_permissions:
//                toastUtil.showToast("会员权限");
                Intent levelPermissionsIntent = new Intent(getActivity(), VipBenefitActivity.class);
                levelPermissionsIntent.setFlags(0);
                getActivity().startActivity(levelPermissionsIntent);
                break;
            case R.id.mine_upgrade_vip:
//                toastUtil.showToast("我要升级");
                Intent upGradeVipIntent = new Intent(getActivity(), UpGradeVipActivity.class);
                upGradeVipIntent.setFlags(0);
                getActivity().startActivity(upGradeVipIntent);
                break;
            case R.id.invitation:
//                toastUtil.showToast("邀请码");
                startActivity(new Intent(getActivity(), QrCodeActivity.class));
                break;
            case R.id.posters:
//                toastUtil.showToast("海报");
                Intent promoteIntent = new Intent(getActivity(), GeneralVersionActivity.class);
                promoteIntent.setFlags(1);
                getActivity().startActivity(promoteIntent);
                break;
            case R.id.fans:
//                toastUtil.showToast("粉丝");
                getActivity().startActivity(new Intent(getActivity(), FansActivity.class));
                break;
            case R.id.security:
//                toastUtil.showToast("安全");
                getActivity().startActivity(new Intent(getActivity(), SecurityCertificationActivity.class));
                break;
            case R.id.help_center:
//                toastUtil.showToast("帮助");
                Intent helpCenterIntent = new Intent(getActivity(), PublicWebActivity.class);
                helpCenterIntent.setFlags(EventCode.TEXT_WEB);
                helpCenterIntent.putExtra("value", String.valueOf(DataClass.HELP_CENTER));
                startActivity(helpCenterIntent);
                break;
            case R.id.customer_service:
//                toastUtil.showToast("客服");
                Intent customerServiceIntent = new Intent(getActivity(), PublicWebActivity.class);
                customerServiceIntent.setFlags(EventCode.TEXT_WEB);
                customerServiceIntent.putExtra("value", String.valueOf(DataClass.CONTACT_CUSTOMER_SERVICE));
                startActivity(customerServiceIntent);
                break;
            case R.id.generalize:
//                toastUtil.showToast("推广");
                Intent generalizeIntent = new Intent(getActivity(), UpGradeVipActivity.class);
                generalizeIntent.setFlags(1);
                getActivity().startActivity(generalizeIntent);
                break;
            case R.id.setting_up:
//                toastUtil.showToast("设置");
                Intent settingIntent = new Intent(getActivity(), GeneralActivity.class);
                settingIntent.setFlags(EventCode.SETTING);
                startActivity(settingIntent);
                break;
            case R.id.user_heart:
                startActivity(new Intent(getActivity(), ImageHeartActivity.class));
                break;
        }
    }

    @Override
    public void onRefresh() {
        initNetDataWork();
    }

    //获取个人信息
    private void initNetDataWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LogUtil.e(TAG,"action :" + DataClass.MY_CENTER_GET);
        linkedHashMap.put("action", DataClass.MY_CENTER_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.MineInfoBeanNetData(map)
                .compose(RxUtil.<MineInfoNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<MineInfoNetBean>(toastUtil) {
                    @Override
                    public void onNext(MineInfoNetBean mineInfoNetBean) {
                        Log.e(TAG, "loginInfoBean : " + mineInfoNetBean.toString());
                        MineInfoNetBean.ResultBean result = mineInfoNetBean.getResult();
                        if (mineInfoNetBean.getStatus() == 1) {
                            new SystemUtil().textMagicToolTypeFace(getActivity(), balance_outstanding, result.getStarbeantotal(), getString(R.string.balance_outstanding), R.dimen.dp16, R.dimen.dp12, R.color.blue, R.color.gray_light_text, "\n");
                            if (!result.getUser().getPhoto().isEmpty())
                                Glide.with(getActivity()).load(SystemUtil.JudgeUrl(result.getUser().getPhoto())).centerCrop().error(R.drawable.user_photo_icon).into(user_heart);
                            invite_code.setText(new StringBuffer().append("邀请码: ").append(result.getUser().getInvitationcode()));
                            nick_name.setText(result.getUser().getSecondname());
                            vip_level.setText(new StringBuffer().append("会员等级: ").append(result.getUser().getLevelname()));
                            if (!"0".equals(result.getMessageamount())) {
                                message_statistical.setText(result.getMessageamount());
                                message_statistical.setVisibility(View.VISIBLE);
                            } else {
                                message_statistical.setVisibility(View.GONE);
                            }
                        } else {
                            toastUtil.showToast(mineInfoNetBean.getMessage());
                        }
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        refresh_layout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                        progressDialog.dismiss();
                        refresh_layout.setRefreshing(false);
                    }
                }));
    }


    //    http://xfx.027perfect.com/api/api.mingfa.php?version=v1&vars={"action":"upvip_init_get","userid":"1"}


}
