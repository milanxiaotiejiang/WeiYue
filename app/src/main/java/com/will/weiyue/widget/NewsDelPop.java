package com.will.weiyue.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.flyco.dialog.widget.popup.base.BasePopup;
import com.will.weiyue.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by android on 2018/1/16.
 */

public class NewsDelPop extends BasePopup<NewsDelPop> {

    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_nolike)
    TextView tvNolike;
    @BindView(R.id.iv_down)
    ImageView ivDown;

    private BaseQuickAdapter<String, BaseViewHolder> adapter;

    List<String> backreason;

    List<Integer> selectid;

    private int position;

    private onClickListener onClickListener;

    public interface onClickListener {
        void onClick(int position);
    }

    public void setClickListener(onClickListener clickListener){
        onClickListener=clickListener;
    }


    public NewsDelPop(Context context) {
        super(context);
    }

    public NewsDelPop setBackReason(List<String> backreason, boolean isTop, int position) {
        this.backreason = backreason;
        this.position = position;
        selectid = new ArrayList<>();
        selectid.clear();
        if (adapter != null) {
            adapter.setNewData(backreason);
        }
        if (isTop) {
            ivTop.setVisibility(View.GONE);
            ivDown.setVisibility(View.VISIBLE);
        } else {
            ivTop.setVisibility(View.VISIBLE);
            ivDown.setVisibility(View.GONE);
        }
        return this;
    }

    @Override
    public View onCreatePopupView() {
        View inflate = View.inflate(mContext, R.layout.popup_newsdel, null);
        ButterKnife.bind(this, inflate);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerView.setAdapter(adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_newsdelpop_del, backreason) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_backreason, item);
                if (selectid.contains(helper.getAdapterPosition())) {
                    helper.getView(R.id.tv_backreason).setBackground(mContext.getResources().getDrawable(R.drawable.delpop_tv_selected_bg));
                    helper.setTextColor(R.id.tv_backreason, mContext.getResources().getColor(android.R.color.holo_red_light));
                } else {
                    helper.getView(R.id.tv_backreason).setBackground(mContext.getResources().getDrawable(R.drawable.delpop_tv_bg));
                    helper.setTextColor(R.id.tv_backreason, mContext.getResources().getColor(android.R.color.black));
                }
            }
        });

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (selectid.contains(position)) {
                    selectid.remove(position);
                } else {
                    selectid.add(position);
                }
                if (selectid.size() > 0) {
                    tvNolike.setText("确定");
                } else {
                    tvNolike.setText("不感兴趣");
                }
                adapter.notifyDataSetChanged();
            }
        });
        return inflate;
    }

    @Override
    public void setUiBeforShow() {

    }

    @OnClick(R.id.tv_nolike)
    public void onViewClicked() {
        onClickListener.onClick(position);
    }
}
