package com.example.administrator.sharenebulaproject.ui.activity.about;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.MyLevelNetBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;


/**
 * Created by Administrator on 2018/8/22.
 * 我的等级
 */

public class MyLevelActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.user_heart)
    ImageView user_heart;
    @BindView(R.id.nick_name)
    TextView nick_name;
    @BindView(R.id.vip_level)
    TextView vip_level;
    @BindView(R.id.invite_code)
    TextView invite_code;
    @BindView(R.id.upgrade_vip)
    TextView upgrade_vip;
    @BindView(R.id.vip_benefit)
    TextView vip_benefit;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    private ProgressDialog progressDialog;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_my_level;
    }

    @Override
    protected void initClass() {
        progressDialog = ShowDialog.getInstance().showProgressStatus(this, getString(R.string.progress));
    }

    @Override
    protected void initData() {
        progressDialog.show();
        initNetDataWork();
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.mine_level));
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
        upgrade_vip.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
            case R.id.upgrade_vip:
                Intent upGradeVipIntent = new Intent(this, UpGradeVipActivity.class);
                upGradeVipIntent.setFlags(0);
                startActivity(upGradeVipIntent);
                break;
        }
    }

    //获取等级信息
    private void initNetDataWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.MYVIP_DETAIL_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getMyLevelNetBean(map)
                .compose(RxUtil.<MyLevelNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<MyLevelNetBean>(toastUtil) {
                    @Override
                    public void onNext(MyLevelNetBean myLevelNetBean) {
                        if (myLevelNetBean.getStatus() == 1) {
                            Log.e(TAG, "MyLevelNetBean : " + myLevelNetBean.toString());
                            MyLevelNetBean.ResultBean result = myLevelNetBean.getResult();
                            MyLevelNetBean.ResultBean.UserlevelBean userlevel = result.getUserlevel();
                            new SystemUtil().textMagicTool(MyLevelActivity.this, vip_benefit, new StringBuffer().append(userlevel.getLevelname()).append("\n").toString(), userlevel.getRemark(), R.dimen.dp16, R.dimen.dp12, R.color.black, R.color.black_overlay);
                            Glide.with(MyLevelActivity.this).load(new StringBuffer().append(DataClass.FileUrl).append(userlevel.getPhoto()).toString()).centerCrop().error(R.drawable.banner_off).into(user_heart);
                            invite_code.setText(new StringBuffer().append("邀请码: ").append(userlevel.getInvitationcode()));
                            nick_name.setText(userlevel.getSecondname());
                            vip_level.setText(new StringBuffer().append("会员等级: ").append(userlevel.getLevelname()));
                        } else {
                            toastUtil.showToast(myLevelNetBean.getMessage());
                        }
                        progressDialog.dismiss();
                    }

                }));
    }


}
