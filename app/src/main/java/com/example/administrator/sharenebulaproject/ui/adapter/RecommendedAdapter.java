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
public class RecommendedAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<TheArticleDetailsNetBean.ResultBean.TjlistBean> list;

    public RecommendedAdapter(Context context, List<TheArticleDetailsNetBean.ResultBean.TjlistBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommended, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        TheArticleDetailsNetBean.ResultBean.TjlistBean tjlistBean = list.get(position);
        ImageView img = holder.itemView.findViewById(R.id.img);
        TextView title = holder.itemView.findViewById(R.id.title);
        if (tjlistBean.getImgs() != null && tjlistBean.getImgs().size() > 0)
            Glide.with(context).load(SystemUtil.JudgeUrl(tjlistBean.getImgs().get(0))).error(R.drawable.banner_off).into(img);

        title.setText(tjlistBean.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recommendedClickListerner != null)
                    recommendedClickListerner.onRecommendedClickListerner(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    public interface RecommendedClickListerner {
        void onRecommendedClickListerner(int position);
    }

    private RecommendedClickListerner recommendedClickListerner;

    public void setRecommendedClickListerner(RecommendedClickListerner recommendedClickListerner) {
        this.recommendedClickListerner = recommendedClickListerner;
    }


}
