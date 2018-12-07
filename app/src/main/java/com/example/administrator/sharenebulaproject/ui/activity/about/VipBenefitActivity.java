package com.example.administrator.sharenebulaproject.ui.activity.about;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.VipBenefitNetBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.adapter.VipBenefitAdapter;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/21.
 * 会员权利
 */

public class VipBenefitActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.vip_benefit_list)
    ListView vip_benefit_list;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_about_img)
    View title_about_img;
    private int flags;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_vip_benefit;
    }

    @Override
    protected void initClass() {
        flags = getIntent().getFlags();
    }

    @Override
    protected void initData() {
        initNetDataWork();
    }

    @Override
    protected void initView() {
        switch (flags) {
            case 0:
                title_name.setText(getString(R.string.vip_benefit));
                title_about_img.setBackground(getResources().getDrawable(R.drawable.vip_icon));
                break;
            case 1:

                break;
        }

    }

    @Override
    protected void initListener() {
        title_about_img.setOnClickListener(this);
        img_btn_black.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_about_img:
                RxBus.getDefault().post(new CommonEvent(EventCode.FINISH_TASK));
                Intent upGrade_VipIntent = new Intent(this, UpGradeVipActivity.class);
                upGrade_VipIntent.setFlags(0);
                startActivity(upGrade_VipIntent);
                finish();
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    //获取个人信息
    private void initNetDataWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.UPVIP_REMARK_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getVipBenefitNetBean(map)
                .compose(RxUtil.<VipBenefitNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<VipBenefitNetBean>(toastUtil) {
                    @Override
                    public void onNext(VipBenefitNetBean vipBenefitNetBean) {
                        if (vipBenefitNetBean.getStatus() ==1){
                            Log.e(TAG, "VipBenefitNetBean : " + vipBenefitNetBean.toString());
                            VipBenefitNetBean.ResultBean result = vipBenefitNetBean.getResult();
                            List<VipBenefitNetBean.ResultBean.LevelremarkBean> levelremark = result.getLevelremark();
                            initAdapter(levelremark);
                        }else {
                            toastUtil.showToast(vipBenefitNetBean.getMessage());
                        }
                    }

                }));
    }

    private void initAdapter(List<VipBenefitNetBean.ResultBean.LevelremarkBean> levelremark) {
        VipBenefitAdapter vipBenefitAdapter = new VipBenefitAdapter(this, levelremark);
        vip_benefit_list.setAdapter(vipBenefitAdapter);
        vipBenefitAdapter.notifyDataSetChanged();
    }


}
