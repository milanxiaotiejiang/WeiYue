package com.will.weiyue.dagger;

/**
 * Created by android on 2018/1/17.
 */

public class DaggerPresenter {

    DaggerActivity activity;
    User user;

    public DaggerPresenter(DaggerActivity activity, User user) {
        this.user = user;
        this.activity = activity;
    }

    public void showUserName() {
        activity.showUserName(user.name);
    }

}
