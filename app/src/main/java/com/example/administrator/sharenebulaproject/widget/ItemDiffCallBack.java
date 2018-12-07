package com.example.administrator.sharenebulaproject.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/7.
 * 邮箱：229017464@qq.com
 * remark:
 */
//public class ItemDiffCallBack extends DiffUtil.Callback{
//
//    private List<Object> mOldList;
//    private List<Object> mNewList;
//    private final String TAG = getClass().getSimpleName();
//    public ItemDiffCallBack(List<Object> oldList, List<Object> newList) {
//        this.mOldList = oldList;
//        this.mNewList = newList;
//    }
//    @Override
//    public int getOldListSize() {
//        return mOldList == null ? 0 : mOldList.size();
//    }
//    @Override
//    public int getNewListSize() {
//        return mNewList == null ? 0 : mNewList.size();
//    }
//    //这个是用来判断是否是一个对象的
//    @Override
//    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//        return mOldList.get(oldItemPosition).id == mNewList.get(newItemPosition).id;
//    }
//    //这个是用来判断相同对象的内容是否相同
//    @Override
//    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//        String oldContent = mOldList.get(oldItemPosition).content();
//        String newContent = mNewList.get(newItemPosition).content();
//        Log.i(TAG, "oldContent:" + oldContent + " newContent:" + newContent);
//        return TextUtils.equals(oldContent ,newContent );
//    }
//    //找出其中的不同
//    @Nullable
//    @Override
//    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
//        Object oldItem = mOldList.get(oldItemPosition);
//        Object newItem = mNewList.get(newItemPosition);
//        Bundle diffBundle = new Bundle();
//        if (!TextUtils.equals(oldItem.content(), newItem.content())) {
//            diffBundle.putString("content", newItem.content());
//        }
//        return diffBundle;
//    }
//
//}
