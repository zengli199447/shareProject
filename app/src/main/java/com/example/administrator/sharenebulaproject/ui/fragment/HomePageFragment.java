package com.example.administrator.sharenebulaproject.ui.fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseFragment;
import com.example.administrator.sharenebulaproject.base.BaseLifecycleObserver;
import com.example.administrator.sharenebulaproject.model.bean.HotAllDataBean;
import com.example.administrator.sharenebulaproject.model.bean.TitleNewsBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.ui.adapter.MyPagerAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.NewsContentAdapter;
import com.example.administrator.sharenebulaproject.ui.adapter.TitleNewsTypeAdapter;
import com.example.administrator.sharenebulaproject.ui.contorller.ContorllerHome;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：真理 Created by Administrator on 2018/11/26.
 * 邮箱：229017464@qq.com
 * remark: 需求变动，当前页面不使用
 */
public class HomePageFragment extends BaseFragment implements View.OnClickListener, TitleNewsTypeAdapter.NewsTitleClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    List<TitleNewsBean> titleList = new ArrayList<>();
    private TitleNewsTypeAdapter titleNewsTypeAdapter;
    private ArrayList<RecyclerView> views = new ArrayList<>();
    private List<HotAllDataBean.Result.newsBean> newsBeans = new ArrayList<>();
    private ArrayList<NewsContentAdapter> adapterArrayList = new ArrayList<>();

    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_page;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {
        titleList.add(new TitleNewsBean("推荐", 0, true));
        titleList.add(new TitleNewsBean("热点", 0, false));
        titleList.add(new TitleNewsBean("武汉", 0, false));
        titleList.add(new TitleNewsBean("推荐", 0, false));
        titleList.add(new TitleNewsBean("体育", 0, false));
        titleList.add(new TitleNewsBean("军事", 0, false));
        titleList.add(new TitleNewsBean("娱乐", 0, false));
        titleList.add(new TitleNewsBean("海外", 0, false));
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        titleNewsTypeAdapter = new TitleNewsTypeAdapter(getActivity(), titleList);
        recyclerView.setAdapter(titleNewsTypeAdapter);
        ViewPageRefresh();
    }

    //
    private void ViewPageRefresh() {
        for (int i = 0; i < titleList.size(); i++) {
            RecyclerView recyclerView = new RecyclerView(getActivity());
            AdapterSetting(recyclerView);
            views.add(recyclerView);
        }
        myPagerAdapter = new MyPagerAdapter(views);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                onNewsTitleClickListener(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //动态刷新
    private void refreshData() {
        views.clear();
        titleList.clear();
//        views.addAll(null);
//        titleList.addAll(null);
        titleNewsTypeAdapter.notifyDataSetChanged();
        myPagerAdapter.notifyDataSetChanged();
    }

    //新闻Adapter
    private void AdapterSetting(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        NewsContentAdapter newsContentAdapter = new NewsContentAdapter(getActivity(), null,null);
        recyclerView.setAdapter(newsContentAdapter);
        adapterArrayList.add(newsContentAdapter);
    }

    //刷新
    private void refreshView(int index) {
        titleNewsTypeAdapter.notifyDataSetChanged();
        smoothMoveToPosition(recyclerView, index);
    }

    @Override
    protected void initListener() {
        titleNewsTypeAdapter.onNewsTitleClickListener(this);
    }

    @OnClick({R.id.about})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about:
                refreshData();
                break;
        }
    }

    @Override
    public void onNewsTitleClickListener(int position) {
        toastUtil.showToast("position : " + position);
        for (int i = 0; i < titleList.size(); i++) {
            if (i == position) {
                titleList.get(i).setStatus(true);
            } else {
                titleList.get(i).setStatus(false);
            }
        }
        refreshView(position);
        viewPager.setCurrentItem(position);
    }

    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }

}
