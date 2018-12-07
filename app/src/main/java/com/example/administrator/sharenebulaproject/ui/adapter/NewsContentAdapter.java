package com.example.administrator.sharenebulaproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.HotAllDataBean;
import com.example.administrator.sharenebulaproject.model.bean.TitleNewsBean;
import com.example.administrator.sharenebulaproject.ui.holder.MyViewHolder;
import com.example.administrator.sharenebulaproject.utils.DataUtils;
import com.example.administrator.sharenebulaproject.utils.LogUtil;
import com.example.administrator.sharenebulaproject.widget.MyItemTouchCallBack;
import com.example.administrator.sharenebulaproject.widget.TouchInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/11/27.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class NewsContentAdapter extends RecyclerView.Adapter<MyViewHolder> implements TouchInterface {

    private String TAG = getClass().getSimpleName();

    Context context;
    List<TitleNewsBean> list;
    RecyclerView recyclerView;
    boolean deletStatus;
    private final MyItemTouchCallBack myItemTouchCallBack;
    String lengthChange = "";

    public NewsContentAdapter(Context context, List<TitleNewsBean> list, RecyclerView recyclerView) {
        this.context = context;
        this.list = list;
        this.recyclerView = recyclerView;
        myItemTouchCallBack = new MyItemTouchCallBack(this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(myItemTouchCallBack);
        touchHelper.attachToRecyclerView(recyclerView);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_news_type, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TitleNewsBean titleNewsBean = list.get(position);
        TextView type_new = holder.itemView.findViewById(R.id.type_new);
        ImageView type_delet = holder.itemView.findViewById(R.id.type_delet);

        refreshView(holder, titleNewsBean);

        if (deletStatus && !context.getString(R.string.recommended).equals(titleNewsBean.getContent())) {
            type_delet.setVisibility(View.VISIBLE);
        } else {
            type_delet.setVisibility(View.GONE);
        }

        myItemTouchCallBack.refreshView(deletStatus);
        ClickListener(type_new, position);
        ClickListener(type_delet, position);

    }

    private void ClickListener(final View view, final int position) {
        if (checkClickListener != null)
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (view.getId()) {
                        case R.id.type_delet:
                            checkClickListener.DeletClickListener(position);
                            break;
                        case R.id.type_new:
                            checkClickListener.TypeClickListener(position);
                            break;
                    }
                }
            });
    }

    public void refreshView(MyViewHolder holder, TitleNewsBean titleNewsBean) {
        TextView type_new = holder.itemView.findViewById(R.id.type_new);
        type_new.setText(titleNewsBean.getContent());
        if (titleNewsBean.isStatus()) {
            type_new.setTextColor(context.getResources().getColor(R.color.orange_red));
        } else {
            type_new.setTextColor(context.getResources().getColor(R.color.black));
        }
    }

    public boolean refreshTheEditorModle(TextView textView) {
        deletStatus = !deletStatus;
        if (deletStatus) {
            textView.setText(context.getString(R.string.commit_the_editor));
        } else {
            textView.setText(context.getString(R.string.the_editor));
        }
        notifyDataSetChanged();
        return deletStatus;
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    @Override
    public void onMove(int currentPosition, int targetPosition) {
        Collections.swap(list, currentPosition, targetPosition);
        if (targetPosition < currentPosition) {
            List<TitleNewsBean> subList = list.subList(targetPosition + 1, currentPosition + 1);
            //向右移一位
            DataUtils.rightStepList(0, subList);
        } else {
            List<TitleNewsBean> subList = list.subList(currentPosition, targetPosition);
            //向左移一位
            DataUtils.leftStepList(0, subList);
        }
        LogUtil.e(TAG, "Arrays.toString(list.toArray()) : " + Arrays.toString(list.toArray()));
        notifyItemMoved(currentPosition, targetPosition);
        lengthChange = JoiningTogetherData(list);
    }

    public String JoiningTogetherData(List<TitleNewsBean> list) {
        String lengthChange = "";
        String theComma = "";
        for (int i = 0; i < list.size(); i++) {
            String typeContent = new StringBuffer().append(DataClass.USERID).append("_")
                    .append(list.get(i).getId()).append("_")
                    .append(i).toString();
            if (i > 0) {
                theComma = ",";
            }
            if (!lengthChange.contains(typeContent))
                lengthChange = new StringBuffer().append(lengthChange).append(theComma).append(typeContent).toString();
        }
        return lengthChange;
    }

    public void emptyChangeTheSorting() {
        lengthChange = "";
    }

    public String getChangeTheSorting() {
        return lengthChange;
    }

    public interface CheckClickListener {
        void TypeClickListener(int position);

        void DeletClickListener(int position);
    }

    private CheckClickListener checkClickListener;

    public void setCheckClickListener(CheckClickListener checkClickListener) {
        this.checkClickListener = checkClickListener;
    }

}
