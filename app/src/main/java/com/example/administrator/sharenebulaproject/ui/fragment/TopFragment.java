package com.example.administrator.sharenebulaproject.ui.fragment;

import android.graphics.Color;
import android.graphics.Outline;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseFragment;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.global.MyApplication;
import com.example.administrator.sharenebulaproject.model.bean.MineInfoNetBean;
import com.example.administrator.sharenebulaproject.model.bean.MonthTopBean;
import com.example.administrator.sharenebulaproject.model.bean.QuarterTopBean;
import com.example.administrator.sharenebulaproject.model.bean.TopNetBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.adapter.MonthTopAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.QuarterTopAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.ScrollRecyclerViewAdapter;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.ui.view.AutoListView;
import com.example.administrator.sharenebulaproject.ui.view.AutoPollRecyclerView;
import com.example.administrator.sharenebulaproject.ui.view.CircleImageView;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/11.
 */

public class TopFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.month_top_list)
    AutoListView month_top_list;
    @BindView(R.id.quarter_top_list)
    AutoListView quarter_top_list;

    @BindView(R.id.first_user_content)
    TextView first_user_content;
    @BindView(R.id.center_user_content)
    TextView center_user_content;
    @BindView(R.id.last_user_content)
    TextView last_user_content;

    @BindView(R.id.center_user)
    CircleImageView center_user;
    @BindView(R.id.first_user)
    CircleImageView first_user;
    @BindView(R.id.last_user)
    CircleImageView last_user;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.scroll_recyclerview)
    AutoPollRecyclerView scroll_recyclerview;


    private List<MonthTopBean> monthTopBeanArrayList = new ArrayList<>();
    private List<QuarterTopBean> quarterTopBeanArrayList = new ArrayList<>();
    private MonthTopAdapter monthTopAdapter;
    private QuarterTopAdapter quarterTopAdapter;
    private ProgressDialog progressDialog;
    private List<TopNetBean.ResultBean.LogsBean> logList = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_top;
    }

    @Override
    protected void initClass() {
        progressDialog = ShowDialog.getInstance().showProgressStatus(getActivity(), getString(R.string.progress));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        monthTopAdapter = new MonthTopAdapter(getActivity(), monthTopBeanArrayList);
        quarterTopAdapter = new QuarterTopAdapter(getActivity(), quarterTopBeanArrayList);
        month_top_list.setAdapter(monthTopAdapter);
        quarter_top_list.setAdapter(quarterTopAdapter);
        month_top_list.setFocusable(false);
        quarter_top_list.setFocusable(false);
    }

    @Override
    protected void initListener() {
        first_user.setOnClickListener(this);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.first_user:

                break;
        }
    }

    private void initTopView(List<TopNetBean.ResultBean.TalentTotalBean> talent_total, List<TopNetBean.ResultBean.TalentMonthBean> talent_month, List<TopNetBean.ResultBean.TalentSeasonBean> talent_season, List<TopNetBean.ResultBean.LogsBean> logs) {
        try {
            if (talent_total.size() > 0) {
                new SystemUtil().textMagicTool(getActivity(), first_user_content, talent_total.get(0).getSecondname(), new StringBuffer().append(getString(R.string.total)).append(talent_total.get(0).getBeantotal_txt()).toString(), R.dimen.dp12, R.dimen.dp14, R.color.white, R.color.fatigue_yellow);
                Glide.with(getActivity()).load(new StringBuffer().append(DataClass.FileUrl).append(talent_total.get(0).getPhoto()).toString()).centerCrop().error(R.drawable.banner_off).into(first_user);
            }
            if (talent_total.size() > 1) {
                new SystemUtil().textMagicTool(getActivity(), center_user_content, talent_total.get(1).getSecondname(), new StringBuffer().append(getString(R.string.total)).append(talent_total.get(1).getBeantotal_txt()).toString(), R.dimen.dp12, R.dimen.dp14, R.color.white, R.color.fatigue_yellow);
                Glide.with(getActivity()).load(new StringBuffer().append(DataClass.FileUrl).append(talent_total.get(1).getPhoto()).toString()).centerCrop().error(R.drawable.banner_off).into(center_user);
            }
            if (talent_total.size() > 2) {
                new SystemUtil().textMagicTool(getActivity(), last_user_content, talent_total.get(2).getSecondname(), new StringBuffer().append(getString(R.string.total)).append(talent_total.get(2).getBeantotal_txt()).toString(), R.dimen.dp12, R.dimen.dp14, R.color.white, R.color.fatigue_yellow);
                Glide.with(getActivity()).load(new StringBuffer().append(DataClass.FileUrl).append(talent_total.get(2).getPhoto()).toString()).centerCrop().error(R.drawable.banner_off).into(last_user);

            }
            monthTopBeanArrayList.clear();
            quarterTopBeanArrayList.clear();
            for (int i = 0; i < talent_month.size(); i++) {
                TopNetBean.ResultBean.TalentMonthBean talentMonthBean = talent_month.get(i);
                monthTopBeanArrayList.add(new MonthTopBean(i, talentMonthBean.getSecondname(), talentMonthBean.getBeantotal_txt()));
            }
            for (int i = 0; i < talent_season.size(); i++) {
                TopNetBean.ResultBean.TalentSeasonBean talentSeasonBean = talent_season.get(i);
                quarterTopBeanArrayList.add(new QuarterTopBean(i, talentSeasonBean.getSecondname(), talentSeasonBean.getBeantotal_txt()));
            }
            monthTopAdapter.notifyDataSetChanged();
            quarterTopAdapter.notifyDataSetChanged();
            scrollView.smoothScrollTo(0, 20);

            if (logs.size() > 0) {
                ScrollRecyclerViewAdapter adapter = new ScrollRecyclerViewAdapter(getActivity(), logs);
                scroll_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                scroll_recyclerview.setAdapter(adapter);
                if (true) //保证itemCount的总个数宽度超过屏幕宽度->自己处理
                    scroll_recyclerview.start();
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "Exception : " + e.toString());
        }
    }


    //获取达人榜单
    private void initNetDataWork() {
//        progressDialog.show();
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.TALENT_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getTopNetBean(map)
                .compose(RxUtil.<TopNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<TopNetBean>(toastUtil) {
                    @Override
                    public void onNext(TopNetBean topNetBean) {
                        Log.e(TAG, "TopNetBean : " + topNetBean.toString());
                        if (topNetBean.getStatus() == 1) {
                            TopNetBean.ResultBean result = topNetBean.getResult();
                            initTopView(result.getTalent_total(), result.getTalent_month(), result.getTalent_season(), result.getLogs());
                        } else {
                            toastUtil.showToast(topNetBean.getMessage());
                        }
                        progressDialog.dismiss();
                    }
                }));
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initNetDataWork();
        }
        LogUtil.e(TAG, "hidden : " + hidden);
    }

}
