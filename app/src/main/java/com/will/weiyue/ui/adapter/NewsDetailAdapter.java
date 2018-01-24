package com.will.weiyue.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.will.weiyue.R;
import com.will.weiyue.bean.NewsDetail;
import com.will.weiyue.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by android on 2018/1/16.
 */

public class NewsDetailAdapter extends BaseMultiItemQuickAdapter<NewsDetail.ItemBean, BaseViewHolder> {

    private Context mContext;

    public NewsDetailAdapter(List<NewsDetail.ItemBean> data, Context context) {
        super(data);
        this.mContext = context;
        addItemType(NewsDetail.ItemBean.TYPE_DOC_TITLEIMG, R.layout.item_detail_doc);
        addItemType(NewsDetail.ItemBean.TYPE_DOC_SLIDEIMG, R.layout.item_detail_doc_slideimg);
        addItemType(NewsDetail.ItemBean.TYPE_ADVERT_TITLEIMG, R.layout.item_detail_advert);
        addItemType(NewsDetail.ItemBean.TYPE_ADVERT_SLIDEIMG, R.layout.item_detail_advert_slideimg);
        addItemType(NewsDetail.ItemBean.TYPE_ADVERT_LONGIMG, R.layout.item_detail_advert_longimage);
        addItemType(NewsDetail.ItemBean.TYPE_SLIDE, R.layout.item_detail_slide);
        addItemType(NewsDetail.ItemBean.TYPE_PHVIDEO, R.layout.item_detail_phvideo);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsDetail.ItemBean item) {
        switch (helper.getItemViewType()) {
            case NewsDetail.ItemBean.TYPE_DOC_TITLEIMG:
                helper.setText(R.id.tv_title, item.getTitle());
                helper.setText(R.id.tv_source, item.getSource());
                helper.setText(R.id.tv_commnetsize,
                        String.format(mContext.getResources().getString(R.string.news_commentsize), item.getCommentsall()));
                ImageLoaderUtil.LoadImage(mContext, item.getThumbnail(), (ImageView) helper.getView(R.id.iv_logo));
                helper.addOnClickListener(R.id.iv_close);
                break;
            case NewsDetail.ItemBean.TYPE_DOC_SLIDEIMG:
                helper.setText(R.id.tv_title, item.getTitle());
                helper.setText(R.id.tv_source, item.getSource());
                helper.setText(R.id.tv_commnetsize,
                        String.format(mContext.getResources().getString(R.string.news_commentsize), item.getCommentsall()));
                try {
                    ImageLoaderUtil.LoadImage(mContext, item.getStyle().getImages().get(0), (ImageView) helper.getView(R.id.iv_1));
                    ImageLoaderUtil.LoadImage(mContext, item.getStyle().getImages().get(1), (ImageView) helper.getView(R.id.iv_2));
                    ImageLoaderUtil.LoadImage(mContext, item.getStyle().getImages().get(2), (ImageView) helper.getView(R.id.iv_3));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                helper.addOnClickListener(R.id.iv_close);
                break;
            case NewsDetail.ItemBean.TYPE_ADVERT_TITLEIMG:
                helper.setText(R.id.tv_title, item.getTitle());
                ImageLoaderUtil.LoadImage(mContext, item.getThumbnail(), (ImageView) helper.getView(R.id.iv_logo));
                helper.addOnClickListener(R.id.iv_close);
                break;
            case NewsDetail.ItemBean.TYPE_ADVERT_SLIDEIMG:
                helper.setText(R.id.tv_title, item.getTitle());
                try {
                    ImageLoaderUtil.LoadImage(mContext, item.getStyle().getImages().get(0), (ImageView) helper.getView(R.id.iv_1));
                    ImageLoaderUtil.LoadImage(mContext, item.getStyle().getImages().get(1), (ImageView) helper.getView(R.id.iv_2));
                    ImageLoaderUtil.LoadImage(mContext, item.getStyle().getImages().get(2), (ImageView) helper.getView(R.id.iv_3));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                helper.addOnClickListener(R.id.iv_close);
                break;
            case NewsDetail.ItemBean.TYPE_ADVERT_LONGIMG:
                helper.setText(R.id.tv_title, item.getTitle());
                ImageLoaderUtil.LoadImage(mContext, item.getThumbnail(), (ImageView) helper.getView(R.id.iv_logo));
                helper.addOnClickListener(R.id.iv_close);
                break;
            case NewsDetail.ItemBean.TYPE_SLIDE:
                helper.setText(R.id.tv_title, item.getTitle());
                helper.setText(R.id.tv_source, item.getSource());
                helper.setText(R.id.tv_commnetsize,
                        String.format(mContext.getResources().getString(R.string.news_commentsize), item.getCommentsall()));
                ImageLoaderUtil.LoadImage(mContext, item.getThumbnail(), (ImageView) helper.getView(R.id.iv_logo));
                helper.addOnClickListener(R.id.iv_close);
                break;
            case NewsDetail.ItemBean.TYPE_PHVIDEO:
                helper.setText(R.id.tv_title, item.getTitle());
                helper.setText(R.id.tv_source, item.getSource());
                helper.setText(R.id.tv_commnetsize,
                        String.format(mContext.getResources().getString(R.string.news_commentsize), item.getCommentsall()));
                helper.addOnClickListener(R.id.iv_close);
                ImageLoaderUtil.LoadImage(mContext, item.getThumbnail(), (ImageView) helper.getView(R.id.iv_logo));
                break;
        }

    }
}
