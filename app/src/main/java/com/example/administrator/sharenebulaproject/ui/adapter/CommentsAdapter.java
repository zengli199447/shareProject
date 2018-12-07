package com.example.administrator.sharenebulaproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.model.bean.TheArticleDetailsNetBean;
import com.example.administrator.sharenebulaproject.ui.holder.MyViewHolder;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;

import java.util.List;

/**
 * 作者：真理 Created by Administrator on 2018/12/5.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class CommentsAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<TheArticleDetailsNetBean.ResultBean.CommentBean> list;

    public CommentsAdapter(Context context, List<TheArticleDetailsNetBean.ResultBean.CommentBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comments, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TheArticleDetailsNetBean.ResultBean.CommentBean commentBean = list.get(position);
        ImageView photo = holder.itemView.findViewById(R.id.photo);
        TextView user_and_creat_time = holder.itemView.findViewById(R.id.user_and_creat_time);
        TextView comments_content = holder.itemView.findViewById(R.id.comments_content);
        View line = holder.itemView.findViewById(R.id.line);

        if (position == 0) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
        }

        Glide.with(context).load(SystemUtil.JudgeUrl(commentBean.getPhoto())).error(R.drawable.banner_off).into(photo);

        SystemUtil.textMagicTool(context, user_and_creat_time, commentBean.getSecondname(), commentBean.getCreatedate(), R.dimen.dp14, R.dimen.dp12, R.color.black, R.color.gray_light, "\n");

        comments_content.setText(commentBean.getContent());

    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }


}
