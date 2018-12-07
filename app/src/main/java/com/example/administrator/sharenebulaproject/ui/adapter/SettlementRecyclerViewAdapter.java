package com.example.administrator.sharenebulaproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.sharenebulaproject.R;
import com.example.administrator.sharenebulaproject.global.DataClass;
import com.example.administrator.sharenebulaproject.model.bean.FansContentNetBean;
import com.example.administrator.sharenebulaproject.model.bean.HotAllDataBean;
import com.example.administrator.sharenebulaproject.model.bean.MoneyAboutNetBean;
import com.example.administrator.sharenebulaproject.model.bean.MyShareNetBean;
import com.example.administrator.sharenebulaproject.model.bean.SettementLogNetBean;
import com.example.administrator.sharenebulaproject.ui.view.CircleImageView;
import com.example.administrator.sharenebulaproject.utils.SystemUtil;

import java.util.List;

import static com.example.administrator.sharenebulaproject.R.id.floating_child;

/**
 * Created by Administrator on 2018/8/13.
 * 结算记录/粉丝列表
 */

public class SettlementRecyclerViewAdapter extends RecyclerView.Adapter<SettlementRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Object> users;
    private HotAllDataBean.Result.newsBean newsBean;
    // 普通布局
    private final int TYPE_ITEM_FIRST = 1;
    // 普通布局2
    private final int TYPE_ITEM_SECOND = 2;
    // 普通布局3
    private final int TYPE_MY_SHARE = 3;
    // 普通布局4
    private final int TYPE_ABOUT = 5;
    // 脚布局
    private final int TYPE_FOOTER = 4;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 加载完成
    public final int LOADING_COMPLETE = 2;
    // 加载到底
    public final int LOADING_END = 3;

    // 正在加载
    public final int LOADING = 1;

    public SettlementRecyclerViewAdapter(Context context, List<Object> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else if (users.get(position) instanceof SettementLogNetBean.ResultBean.UsersBean) {
            return TYPE_ITEM_FIRST;
        } else if (users.get(position) instanceof FansContentNetBean.ResultBean.UsersBean) {
            return TYPE_ITEM_SECOND;
        } else if (users.get(position) instanceof MyShareNetBean.ResultBean.MyshareBean) {
            return TYPE_MY_SHARE;
        } else if (users.get(position) instanceof MoneyAboutNetBean.ResultBean.MoneyinBean) {
            return TYPE_ABOUT;
        } else {
            return TYPE_FOOTER;
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == TYPE_ITEM_FIRST) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_settlement, parent, false);
        } else if (viewType == TYPE_FOOTER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer_view, parent, false);
        } else if (viewType == TYPE_ITEM_SECOND) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_show_type, parent, false);
        } else if (viewType == TYPE_MY_SHARE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share, parent, false);
        } else if (viewType == TYPE_ABOUT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_income_child, parent, false);
        }
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Object usersBean = null;
        if (position != users.size()) {
            usersBean = users.get(position);
        }
        if (position == users.size()) {
            switch (loadState) {
                case LOADING: // 正在加载
                    holder.progress_bar.setVisibility(View.VISIBLE);
                    break;
                case LOADING_COMPLETE:
                    // 加载完成
                    holder.progress_bar.setVisibility(View.INVISIBLE);
                    break;
                case LOADING_END:
                    // 加载到底
                    holder.progress_bar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        } else if (usersBean instanceof SettementLogNetBean.ResultBean.UsersBean) {
            SettementLogNetBean.ResultBean.UsersBean users = (SettementLogNetBean.ResultBean.UsersBean) usersBean;
            new SystemUtil().textMagicTool(context, holder.type_account, new StringBuffer().append(users.getRemark()).append("\n").toString()
                    , users.getCreatedate(), R.dimen.dp14, R.dimen.dp12, R.color.black, R.color.black_overlay);
            holder.value.setText(users.getOptmoney());
        } else if (usersBean instanceof FansContentNetBean.ResultBean.UsersBean) {
            FansContentNetBean.ResultBean.UsersBean users = (FansContentNetBean.ResultBean.UsersBean) usersBean;
            Glide.with(context).load(new StringBuffer().append(DataClass.FileUrl).append(users.getPhoto()).toString()).centerCrop().error(R.drawable.banner_off).into(holder.fans_heart);
            new SystemUtil().textMagicTool(context, holder.fans_content, new StringBuffer().append(users.getSecondname()).append("").toString()
                    , users.getCreatedate(), R.dimen.dp14, R.dimen.dp12, R.color.black, R.color.black_overlay);
        } else if (usersBean instanceof MyShareNetBean.ResultBean.MyshareBean) {
            if (holder.layout_my_share != null)
                holder.layout_my_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCheckListener.onClick(position);
                    }
                });
            MyShareNetBean.ResultBean.MyshareBean users = (MyShareNetBean.ResultBean.MyshareBean) usersBean;
            holder.title_content.setText(users.getTitle());
            if ("0".equals(users.getIfing())) {
                new SystemUtil().textMagicTool(context, holder.create_date, new StringBuffer().append(users.getCreatedate()).toString()
                        , context.getString(R.string.activities_in_progress_over), R.dimen.dp12, R.dimen.dp14, R.color.black_overlay, R.color.black, "     ");
            } else {
                new SystemUtil().textMagicTool(context, holder.create_date, new StringBuffer().append(users.getCreatedate()).toString()
                        , context.getString(R.string.activities_in_progress), R.dimen.dp12, R.dimen.dp14, R.color.black_overlay, R.color.fatigue_red, "     ");
            }
            new SystemUtil().textMagicTool(context, holder.statistical, new StringBuffer().append(context.getString(R.string.share_statistical)).append("     ")
                            .append(context.getString(R.string.read)).append("   ").append(users.getAmount_read()).append("   ").append(context.getString(R.string.share))
                            .append("  ").append(users.getAmount_share()).append(context.getString(R.string.people)).append("    ").append(context.getString(R.string.total)).append("  ").toString()
                    , users.getBeantotal(), R.dimen.dp12, R.dimen.dp16, R.color.blue, R.color.blue, "");
        } else if (usersBean instanceof MoneyAboutNetBean.ResultBean.MoneyinBean) {
            MoneyAboutNetBean.ResultBean.MoneyinBean moneyinBean = (MoneyAboutNetBean.ResultBean.MoneyinBean) usersBean;
            new SystemUtil().textMagicTool(context, holder.content_child, moneyinBean.getRemark(), moneyinBean.getCreatedate(), R.dimen.dp13, R.dimen.dp12, R.color.black_overlay, R.color.gray_light_text, "     ");
            holder.floating_child.setText(new StringBuffer().append("+").append(moneyinBean.getOptmoney()).toString());
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return users.size() == 0 ? 0 : users.size() + 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView type_account;
        private final TextView value;
        private final RelativeLayout progress_bar;
        private final CircleImageView fans_heart;
        private final TextView fans_content;
        private final TextView title_content;
        private final TextView create_date;
        private final TextView statistical;
        private final LinearLayout layout_my_share;
        private final TextView content_child;
        private final TextView floating_child;

        public ViewHolder(View itemView) {
            super(itemView);
            type_account = (TextView) itemView.findViewById(R.id.type_account);
            value = (TextView) itemView.findViewById(R.id.value);
            progress_bar = (RelativeLayout) itemView.findViewById(R.id.progress_bar);
            fans_heart = (CircleImageView) itemView.findViewById(R.id.fans_heart);
            fans_content = (TextView) itemView.findViewById(R.id.fans_content);
            title_content = (TextView) itemView.findViewById(R.id.title_content);
            create_date = (TextView) itemView.findViewById(R.id.create_date);
            statistical = (TextView) itemView.findViewById(R.id.statistical);
            layout_my_share = (LinearLayout) itemView.findViewById(R.id.layout_my_share);
            content_child = (TextView) itemView.findViewById(R.id.content_child);
            floating_child = (TextView) itemView.findViewById(R.id.floating_child);
        }
    }

    public interface onCheckListener {
        void onClick(int i);
    }

    public onCheckListener onCheckListener;

    public void setOnCheckListener(onCheckListener onCheckListener) {
        this.onCheckListener = onCheckListener;
    }

    /**
     * 设置上拉加载状态 * * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }


}
