package com.will.weiyue.ui.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.will.weiyue.MyApp;
import com.will.weiyue.R;
import com.will.weiyue.bean.JdDetailBean;
import com.will.weiyue.ui.jandan.ImageBrowseActivity;
import com.will.weiyue.utils.ContextUtils;
import com.will.weiyue.utils.DateUtil;
import com.will.weiyue.utils.ImageLoaderUtil;
import com.will.weiyue.utils.ShareUtils;
import com.will.weiyue.widget.MultiImageView;
import com.will.weiyue.widget.ShowMaxImageView;

import org.litepal.util.SharedUtil;
import org.w3c.dom.Text;

import java.util.List;

import io.reactivex.annotations.Nullable;

/**
 * Created by android on 2018/1/22.
 */

public class BoredPicAdapter extends BaseMultiItemQuickAdapter<JdDetailBean.CommentsBean, BaseViewHolder> {

    private Activity mContext;

    public BoredPicAdapter(Activity context, @Nullable List<JdDetailBean.CommentsBean> data) {
        super(data);
        this.mContext = context;
        addItemType(JdDetailBean.CommentsBean.TYPE_MULTIPLE, R.layout.item_jandan_pic);
        addItemType(JdDetailBean.CommentsBean.TYPE_SINGLE, R.layout.item_jandan_pic_single);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final JdDetailBean.CommentsBean item) {
        helper.setText(R.id.tv_author, item.getComment_author());

        if (!TextUtils.isEmpty(item.getComment_agent())) {
            if (item.getComment_agent().contains("Android")) {
                helper.setText(R.id.tv_from, "来自Android客户端");
                helper.setVisible(R.id.tv_from, true);
            } else {
                helper.setVisible(R.id.tv_from, false);
            }
        } else {
            helper.setVisible(R.id.tv_from, false);
        }

        helper.setText(R.id.tv_time, DateUtil.getTimestampString(DateUtil.string2Date(item.getComment_date(), "yyyy-MM-dd HH:mm:ss")));

        if (TextUtils.isEmpty(item.getText_content())) {
            helper.setVisible(R.id.tv_content, false);
        } else {
            helper.setVisible(R.id.tv_content, true);
            String content = item.getText_content().replace(" ", "").replace("\r", "").replace("\n", "");
            helper.setText(R.id.tv_content, content);
        }

        helper.setVisible(R.id.img_gif, item.getPics().get(0).contains("gif"));
        helper.setVisible(R.id.progress, item.getPics().get(0).contains("gif"));
        helper.setText(R.id.tv_like, item.getVote_negative());
        helper.setText(R.id.tv_unlike, item.getVote_positive());
        helper.setText(R.id.tv_comment_count, item.getSub_comment_count());
        helper.addOnClickListener(R.id.img_share);

        switch (helper.getItemViewType()) {
            case JdDetailBean.CommentsBean.TYPE_MULTIPLE:
                MultiImageView multiImageView = helper.getView(R.id.img);
                helper.setVisible(R.id.img_gif, false);
                multiImageView.setList(item.getPics());
                multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String[] imageUrls = new String[item.getPics().size()];
                        for (int i = 0; i < item.getPics().size(); i++) {
                            imageUrls[i] = item.getPics().get(i);
                        }
                        ImageBrowseActivity.launch(mContext, imageUrls, position);
                    }
                });
                helper.getView(R.id.img_share).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShareUtils.shareSingleImage(mContext, item.getPics().get(0));
                    }
                });
                break;
            case JdDetailBean.CommentsBean.TYPE_SINGLE:
                ShowMaxImageView showMaxImageView = helper.getView(R.id.img);
                showMaxImageView.getLayoutParams().height = ContextUtils.dip2px(MyApp.getContext(), 250);
                showMaxImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String[] imageUrls = new String[item.getPics().size()];
                        imageUrls[0] = item.getPics().get(0);
                        ImageBrowseActivity.launch(mContext, imageUrls, 0);
                    }
                });
                ImageLoaderUtil.LoadImage(mContext, item.getPics().get(0), new DrawableImageViewTarget((ImageView) helper.getView(R.id.img)) {
                    @Override
                    public void onResourceReady(Drawable resource, @android.support.annotation.Nullable Transition<? super Drawable> transition) {
                        super.onResourceReady(resource, transition);
                        int pmWidth = ContextUtils.getSreenWidth(MyApp.getContext());
                        int pmHeight = ContextUtils.getSreenHeight(MyApp.getContext());
                        float sal = (float) pmHeight / pmWidth;
                        int actualHeight = (int) Math.ceil(sal * resource.getIntrinsicWidth());
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, actualHeight);
                        helper.getView(R.id.img).setLayoutParams(params);
                        helper.setVisible(R.id.img_gif, false);
                    }
                });
                helper.getView(R.id.img_share).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShareUtils.shareText(mContext, "http://jandan.net/pic/");
                    }
                });
                break;
        }
    }
}
