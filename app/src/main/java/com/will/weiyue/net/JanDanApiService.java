package com.will.weiyue.net;

import com.will.weiyue.bean.FreshNewsArticleBean;
import com.will.weiyue.bean.FreshNewsBean;
import com.will.weiyue.bean.JdDetailBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by android on 2018/1/22.
 */

public interface JanDanApiService {

    @GET
    Observable<FreshNewsBean> getFreshNews(@Url String url,
                                           @Query("oxwlxojflwblxbsapi") String oxwlxojflwblxbsapi,
                                           @Query("include") String include,
                                           @Query("page") int page,
                                           @Query("custom_fields") String custom_fields,
                                           @Query("dev") String dev);

    @GET
    Observable<JdDetailBean> getDetailData(@Url String url,
                                           @Query("oxwlxojflwblxbsapi") String oxwlxojflwblxbsapi,
                                           @Query("page") int page);

    @GET
    Observable<FreshNewsArticleBean> getFreshNewsArticle(@Url String url, @Query("oxwlxojflwblxbsapi") String oxwlxojflwblxbsapi,
                                                         @Query("include") String include,
                                                         @Query("id") int id);
}
