package com.example.administrator.sharenebulaproject.ui.fragment.all;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.mobads.BaiduNativeAdPlacement;
import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseFragment;
import com.example.administrator.sharenebulaproject.base.EndlessRecyclerOnScrollListener;
import com.example.administrator.sharenebulaproject.global.AppKeyConfig;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.global.MyApplication;
import com.example.administrator.sharenebulaproject.model.bean.HotAllDataBean;
import com.example.administrator.sharenebulaproject.model.bean.RefreshDateBean;
import com.example.administrator.sharenebulaproject.model.bean.SelfBuiltAdvertisingBean;
import com.example.administrator.sharenebulaproject.model.db.entity.LoginUserInfo;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.activity.about.AnecdotesActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.PublicWebActivity;
import com.example.administrator.sharenebulaproject.ui.adapter.DiversifiedAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.DiversifiedRecyclerViewAboutAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.DiversifiedRecyclerViewAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.DiversifiedTopAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.TopRecyclerViewAdapter;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.AdvertisingBuilder;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.example.administrator.sharenebulaproject.widget.FullyLinearLayoutManager;
import com.example.administrator.sharenebulaproject.widget.UmShareListenerBuilder;
import com.example.administrator.sharenebulaproject.widget.ViewBuilder;
import com.example.administrator.sharenebulaproject.widget.WebViewBuilder;
import com.google.gson.Gson;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.tencent.smtt.sdk.WebView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：真理 Created by Administrator on 2018/11/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class AllTypeAboutFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, DiversifiedAdapter.onItemCheckListener, AdvertisingBuilder.InitDataListener {

    @BindView(R.id.empty_layout)
    TextView empty_layout;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

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

    private List<Object> AllSortClassList = new ArrayList<>();
    private List<RefreshDateBean> refreshStatusClassList = new ArrayList<>();

    private DiversifiedAdapter diversifiedRecyclerViewAdapter;
    private UmShareListenerBuilder umShareListenerBuilder;
    private ShowDialog instance;
    private WebViewBuilder webViewBuilder;
    private AdvertisingBuilder advertisingBuilder;

    private int pageNumberMore = 1;

    private List<NativeExpressADView> nativeExpressADViewList = new ArrayList<>();
    private int topNewsSize;

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
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        diversifiedRecyclerViewAdapter = new DiversifiedAdapter(getActivity(), AllSortClassList, nativeExpressADViewList);
        recycler_view.setAdapter(diversifiedRecyclerViewAdapter);

        umShareListenerBuilder = new UmShareListenerBuilder(getActivity(), toastUtil);
        instance = ShowDialog.getInstance();

        advertisingBuilder = new AdvertisingBuilder(getActivity());

        webViewBuilder = new WebViewBuilder(web_view, null, toastUtil, getActivity(), null);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        title = bundle != null ? bundle.getString("title") : "";
        typeId = bundle != null ? bundle.getString("typeId") : "";
        url = bundle != null ? bundle.getString("url") : "";
        ViewBuilder.ProgressStyleChange(swipe_layout);
        swipe_layout.setRefreshing(true);
        if (DataClass.CNBYLOCATION != null)
            if (!DataClass.CNBYLOCATION.equals(title)) {
                web_layout.setVisibility(View.GONE);
                initAdData();
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
        diversifiedRecyclerViewAdapter.setOnItemCheckListener(this);
        advertisingBuilder.setInitDataListener(this);
        recycler_view.addOnScrollListener(scrollListener);
    }

    @Override
    public void onRefresh() {
        pushStatus = true;
        initAdData();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onItemClick(int i) {
        if (AllSortClassList.get(i) instanceof HotAllDataBean.Result.newsBean) {
            HotAllDataBean.Result.newsBean newsBean = (HotAllDataBean.Result.newsBean) AllSortClassList.get(i);
            Intent intent = null;
            intent = new Intent(getActivity(), PublicWebActivity.class);
            switch (newsBean.getType()) {
                case 0:
                    intent.setFlags(EventCode.GENERAL_WEB);
                    intent.putExtra("value", String.valueOf(newsBean.getNewsid()));
                    intent.putExtra("shareTitle", newsBean.getTitle());
                    intent.putExtra("shareImgUrl", new StringBuffer().append(DataClass.FileUrl).append(newsBean.getListimg().split(",")[0]).toString());
                    intent.putExtra("shareNewsUrl", new StringBuffer().append(DataClass.DAILY_URL).append(newsBean.getNewsid()).append(DataClass.USERID_SHARE).toString());
                    intent.putExtra("total", String.valueOf(newsBean.getStarbean()));
                    intent.putExtra("ifcanmoney", String.valueOf(newsBean.getIfcanmoney()));
                    break;
                case 1:
                    intent.setFlags(EventCode.ADVERTISING);
                    intent.putExtra("value", String.valueOf(newsBean.getNewsid()));
                    break;
            }
            startActivity(intent);
        }
    }

    @Override
    public void onRefreshItemClick() {
        swipe_layout.setRefreshing(true);
        pushStatus = true;
        initAdData();
    }

    private RecyclerView.OnScrollListener scrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadMore() {
            if (diversifiedRecyclerViewAdapter != null) {
                diversifiedRecyclerViewAdapter.setLoadState(diversifiedRecyclerViewAdapter.LOADING);
                if (AllSortClassList.size() > DataClass.DefaultInformationFlow) {
                    pageNumberMore = pageNumberMore + 1;
                    initAdData();
                } else {
                    // 显示加载到底的提示
                    diversifiedRecyclerViewAdapter.setLoadState(diversifiedRecyclerViewAdapter.LOADING_END);
                }
                if (newSize == 0) {
                    diversifiedRecyclerViewAdapter.setLoadState(diversifiedRecyclerViewAdapter.LOADING_END);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView) {
        }
    };

    //用户状态
    private void clearUserInfo(int status) {
        //需要清空本地登陆信息，以游客方式登陆
        DataClass.USERID = "";
        DataClass.SELECT = new StringBuffer().append(DataClass.URL_).append("pf_wx.php?act=oneday&do=display&userid_share=").toString();
        if (DataClass.LOGINTYPE != 0)
            umShareListenerBuilder.deleteOauth(DataClass.LOGINTYPE);
        dataManager.deleteLoginUserInfo("admin");
        RxBus.getDefault().post(new CommonEvent(EventCode.LOGIN_OUT));
        switch (status) {
            case 0:
                instance.showLoginStatusPromptDialog(getActivity(), getString(R.string.illegal_user));
                break;
            case 2:
                instance.showLoginStatusPromptDialog(getActivity(), getString(R.string.other_equipment_login));
                break;
        }
    }

    private void refreshView() {
        diversifiedRecyclerViewAdapter.refreshViewStatus(newSize, new Date().getTime());
        recycler_view.scrollToPosition(0);
    }

    //先拉取广告，保证同步加载
    private void initAdData() {
        advertisingBuilder.initNativeExpressAD(pageNumber);
    }

    //广告加载完成再加载数据
    @Override
    public void onInitDataListener(List<NativeExpressADView> list) {
        nativeExpressADViewList.addAll(list);
        initNetDataWork();
    }

    //删除广告预置位
    @Override
    public void onRemoverView(NativeExpressADView adView) {
        diversifiedRecyclerViewAdapter.removeADView(adView);
    }

    //获取首页数据
    private void initNetDataWork() {
        String token = "";
        if (pushStatus) {
            pageNumber = 1;
        } else {
            pageNumber = pageNumberMore;
        }
        LoginUserInfo loginUserInfo = dataManager.queryLoginUserInfo("admin");
        if (loginUserInfo != null) {
            token = loginUserInfo.getToken();
        }
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
        linkedHashMap.put("token", token);
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
                            if (result.getAllowlogin() != 1) {
                                clearUserInfo(result.getAllowlogin());
                            }

                            if (topNewsSize > 0 && pageNumber == 1) {
                                for (int i = 0; i < topNewsSize; i++) {
                                    diversifiedRecyclerViewAdapter.deletData(0);
                                }
                            }

                            newSize = result.getNews().size();

                            if (result.getTopnews() != null) {
                                topNewsSize = result.getTopnews().size();
                                diversifiedRecyclerViewAdapter.refreshTopSize(topNewsSize);
                            }

                            if (pushStatus) {
                                RxBus.getDefault().post(new CommonEvent(EventCode.REFRESH_NEWS, newSize));
                                pushStatus = false;
                            }

                            if (pageNumber == 1) {
                                if (newSize == 0) {
                                    empty_layout.setVisibility(View.VISIBLE);
                                } else {
                                    empty_layout.setVisibility(View.GONE);

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
                                                refreshStatusClassList.add(0, refreshDateBean);
                                                diversifiedRecyclerViewAdapter.addData(i, refreshDateBean);
                                            }
                                        } else {
                                            diversifiedRecyclerViewAdapter.addData(i, result.getNews().get(i));
                                        }
                                    }
                                    for (int i = 0; i < topNewsSize; i++) {
                                        HotAllDataBean.Result.topNews topNews = result.getTopnews().get(i);
                                        HotAllDataBean.Result.newsBean newsBean = new HotAllDataBean.Result.newsBean(
                                                topNews.getAmount_read(),
                                                topNews.getAmount_share(),
                                                topNews.getCreatedate(),
                                                topNews.getIfcanmoney(),
                                                topNews.getListimg(),
                                                topNews.getNewsid(),
                                                topNews.getNewstype(),
                                                "",
                                                topNews.getStarbean(),
                                                topNews.getTitle(),
                                                topNews.getViewtype(),
                                                0,
                                                "top",
                                                topNews.getSource());
                                        diversifiedRecyclerViewAdapter.addData(i, newsBean);
                                    }
                                    //整体刷新
                                    refreshView();
                                }
                            } else {
                                //添加下拉加载更多的数据
                                int size = AllSortClassList.size();
                                for (int i = 0; i < newSize; i++) {
                                    HotAllDataBean.Result.newsBean newsBean = result.getNews().get(i);
                                    diversifiedRecyclerViewAdapter.addData(i + size, newsBean);
                                }
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
