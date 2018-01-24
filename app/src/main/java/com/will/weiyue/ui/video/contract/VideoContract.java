package com.will.weiyue.ui.video.contract;

import com.will.weiyue.bean.VideoChannelBean;
import com.will.weiyue.bean.VideoDetailBean;
import com.will.weiyue.ui.base.BaseContract;

import java.util.List;

/**
 * Created by android on 2018/1/22.
 */

public interface VideoContract {

    interface View extends BaseContract.BaseView {

        void loadVideoChannel(List<VideoChannelBean> channelBean);

        void loadVideoDetails(List<VideoDetailBean> detailBean);

        void loadMoreVideoDetails(List<VideoDetailBean> detailBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getVideoChannel();

        void getVideoDetails(int page, String listType, String typeId);
    }
}
