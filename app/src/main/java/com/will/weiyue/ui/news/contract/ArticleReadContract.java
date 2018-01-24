package com.will.weiyue.ui.news.contract;

import com.will.weiyue.bean.NewsArticleBean;
import com.will.weiyue.ui.base.BaseContract;

/**
 * Created by android on 2018/1/17.
 */

public interface ArticleReadContract {

    interface View extends BaseContract.BaseView {

        void loadData(NewsArticleBean articleBean);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getData(String aid);

    }

}
