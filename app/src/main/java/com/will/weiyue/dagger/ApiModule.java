package com.will.weiyue.dagger;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by android on 2018/1/17.
 */

@Module
public class ApiModule {

    public static final String END_POINT = "http://www.baidu.com";

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .build();
        return client;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(END_POINT)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    User provideUser() {
        return new User("name from ApiProvide");
    }


}
