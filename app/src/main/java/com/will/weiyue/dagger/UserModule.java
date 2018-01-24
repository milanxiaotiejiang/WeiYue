package com.will.weiyue.dagger;

import android.content.Context;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by android on 2018/1/17.
 */

@Module
public class UserModule {

    //在Module中传递上下文对象
    private Context mContext;

    public UserModule(Context context) {
        this.mContext = context;
    }

    @Provides
    ApiService provideApiService(){
        return new ApiService();
    }

    //如果自定义Module中的方法需要对象参数，怎么处理？
    //1）通过Module中的方法进行提供，比如provideUserStore()
    //2）通过类的构造函数进行提供对象，注释掉上面的provideUserStore()方法
    //3）代码执行顺序:创建参数对象的方法先执行，比如构造；


}
