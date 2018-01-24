package com.will.weiyue.ui.news.presenter;

import com.will.weiyue.bean.NewsArticleBean;
import com.will.weiyue.net.NewsApi;
import com.will.weiyue.net.RxSchedulers;
import com.will.weiyue.ui.base.BasePresenter;
import com.will.weiyue.ui.news.contract.ArticleReadContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by android on 2018/1/17.
 */

public class ArticleReadPresenter extends BasePresenter<ArticleReadContract.View> implements ArticleReadContract.Presenter {

    NewsApi mNewsApi;

    @Inject
    public ArticleReadPresenter(NewsApi newsApi) {
        this.mNewsApi = newsApi;
    }

    @Override
    public void getData(String aid) {
        mNewsApi.getNewsArticle(aid)
                .compose(RxSchedulers.<NewsArticleBean>applySchedulers())
                .compose(mView.<NewsArticleBean>bindToLife())
                .subscribe(new Observer<NewsArticleBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsArticleBean newsArticleBean) {
                        mView.loadData(newsArticleBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
