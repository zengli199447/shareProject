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

public class LoginStatusPromptDialog extends BaseDialog implements View.OnClickListener {

    @BindView(R.id.btn_commit)
    Button btn_commit;
    @BindView(R.id.input_type)
    TextView input_type;

    private Context context;
    private String content;

    protected LoginStatusPromptDialog(Context context, @StyleRes int themeResId, String content) {
        super(context, themeResId);
        this.context = context;
        this.content = content;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_login_status_prompt;
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
        input_type.setText(content);
    }

    @Override
    protected void initListener() {
        btn_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
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
