package com.will.weiyue.module;

import android.content.Context;

import com.will.weiyue.MyApp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by android on 2018/1/15.
 */

@Module
public class ApplicationModule {

    private Context mContext;

    public ApplicationModule(Context context) {
        this.mContext = context;
    }

    @Provides
    MyApp provideApplication() {
        return (MyApp) mContext.getApplicationContext();
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
