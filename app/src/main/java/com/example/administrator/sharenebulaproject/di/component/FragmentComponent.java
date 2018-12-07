package com.example.administrator.sharenebulaproject.di.component;

import android.app.Activity;

import com.example.administrator.sharenebulaproject.di.module.FragmentModule;
import com.example.administrator.sharenebulaproject.di.scope.FragmentScope;
import com.example.administrator.sharenebulaproject.ui.fragment.HomePageFragment;
import com.example.administrator.sharenebulaproject.ui.fragment.HotPageFragment;
import com.example.administrator.sharenebulaproject.ui.fragment.IncomeFragment;
import com.example.administrator.sharenebulaproject.ui.fragment.MineFragment;
import com.example.administrator.sharenebulaproject.ui.fragment.ToDayPageFragment;
import com.example.administrator.sharenebulaproject.ui.fragment.TopFragment;
import com.example.administrator.sharenebulaproject.ui.fragment.all.AllTypeAboutFragment;
import com.example.administrator.sharenebulaproject.ui.fragment.all.AllTypeFragment;

import dagger.Component;



@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(HotPageFragment hotPageFragment);

    void inject(IncomeFragment incomeFragment);

    void inject(MineFragment mineFragment);

    void inject(TopFragment topFragment);

    void inject(HomePageFragment homePageFragment);

    void inject(ToDayPageFragment toDayPageFragment);

    void inject(AllTypeFragment allTypeFragment);

    void inject(AllTypeAboutFragment allTypeAboutFragment);

}
