package com.example.administrator.sharenebulaproject.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseFragment;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.IncomeNetBean;
import com.example.administrator.sharenebulaproject.model.bean.MineInfoNetBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.activity.about.SettlementLogActivity;
import com.example.administrator.sharenebulaproject.ui.activity.certification.GeneralActivity;
import com.example.administrator.sharenebulaproject.ui.adapter.IncomeAdapter;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.ui.view.AutoExpandableListView;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.example.administrator.sharenebulaproject.widget.MediaPlayBuilder;
import com.example.administrator.sharenebulaproject.widget.PickerViewBuilder;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/11.
 * 收入
 */

public class IncomeFragment extends BaseFragment implements View.OnClickListener, PickerViewBuilder.SelectListener, ExpandableListView.OnChildClickListener, ExpandableListView.OnItemClickListener {

    @BindView(R.id.all_icome)
    TextView all_icome;
    @BindView(R.id.today_income)
    TextView today_income;
    @BindView(R.id.today_income_content)
    TextView today_income_content;
    @BindView(R.id.tomonth_income)
    TextView tomonth_income;
    @BindView(R.id.tomonth_income_content)
    TextView tomonth_income_content;
    @BindView(R.id.income_expand_list)
    AutoExpandableListView income_expand_list;
    @BindView(R.id.content_more)
    TextView content_more;
    @BindView(R.id.income_permissions)
    TextView income_permissions;
    @BindView(R.id.income_my_share)
    TextView income_my_share;
    @BindView(R.id.come_show_logo_prompt)
    ImageView come_show_logo_prompt;

    private ProgressDialog progressDialog;
    private String todayChar = "";
    private String monthChar = "";
    private PickerViewBuilder pickerViewBuilder;
    private String queryTime = "今天";
    private List<IncomeNetBean.ResultBean.MoneyindetailBean> moneyindetail;
    private MediaPlayBuilder mediaPlayBuilder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_income;
    }

    @Override
    protected void initClass() {
        pickerViewBuilder = new PickerViewBuilder(getActivity());
        progressDialog = ShowDialog.getInstance().showProgressStatus(getActivity(), getString(R.string.progress));
        mediaPlayBuilder = new MediaPlayBuilder();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        income_expand_list.setFocusable(false);
    }

    @Override
    protected void initListener() {
        content_more.setOnClickListener(this);
        pickerViewBuilder.initSelectListener(this);
        income_permissions.setOnClickListener(this);
        income_expand_list.setOnChildClickListener(this);
        income_expand_list.setOnItemClickListener(this);
        income_my_share.setOnClickListener(this);
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.content_more:
                pickerViewBuilder.showTimePickView(content_more, true);
                break;
            case R.id.income_permissions:
                Intent intent = new Intent(getActivity(), GeneralActivity.class);
                intent.setFlags(EventCode.INCOME_BENEFIT);
                startActivity(intent);
                break;
            case R.id.income_my_share:
                Intent settlementLogIntent = new Intent(getActivity(), SettlementLogActivity.class);
                settlementLogIntent.setFlags(2);
                startActivity(settlementLogIntent);
                break;
        }
    }

    @Override
    public void selectListener(String content) {
        initNetDataWork(content);
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
        Intent settlementLogIntent = new Intent(getActivity(), SettlementLogActivity.class);
        settlementLogIntent.putExtra("value", moneyindetail.get(i).getMoneyintypeid());
        settlementLogIntent.setFlags(3);
        startActivity(settlementLogIntent);
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private void initAdapter() {
        IncomeAdapter incomeAdapter = new IncomeAdapter(getActivity(), moneyindetail);
        income_expand_list.setAdapter(incomeAdapter);
        incomeAdapter.notifyDataSetChanged();
        SystemUtil.setExpandableListViewHeight(income_expand_list);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initNetDataWork("");
        }
        LogUtil.e(TAG, "hidden : " + hidden);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    come_show_logo_prompt.setVisibility(View.GONE);
                    break;
            }
        }
    };

    //获取收入
    private void initNetDataWork(String searchdate) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.MONEY_IN_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("searchdate", searchdate);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getIncomeNetBean(map)
                .compose(RxUtil.<IncomeNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<IncomeNetBean>(toastUtil) {
                    @Override
                    public void onNext(IncomeNetBean incomeNetBean) {
                        queryTime = content_more.getText().toString();
                        Log.e(TAG, "IncomeNetBean : " + incomeNetBean.toString());
                        if (incomeNetBean.getStatus() == 1) {
                            IncomeNetBean.ResultBean result = incomeNetBean.getResult();
                            moneyindetail = result.getMoneyindetail();
                            if (result.getIs_inmoney() > 0) {
                                come_show_logo_prompt.setVisibility(View.VISIBLE);
                                mediaPlayBuilder.playFromRawFile(getActivity());
                                handler.sendEmptyMessageDelayed(0, 3000);
                            }
                            initAdapter();
                            SystemUtil.textMagicTool(getActivity(), all_icome, result.getMoneyin_all(), getString(R.string.money_all), R.dimen.dp20, R.dimen.dp12, R.color.motion_red, R.color.black_overlay, "\n");
                            IncomeNetBean.ResultBean.DayChangeBean day_change = result.getDay_change();
                            IncomeNetBean.ResultBean.MonthChangeBean month_change = result.getMonth_change();
                            today_income.setText(result.getToday_total());
                            tomonth_income.setText(result.getMonth_current_total());
                            if ("1".equals(String.valueOf(day_change.getKey()))) {
                                todayChar = "%";
                            } else {
                                todayChar = "";
                            }
                            if ("1".equals(String.valueOf(month_change.getKey()))) {
                                monthChar = "%";
                            } else {
                                monthChar = "";
                            }
                            try {
                                if (day_change.getValue().contains("-")) {
                                    SystemUtil.textMagicTool(getActivity(), today_income_content, new StringBuffer().append("比昨日").append(day_change.getValue()).append(todayChar).append("↓").toString(), getString(R.string.today_income), R.dimen.dp12, R.dimen.dp12, R.color.black, R.color.black_overlay, "\n");
                                } else {
                                    SystemUtil.textMagicTool(getActivity(), today_income_content, new StringBuffer().append("比昨日+").append(day_change.getValue()).append(todayChar).append("↑").toString(), getString(R.string.today_income), R.dimen.dp12, R.dimen.dp12, R.color.blue, R.color.black_overlay, "\n");
                                }
//                            0 == Integer.valueOf(month_change.getValue()) || 0 > Integer.valueOf(month_change.getValue())
                                if (month_change.getValue().contains("-")) {
                                    SystemUtil.textMagicTool(getActivity(), tomonth_income_content, new StringBuffer().append("比上月").append(month_change.getValue()).append(monthChar).append("↓").toString(), getString(R.string.tomonth_income), R.dimen.dp12, R.dimen.dp12, R.color.black, R.color.black_overlay, "\n");
                                } else {
                                    SystemUtil.textMagicTool(getActivity(), tomonth_income_content, new StringBuffer().append("比上月+").append(month_change.getValue()).append(monthChar).append("↑").toString(), getString(R.string.tomonth_income), R.dimen.dp12, R.dimen.dp12, R.color.blue, R.color.black_overlay, "\n");
                                }
                            } catch (Exception e) {
                                LogUtil.e(TAG, "Exception : " + e.toString());
                                toastUtil.showToast("数据有异,请联系管理员!");
                            }
                        } else {
                            content_more.setTag(queryTime);
                            toastUtil.showToast(incomeNetBean.getMessage());
                        }
                        if (progressDialog != null)
                            progressDialog.dismiss();
                    }
                }));
    }

}
