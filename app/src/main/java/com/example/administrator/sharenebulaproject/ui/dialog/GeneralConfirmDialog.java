package com.example.administrator.sharenebulaproject.ui.dialog;

import android.content.Context;
import android.support.annotation.StyleRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.base.BaseDialog;
import com.example.administrator.sharenebulaproject.model.event.CommonEvent;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.utils.ToastUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/15.
 */

public class GeneralConfirmDialog extends BaseDialog implements View.OnClickListener {

    private Context context;
    private int status;
    private String content;
    @BindView(R.id.btn_cancle)
    Button btn_cancle;
    @BindView(R.id.btn_commit)
    Button btn_commit;
    @BindView(R.id.warm_prompt_content)
    TextView warm_prompt_content;

    protected GeneralConfirmDialog(Context context, @StyleRes int themeResId, int status, String content) {
        super(context, themeResId);
        this.context = context;
        this.status = status;
        this.content = content;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_general_confirm;
    }

    @Override
    protected void initClass() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        warm_prompt_content.setText(content);
    }

    @Override
    protected void initListener() {
        btn_cancle.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancle:
                dismiss();
                break;
            case R.id.btn_commit:
                RxBus.getDefault().post(new CommonEvent(status));
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
