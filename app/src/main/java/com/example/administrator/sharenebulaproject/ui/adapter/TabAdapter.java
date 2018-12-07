package com.example.administrator.sharenebulaproject.ui.adapter;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/29.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class TabAdapter extends FragmentPagerAdapter {

    private List<String> strings;
    private ArrayList<Fragment> FragmentList;

    public TabAdapter(android.support.v4.app.FragmentManager supportFragmentManager, List<String> strings, ArrayList<Fragment> FragmentList) {
        super(supportFragmentManager);
        this.strings = strings;
        this.FragmentList = FragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentList.get(position);
    }

    @Override
    public int getCount() {
        return FragmentList.size();
    }

    //显示标签上的文字
    @Override
    public CharSequence getPageTitle(int position) {
        return strings.get(position);
    }

}
