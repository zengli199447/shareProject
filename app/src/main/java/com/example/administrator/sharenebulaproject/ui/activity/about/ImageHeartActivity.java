package com.example.administrator.sharenebulaproject.ui.activity.about;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.UserInfoBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/31.
 */

public class ImageHeartActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.user_heart)
    ImageView user_heart;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;
    @BindView(R.id.title_name)
    TextView title_name;

    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_image_heart;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        Glide.with(this).load(SystemUtil.JudgeUrl(UserInfoBean.photo)).centerCrop().error(R.drawable.banner_off).into(user_heart);
        title_name.setText(getString(R.string.user_herat));
    }

    @Override
    protected void initListener() {
        img_btn_black.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

}
