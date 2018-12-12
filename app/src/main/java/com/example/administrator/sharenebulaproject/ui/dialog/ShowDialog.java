package com.example.administrator.sharenebulaproject.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2018/3/19.
 */

public class ShowDialog {

    private static ShowDialog instance = new ShowDialog();

    private ShowDialog() {
    }

    public static ShowDialog getInstance() {
        return instance;
    }

    public ProgressDialog showProgressStatus(final Context context, String content) {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.dialog, content);
        progressDialog.setCanceledOnTouchOutside(true);
        Window window = progressDialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);//此处可以设置dialog显示的位置
        }
        return progressDialog;
    }

    public void showPromptDialog(final Context context, String content, boolean status) {
        final PromptDialog loopDialog = new PromptDialog(context, R.style.dialog, content, status);
        loopDialog.show();
        SystemUtil.windowToDark(context);
    }

    public void showLoginStatusPromptDialog(final Context context, String content) {
        final LoginStatusPromptDialog loginStatusPromptDialog = new LoginStatusPromptDialog(context, R.style.dialog, content);
        loginStatusPromptDialog.show();
        SystemUtil.windowToDark(context);
    }

    public void showGeneralEditInput(final Context context, int status, String title, String content) {
        final GeneralEditInputDialog generalEditInputDialog = new GeneralEditInputDialog(context, R.style.dialog, status, title, content);
        generalEditInputDialog.show();
        Timer timer = new Timer();
        SystemUtil.windowToDark(context);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                generalEditInputDialog.showKeyboard();
            }
        }, 200);
    }

    public void showPayStatusDialog(final Context context, int status) {
        final PayStatusDialog payStatusDialog = new PayStatusDialog(context, R.style.dialog, status);
        payStatusDialog.setCanceledOnTouchOutside(true);
        Window window = payStatusDialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);//此处可以设置dialog显示的位置
        }
        payStatusDialog.show();
    }

    public void showVerificationInputDialog(final Context context, int status, String content) {
        final VerificationInputDialog verificationInputDialog = new VerificationInputDialog(context, R.style.dialog, status, content);
        verificationInputDialog.show();
        Timer timer = new Timer();
        SystemUtil.windowToDark(context);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                verificationInputDialog.showKeyboard();
            }
        }, 200);
    }

    public void showSettlementStatusDialog(final Context context) {
        final SettlementStatusDialog settlementStatusDialog = new SettlementStatusDialog(context, R.style.dialog);
        settlementStatusDialog.show();
        SystemUtil.windowToDark(context);
    }

    public void showGeneralConfirmDialog(final Context context, int status, String content) {
        final GeneralConfirmDialog generalConfirmDialog = new GeneralConfirmDialog(context, R.style.dialog, status, content);
        generalConfirmDialog.show();
        SystemUtil.windowToDark(context);
    }

}
