package com.example.administrator.sharenebulaproject.ui.activity.about;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.FansNetBean;
import com.example.administrator.sharenebulaproject.model.bean.MineInfoNetBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.adapter.FansAdapter;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/22.
 * 粉丝管理
 */

public class FansActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @BindView(R.id.fans_prople)
    TextView fans_prople;
    @BindView(R.id.fans_list)
    ListView fans_list;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    private ProgressDialog progressDialog;
    private List<FansNetBean.ResultBean.DetaillistBean> detaillist;
    private FansNetBean.ResultBean.DetaillistBean detaillistBean;


    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_fans;
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
        title_name.setText(getString(R.string.fans));
    }

    @Override
    protected void initListener() {
        fans_list.setOnItemClickListener(this);
        img_btn_black.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        detaillistBean = detaillist.get(i);
        Intent fansIntent = new Intent(this, SettlementLogActivity.class);
        fansIntent.putExtra("value", detaillistBean.getLevelconfigid());
        fansIntent.setFlags(1);
        startActivity(fansIntent);
    }

    //获取粉丝信息
    private void initNetDataWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.FANS_TJ_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getFansNetBean(map)
                .compose(RxUtil.<FansNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<FansNetBean>(toastUtil) {
                    @Override
                    public void onNext(FansNetBean fansNetBean) {
                        Log.e(TAG, "FansNetBean : " + fansNetBean.toString());
                        if (fansNetBean.getStatus() == 1) {
                            detaillist = fansNetBean.getResult().getDetaillist();
                            initAdapter();
                            new SystemUtil().textMagicTool(FansActivity.this, fans_prople, new StringBuffer().append(fansNetBean.getResult().getTotal()).append("\n").toString(), getString(R.string.fans_about), R.dimen.dp18, R.dimen.dp12, R.color.white, R.color.white);
                        }else {
                            toastUtil.showToast(fansNetBean.getMessage());
                        }
                        progressDialog.dismiss();
                    }
                }));
    }

    private void initAdapter() {
        FansAdapter fansAdapter = new FansAdapter(this, detaillist);
        fans_list.setAdapter(fansAdapter);
        fansAdapter.notifyDataSetChanged();
    }


}
