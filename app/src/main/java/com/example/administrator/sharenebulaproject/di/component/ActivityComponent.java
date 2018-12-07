package com.example.administrator.sharenebulaproject.di.component;

import android.app.Activity;


import com.example.administrator.sharenebulaproject.ui.activity.AdvertisingActivity;
import com.example.administrator.sharenebulaproject.ui.activity.HomeActivity;
import com.example.administrator.sharenebulaproject.ui.activity.LoginActivity;
import com.example.administrator.sharenebulaproject.ui.activity.MainActivity;
import com.example.administrator.sharenebulaproject.di.module.ActivityModule;
import com.example.administrator.sharenebulaproject.di.scope.ActivityScope;
import com.example.administrator.sharenebulaproject.ui.activity.PersonageActivity;
import com.example.administrator.sharenebulaproject.ui.activity.WelComeActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.AnecdotesActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.DailyActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.FansActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.GeneralVersionActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.ImageHeartActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.MyLevelActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.PublicWebActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.PushMessageActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.SearchActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.SettlementActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.SettlementLogActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.UpGradeVipActivity;
import com.example.administrator.sharenebulaproject.ui.activity.about.VipBenefitActivity;
import com.example.administrator.sharenebulaproject.ui.activity.certification.AccountBindActivity;
import com.example.administrator.sharenebulaproject.ui.activity.certification.GeneralActivity;
import com.example.administrator.sharenebulaproject.ui.activity.certification.PhoneNumberBindActivity;
import com.example.administrator.sharenebulaproject.ui.activity.certification.RealNameSystemActivity;
import com.example.administrator.sharenebulaproject.ui.activity.certification.SecurityCertificationActivity;
import com.example.administrator.sharenebulaproject.ui.activity.certification.TradePasswordActivity;
import com.example.administrator.sharenebulaproject.ui.activity.tools.QrCodeActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/10/27.
 */
@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(MainActivity mainActivity);

    void inject(WelComeActivity welComeActivity);

    void inject(LoginActivity loginActivity);

    void inject(HomeActivity homeActivity);

    void inject(PersonageActivity personageActivity);

    void inject(SecurityCertificationActivity securityCertificationActivity);

    void inject(RealNameSystemActivity realNameSystemActivity);

    void inject(TradePasswordActivity tradePasswordActivity);

    void inject(PhoneNumberBindActivity phoneNumberBindActivity);

    void inject(GeneralActivity paymentOfTheBindingActivity);

    void inject(AccountBindActivity accountBindActivity);

    void inject(UpGradeVipActivity upgradeVipActivity);

    void inject(VipBenefitActivity vipBenefitActivity);

    void inject(SettlementActivity settlementActivity);

    void inject(SettlementLogActivity settlementLogActivity);

    void inject(MyLevelActivity myLevelActivity);

    void inject(FansActivity fansActivity);

    void inject(QrCodeActivity qrCodeActivity);

    void inject(DailyActivity dailyActivity);

    void inject(GeneralVersionActivity generalVersionActivity);

    void inject(PublicWebActivity publicWebActivity);

    void inject(ImageHeartActivity imageHeartActivity);

    void inject(PushMessageActivity pushMessageActivity);

    void inject(AdvertisingActivity advertisingActivity);

    void inject(SearchActivity searchActivity);

    void inject(AnecdotesActivity anecdotesActivity);

}
