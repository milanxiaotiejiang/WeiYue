package com.will.weiyue.dagger;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

/**
 * Created by android on 2018/1/17.
 */

@Module
public class ActivityModule {

    private DaggerActivity activity;

    public ActivityModule(DaggerActivity activity) {
        this.activity = activity;
    }

    @Provides
    public DaggerActivity provideActivity() {
        return activity;
    }

    @Provides
    public User provideUser() {
        return new User("user from ActivityModule");
    }

    @Provides
    public DaggerPresenter provideDaggerPresenter(DaggerActivity activity, User user) {
        return new DaggerPresenter(activity, user);
    }
}
