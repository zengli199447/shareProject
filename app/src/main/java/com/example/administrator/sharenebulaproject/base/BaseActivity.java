package com.example.administrator.sharenebulaproject.base;

import com.example.administrator.sharenebulaproject.di.component.ActivityComponent;
import com.example.administrator.sharenebulaproject.di.component.DaggerActivityComponent;
import com.example.administrator.sharenebulaproject.di.module.ActivityModule;
import com.example.administrator.sharenebulaproject.global.MyApplication;
import com.example.administrator.sharenebulaproject.model.DataManager;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.utils.ToastUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.umeng.message.PushAgent;

import javax.inject.Inject;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by Administrator on 2018/3/1.
 */

public abstract class BaseActivity extends SimpleActivity {

    @Inject
    public ToastUtil toastUtil;

    @Inject
    public DataManager dataManager;

    protected CompositeDisposable mCompositeDisposable;

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        PushAgent.getInstance(this).onAppStart();
        initInject();
        initRegisterEvent();
    }

    protected abstract void registerEvent(CommonEvent commonevent);

    protected abstract void initInject();

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    protected void initRegisterEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(CommonEvent.class)
                .compose(RxUtil.<CommonEvent>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<CommonEvent>(toastUtil, null) {

                    @Override
                    public void onNext(CommonEvent commonevent) {
                        registerEvent(commonevent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                })
        );
    }

    @Override
    protected void onUnSubscribe() {
        super.onUnSubscribe();
        unSubscribe();
    }

}
