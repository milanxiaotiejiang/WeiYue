package com.will.weiyue.net;

import android.support.annotation.RequiresApi;
import android.support.annotation.StringDef;

import com.will.weiyue.bean.NewsArticleBean;
import com.will.weiyue.bean.NewsDetail;
import com.will.weiyue.bean.VideoChannelBean;
import com.will.weiyue.bean.VideoDetailBean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by android on 2018/1/15.
 */

public class NewsApi {

    public static final String ACTION_DEFAULT = "default";
    public static final String ACTION_DOWN = "down";
    public static final String ACTION_UP = "up";

    @StringDef({ACTION_DEFAULT, ACTION_DOWN, ACTION_UP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Actions {

    }

    public static NewsApi sInstance;

    private NewsApiService mService;

    public NewsApi(NewsApiService newsApiService) {
        this.mService = newsApiService;
    }

    public static NewsApi getInstance(NewsApiService newsApiService) {
        if (sInstance == null)
            return new NewsApi(newsApiService);
        return sInstance;
    }


    public Observable<NewsDetail> getNewsDetail(String id, @Actions String action, int pullNum) {
        return mService.getNewsDetail(id, action, pullNum)
                .flatMap(new Function<List<NewsDetail>, ObservableSource<NewsDetail>>() {
                    @Override
                    public ObservableSource<NewsDetail> apply(List<NewsDetail> newsDetails) throws Exception {
                        //遍历集合，发送每个item
                        return Observable.fromIterable(newsDetails);
                    }
                });
    }

    public Observable<NewsArticleBean> getNewsArticle(String aid) {
        if (aid.startsWith("sub")) {
            return mService.getNewsArticleWithSub(aid);
        } else {
            return mService.getNewsArticleWithCmpp(ApiConstants.sGetNewsArticleCmppApi + ApiConstants.sGetNewsArticleDocCmppApi, aid);
        }
    }

    public Observable<List<VideoChannelBean>> getVideoChannel() {
        return mService.getVideoChannel(1);
    }

    public Observable<List<VideoDetailBean>> getVideoDetail(int page, String listType, String typeid) {
        return mService.getVideoDetail(page, listType, typeid);
    }


}
