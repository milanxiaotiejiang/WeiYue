package com.will.weiyue.net;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by android on 2018/1/16.
 */

public abstract class BaseObserver<T> implements Observer<T> {

    public abstract void onSuccess(T t);

    public abstract void onFail(Throwable e);

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onFail(e);
    }

    @Override
    public void onComplete() {

    }
}
