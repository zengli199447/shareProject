package com.example.administrator.sharenebulaproject.di.component;




import com.example.administrator.sharenebulaproject.base.ControllerClassObserver;
import com.example.administrator.sharenebulaproject.di.module.ControllerModule;
import com.example.administrator.sharenebulaproject.di.scope.ControllerScope;
import com.example.administrator.sharenebulaproject.ui.contorller.ContorllerHome;

import dagger.Component;


/**
 * Created by Administrator on 2017/10/27.
 */
@ControllerScope
@Component(modules = ControllerModule.class, dependencies = AppComponent.class)
public interface ControllerComponent {
    ControllerClassObserver getController();

    void inject(ContorllerHome controllerHome);



}
