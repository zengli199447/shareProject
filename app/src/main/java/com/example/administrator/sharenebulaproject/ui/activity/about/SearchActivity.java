package com.example.administrator.sharenebulaproject.ui.activity.about;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.HotAllDataBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.adapter.DiversifiedAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.HotRecyclerViewAdapter;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/11/29.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener, DiversifiedAdapter.onItemCheckListener {

    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.search_view)
    EditText search_view;
    @BindView(R.id.search)
    TextView search;

    private List<Object> AllSortClassList = new ArrayList<>();
    private DiversifiedAdapter diversifiedAdapter;
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
        return R.layout.activity_search;
    }

    @Override
    protected void initClass() {
        progressDialog = ShowDialog.getInstance().showProgressStatus(this, getString(R.string.searching));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        diversifiedAdapter = new DiversifiedAdapter(this, AllSortClassList, null);
        recycler_view.setAdapter(diversifiedAdapter);
    }

    @Override
    protected void initListener() {
        search.setOnClickListener(this);
        img_btn_black.setOnClickListener(this);
        diversifiedAdapter.setOnItemCheckListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search:
                if (search_view.getText().toString().isEmpty()) {
                    toastUtil.showToast(getString(R.string.empty_search));
                } else {
                    SystemUtil.closeKeybord(search_view, this);
                    initNetDataWork(search_view.getText().toString());
                }
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    private void refreshView() {
        diversifiedAdapter.notifyDataSetChanged();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onItemClick(int i) {
        HotAllDataBean.Result.newsBean newsBean = (HotAllDataBean.Result.newsBean) AllSortClassList.get(i);
        Intent dailyIntent = new Intent(this, PublicWebActivity.class);
        dailyIntent.setFlags(EventCode.GENERAL_WEB);
        dailyIntent.putExtra("value", String.valueOf(newsBean.getNewsid()));
        dailyIntent.putExtra("shareTitle", newsBean.getTitle());
        dailyIntent.putExtra("shareImgUrl", new StringBuffer().append(DataClass.FileUrl).append(newsBean.getListimg().split(",")[0]).toString());
        dailyIntent.putExtra("shareNewsUrl", new StringBuffer().append(DataClass.DAILY_URL).append(newsBean.getNewsid()).toString());
        dailyIntent.putExtra("total", String.valueOf(newsBean.getStarbean()));
        dailyIntent.putExtra("ifcanmoney", String.valueOf(newsBean.getIfcanmoney()));
        startActivity(dailyIntent);
    }

    @Override
    public void onRefreshItemClick() {

    }

    //搜索内容
    private void initNetDataWork(String keyWord) {
        progressDialog.show();
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.HOMEPAGE_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("versionvalue", SystemUtil.packageCode(this));
        linkedHashMap.put("systemtype", "1");
        linkedHashMap.put("pagenum", 1);
        linkedHashMap.put("ordertype", 1);
        linkedHashMap.put("city", DataClass.CNBYLOCATION);
        linkedHashMap.put("catid", "");
        linkedHashMap.put("kw", keyWord);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.HotPageData(map)
                .compose(RxUtil.<HotAllDataBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<HotAllDataBean>(toastUtil) {
                    @Override
                    public void onNext(HotAllDataBean hotAllDataBean) {
                        if (hotAllDataBean.getStatus() == 1) {
                            HotAllDataBean.Result result = hotAllDataBean.getResult();
                            AllSortClassList.clear();
                            AllSortClassList.addAll(result.getNews());
                            if (AllSortClassList.size() == 0) {
                                empty_layout.setVisibility(View.VISIBLE);
                            } else {
                                empty_layout.setVisibility(View.GONE);
                            }
                            refreshView();
                        } else {
                            toastUtil.showToast(hotAllDataBean.getMessage());
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        super.onError(e);
                        progressDialog.dismiss();
                    }
                }));
    }


}
