package com.will.weiyue.dagger;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by android on 2018/1/17.
 */

public class UserStore {

    //通过构造函数提供依赖


    @Inject
    public UserStore() {
    }

    private static final String TAG = "UserStore";

    public  void register(){
        Log.i(TAG,"测试数据");

    }
}
