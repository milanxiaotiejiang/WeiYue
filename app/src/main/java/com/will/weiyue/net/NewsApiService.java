package com.will.weiyue.net;

import com.will.weiyue.bean.NewsArticleBean;
import com.will.weiyue.bean.NewsDetail;
import com.will.weiyue.bean.VideoChannelBean;
import com.will.weiyue.bean.VideoDetailBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by android on 2018/1/15.
 */

public interface NewsApiService {


    @GET("ClientNews")
    Observable<List<NewsDetail>> getNewsDetail(@Query("id") String id,
                                               @Query("action") String action,
                                               @Query("pullNum") int pullNum);


    @GET("api_vampire_article_detail")
    Observable<NewsArticleBean> getNewsArticleWithSub(@Query("aid") String aid);

    @GET
    Observable<NewsArticleBean> getNewsArticleWithCmpp(@Url String url,
                                                       @Query("aid") String aid);

    @GET("ifengvideoList")
    Observable<List<VideoChannelBean>> getVideoChannel(@Query("page") int page);

    @GET("ifengvideoList")
    Observable<List<VideoDetailBean>> getVideoDetail(@Query(("page")) int page,
                                                     @Query("listtype") String listtype,
                                                     @Query("typeid") String typeid);
}
