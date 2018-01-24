package com.will.weiyue.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.will.weiyue.R;

/**
 * Created by android on 2018/1/16.
 */

public final class CustomLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.view_load_more;
    }

    @Override
    public boolean isLoadEndGone() {
        return true;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return 0;
    }


}
