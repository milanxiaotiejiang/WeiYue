package com.will.weiyue.ui.news.contract;

import com.will.weiyue.bean.NewsDetail;
import com.will.weiyue.ui.base.BaseContract;

import java.util.List;

/**
 * Created by android on 2018/1/16.
 */

public interface DetailContract {


    interface View extends BaseContract.BaseView {


        void loadBannerData(NewsDetail newsDetail);

        void loadTopNewsData(NewsDetail newsDetail);

        void loadData(List<NewsDetail.ItemBean> itemBeanList);

        void loadMoreData(List<NewsDetail.ItemBean> itemBeanList);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getData(String id, String action, int pullNum);

    }
}
