package com.example.administrator.sharenebulaproject.ui.activity.tools;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseActivity;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.LoginInfoBean;
import com.example.administrator.sharenebulaproject.model.bean.PosterGetNetBean;
import com.example.administrator.sharenebulaproject.model.bean.UserInfoBean;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.rxtools.RxUtil;
import com.example.administrator.sharenebulaproject.ui.activity.about.GeneralVersionActivity;
import com.example.administrator.sharenebulaproject.ui.dialog.ProgressDialog;
import com.example.administrator.sharenebulaproject.ui.dialog.ShowDialog;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.utils.QrUtils;
import com.example.administrator.sharenebulaproject.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/23.
 * 邀请码
 */

public class QrCodeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.qr_img)
    ImageView qr_img;
    @BindView(R.id.copy)
    TextView copy;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.img_btn_black)
    ImageButton img_btn_black;

    private String code = "123456";
    private int view[] = {R.id.text_qr_1, R.id.text_qr_2, R.id.text_qr_3, R.id.text_qr_4, R.id.text_qr_5, R.id.text_qr_6, R.id.text_qr_7, R.id.text_qr_8};
    private ClipboardManager myClipboard;
    private ProgressDialog progressDialog;


    @Override
    protected void registerEvent(CommonEvent commonevent) {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_qr_code;
    }

    @Override
    protected void initClass() {
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        progressDialog = ShowDialog.getInstance().showProgressStatus(this, getString(R.string.progress));
    }

    @Override
    protected void initData() {
        try {
            for (int i = 0; i < UserInfoBean.Invitationcode.length(); i++) {
                String substring = UserInfoBean.Invitationcode.substring(i, i + 1);
                TextView viewById = (TextView) findViewById(view[i]);
                viewById.setText(substring);
                viewById.setVisibility(View.VISIBLE);
                LogUtil.e(TAG, "substring : " + substring);
            }
            initNetDataWork();
        } catch (Exception e) {
            toastUtil.showToast(getString(R.string.exception));
        }
    }

    @Override
    protected void initView() {
        title_name.setText(getString(R.string.invitation));
    }

    @Override
    protected void initListener() {
        copy.setOnClickListener(this);
        img_btn_black.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.copy:
                if (UserInfoBean.Invitationcode != null) {
                    myClipboard.setPrimaryClip(ClipData.newPlainText("text", UserInfoBean.Invitationcode));
                    toastUtil.showToast(getString(R.string.replicated));
                } else {
                    toastUtil.showToast(getString(R.string.replicated_exception));
                }
                break;
            case R.id.img_btn_black:
                finish();
                break;
        }
    }

    //获取海报信息
    private void initNetDataWork() {
        progressDialog.show();
        HashMap map = new HashMap<>();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("action", DataClass.POSTER_GET);
        linkedHashMap.put("userid", DataClass.USERID);
        String toJson = new Gson().toJson(linkedHashMap);
        map.put("version", "v1");
        map.put("vars", toJson);
        addSubscribe(dataManager.getPosterGetNetBean(map)
                .compose(RxUtil.<PosterGetNetBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<PosterGetNetBean>(toastUtil) {
                    @Override
                    public void onNext(PosterGetNetBean posterGetNetBean) {
                        Log.e(TAG, "UpLoadStatusNetBean : " + posterGetNetBean.toString());
                        if (posterGetNetBean.getStatus() == 1) {
                            PosterGetNetBean.ResultBean result = posterGetNetBean.getResult();
                            if (result.getInvitationcode() != null) {
                                Bitmap bitmap = QrUtils.encodeAsBitmap(result.getEwm_url());
                                qr_img.setImageBitmap(bitmap);
                            }
                        } else {
                            toastUtil.showToast(getString(R.string.empty_data));
                        }
                        progressDialog.dismiss();
                    }
                }));
    }

}
