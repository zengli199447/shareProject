package com.example.administrator.sharenebulaproject.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseFragment;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.HotAllDataBean;
import com.example.administrator.sharenebulaproject.model.bean.TheNewTypeNetBean;
import com.example.administrator.sharenebulaproject.model.bean.TitleNewsBean;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadStatusNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UpLoadUserInfoNetBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.activity.LoginActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.PublicWebActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.SearchActivity;
import com.example.administrator.sharenebulaproject.ui.adapter.AllTypeAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.MyPagerAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.NewsContentAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.TabAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.TabPageIndicatorAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.TitleNewsTypeAdapter;
import com.example.administrator.sharenebulaproject.ui.fragment.all.AllTypeAboutFragment;
import com.example.administrator.sharenebulaproject.ui.fragment.all.AllTypeFragment;
import com.example.administrator.sharenebulaproject.utils.AESCryptUtil;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.example.administrator.sharenebulaproject.widget.FullyGridLayoutManager;
import com.example.administrator.sharenebulaproject.widget.MyItemTouchCallBack;
import com.example.administrator.sharenebulaproject.widget.ViewBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/11/26.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class ToDayPageFragment extends BaseFragment implements View.OnClickListener, TabLayout.OnTabSelectedListener, NewsContentAdapter.CheckClickListener, AllTypeAdapter.AddCheckClickListener {

    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.layout_news_channel)
    LinearLayout layout_news_channel;
    @BindView(R.id.my_chaanel)
    TextView my_chaanel;
    @BindView(R.id.the_editor)
    TextView the_editor;
    @BindView(R.id.recycler_view_my_chaanel)
    RecyclerView recycler_view_my_chaanel;

    @BindView(R.id.recommended_chaanel)
    TextView recommended_chaanel;
    @BindView(R.id.recycler_view_recommended_chaanel)
    RecyclerView recycler_view_recommended_chaanel;

    @BindView(R.id.search_view)
    TextView search_view;
    @BindView(R.id.new_index)
    TextView new_index;


    private List<Fragment> mFragments = new ArrayList<>();
    private TabPageIndicatorAdapter tabPageIndicatorAdapter;
    private List<String> title = new ArrayList<>();
    private List<TitleNewsBean> titleNews = new ArrayList<>();
    private List<TitleNewsBean> titleAboutNews = new ArrayList<>();
    private NewsContentAdapter newsContentAdapter;
    private boolean TheEditorModle = true;
    private AllTypeAdapter allTypeAdapter;
    private Animation animationRefreshIn;
    private Animation animationRefreshOut;
    private Animation animationRefreshInBottom;
    private Animation animationRefreshOutBottom;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    new_index.setAnimation(animationRefreshOut);
                    animationRefreshOut.start();
                    new_index.setVisibility(View.GONE);
                    break;
            }
        }
    };

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.REFRESH_NEWS:
                new_index.setVisibility(View.VISIBLE);
                new_index.setAnimation(animationRefreshIn);
                animationRefreshIn.start();
                if (commonevent.getTemp_value() > 0) {
                    new_index.setText(new StringBuffer().append("已更新").append(commonevent.getTemp_value()).append("条内容").toString());
                } else {
                    new_index.setText(getString(R.string.current_not_refresh));
                }
                handler.sendEmptyMessageDelayed(0, getResources().getInteger(R.integer.refresh_prompt_simulation));
                break;
            case EventCode.LOGIN_OUT:
            case EventCode.USERID_REFRESH:
                TheNewsTypeNetWork();
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_today_page;
    }

    @Override
    protected void initClass() {
        animationRefreshIn = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_alpha_and_in);
        animationRefreshOut = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_alpha_and_out);

        animationRefreshInBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_alpha_and_in_bottom);
        animationRefreshOutBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_alpha_and_out_bottom);
    }

    @Override
    protected void initData() {
        LogUtil.e(TAG, "DataClass.TYPE_TITLE.size() : " + DataClass.TYPE_TITLE.size());
        for (int i = 0; i < DataClass.TYPE_TITLE.size(); i++) {
            Object object = DataClass.TYPE_TITLE.get(i);
            packagingData(object, i);
        }
        filterData();
    }

    //包装数据
    private void packagingData(Object object, int i) {
        String catename = "";
        int cateid = 0;
        String url = "";
        if (object instanceof TheNewTypeNetBean.ResultBean.AllBean) {
            TheNewTypeNetBean.ResultBean.AllBean allBean = (TheNewTypeNetBean.ResultBean.AllBean) object;
            catename = allBean.getCatename();
            cateid = allBean.getCateid();
            url = allBean.getUrl();
        } else if (object instanceof TheNewTypeNetBean.ResultBean.SelectBean) {
            TheNewTypeNetBean.ResultBean.SelectBean selectBean = (TheNewTypeNetBean.ResultBean.SelectBean) object;
            catename = selectBean.getCatename();
            cateid = selectBean.getCatid();
            url = selectBean.getUrl();
        }
        title.add(catename);
        AllTypeAboutFragment allTypeFragment = new AllTypeAboutFragment();
        Bundle data = new Bundle();
        data.putString("title", catename);
        data.putString("typeId", String.valueOf(cateid));
        data.putString("url", url);
        allTypeFragment.setArguments(data);
        mFragments.add(allTypeFragment);
        titleNews.add(new TitleNewsBean(catename, cateid, i == 0 ? true : false));

        for (int j = 0; j < DataClass.ALL_TYPE_TITLE.size(); j++) {
            if (DataClass.ALL_TYPE_TITLE.get(j).getCatename() != null)
                if (DataClass.ALL_TYPE_TITLE.get(j).getCatename().equals(catename)) {
                    DataClass.ALL_TYPE_TITLE.remove(j);
                }
        }
    }

    //过滤未选择类型
    private void filterData() {
        for (int j = 0; j < DataClass.ALL_TYPE_TITLE.size(); j++) {
            titleAboutNews.add(new TitleNewsBean(DataClass.ALL_TYPE_TITLE.get(j).getCatename(), DataClass.ALL_TYPE_TITLE.get(j).getCateid(), false));
        }
    }

    @Override
    protected void initView() {
        tabPageIndicatorAdapter = new TabPageIndicatorAdapter(getChildFragmentManager(), title, (ArrayList<Fragment>) mFragments);
        viewPager.setAdapter(tabPageIndicatorAdapter);
        tab_layout.setupWithViewPager(viewPager);
        tab_layout.setOnTabSelectedListener(this);

        MeasurementIndicator();

        FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(getActivity(), 4);
        fullyGridLayoutManager.setScrollEnable(false);
        recycler_view_my_chaanel.setLayoutManager(fullyGridLayoutManager);
        newsContentAdapter = new NewsContentAdapter(getActivity(), titleNews, recycler_view_my_chaanel);
        recycler_view_my_chaanel.setAdapter(newsContentAdapter);
        newsContentAdapter.setCheckClickListener(this);

        FullyGridLayoutManager fullyAllGridLayoutManager = new FullyGridLayoutManager(getActivity(), 4);
        fullyAllGridLayoutManager.setScrollEnable(false);
        recycler_view_recommended_chaanel.setLayoutManager(fullyAllGridLayoutManager);
        allTypeAdapter = new AllTypeAdapter(getActivity(), titleAboutNews);
        recycler_view_recommended_chaanel.setAdapter(allTypeAdapter);
        allTypeAdapter.setAddCheckClickListener(this);

        chaanelViewStatus(getString(R.string.enter_the_channel));
    }

    //频道提示
    private void chaanelViewStatus(String content) {
        SystemUtil.textMagicTool(getActivity(), my_chaanel, getString(R.string.my_channel), content, R.dimen.dp16, R.dimen.dp12, R.color.black, R.color.gray_light_text, "   ");
        SystemUtil.textMagicTool(getActivity(), recommended_chaanel, getString(R.string.recommended_chaanel), getString(R.string.enter_add_channel), R.dimen.dp16, R.dimen.dp12, R.color.black, R.color.gray_light_text, "   ");
    }

    //指示器宽度
    private void MeasurementIndicator() {
        tab_layout.post(new Runnable() {
            @Override
            public void run() {
                ViewBuilder.setIndicator(tab_layout, getResources().getInteger(R.integer.magin), getResources().getInteger(R.integer.magin));
            }
        });
        tabPageIndicatorAdapter.notifyDataSetChanged();
    }

    //刷新view
    public void refreshView() {
        title.clear();
        mFragments.clear();
        titleNews.clear();
        titleAboutNews.clear();
        initData();
        initView();
    }

    @Override
    protected void initListener() {

    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.about, R.id.delet, R.id.the_editor, R.id.placeholder, R.id.awards_show, R.id.search_view})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about:
                if (DataClass.USERID.isEmpty()) {
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    layout_news_channel.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.delet:
                layout_news_channel.setVisibility(View.GONE);
                break;
            case R.id.the_editor:
                if (!newsContentAdapter.refreshTheEditorModle(the_editor)) {
                    chaanelViewStatus(getString(R.string.enter_the_channel));
                    if (!newsContentAdapter.getChangeTheSorting().isEmpty()) {
                        TheSortingNetWork(newsContentAdapter.getChangeTheSorting());
                    } else if (titleNews.size() != DataClass.TYPE_TITLE.size()) {
                        newsContentAdapter.emptyChangeTheSorting();
                        TheSortingNetWork(newsContentAdapter.JoiningTogetherData(titleNews));
                    }
                } else {
                    chaanelViewStatus(getString(R.string.enter_the_the_sorting));
                }
                newsContentAdapter.emptyChangeTheSorting();
                break;
            case R.id.placeholder:
                layout_news_channel.setVisibility(View.GONE);
                break;
            case R.id.awards_show:
                Intent awardsShow = new Intent(getActivity(), PublicWebActivity.class);
                awardsShow.setFlags(EventCode.TEXT_WEB);
                awardsShow.putExtra("value", String.valueOf(DataClass.AWARDS_SHOW));
                startActivity(awardsShow);
                break;
            case R.id.search_view:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        for (int i = 0; i < titleNews.size(); i++) {
            if (i == tab.getPosition()) {
                titleNews.get(i).setStatus(true);
            } else {
                titleNews.get(i).setStatus(false);
            }
        }
        if (newsContentAdapter != null)
            newsContentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void TypeClickListener(int positon) {
        viewPager.setCurrentItem(positon);
        layout_news_channel.setVisibility(View.GONE);
        toastUtil.showToast(titleNews.get(positon).getContent());
    }

    @Override
    public void DeletClickListener(int position) {
        TitleNewsBean titleNewsBean = titleNews.get(position);
        titleAboutNews.add(new TitleNewsBean(titleNewsBean.getContent(), titleNewsBean.getId(), false));
        titleNews.remove(position);
        newsContentAdapter.notifyDataSetChanged();
        allTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddCheckClickListener(int position) {
        TitleNewsBean titleNewsBean = titleAboutNews.get(position);
        titleNews.add(new TitleNewsBean(titleNewsBean.getContent(), titleNewsBean.getId(), false));
        titleAboutNews.remove(position);
        allTypeAdapter.notifyDataSetChanged();
        newsContentAdapter.notifyDataSetChanged();
        newsContentAdapter.emptyChangeTheSorting();
        TheSortingNetWork(newsContentAdapter.JoiningTogetherData(titleNews));
        newsContentAdapter.confirmTheEditorModle(the_editor);
    }

    //新闻类型获取
    private void TheNewsTypeNetWork() {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.CATE_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getTheNewTypeNetBean(map)
                .compose(RxUtil.<TheNewTypeNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<TheNewTypeNetBean>(toastUtil) {
                    @Override
                    public void onNext(TheNewTypeNetBean theNewTypeNetBean) {
                        if (theNewTypeNetBean.getStatus() == 1) {
                            List<TheNewTypeNetBean.ResultBean.AllBean> all = theNewTypeNetBean.getResult().getAll();
                            List<TheNewTypeNetBean.ResultBean.SelectBean> select = theNewTypeNetBean.getResult().getSelect();
                            DataClass.TYPE_TITLE.clear();
                            DataClass.ALL_TYPE_TITLE.clear();

                            for (int i = 0; i < all.size(); i++) {
                                TheNewTypeNetBean.ResultBean.AllBean allBean = all.get(i);
                                if (getString(R.string.local).equals(allBean.getCatename())) {
                                    allBean.setCatename(DataClass.CNBYLOCATION);
                                }
                            }

                            for (int i = 0; i < select.size(); i++) {
                                TheNewTypeNetBean.ResultBean.SelectBean selectBean = select.get(i);
                                if (getString(R.string.local).equals(selectBean.getCatename())) {
                                    selectBean.setCatename(DataClass.CNBYLOCATION);
                                }
                            }

                            DataClass.ALL_TYPE_TITLE.addAll(all);
                            if (theNewTypeNetBean.getResult().getSelect().size() == 0) {
                                DataClass.TYPE_TITLE.addAll(all);
                            } else {
                                DataClass.TYPE_TITLE.addAll(select);
                            }

                            refreshView();
                        } else {
                            toastUtil.showToast(theNewTypeNetBean.getMessage());
                        }
                    }
                }));
    }

    //顺序排列
    private void TheSortingNetWork(String content) {
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.CATE_SORT_SET);
        linkedHashMap.put("userid", DataClass.USERID);
        linkedHashMap.put("sortlist", content);
        String toJson =  AESCryptUtil.encrypt(new Gson().toJson(linkedHashMap));
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.UpLoadStatus(map)
                .compose(RxUtil.<UpLoadStatusNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<UpLoadStatusNetBean>(toastUtil) {
                    @Override
                    public void onNext(UpLoadStatusNetBean upLoadStatusNetBean) {
                        if (upLoadStatusNetBean.getStatus() == 1) {
                            TheNewsTypeNetWork();
                        } else {
                            toastUtil.showToast(upLoadStatusNetBean.getMessage());
                        }
                    }
                }));
    }

}
