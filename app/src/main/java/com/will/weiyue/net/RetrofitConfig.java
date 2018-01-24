package com.will.weiyue.net;

import android.util.Log;

import com.will.weiyue.MyApp;
import com.will.weiyue.utils.NetUtil;
import com.will.weiyue.bean.Constants;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by android on 2018/1/15.
 */

public class RetrofitConfig {

    private static final String TAG = "RetrofitConfig";

    //设置缓存有效期为1天
    static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器， max-stale可以配置设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查新网络的Cache-Control设置
    //（假如请求了服务器并在a时刻返回相应结果，则在max-age规定的秒数内，浏览器不会发送对应的请求到服务器，数据由缓存直接返回）
    static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600";
    // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
    static final String AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";

    /**
     * 云端相应头拦截器，用来配置缓存策略
     */
    public static final Interceptor sRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isNetworkAvailable(MyApp.getContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Log.d(TAG, "no network");
            }
            Response originalResponse = chain.proceed(request);
            if (NetUtil.isNetworkAvailable(MyApp.getContext())) {
                String cacheContorl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheContorl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, " + CACHE_CONTROL_CACHE)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * 公共参数
     */
    public static final Interceptor sQueryParameterInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request request;
            HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                    .addQueryParameter("uid", Constants.uid)
                    .addQueryParameter("devid", Constants.uid)
                    .addQueryParameter("proid", "ifengnews")
                    .addQueryParameter("vt", "5")
                    .addQueryParameter("publishid", "6103")
                    .addQueryParameter("screen", "1080x1920")
                    .addQueryParameter("os", "androidphone")
                    .addQueryParameter("df", "android_22")
                    .addQueryParameter("nw", "wifi")
                    .build();
            request = originalRequest.newBuilder().url(modifiedUrl).build();
            return chain.proceed(request);
        }
    };

    /**
     * 打印返回的json数据拦截器
     */
    public static final Interceptor sLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);
            } else {
                Log.d(TAG, "request.body() == null");
            }
            Log.e(TAG, "intercept: " + request.url() + (request.body() != null ? "?" + _parseParams(request.body(), requestBuffer) : ""));
            final Response response = chain.proceed(request);
            return response;
        }
    };

    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }
}
