package com.will.weiyue.dagger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.will.weiyue.R;

import javax.inject.Inject;

/**
 * Created by android on 2018/1/17.
 */

public class DaggerActivity extends AppCompatActivity {

    /**
     * @Inject： 此注解用于告诉Dagger2，我们需要这个类的实例对象。主要用于标记哪个类是需要注入的。
     */
    @Inject
    DaggerPresenter presenter;

    @Inject
    ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DaggerActivityComponent.builder()
//                .activityModule(new ActivityModule(this))
//                .build()
//                .inject(this);
//
//        presenter.showUserName();

        //Dagger会自动创建这个类，以Dagger开头+UserComponent
//        DaggerUserComponent.create()
//                .inject(this);
//        apiService.register();

        //主要两个问题:
        //1）自定义Module需要传递上下文怎么办
        //2）自定义Module中的方法中需要参数对象怎么处理？(构造函数、自己提供方法)

        //Dagger的关系非常简单，MainActivity中需要对象，那么就在Module中提供对象；而他们之间的桥梁就是componnent


        //自定义类Module的构造方法中需要传入一个上下文对象，那么在MainActivity中就需要传递进去。
        //使用下面的这种方式进行参数传递:this就是上下文
//        DaggerUserComponnent.builder().userModule(new UserModule(this)).build().inject(this);
        DaggerUserComponent.builder()
                .userModule(new UserModule(this))
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
        apiService.register();

        presenter.showUserName();
    }

    public void showUserName(String name) {
        Log.d("DaggerActivity", name);
    }

    /**
     * @Named与@Qualifier：用于区别不同对象的实例。必须要成对出现，否则会报错。
     * @PerActivity：限定对象的生命周期和Activity一样。一般应用于自定义的Component上。
     * @Singleton：标记为单例模式，如果在自定义Module中使用了此注解，在自定义的Component上也要使用该注解。
     */

    /**
     1）自定义Module，里面的方法用于提供依赖，

     2）自定义Component接口，里面全是Activity的注入方法，

     3）按Ctrl+F9进行编译。
     */

    /**
     * 自定义module中的方法 要以provide开头
     * 通过构造方法传递Context
     */
}
