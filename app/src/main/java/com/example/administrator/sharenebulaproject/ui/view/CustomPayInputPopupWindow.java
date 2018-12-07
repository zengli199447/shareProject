package com.example.administrator.sharenebulaproject.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.utils.ToastUtil;

/**
 * Created by Administrator on 2017/10/17.
 */

public class CustomPayInputPopupWindow extends PopupWindow implements View.OnClickListener {
    private View mPopView;
    private OnItemClickListener mListener;
    private OnInputPassWordListener onInputPassWordListener;
    private final ToastUtil toastUtil;
    private Context context;
    private TextView pop_pay_title_text;
    private View empty_input_view;
    private TextView wechat_pay;
    private TextView zhifubao_pay;
    private TextView bank_pay;
    private TextView commit_pay;

    public CustomPayInputPopupWindow(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
        setPopupWindow();
        this.context = context;
        toastUtil = new ToastUtil(context);
        pop_pay_title_text.setOnClickListener(this);
        empty_input_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        wechat_pay.setOnClickListener(this);
        zhifubao_pay.setOnClickListener(this);
        bank_pay.setOnClickListener(this);
        commit_pay.setOnClickListener(this);

    }

    public void setTitlePayNumber(String payNumber) {
        pop_pay_title_text.setText(new StringBuffer().append("支付金额 : ").append(payNumber).append("元"));
    }

    public void setSelectIndex(int view) {
        switch (view) {
            case R.id.wechat_pay:
                wechat_pay.setTextColor(context.getResources().getColor(R.color.blue_bar_pay));
                zhifubao_pay.setTextColor(context.getResources().getColor(R.color.black_overlay));
                bank_pay.setTextColor(context.getResources().getColor(R.color.black_overlay));
                break;
            case R.id.zhifubao_pay:
                wechat_pay.setTextColor(context.getResources().getColor(R.color.black_overlay));
                zhifubao_pay.setTextColor(context.getResources().getColor(R.color.blue_bar_pay));
                bank_pay.setTextColor(context.getResources().getColor(R.color.black_overlay));
                break;
            case R.id.bank_pay:
                wechat_pay.setTextColor(context.getResources().getColor(R.color.black_overlay));
                zhifubao_pay.setTextColor(context.getResources().getColor(R.color.black_overlay));
                bank_pay.setTextColor(context.getResources().getColor(R.color.blue_bar_pay));
                break;
        }
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.custom_pay_input_popup_window, null);
        pop_pay_title_text = (TextView) mPopView.findViewById(R.id.pop_pay_title_text);
        empty_input_view = mPopView.findViewById(R.id.empty_input_view);
        wechat_pay = (TextView) mPopView.findViewById(R.id.wechat_pay);
        zhifubao_pay = (TextView) mPopView.findViewById(R.id.zhifubao_pay);
        bank_pay = (TextView) mPopView.findViewById(R.id.bank_pay);
        commit_pay = (TextView) mPopView.findViewById(R.id.commit_pay);

    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setAnimationStyle(R.style.popwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mPopView.findViewById(R.id.id_pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        SystemUtil.windowToLight(context);
                        dismiss();
                    }
                }
                return true;
            }
        });
    }


    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
    public interface OnItemClickListener {
        void setOnItemClick(View v);
    }

    public interface OnInputPassWordListener {
        void setInputPassWord(String payPassWord);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setOnInputPassWordListener(OnInputPassWordListener listener) {
        this.onInputPassWordListener = listener;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (mListener != null) {
            mListener.setOnItemClick(v);
        }
    }


}
