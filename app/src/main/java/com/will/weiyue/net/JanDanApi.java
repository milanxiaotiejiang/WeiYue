package com.will.weiyue.net;

import android.support.annotation.StringDef;

import com.will.weiyue.bean.FreshNewsArticleBean;
import com.will.weiyue.bean.FreshNewsBean;
import com.will.weiyue.bean.JdDetailBean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.reactivex.Observable;

/**
 * Created by android on 2018/1/15.
 */

public class JanDanApi {

    public static final String TYPE_FRESH = "get_recent_posts";
    public static final String TYPE_FRESHARTICLE = "get_post";
    public static final String TYPE_BORED = "jandan.get_pic_comments";
    public static final String TYPE_GIRLS = "jandan.get_ooxx_comments";
    public static final String TYPE_Duan = "jandan.get_duan_comments";

    @StringDef({TYPE_FRESH, TYPE_BORED, TYPE_GIRLS, TYPE_Duan})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {

    }

    public static JanDanApi sInstance;

    private JanDanApiService mService;

    public JanDanApi(JanDanApiService janDanApiService) {
        this.mService = janDanApiService;
    }

    public static JanDanApi getInstance(JanDanApiService janDanApiService) {
        if (sInstance == null)
            sInstance = new JanDanApi(janDanApiService);
        return sInstance;
    }

    public Observable<FreshNewsBean> getFreshNews(int page) {
        return mService.getFreshNews(ApiConstants.sJanDanApi, TYPE_FRESH,
                "url,date,tags,author,title,excerpt,comment_count,comment_status,custom_fields",
                page, "thumb_c,views", "1");
    }

    public Observable<JdDetailBean> getJdDetails(@Type String type, int page) {
        return mService.getDetailData(ApiConstants.sJanDanApi, type, page);
    }

    public Observable<FreshNewsArticleBean> getFreshNewsArticle(int id) {
        return mService.getFreshNewsArticle(ApiConstants.sJanDanApi, TYPE_FRESHARTICLE, "content,date,modified", id);
    }
}
