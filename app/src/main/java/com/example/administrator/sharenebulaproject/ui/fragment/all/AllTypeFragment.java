package com.example.administrator.sharenebulaproject.ui.fragment.all;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.mobads.BaiduNativeAdPlacement;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseFragment;
import com.example.administrator.sharenebulaproject.base.EndlessRecyclerOnScrollListener;
import com.example.administrator.sharenebulaproject.global.AppKeyConfig;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.global.MyApplication;
import com.example.administrator.sharenebulaproject.model.bean.HotAllDataBean;
import com.example.administrator.sharenebulaproject.model.bean.RefreshDateBean;
import com.example.administrator.sharenebulaproject.model.bean.SelfBuiltAdvertisingBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.activity.about.AnecdotesActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.PublicWebActivity;
import com.example.administrator.sharenebulaproject.ui.adapter.DiversifiedRecyclerViewAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.HotRecyclerViewAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.TopRecyclerViewAdapter;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.utils.LocationUtils;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.example.administrator.sharenebulaproject.widget.FullyLinearLayoutManager;
import com.example.administrator.sharenebulaproject.widget.UmShareListenerBuilder;
import com.example.administrator.sharenebulaproject.widget.ViewBuilder;
import com.example.administrator.sharenebulaproject.widget.WebViewBuilder;
import com.google.gson.Gson;
import com.tencent.smtt.sdk.WebView;
import com.umeng.commonsdk.debug.E;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * 作者：真理 Created by Administrator on 2018/11/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class AllTypeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, DiversifiedRecyclerViewAdapter.onItemCheckListener, NestedScrollView.OnScrollChangeListener, TopRecyclerViewAdapter.onItemCheckListener {

    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.top_recycler_view)
    RecyclerView top_recycler_view;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.progress_bar)
    RelativeLayout progress_bar;
    @BindView(R.id.footer_layout)
    RelativeLayout footer_layout;

    @BindView(R.id.placeholder)
    View placeholder;
    @BindView(R.id.web_layout)
    FrameLayout web_layout;
    @BindView(R.id.web_view)
    WebView web_view;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    private String title;
    private String typeId;
    private String url;
    int pageNumber = 1;
    int newSize;
    boolean pushStatus;
    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;

    private List<Object> AllSortClassList = new ArrayList<>();
    private List<HotAllDataBean.Result.topNews> TopSortClassList = new ArrayList<>();
    private List<RefreshDateBean> refreshStatusClassList = new ArrayList<>();
    private DiversifiedRecyclerViewAdapter diversifiedRecyclerViewAdapter;
    private UmShareListenerBuilder umShareListenerBuilder;
    private ShowDialog instance;
    private TopRecyclerViewAdapter topRecyclerViewAdapter;
    private WebViewBuilder webViewBuilder;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_type;
    }

    @Override
    protected void initClass() {
        recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(getActivity()));
        top_recycler_view.setLayoutManager(ViewBuilder.getFullyLinearLayoutManager(getActivity()));
        diversifiedRecyclerViewAdapter = new DiversifiedRecyclerViewAdapter(getActivity(), AllSortClassList);
        topRecyclerViewAdapter = new TopRecyclerViewAdapter(getActivity(), TopSortClassList);

        recycler_view.setAdapter(diversifiedRecyclerViewAdapter);
        top_recycler_view.setAdapter(topRecyclerViewAdapter);

        umShareListenerBuilder = new UmShareListenerBuilder(getActivity(), toastUtil);
        instance = ShowDialog.getInstance();

        webViewBuilder = new WebViewBuilder(web_view, null, toastUtil, getActivity(), null);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        title = bundle != null ? bundle.getString("title") : "";
        typeId = bundle != null ? bundle.getString("typeId") : "";
        url = bundle != null ? bundle.getString("url") : "";
        toastUtil.showToast("title : " + title + "  -  " + "typeId : " + typeId);
        ViewBuilder.ProgressStyleChange(swipe_layout);
        swipe_layout.setRefreshing(true);
        if (!DataClass.CNBYLOCATION.equals(title)) {
            web_layout.setVisibility(View.GONE);
            initNetDataWork();
        } else {
            webViewBuilder.initWebView();
            web_layout.setVisibility(View.VISIBLE);
            webViewBuilder.loadWebView(url, true);
        }
    }

    @Override
    protected void initView() {
        empty_layout.setText(title);
    }

    @Override
    protected void initListener() {
        swipe_layout.setOnRefreshListener(this);
        scrollView.setOnScrollChangeListener(this);
        diversifiedRecyclerViewAdapter.setOnItemCheckListener(this);
        topRecyclerViewAdapter.setOnItemCheckListener(this);
    }

    @Override
    public void onRefresh() {
        pageNumber = 1;
        pushStatus = true;
        initNetDataWork();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onItemClick(int i) {
        if (AllSortClassList.get(i) instanceof HotAllDataBean.Result.newsBean) {
            HotAllDataBean.Result.newsBean newsBean = (HotAllDataBean.Result.newsBean) AllSortClassList.get(i);
            Intent dailyIntent = new Intent(getActivity(), AnecdotesActivity.class);
            dailyIntent.setFlags(EventCode.GENERAL_WEB);
            dailyIntent.putExtra("value", String.valueOf(newsBean.getNewsid()));
            dailyIntent.putExtra("shareTitle", newsBean.getTitle());
            dailyIntent.putExtra("shareImgUrl", new StringBuffer().append(DataClass.FileUrl).append(newsBean.getListimg().split(",")[0]).toString());
            dailyIntent.putExtra("shareNewsUrl", new StringBuffer().append(DataClass.DAILY_URL).append(newsBean.getNewsid()).append(DataClass.USERID_SHARE).toString());
            dailyIntent.putExtra("total", String.valueOf(newsBean.getStarbean()));
            dailyIntent.putExtra("ifcanmoney", String.valueOf(newsBean.getIfcanmoney()));
            startActivity(dailyIntent);
        } else if (AllSortClassList.get(i) instanceof SelfBuiltAdvertisingBean) {
            SelfBuiltAdvertisingBean selfBuiltAdvertisingBean = (SelfBuiltAdvertisingBean) AllSortClassList.get(i);
            Intent dailyIntent = new Intent(getActivity(), PublicWebActivity.class);
            dailyIntent.setFlags(EventCode.GENERAL_WEB);
            dailyIntent.putExtra("value", String.valueOf(selfBuiltAdvertisingBean.getId()));
            dailyIntent.putExtra("shareNewsUrl", new StringBuffer().append(DataClass.DAILY_URL).append(selfBuiltAdvertisingBean.getId()).append(DataClass.USERID_SHARE).toString());
            startActivity(dailyIntent);
        }

    }

    @Override
    public void onRefreshItemClick() {
        pageNumber = 1;
        swipe_layout.setRefreshing(true);
        initNetDataWork();
        scrollView.scrollTo(0, 0);
        scrollView.fullScroll(ScrollView.FOCUS_UP);//滑到顶部
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
            pageNumber = pageNumber + 1;
            initNetDataWork();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            LogUtil.e(TAG, "title :" + title);
            LogUtil.e(TAG, "typeId :" + typeId);
        }
    }

    private void clearUserInfo() {
        //需要清空本地登陆信息，以游客方式登陆
        DataClass.USERID = "";
        DataClass.SELECT = new StringBuffer().append(DataClass.URL_).append("pf_wx.php?act=oneday&do=display&userid_share=").toString();
        if (DataClass.LOGINTYPE != 0)
            umShareListenerBuilder.deleteOauth(DataClass.LOGINTYPE);
        dataManager.deleteLoginUserInfo("admin");
        RxBus.getDefault().post(new CommonEvent(EventCode.LOGIN_OUT));
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    refreshView();
                    break;
            }
        }
    };

    /**
     * 数据包装(广告分类)
     *
     * @param position 插入角标
     * @param newsBean 源数据
     * @param status   是否选定插入角标
     */
    private void ItemDataTypeRefresh(int position, HotAllDataBean.Result.newsBean newsBean, boolean status) {
        switch (newsBean.getType()) {
            case 0:
                if (status) {
                    AllSortClassList.add(position, newsBean);
                } else {
                    AllSortClassList.add(newsBean);
                }
                break;
            case 1:
                if (status) {
                    AllSortClassList.add(position, new SelfBuiltAdvertisingBean(newsBean.getListimg(), newsBean.getContent(), newsBean.getTitle()));
                } else {
                    AllSortClassList.add(new SelfBuiltAdvertisingBean(newsBean.getListimg(), newsBean.getContent(), newsBean.getTitle()));
                }
                break;
            case 2:
                //添加上拉刷新时间、添加刷新数据
                BaiduNativeAdPlacement placement = new BaiduNativeAdPlacement();
                placement.setSessionId(1); // 设置页面id，不同listview不同，从1开始的正整数，可选
                placement.setPositionId(1); // 设置广告在页面的楼层，从1开始的正整数，可选
                placement.setApId(AppKeyConfig.ADVERTISING_BAIDU_ADVERTISING_RECYCLER_ID);
                if (status) {
                    AllSortClassList.add(position, placement);
                } else {
                    AllSortClassList.add(placement);
                }
                break;
            case 3:

                break;
            case 4:

                break;
        }
    }

    private void refreshView() {
        diversifiedRecyclerViewAdapter.refreshViewStatus(newSize, new Date().getTime());
        topRecyclerViewAdapter.notifyDataSetChanged();
        diversifiedRecyclerViewAdapter.notifyDataSetChanged();
        if (AllSortClassList.size() == 0) {
            footer_layout.setVisibility(View.GONE);
        } else {
            footer_layout.setVisibility(View.VISIBLE);
        }
        if (newSize == 0) {
            progress_bar.setVisibility(View.GONE);
        } else {
            progress_bar.setVisibility(View.VISIBLE);
        }
    }

    //获取首页数据
    private void initNetDataWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.HOMEPAGE_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("versionvalue", SystemUtil.packageCode(getActivity()));
        linkedHashMap.put("systemtype", "1");
        linkedHashMap.put("pagenum", pageNumber);
        linkedHashMap.put("ordertype", 1);
        linkedHashMap.put("city", DataClass.CNBYLOCATION);
        linkedHashMap.put("catid", typeId);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.HotPageData(map)
                .compose(RxUtil.<HotAllDataBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<HotAllDataBean>(toastUtil) {
                    @Override
                    public void onNext(HotAllDataBean hotAllDataBean) {
                        swipe_layout.setRefreshing(false);
                        if (hotAllDataBean.getStatus() == 1) {
                            final HotAllDataBean.Result result = hotAllDataBean.getResult();
                            if (result.getAllowlogin() == 0) {
                                clearUserInfo();
                            }
                            newSize = result.getNews().size();

                            if (pushStatus) {
                                RxBus.getDefault().post(new CommonEvent(EventCode.REFRESH_NEWS, newSize));
                                pushStatus = false;
                            }

                            if (pageNumber == 1) {
                                if (newSize == 0) {
                                    empty_layout.setVisibility(View.VISIBLE);
                                } else {
                                    empty_layout.setVisibility(View.GONE);

                                    //置顶数据刷新
                                    TopSortClassList.clear();
                                    TopSortClassList.addAll(result.getTopnews());

                                    MyApplication.executorService.submit(new Runnable() {
                                        @Override
                                        public void run() {
                                            //上拉刷新位置状态变化
                                            if (refreshStatusClassList.size() > 0) {
                                                for (int i = 0; i < refreshStatusClassList.size(); i++) {
                                                    refreshStatusClassList.get(i).setStatus(false);
                                                }
                                            }
                                            for (int i = 0; i < newSize + 1; i++) {
                                                if (i == newSize) {
                                                    if (AllSortClassList.size() > newSize) {
                                                        RefreshDateBean refreshDateBean = new RefreshDateBean(true, "");
                                                        AllSortClassList.add(i, refreshDateBean);
                                                        refreshStatusClassList.add(0, refreshDateBean);
                                                    }
                                                } else {
                                                    ItemDataTypeRefresh(i, result.getNews().get(i), true);
                                                }
                                            }
                                            //整体刷新
                                            handler.sendEmptyMessage(0);
                                        }
                                    });
                                }
                            } else {
                                MyApplication.executorService.submit(new Runnable() {
                                    @Override
                                    public void run() {
                                        //添加下拉加载更多的数据
                                        for (int i = 0; i < newSize; i++) {
                                            ItemDataTypeRefresh(i, result.getNews().get(i), false);
                                        }
                                        //整体刷新
                                        handler.sendEmptyMessage(0);
                                    }
                                });
                            }
                            // 应用更新状态
                            if (!result.getNewversion().isEmpty()) {
                                DataClass.APK_URL = result.getRootPath();
                                if ("0".equals(result.getIfmust())) {
                                    instance.showPromptDialog(getActivity(), result.getNewversion(), false);
                                } else {
                                    instance.showPromptDialog(getActivity(), result.getNewversion(), true);
                                }
                            }
                        } else {
                            toastUtil.showToast(hotAllDataBean.getMessage());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Throwable : " + e.toString());
                        swipe_layout.setRefreshing(false);
                        super.onError(e);
                    }
                }));
    }

}
