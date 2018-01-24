package com.will.weiyue.component;

import android.content.Context;

import com.will.weiyue.MyApp;
import com.will.weiyue.module.ApplicationModule;
import com.will.weiyue.module.HttpModule;
import com.will.weiyue.net.JanDanApi;
import com.will.weiyue.net.NewsApi;

import dagger.Component;

/**
 * Created by android on 2018/1/15.
 */

/**
 * @Component： 此注解主要用于关联自定义module类和MainActivity；
 * 关联module使用：@Component(modules={UserModule.class})；
 * 关联Activity，以方法参数的形式传入MainActivity到连接器中
 */
@Component(modules = {ApplicationModule.class, HttpModule.class})
public interface ApplicationComponent {

    MyApp getApplication();

    NewsApi getNetEaseApi();

    JanDanApi getJanDanApi();

    Context getContext();

}
