package com.will.weiyue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.will.weiyue.component.ApplicationComponent;
import com.will.weiyue.ui.base.BaseActivity;
import com.will.weiyue.utils.ImageLoaderUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by android on 2018/1/15.
 */

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.gifImageView)
    GifImageView gifImageView;
    @BindView(R.id.iv_ad)
    ImageView ivAd;
    @BindView(R.id.ll_bottom)
    RelativeLayout llBottom;
    @BindView(R.id.tv_skip)
    TextView tvSkip;
    @BindView(R.id.fl_ad)
    FrameLayout flAd;

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public int getContentLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle saveInstanceState) {
        final GifDrawable gifDrawable = (GifDrawable) gifImageView.getDrawable();
        gifDrawable.setLoopCount(1);
        gifImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                gifDrawable.start();
            }
        }, 100);

        ImageLoaderUtil.LoadImage(this, "http://api.dujin.org/bing/1920.php", ivAd);

        mCompositeDisposable.add(countDown(3)
                // doOnSubscribe() 执行在 subscribe() 发生的线程。而如果在 doOnSubscribe() 之后有 subscribeOn() 的话，它将执行在离它最近的 subscribeOn() 所指定的线程。
                .doOnSubscribe(
                        //实现简便式的观察者模式
                        //其中Consumer中的accept()方法接收一个来自Observable的单个值。Consumer就是一个观察者。其他函数式接口可以类似应用
                        new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                tvSkip.setText("跳过 4");
                            }
                        })
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        tvSkip.setText("跳过 " + (integer + 1));
                    }

                    //件队列异常。在事件处理过程中出异常时，onError() 会被触发，同时队列自动终止，不允许再有事件发出。
                    @Override
                    public void onError(Throwable e) {

                    }

                    //事件队列完结时调用该方法
                    @Override
                    public void onComplete() {
                        toMain();
                    }
                }));

    }

    @Override
    protected void onDestroy() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
        super.onDestroy();
    }

    private void toMain() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
        Intent intent = new Intent();
        intent.setClass(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private Observable<Integer> countDown(int time) {
        if (time < 0)
            time = 0;
        final int countTime = time;
        //利用interval()定时发送Observable
        //创建一个按固定时间间隔发射整数序列的Observable，可用作定时器
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                //通过map()将0、1、2、3...的计数变为...3、2、1、0倒计时
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long aLong) throws Exception {
                        return countTime - aLong.intValue();
                    }
                })
                //通过take()取>=0的Observable
                .take(countTime + 1);
    }

    @OnClick(R.id.fl_ad)
    public void onViewClicked() {
        toMain();
    }


    @Override
    public void initData() {
//        Observable<String> observable = new Observable<String>() {
//            @Override
//            protected void subscribeActual(Observer<? super String> observer) {
//                observer.onNext("发射");
//            }
//        };
//        Observer<String> observer = new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.d("WelcomeActivity", s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };
//        observable.subscribe(observer);
//
//        Observable.just("hello").subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.d("WelcomeActivity", s);
//            }
//        });
//
//        Observable<Long> observable1 = Observable.interval(2, TimeUnit.SECONDS);
//        Observer<Long> observer1 = new Observer<Long>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Long aLong) {
//                Log.d("WelcomeActivity", "aLong:" + aLong);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };
//        observable1.subscribe(observer1);
//
//        Observable.just("hello").map(new Function<String, Integer>() {
//            @Override
//            public Integer apply(String s) throws Exception {
//                return s.length();
//            }
//        });
//
//        List<String> list = null;
//        Observable.just(list).flatMap(new Function<List<String>, ObservableSource<?>>() {
//            @Override
//            public ObservableSource<?> apply(List<String> strings) throws Exception {
//                return Observable.fromIterable(strings);
//            }
//        }).filter(new Predicate<Object>() {
//            @Override
//            public boolean test(Object o) throws Exception {
//                String newStr = (String) o;
//                if(newStr.charAt(5) - '0' > 5){
//                    return true;
//                }
//                return false;
//            }
//        }).subscribe(new Consumer<Object>() {
//            @Override
//            public void accept(Object o) throws Exception {
//                Log.d("WelcomeActivity", "o:" + o);
//            }
//        });

    }

    @Override
    public void onRetry() {

    }
    /**
     *RxJava 的事件回调方法普通事件 onNext()
     *onComplete(): 事件队列完结时调用该方法。RxJava 不仅把每个事件单独处理，还会把它们看做一个队列
     *onError(): 事件队列异常。在事件处理过程中出异常时，onError() 会被触发，同时队列自动终止，不允许再有事件发出
     *onSubscribe()：RxJava 2.0 中新增的，传递参数为Disposable ，Disposable 相当于RxJava1.x中的Subscription,用于解除订阅
     */

    /**
     *create( )创建Observable最基本的创建方式
     *      ObservableOnSubscribe对象作为参数，它的作用相当于一个计划表，当 Observable被订阅的时候，ObservableOnSubscribe的subscribe()方法会自动被调用，事件序列就会依照设定依次触发
     *just()方式
     *      just中传递的参数将直接在Observer的onNext()方法中接收到
     *fromIterable()方式
     *      使用fromIterable()，遍历集合，发送每个item。相当于多次回调onNext()方法，每次传入一个item。
     *defer()方式
     *      当观察者订阅时，才创建Observable，并且针对每个观察者创建都是一个新的Observable。以何种方式创建这个Observable对象，当满足回调条件后，就会进行相应的回调。
     *interval( )方式
     *      创建一个按固定时间间隔发射整数序列的Observable，可用作定时器。即按照固定2秒一次调用onNext()方法。
     *range( )方式
     *      创建一个发射特定整数序列的Observable，第一个参数为起始值，第二个为发送的个数，如果为0则不发送，负数则抛异常
     *timer( )方式
     *      创建一个Observable，它在一个给定的延迟后发射一个特殊的值，即表示延迟2秒后，调用onNext()方法。
     *repeat( )方式
     *      创建一个Observable，该Observable的事件可以重复调用。
     */

    /**
     * map()操作符
     *      map()操作符，就是把原来的Observable对象转换成另一个Observable对象，同时将传输的数据进行一些灵活的操作，方便Observer获得想要的数据形式
     *flatMap()操作符
     *      flatMap()对于数据的转换比map()更加彻底，如果发送的数据是集合，flatmap()重新生成一个Observable对象，并把数据转换成Observer想要的数据形式。它可以返回任何它想返回的Observable对象
     *filter()操作符
     *      filter()操作符根据test()方法中，根据自己想过滤的数据加入相应的逻辑判断，返回true则表示数据满足条件，返回false则表示数据需要被过滤。
     *      最后过滤出的数据将加入到新的Observable对象中，方便传递给Observer想要的数据形式。
     *take()操作符
     *      take()操作符：输出最多指定数量的结果。
     *doOnNext()
     *      doOnNext()允许我们在每次输出一个元素之前做一些额外的事情。
     */

    /**
     *Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
     *Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
     *Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率
     *Schedulers.computation(): **计算所使用的 Scheduler
     *Android 还有一个专用的** AndroidSchedulers.mainThread()**，它指定的操作将在 Android 主线程运行
     *
     * subscribeOn(): 指定Observable(被观察者)所在的线程，或者叫做事件产生的线程。 *
     * observeOn(): 指定 Observer(观察者)所运行在的线程，或者叫做事件消费的线程
     *
     * https://www.jianshu.com/p/310726a75045
     */

    /**
     * Disposable简介
     * 切断Observer(观察者)与Observable(被观察者)之间的连接
     * 调用它的dispose()方法时, 它就会将Observer(观察者)与Observable(被观察者)之间的连接切断, 从而导致Observer(观察者)收不到事件
     */

    /**
     * Observable(被观察者)可以发送无限个onNext, Observer(观察者)也可以接收无限个onNext
     * 当Observable(被观察者)发送了一个onComplete后, Observable(被观察者)中onComplete之后的事件将会继续发送, 而Observer(观察者)收到onComplete事件之后将不再继续接收事件
     * 当Observable(被观察者)发送了一个onError后, Observable(被观察者)中onError之后的事件将继续发送, 而Observer(观察者)收到onError事件之后将不再继续接收事件
     */

    /**
     * compose()
     * 是唯一一个能从流中获取原生Observable的方法，因此，影响整个流的操作符（像subscribeOn()和observeOn()需要使用compose(), 相对的，如果你在flatMap()中使用
     * 这两个方法，它只会影响你创建flatMap()中的Observable，而不是流）
     * 当你创建一个Observable流并且内联了一堆操作符以后，compose()会立即执行，flatMap()则是在onNext()被调用以后才会执行
     * flatMap()效率低
     */
}
