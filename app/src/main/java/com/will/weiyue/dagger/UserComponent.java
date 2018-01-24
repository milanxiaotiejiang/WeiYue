package com.will.weiyue.dagger;

import dagger.Component;

/**
 * Created by android on 2018/1/17.
 */

@Component(modules = {UserModule.class, ActivityModule.class})
public interface UserComponent {

    //当前只能写MainActivity，不能写Activity，要不然会出现空指针。
    void inject(DaggerActivity activity);
}
