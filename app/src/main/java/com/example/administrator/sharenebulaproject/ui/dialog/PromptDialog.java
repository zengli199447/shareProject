package com.example.administrator.sharenebulaproject.ui.dialog;

import android.content.Context;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseDialog;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/11.
 */

public class PromptDialog extends BaseDialog implements View.OnClickListener {

    @BindView(R.id.btn_commit)
    Button btn_commit;
    @BindView(R.id.btn_cancle)
    Button btn_cancle;
    @BindView(R.id.input_type)
    TextView input_type;
    @BindView(R.id.line)
    View line;
    private Context context;
    private String content;
    private boolean status;

    protected PromptDialog(Context context, @StyleRes int themeResId, String content, boolean status) {
        super(context, themeResId);
        this.context = context;
        this.content = content;
        this.status = status;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_prompt;
    }

    @Override
    protected void initClass() {
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void initData() {
        input_type.setText(content);
    }

    @Override
    protected void initView() {
        if (status) {
            btn_cancle.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {
        btn_commit.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                RxBus.getDefault().post(new CommonEvent(EventCode.DOWNLOAD_APK));
                dismiss();
                break;
            case R.id.btn_cancle:
                dismiss();
                break;
        }
    }

    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        SystemUtil.windowToLight(context);
    }
}
