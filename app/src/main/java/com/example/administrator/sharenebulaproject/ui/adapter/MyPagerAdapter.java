package com.example.administrator.sharenebulaproject.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/27.
 * 邮箱：229017464@qq.com
 * remark:
 */

public class MyPagerAdapter extends PagerAdapter {
    private List<RecyclerView> views;

    public MyPagerAdapter(List<RecyclerView> views) {
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
        View view = views.get(position);
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
