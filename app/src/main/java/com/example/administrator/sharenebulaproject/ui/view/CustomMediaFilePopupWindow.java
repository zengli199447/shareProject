package com.example.administrator.sharenebulaproject.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;
import com.example.administrator.sharenebulaproject.utils.ToastUtil;

/**
 * Created by Administrator on 2017/10/17.
 */

public class CustomMediaFilePopupWindow extends PopupWindow implements View.OnClickListener{
    private Button btnTakePhoto, btnSelect, btnCancel,btnTailoring;
    private View mPopView;
    private OnItemClickListener mListener;
    private final ToastUtil toastUtil;
    private Context context;

    public CustomMediaFilePopupWindow(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
        setPopupWindow();
        this.context = context;
        toastUtil = new ToastUtil(context);
        btnTakePhoto.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnTailoring.setOnClickListener(this);
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
        mPopView = inflater.inflate(R.layout.custom_media_file_popup_window, null);
        btnTakePhoto = (Button) mPopView.findViewById(R.id.id_btn_take_photo);
        btnSelect = (Button) mPopView.findViewById(R.id.id_btn_select);
        btnCancel = (Button) mPopView.findViewById(R.id.id_btn_cancelo);
        btnTailoring = (Button) mPopView.findViewById(R.id.id_btn_tailoring);
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (mListener != null) {
            SystemUtil.windowToLight(context);
            mListener.setOnItemClick(v);
        }
    }


}
