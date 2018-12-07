package com.example.administrator.sharenebulaproject.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * 作者：真理 Created by Administrator on 2018/11/28.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MyItemTouchCallBack extends ItemTouchHelper.Callback {

    private TouchInterface touchInterface;

    public MyItemTouchCallBack(TouchInterface touchInterface) {
        this.touchInterface = touchInterface;
    }

    private boolean status;

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //拖拽
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        //滑出屏幕
        int swipeFlags = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //保持第一个item长按不能移动
        if (viewHolder.getAdapterPosition() == 0){
            return false;
        }
        //保持第一个item不能移动
        if (target.getAdapterPosition() == 0){
            return false;
        }else {
            int position_target = target.getLayoutPosition();
            int position = viewHolder.getLayoutPosition();
            touchInterface.onMove(position, position_target);
            return true;
        }
//        return true;
    }


    //标签动画持续时间，默认是250
    @Override
    public long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {

        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
    }


    /**
     * 是否可以长按拖拽，默认是true
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return status;
    }

    public void refreshView(boolean status) {
        this.status = status;
    }

    /**
     * 标签划出去的回调，direction是滑动的方向
     *
     * @return
     */

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

}
