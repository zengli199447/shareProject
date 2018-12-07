package com.example.administrator.sharenebulaproject.ui.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.TheNewTypeNetBean;
import com.example.administrator.sharenebulaproject.model.db.entity.AppDBInfo;
import com.example.administrator.sharenebulaproject.model.db.entity.LoginUserInfo;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.view.ImageSlideshow;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/11.
 */

public class WelComeActivity extends BaseActivity implements View.OnClickListener, ImageSlideshow.OnItemClickListener {

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    private DataClass dataClass;
    private ArrayList<Integer> bannerList;
    private ArrayList<ImageView> views = new ArrayList<>();
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void registerEvent(CommonEvent commonevent) {
        switch (commonevent.getCode()) {
            case EventCode.OVERINSERT_NEW_TASK:
                startActivity(new Intent(WelComeActivity.this, HomeActivity.class));
                finish();
                break;
        }

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initClass() {
        dataClass = new DataClass(dataManager);
        if (dataManager.queryLoginUserInfo("admin") != null) {
            LoginUserInfo admin = dataManager.queryLoginUserInfo("admin");
            DataClass.USERID = admin.getUserid();
            DataClass.USERID_SHARE = "&userid_share=" + admin.getUserid();
            DataClass.SELECT = DataClass.SELECT + admin.getUserid();
        }
        if (dataManager.loadAppDBInfoDao().size() > 0) {
            startActivity(new Intent(WelComeActivity.this, AdvertisingActivity.class));
            finish();
        } else {
            TheNewsTypeNetWork();
        }
    }

    @Override
    protected void initData() {
        bannerList = dataClass.getWelcomeBannerList();
        for (int i = 0; i < bannerList.size(); i++) {
            views.add(new ImageView(this));
            Glide.with(WelComeActivity.this).load(bannerList.get(i)).asBitmap().placeholder(R.drawable.banner_off).into(views.get(i));
        }
        myPagerAdapter = new MyPagerAdapter(views);
    }

    @Override
    protected void initView() {
        SystemUtil.hideBottomUIMenu(this);
        view_pager.setAdapter(myPagerAdapter);
    }

    @Override
    protected void initListener() {
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                views.get(position).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (position == bannerList.size() - 1)
                            startActivity(new Intent(WelComeActivity.this, HomeActivity.class));
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position == bannerList.size() - 1) {
            startActivity(new Intent(WelComeActivity.this, HomeActivity.class));
            finish();
        }
    }


    public class MyPagerAdapter extends PagerAdapter {
        private List<ImageView> views;

        public MyPagerAdapter(List<ImageView> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = views.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "标题" + position;
        }
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
                            DataClass.ALL_TYPE_TITLE.addAll(all);
                            if (select.size() == 0) {
                                DataClass.TYPE_TITLE.addAll(all);
                            } else {
                                DataClass.TYPE_TITLE.addAll(select);
                            }
                        } else {
                            toastUtil.showToast(theNewTypeNetBean.getMessage());
                        }
                    }
                }));
    }

}
