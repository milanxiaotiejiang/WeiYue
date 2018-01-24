package com.will.weiyue.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.will.weiyue.R;
import com.will.weiyue.bean.FreshNewsBean;
import com.will.weiyue.ui.jandan.ReadActivity;
import com.will.weiyue.utils.ImageLoaderUtil;

import java.util.List;

import io.reactivex.annotations.Nullable;

/**
 * Created by android on 2018/1/22.
 */

public class FreshNewsAdapter extends BaseQuickAdapter<FreshNewsBean.PostsBean, BaseViewHolder> implements BaseQuickAdapter.OnItemClickListener {

    private Context mContext;

    public FreshNewsAdapter(Context context, @Nullable List<FreshNewsBean.PostsBean> data){
        super(R.layout.item_freshnews);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FreshNewsBean.PostsBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_info, item.getAuthor().getName());
        helper.setText(R.id.tv_commnetsize, item.getComment_count()+"评论");
        ImageLoaderUtil.LoadImage(mContext, item.getCustom_fields().getThumb_c().get(0), (ImageView) helper.getView(R.id.iv_logo));
        setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        View view1 = adapter.getViewByPosition(position, R.id.iv_logo);
        ReadActivity.launch(mContext, (FreshNewsBean.PostsBean) adapter.getItem(position),view1);
    }
}
