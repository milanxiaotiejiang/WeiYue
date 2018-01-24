package com.will.weiyue.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;

import com.will.weiyue.R;

/**
 * Created by android on 2018/1/15.
 */

public class SimpleMultiStateView extends MultiStateView {

    private static final int MIN_SHOW_TIME = 400; // ms
    private static final int MIN_DELAY = 600; // ms

    private int mTargetState = -1;
    private long mLoadingStartTime = -1;

    private final Runnable mLoadingHide = new Runnable() {
        @Override
        public void run() {
            setViewState(mTargetState);
            mLoadingStartTime = -1;
            mTargetState = -1;
        }
    };


    int resIdEmpty;
    int resIdLoading;
    int resIdFail;
    int resIdNonet;

    public SimpleMultiStateView(Context context) {
        this(context, null);
    }

    public SimpleMultiStateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleMultiStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.msv_SimpleMultiStateView);
        resIdEmpty = typedArray.getResourceId(R.styleable.msv_SimpleMultiStateView_msv_emptyView, -1);
        resIdLoading = typedArray.getResourceId(R.styleable.msv_SimpleMultiStateView_msv_loadingView, -1);
        resIdFail = typedArray.getResourceId(R.styleable.msv_SimpleMultiStateView_msv_failView, -1);
        resIdNonet = typedArray.getResourceId(R.styleable.msv_SimpleMultiStateView_msv_nonetView, -1);
        typedArray.recycle();
        if (typedArray != null) {
            if (resIdEmpty != -1) {
                addViewForStatus(MultiStateView.STATE_EMPTY, resIdEmpty);
            }
            if (resIdFail != -1) {
                addViewForStatus(MultiStateView.STATE_FAIL, resIdFail);
            }
            if (resIdLoading != -1) {
                addViewForStatus(MultiStateView.STATE_LOADING, resIdLoading);
            }
            if (resIdNonet != -1) {
                addViewForStatus(MultiStateView.STATE_NONET, resIdNonet);
            }

        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        removeCallbacks(mLoadingHide);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(mLoadingHide);
    }

    public void showLoadingView() {
        setViewState(MultiStateView.STATE_LOADING);
    }

    public void showErrorView() {
        if (getViewState() != MultiStateView.STATE_CONTENT) {
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setViewState(MultiStateView.STATE_FAIL);
                }
            }, 100);
        }

    }

    public void showEmptyView() {
        if (getViewState() != MultiStateView.STATE_CONTENT) {
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setViewState(MultiStateView.STATE_EMPTY);
                }
            }, 100);
        }

    }

    public void showNoNetView() {
        if (getViewState() != MultiStateView.STATE_CONTENT) {
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setViewState(MultiStateView.STATE_NONET);
                }
            }, 100);

        }

    }

    public void showContent() {

        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                setViewState(MultiStateView.STATE_CONTENT);
            }
        }, 100);
    }

    @Override
    public void setViewState(int state) {
        if (getViewState() == STATE_LOADING && state != STATE_LOADING) {
            long diff = System.currentTimeMillis() - mLoadingStartTime;
            if (diff < MIN_SHOW_TIME) {
                mTargetState = state;
                postDelayed(mLoadingHide, MIN_DELAY);
            } else {
                super.setViewState(state);
            }
            return;
        } else if (state == STATE_LOADING) {
            mLoadingStartTime = System.currentTimeMillis();
        }
        super.setViewState(state);
    }


    public SimpleMultiStateView setEmptyResource(@LayoutRes int emptyResource) {
        this.resIdEmpty = emptyResource;
        addViewForStatus(MultiStateView.STATE_EMPTY, resIdEmpty);
        return this;
    }

    public SimpleMultiStateView setRetryResource(@LayoutRes int retryResource) {
        this.resIdFail = retryResource;
        addViewForStatus(MultiStateView.STATE_FAIL, resIdFail);
        return this;
    }

    public SimpleMultiStateView setLoadingResource(@LayoutRes int loadingResource) {
        resIdLoading = loadingResource;
        addViewForStatus(MultiStateView.STATE_LOADING, resIdLoading);
        return this;
    }

    public SimpleMultiStateView setNoNetResource(@LayoutRes int noNetResource) {
        resIdNonet = noNetResource;
        addViewForStatus(MultiStateView.STATE_NONET, resIdNonet);
        return this;
    }

    public SimpleMultiStateView build() {
        showLoadingView();
        return this;
    }

}
