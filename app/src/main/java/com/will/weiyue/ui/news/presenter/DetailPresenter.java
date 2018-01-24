package com.will.weiyue.ui.news.presenter;

import android.util.Log;

import com.will.weiyue.bean.NewsDetail;
import com.will.weiyue.net.BaseObserver;
import com.will.weiyue.net.NewsApi;
import com.will.weiyue.net.NewsUtils;
import com.will.weiyue.net.RxSchedulers;
import com.will.weiyue.ui.base.BasePresenter;
import com.will.weiyue.ui.news.contract.DetailContract;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by android on 2018/1/16.
 */

public class DetailPresenter extends BasePresenter<DetailContract.View> implements DetailContract.Presenter {

    NewsApi mNewsApi;

    @Inject
    public DetailPresenter(NewsApi newsApi) {
        this.mNewsApi = newsApi;
    }

    @Override
    public void getData(String id, final String action, int pullNum) {
        mNewsApi.getNewsDetail(id, action, pullNum)
                .compose(RxSchedulers.<NewsDetail>applySchedulers())
                .filter(new Predicate<NewsDetail>() {
                    @Override
                    public boolean test(NewsDetail newsDetail) throws Exception {
                        if (NewsUtils.isBannerNews(newsDetail)) {
                            mView.loadBannerData(newsDetail);
                        }
                        if (NewsUtils.isTopNews(newsDetail)) {
                            mView.loadTopNewsData(newsDetail);
                        }
                        return NewsUtils.isListNews(newsDetail);
                    }
                })
                .map(new Function<NewsDetail, List<NewsDetail.ItemBean>>() {
                    @Override
                    public List<NewsDetail.ItemBean> apply(NewsDetail newsDetail) throws Exception {
                        Iterator<NewsDetail.ItemBean> iterator = newsDetail.getItem().iterator();
                        while (iterator.hasNext()) {
                            try {
                                NewsDetail.ItemBean bean = iterator.next();
                                if (bean.getType().equals(NewsUtils.TYPE_DOC)) {
                                    if (bean.getStyle().getView() != null) {
                                        if (bean.getStyle().getView().equals(NewsUtils.VIEW_TITLEIMG)) {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_DOC_TITLEIMG;
                                        } else {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_DOC_SLIDEIMG;
                                        }
                                    }

                                } else if (bean.getType().equals(NewsUtils.TYPE_ADVERT)) {

                                    if (bean.getStyle() != null) {
                                        if (bean.getStyle().getView().equals(NewsUtils.VIEW_TITLEIMG)) {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_ADVERT_TITLEIMG;
                                        } else if (bean.getStyle().getView().equals(NewsUtils.VIEW_SLIDEIMG)) {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_ADVERT_SLIDEIMG;
                                        } else {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_ADVERT_LONGIMG;
                                        }
                                    } else {
                                        iterator.remove();
                                    }

                                } else if (bean.getType().equals(NewsUtils.TYPE_SLIDE)) {
                                    if (bean.getLink().equals("doc")) {
                                        if (bean.getStyle().getView().equals(NewsUtils.VIEW_SLIDEIMG)) {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_DOC_SLIDEIMG;
                                        } else {
                                            bean.itemType = NewsDetail.ItemBean.TYPE_DOC_TITLEIMG;
                                        }
                                    } else {
                                        bean.itemType = NewsDetail.ItemBean.TYPE_SLIDE;
                                    }
                                } else if (bean.getType().equals(NewsUtils.TYPE_PHVIDEO)) {
                                    bean.itemType = NewsDetail.ItemBean.TYPE_PHVIDEO;
                                } else {
                                    iterator.remove();
                                }
                            } catch (Exception e) {
                                iterator.remove();
                                e.printStackTrace();
                            }
                        }
                        return newsDetail.getItem();
                    }
                })
                .compose(mView.<List<NewsDetail.ItemBean>>bindToLife())
                .subscribe(new BaseObserver<List<NewsDetail.ItemBean>>() {
                    @Override
                    public void onSuccess(List<NewsDetail.ItemBean> itemBean) {
                        if (!action.equals(NewsApi.ACTION_UP)) {
                            mView.loadData(itemBean);
                        } else {
                            mView.loadMoreData(itemBean);
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        Log.d("DetailPresenter", e.getMessage().toString());
                        if (!action.equals(NewsApi.ACTION_UP)) {
                            mView.loadData(null);
                        } else {
                            mView.loadMoreData(null);
                        }
                    }
                });
    }
}
