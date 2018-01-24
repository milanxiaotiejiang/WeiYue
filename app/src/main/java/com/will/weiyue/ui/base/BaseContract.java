package com.will.weiyue.ui.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by android on 2018/1/15.
 */

public interface BaseContract {

    interface BasePresenter<T extends BaseView> {

        void attachView(T view);

        void detachView();
    }


    interface BaseView {
        //显示进度中
        void showLoading();
        //显示请求成功
        void showSuccess();
        //失败重试
        void showFaild();
        //显示当前网络不可用
        void showNoNet();
        //重试
        void onRetry();
        //绑定生命周期
        <T> LifecycleTransformer<T> bindToLife();
    }
}
