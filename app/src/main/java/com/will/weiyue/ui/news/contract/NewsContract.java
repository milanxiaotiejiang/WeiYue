package com.will.weiyue.ui.news.contract;

import com.will.weiyue.bean.Channel;
import com.will.weiyue.ui.base.BaseContract;

import java.util.List;

/**
 * Created by android on 2018/1/16.
 */

public interface NewsContract {


    interface View extends BaseContract.BaseView {

        void loadData(List<Channel> channels, List<Channel> otherChannels);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getChannel();

    }
}
