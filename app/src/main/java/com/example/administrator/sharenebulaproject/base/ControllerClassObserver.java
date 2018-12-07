package com.example.administrator.sharenebulaproject.base;

import android.content.Context;

import com.example.administrator.sharenebulaproject.di.component.ControllerComponent;
import com.example.administrator.sharenebulaproject.di.component.DaggerControllerComponent;
import com.example.administrator.sharenebulaproject.di.module.ControllerModule;
import com.example.administrator.sharenebulaproject.global.MyApplication;
import com.example.administrator.sharenebulaproject.model.DataManager;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.utils.ToastUtil;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 作者：真理 Created by Administrator on 2018/11/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public abstract class ControllerClassObserver extends BaseLifecycleObserver {


    public String TAG = getClass().getSimpleName();

    @Inject
    public ToastUtil toastUtil;

    @Inject
    public DataManager dataManager;

    protected CompositeDisposable mCompositeDisposable;

    protected ControllerComponent getControllerComponent() {
        return DaggerControllerComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .controllerModule(new ControllerModule(this))
                .build();
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
    protected void onClassCreate() {
        initInject();
        initRegisterEvent();
    }

    @Override
    protected void onClassStart() {

    }

    @Override
    protected void onClassResume() {

    }

    @Override
    protected void onClassPause() {

    }

    @Override
    protected void onClassStop() {

    }

    @Override
    protected void onClassDestroy() {
        unSubscribe();
    }
}