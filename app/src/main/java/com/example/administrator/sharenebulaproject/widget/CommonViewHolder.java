package com.example.administrator.sharenebulaproject.widget;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

/**
 * 通用的ViewHolder，用 集合<View> 来代替多个成员变量 ，然后用同一个方法传入不同的id，来拿到需要的小控件
 */
public class CommonViewHolder {
    public final View convertView;
    private final SparseArray<View> views = new SparseArray<>();

    private CommonViewHolder(View convertView) {
        this.convertView = convertView;
    }

    public static CommonViewHolder createCVH(View convertView, ViewGroup parent, int layoutRes) {
        if (Objects.equals(convertView, null)) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(layoutRes, parent, false);
            CommonViewHolder cvh = new CommonViewHolder(convertView);
            convertView.setTag(cvh);
        }
        return (CommonViewHolder) convertView.getTag();
    }

    public View getView(int id) {
        if (Objects.equals(views.get(id), null)) {
            views.put(id, convertView.findViewById(id));
        }
        return views.get(id);
    }

    public TextView getTv(int id) {
        return ((TextView) getView(id));
    }

    public ImageView getIv(int id) {
        return ((ImageView) getView(id));
    }
}
