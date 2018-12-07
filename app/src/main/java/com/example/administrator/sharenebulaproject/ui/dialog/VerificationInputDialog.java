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
import com.example.administrator.sharenebulaproject.model.event.EventCode;
import com.example.administrator.sharenebulaproject.rxtools.RxBus;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.utils.ToastUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/8/15.
 */

public class VerificationInputDialog extends BaseDialog implements View.OnClickListener {

    private Context context;
    private int status;
    private String content;
    @BindView(R.id.general_edit)
    EditText general_edit;
    @BindView(R.id.btn_cancle)
    TextView btn_cancle;
    @BindView(R.id.btn_commit)
    TextView btn_commit;
    @BindView(R.id.empty_input_view)
    View empty_input_view;

    protected VerificationInputDialog(Context context, @StyleRes int themeResId, int status,String content) {
        super(context, themeResId);
        this.context = context;
        this.status = status;
        this.content = content;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_verification_edit_input;
    }

    @Override
    protected void initClass() {
        showKeyboard();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setCanceledOnTouchOutside(false);
        if (!content.isEmpty()) {
            general_edit.setText(content);
            empty_input_view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initListener() {
        btn_cancle.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        general_edit.addTextChangedListener(TextChangedListener);
        empty_input_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancle:
                RxBus.getDefault().post(new CommonEvent(EventCode.DISMISS));
                dismiss();
                break;
            case R.id.btn_commit:
                if (!general_edit.getText().toString().isEmpty()) {
                    RxBus.getDefault().post(new CommonEvent(status, general_edit.getText().toString()));
                    dismiss();
                }else {
                    new ToastUtil(context).showToast(context.getResources().getString(R.string.empty_exception));
                }
                break;
            case R.id.empty_input_view:
                general_edit.setText("");
                break;
        }
    }

    private String beforeText;
    private TextWatcher TextChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            beforeText = charSequence.toString();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.toString().isEmpty()) {
                empty_input_view.setVisibility(View.INVISIBLE);
            } else {
                empty_input_view.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected void onDismissListener() {
        super.onDismissListener();
        SystemUtil.windowToLight(context);
    }

    public void showKeyboard() {
        if (general_edit != null) {
            //设置可获得焦点
            general_edit.setFocusable(true);
            general_edit.setFocusableInTouchMode(true);
            //请求获得焦点
            general_edit.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) general_edit
                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(general_edit, 0);
        }
    }

}
