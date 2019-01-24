package com.example.administrator.sharenebulaproject.ui.activity.about;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.base.EndlessRecyclerOnScrollListener;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.FansContentNetBean;
import com.example.administrator.sharenebulaproject.model.bean.MoneyAboutNetBean;
import com.example.administrator.sharenebulaproject.model.bean.MyShareNetBean;
import com.example.administrator.sharenebulaproject.model.bean.SettementLogNetBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.adapter.SettlementRecyclerViewAdapter;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.utils.AESCryptUtil;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/22.
 * 结算记录/粉丝列表/我的分享
 */

public class SettlementLogActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, SettlementRecyclerViewAdapter.onCheckListener {

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.settlement_recycler_view)
    RecyclerView settlement_recycler_view;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.empty_view)
    TextView empty_view;

    private List<SettementLogNetBean.ResultBean.UsersBean> users;
    private LinearLayoutManager mLayoutManager;
    private SettlementRecyclerViewAdapter settlementRecyclerViewAdapter;
    private List<Object> allDataList = new ArrayList<>();
    private int pageNumber = 1;
    private int newIndex = 0;
    private int flags;
    private List<FansContentNetBean.ResultBean.UsersBean> fansUsers;
    private int Max = 0;
    private String intentStringValue;
    private List<MyShareNetBean.ResultBean.MyshareBean> myshare;
    private ProgressDialog progressDialog;
    private List<MoneyAboutNetBean.ResultBean.MoneyinBean> moneyin;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_settlement_log;
    }

    @Override
    protected void initClass() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        flags = getIntent().getFlags();
        intentStringValue = getIntent().getStringExtra("value");
        progressDialog = ShowDialog.getInstance().showProgressStatus(this, getString(R.string.progress));
        settlementRecyclerViewAdapter = new SettlementRecyclerViewAdapter(this, allDataList);
        settlement_recycler_view.setAdapter(settlementRecyclerViewAdapter);
        LogUtil.e(TAG, "flags : " + flags);
        LogUtil.e(TAG, "intentStringValue : " + intentStringValue);
    }

    @Override
    protected void initData() {
        progressDialog.show();
        switch (flags) {
            case 0:
                Max = 1;
                initNetDataWork(pageNumber);
                break;
            case 1:
                Max = 1;
                initNetFansContentDataWork(pageNumber);
                break;
            case 2:
                Max = 1;
                initNetShareDataWork(pageNumber);
                break;
            case 3:
                Max = 1;
                initNetAboutDataWork(pageNumber);
                break;
        }

    }

    @Override
    protected void initView() {
        switch (flags) {
            case 0:
                title_name.setText(R.string.settlement_log);
                break;
            case 1:
                title_name.setText(R.string.fan_list);
                break;
            case 2:
                title_name.setText(R.string.my_share);
                break;
            case 3:
                title_name.setText(R.string.about);
                break;
        }
        settlement_recycler_view.setLayoutManager(mLayoutManager);
        swipe_layout.setProgressViewOffset(true, -20, 100);
        swipe_layout.setColorSchemeResources(R.color.blue);
    }

    @Override
    protected void initListener() {
        swipe_layout.setOnRefreshListener(this);
        img_btn_black.setOnClickListener(this);
        if (flags == 2)
            settlementRecyclerViewAdapter.setOnCheckListener(this);

        settlement_recycler_view.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (settlementRecyclerViewAdapter != null) {
                    settlementRecyclerViewAdapter.setLoadState(settlementRecyclerViewAdapter.LOADING);
                    if (allDataList.size() > Max) {
                        pageNumber = pageNumber + 1;
                        LogUtil.e(TAG, "pageNumber : " + pageNumber);
                        switch (flags) {
                            case 0:
                                initNetDataWork(pageNumber);
                                break;
                            case 1:
                                initNetFansContentDataWork(pageNumber);
                                break;
                            case 2:
                                initNetShareDataWork(pageNumber);
                                break;
                            case 3:
                                initNetAboutDataWork(pageNumber);
                                break;
                        }
                    } else {
                        // 显示加载到底的提示
                        settlementRecyclerViewAdapter.setLoadState(settlementRecyclerViewAdapter.LOADING_END);
                    }
                    if (newIndex == 0) {
                        settlementRecyclerViewAdapter.setLoadState(settlementRecyclerViewAdapter.LOADING_END);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView) {

            }
        });
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        switch (flags) {
            case 0:
                initNetDataWork(pageNumber);
                break;
            case 1:
                initNetFansContentDataWork(pageNumber);
                break;
            case 2:
                initNetShareDataWork(pageNumber);
                break;
            case 3:
                initNetAboutDataWork(pageNumber);
                break;
        }
        LogUtil.e(TAG, "pageNumber : " + pageNumber);
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
    public void onClick(int i) {
        MyShareNetBean.ResultBean.MyshareBean myshareBean =(MyShareNetBean.ResultBean.MyshareBean) allDataList.get(i);
        Intent dailyIntent = new Intent(this, PublicWebActivity.class);
        dailyIntent.setFlags(EventCode.GENERAL_WEB);
        dailyIntent.putExtra("value", myshareBean.getNewsid());
        dailyIntent.putExtra("shareTitle", myshareBean.getTitle());
        dailyIntent.putExtra("shareImgUrl", new StringBuffer().append(DataClass.FileUrl).append(myshareBean.getListimg().split(",")[0]).toString());
        dailyIntent.putExtra("shareNewsUrl", new StringBuffer().append(DataClass.DAILY_URL).append(myshareBean.getNewsid()).append(DataClass.USERID_SHARE).toString());
        dailyIntent.putExtra("total", myshareBean.getBeantotal());
        dailyIntent.putExtra("ifcanmoney", myshareBean.getIfcanmoney());
        startActivity(dailyIntent);
    }

    private void initAdapter() {
        if (pageNumber == 1) {
            allDataList.clear();
        }
        switch (flags) {
            case 0:
                allDataList.addAll(users);
                break;
            case 1:
                allDataList.addAll(fansUsers);
                break;
            case 2:
                allDataList.addAll(myshare);
                break;
            case 3:
                allDataList.addAll(moneyin);
                break;
        }
        if (allDataList.size() == 0) {
            empty_view.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.GONE);
        }
        settlement_recycler_view.scrollToPosition(allDataList.size() - newIndex - 1);
        settlementRecyclerViewAdapter.notifyDataSetChanged();
        settlementRecyclerViewAdapter.setLoadState(settlementRecyclerViewAdapter.LOADING_COMPLETE);
        swipe_layout.setRefreshing(false);
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    //日志获取
    private void initNetDataWork(int index) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.SETTLEACCOUNTS_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("pagenum", index);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getSettementLogNetBean(map)
                .compose(RxUtil.<SettementLogNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<SettementLogNetBean>(toastUtil) {
                    @Override
                    public void onNext(SettementLogNetBean settementLogNetBean) {
                        if (settementLogNetBean.getStatus() ==1){
                            LogUtil.e(TAG, "SettementLogNetBean : " + settementLogNetBean.toString());
                            users = settementLogNetBean.getResult().getUsers();
                            newIndex = users.size();
                            initAdapter();
                        }else {
                            toastUtil.showToast(settementLogNetBean.getMessage());
                        }
                    }
                }));
    }

    //粉丝列表
    private void initNetFansContentDataWork(int index) {
        LogUtil.e(TAG, "intentStringValue : " + intentStringValue);
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.FANS_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("levelconfigid", intentStringValue);
        linkedHashMap.put("pagenum", index);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getFansContentNetBean(map)
                .compose(RxUtil.<FansContentNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<FansContentNetBean>(toastUtil) {
                    @Override
                    public void onNext(FansContentNetBean fansContentNetBean) {
                        if (fansContentNetBean.getStatus() ==1){
                            LogUtil.e(TAG, "FansContentNetBean : " + fansContentNetBean.toString());
                            fansUsers = fansContentNetBean.getResult().getUsers();
                            newIndex = fansUsers.size();
                            initAdapter();
                        }else {
                            toastUtil.showToast(fansContentNetBean.getMessage());
                        }
                    }
                }));
    }

    //分享列表
    private void initNetShareDataWork(int index) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.MYSHARE_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("pagenum", index);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getMyShareNetBean(map)
                .compose(RxUtil.<MyShareNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<MyShareNetBean>(toastUtil) {
                    @Override
                    public void onNext(MyShareNetBean myShareNetBean) {
                        LogUtil.e(TAG, "MyShareNetBean : " + myShareNetBean.toString());
                        if (myShareNetBean.getStatus() == 1) {
                            myshare = myShareNetBean.getResult().getMyshare();
                            newIndex = myshare.size();
                            initAdapter();
                        }else {
                            toastUtil.showToast(myShareNetBean.getMessage());
                        }
                    }
                }));
    }

    //更多
    private void initNetAboutDataWork(int index) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.MONEY_IN_LIST_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("moneyintypeid", intentStringValue);
        linkedHashMap.put("pagenum", index);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getMoneyAboutNetBean(map)
                .compose(RxUtil.<MoneyAboutNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<MoneyAboutNetBean>(toastUtil) {
                    @Override
                    public void onNext(MoneyAboutNetBean moneyAboutNetBean) {
                        LogUtil.e(TAG, "MoneyAboutNetBean : " + moneyAboutNetBean.toString());
                        if (moneyAboutNetBean.getStatus() == 1) {
                            moneyin = moneyAboutNetBean.getResult().getMoneyin();
                            newIndex = moneyin.size();
                            initAdapter();
                        }else {
                            toastUtil.showToast(moneyAboutNetBean.getMessage());
                        }
                    }
                }));
    }


}
