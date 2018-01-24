package com.will.weiyue.net;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by android on 2018/1/16.
 */

public class RxSchedulers {

    static final ObservableTransformer schedulersTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {

            return (upstream)
                    //指定Observable(被观察者)所在的线程
                    .subscribeOn(Schedulers.io())
                    //指定 Observer(观察者)所运行在的线程
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) schedulersTransformer;
    }
}
