package com.will.weiyue.module;

import com.will.weiyue.MyApp;
import com.will.weiyue.net.ApiConstants;
import com.will.weiyue.net.JanDanApi;
import com.will.weiyue.net.JanDanApiService;
import com.will.weiyue.net.NewsApi;
import com.will.weiyue.net.NewsApiService;
import com.will.weiyue.net.RetrofitConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by android on 2018/1/15.
 */

/**
 * @Module：
 * 此注解里面全是方法，用于对外提供对象，自己定义方法，方法上使用@Provides。自定义一个类，以Module结尾，用@Module注解。
 */
@Module
public class HttpModule {

    /**
     * @Provides：
     * 此注解用于标记方法，表示可以通过这个方法获取一个对象，一般用于自定义类中。
     */
    @Provides
    OkHttpClient.Builder provoideOkHttpClient() {
        Cache cache = new Cache(new File(MyApp.getContext().getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);
        return new OkHttpClient().newBuilder().cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(RetrofitConfig.sLoggingInterceptor)
                .addInterceptor(RetrofitConfig.sRewriteCacheControlInterceptor)
                .addNetworkInterceptor(RetrofitConfig.sRewriteCacheControlInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS);
    }

    @Provides
    NewsApi provideNetEaseApis(OkHttpClient.Builder builder) {
        builder.addInterceptor(RetrofitConfig.sQueryParameterInterceptor);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build());

        return NewsApi.getInstance(retrofitBuilder
                .baseUrl(ApiConstants.sIFengApi)
                .build()
                .create(NewsApiService.class));
    }

    @Provides
    JanDanApi provideJanDanApis(OkHttpClient.Builder builder) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build());

        return JanDanApi.getInstance(retrofitBuilder
                .baseUrl(ApiConstants.sJanDanApi)
                .build().create(JanDanApiService.class));
    }

}
