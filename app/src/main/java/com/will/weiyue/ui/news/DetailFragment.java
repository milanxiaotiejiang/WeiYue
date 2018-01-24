package com.will.weiyue.ui.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.flyco.animation.SlideEnter.SlideRightEnter;
import com.flyco.animation.SlideExit.SlideRightExit;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.will.weiyue.MyApp;
import com.will.weiyue.R;
import com.will.weiyue.bean.NewsDetail;
import com.will.weiyue.component.ApplicationComponent;
import com.will.weiyue.component.DaggerHttpComponent;
import com.will.weiyue.net.NewsApi;
import com.will.weiyue.net.NewsUtils;
import com.will.weiyue.ui.adapter.NewsDetailAdapter;
import com.will.weiyue.ui.base.BaseFragment;
import com.will.weiyue.ui.news.contract.DetailContract;
import com.will.weiyue.ui.news.presenter.DetailPresenter;
import com.will.weiyue.utils.ContextUtils;
import com.will.weiyue.utils.ImageLoaderUtil;
import com.will.weiyue.widget.CustomLoadMoreView;
import com.will.weiyue.widget.NewsDelPop;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by android on 2018/1/16.
 */

public class DetailFragment extends BaseFragment<DetailPresenter> implements DetailContract.View {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mPtrFrameLayout)
    PtrFrameLayout mPtrFrameLayout;
    @BindView(R.id.tv_toast)
    TextView mTvToast;
    @BindView(R.id.rl_top_toast)
    RelativeLayout mRlTopToast;

    public static DetailFragment newInstance(String newsid, int position) {
        Bundle args = new Bundle();
        args.putString("newsid", newsid);
        args.putInt("position", position);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View view_Focus;//顶部banner
    private Banner mBanner;

    private boolean isRemoveHeaderView = false;

    private String newsid;
    private int position;

    private int downPullNum = 1;
    private int upPullNum = 1;

    private List<NewsDetail.ItemBean> beanList;
    private List<NewsDetail.ItemBean> mBannerList;

    private NewsDetailAdapter detailAdapter;

    private NewsDelPop newsDelPop;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder()
                .applicationComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void bindView(View view, Bundle saveInstanceState) {
        mPtrFrameLayout.disableWhenHorizontalMove(true);
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isRemoveHeaderView = true;
                mPresenter.getData(newsid, NewsApi.ACTION_DOWN, downPullNum);
            }
        });

        beanList = new ArrayList<>();
        mBannerList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(detailAdapter = new NewsDetailAdapter(beanList, getActivity()));

        detailAdapter.setEnableLoadMore(true);
        detailAdapter.setLoadMoreView(new CustomLoadMoreView());
        detailAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        detailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getData(newsid, NewsApi.ACTION_UP, upPullNum);
            }
        }, mRecyclerView);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                NewsDetail.ItemBean itemBean = (NewsDetail.ItemBean) adapter.getItem(position);
                toRead(itemBean);
            }
        });

        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                NewsDetail.ItemBean itemBean = (NewsDetail.ItemBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.iv_close:
                        view.getHeight();
                        int[] location = new int[2];
                        view.getLocationInWindow(location);
                        if (itemBean.getStyle() == null)
                            return;

                        if (ContextUtils.getSreenWidth(MyApp.getContext()) - 50 - location[1] < ContextUtils.dip2px(MyApp.getContext(), 80)) {
                            newsDelPop.anchorView(view)
                                    .gravity(Gravity.TOP)
                                    .setBackReason(itemBean.getStyle().getBackreason(), true, position)
                                    .show();
                        } else {
                            newsDelPop
                                    .anchorView(view)
                                    .gravity(Gravity.BOTTOM)
                                    .setBackReason(itemBean.getStyle().getBackreason(), false, position)
                                    .show();
                        }
                        break;
                }
            }
        });

        view_Focus = getView().inflate(getActivity(), R.layout.news_detail_headerview, null);
        mBanner = (Banner) view_Focus.findViewById(R.id.banner);
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        ImageLoaderUtil.LoadImage(getActivity(), path, imageView);
                    }
                })
                .setDelayTime(3000)
                .setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (mBannerList.size() < 1)
                    return;

                bannerToRead(mBannerList.get(position));
            }
        });

        newsDelPop = new NewsDelPop(getActivity())
                .alignCenter(false)
                .widthScale(0.95f)
                .showAnim(new SlideRightEnter())
                .dismissAnim(new SlideRightExit())
                .offset(-100, 0)
                .dimEnabled(true);

        newsDelPop.setClickListener(new NewsDelPop.onClickListener() {
            @Override
            public void onClick(int position) {
                newsDelPop.dismiss();
                detailAdapter.remove(position);
                showToast(0, false);
            }
        });
    }

    @Override
    public void initData() {
        if (getArguments() == null)
            return;

        newsid = getArguments().getString("newsid");
        position = getArguments().getInt("position");
        mPresenter.getData(newsid, NewsApi.ACTION_DEFAULT, 1);
    }

    @Override
    public void onRetry() {
        initData();
    }

    @Override
    public void loadBannerData(NewsDetail newsDetail) {
        List<String> mTitleList = new ArrayList<>();
        List<String> mUrlList = new ArrayList<>();
        mBannerList.clear();
        for (NewsDetail.ItemBean bean : newsDetail.getItem()) {
            if (!TextUtils.isEmpty(bean.getThumbnail())) {
                mTitleList.add(bean.getTitle());
                mBannerList.add(bean);
                mUrlList.add(bean.getThumbnail());
            }
        }

        if (mUrlList.size() > 0) {
            mBanner.setImages(mUrlList);
            mBanner.setBannerTitles(mTitleList);
            mBanner.start();
            if (detailAdapter.getHeaderLayoutCount() < 1) {
                detailAdapter.addHeaderView(view_Focus);
            }
        }
    }

    @Override
    public void loadTopNewsData(NewsDetail newsDetail) {
        Log.d("DetailFragment", newsDetail.toString());
    }

    @Override
    public void loadData(List<NewsDetail.ItemBean> itemBeanList) {
        if (itemBeanList == null) {
            showFaild();
        } else {
            downPullNum++;
            if (isRemoveHeaderView) {
                detailAdapter.removeAllHeaderView();
            }
            detailAdapter.setNewData(itemBeanList);
            showToast(itemBeanList.size(), true);
            mPtrFrameLayout.refreshComplete();
            showSuccess();

        }
    }

    @Override
    public void loadMoreData(List<NewsDetail.ItemBean> itemBeanList) {
        if (itemBeanList == null) {
            detailAdapter.loadMoreFail();
        } else {
            upPullNum++;
            detailAdapter.addData(itemBeanList);
            detailAdapter.loadMoreComplete();
        }
    }

    private void showToast(int num, boolean isRefresh) {

        if (isRefresh) {
            mTvToast.setText(String.format(getResources().getString(R.string.news_toast), num + ""));
        } else {
            mTvToast.setText("将为你减少此类内容");
        }
        mRlTopToast.setVisibility(View.VISIBLE);
        ViewAnimator.animate(mRlTopToast)
                .newsPaper()
                .duration(1000)
                .start()
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        ViewAnimator.animate(mRlTopToast)
                                .bounceOut()
                                .duration(1000)
                                .start();
                    }
                });

    }


    private void bannerToRead(NewsDetail.ItemBean itemBean) {
        if (itemBean == null) {
            return;
        }
        switch (itemBean.getType()) {
            case NewsUtils.TYPE_DOC:
                Log.e("DetailFragment", "TYPE_DOC");
                Intent intent = new Intent(getActivity(), ArticleReadActivity.class);
                intent.putExtra("aid", itemBean.getDocumentId());
                startActivity(intent);
                break;
            case NewsUtils.TYPE_SLIDE:
                Log.e("DetailFragment", "TYPE_SLIDE");
                ImageBrowseActivity.launch(getActivity(), itemBean);
                break;
            case NewsUtils.TYPE_ADVERT:
                Log.e("DetailFragment", "TYPE_ADVERT");
                AdvertActivity.launch(getActivity(), itemBean.getLink().getWeburl());
                break;
            case NewsUtils.TYPE_PHVIDEO:
                Log.e("DetailFragment", "TYPE_PHVIDEO");
                break;
        }
    }

    private void toRead(NewsDetail.ItemBean itemBean) {
        if (itemBean == null) {
            return;
        }
        switch (itemBean.getItemType()) {
            case NewsDetail.ItemBean.TYPE_DOC_TITLEIMG:
            case NewsDetail.ItemBean.TYPE_DOC_SLIDEIMG:
                Intent intent = new Intent(getActivity(), ArticleReadActivity.class);
                intent.putExtra("aid", itemBean.getDocumentId());
                startActivity(intent);
                break;
            case NewsDetail.ItemBean.TYPE_SLIDE:
                ImageBrowseActivity.launch(getActivity(), itemBean);
                break;
            case NewsDetail.ItemBean.TYPE_ADVERT_TITLEIMG://广告
            case NewsDetail.ItemBean.TYPE_ADVERT_SLIDEIMG:
            case NewsDetail.ItemBean.TYPE_ADVERT_LONGIMG:
                AdvertActivity.launch(getActivity(), itemBean.getLink().getWeburl());
                break;
            case NewsDetail.ItemBean.TYPE_PHVIDEO:
                break;
        }
    }
}
