package com.will.weiyue.ui.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.will.weiyue.MyApp;
import com.will.weiyue.R;
import com.will.weiyue.ui.base.BaseContract;
import com.will.weiyue.ui.base.SupportActivity;
import com.will.weiyue.ui.inter.IBase;
import com.will.weiyue.utils.DialogHelper;
import com.will.weiyue.utils.StatusBarUtil;
import com.will.weiyue.utils.T;
import com.will.weiyue.widget.MultiStateView;
import com.will.weiyue.widget.SimpleMultiStateView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;
import io.reactivex.annotations.Nullable;
import me.yokeyword.fragmentation.SupportHelper;

/**
 * Created by android on 2018/1/15.
 */

public abstract class BaseActivity<T1 extends BaseContract.BasePresenter> extends SupportActivity
        implements IBase, BaseContract.BaseView, BGASwipeBackHelper.Delegate {

    protected View mRootView;
    protected Dialog mLoadingDialog = null;
    Unbinder unbinder;

    @Nullable
    @BindView(R.id.SimpleMultiStateView)
    SimpleMultiStateView mSimpleMultiStateView;

    @Nullable
    @Inject
    protected T1 mPresenter;
    protected BGASwipeBackHelper mSwipeBackHelper;

    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        mRootView = createView(null, null, savedInstanceState);
        setContentView(mRootView);
        initInjector(MyApp.getInstance().getApplicationComponent());
        attachView();
        bindView(mRootView, savedInstanceState);
        initStateView();
        initData();
        mLoadingDialog = DialogHelper.getLoadingDialog(this);
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = getLayoutInflater().inflate(getContentLayout(), container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public View getView() {
        return mRootView;
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    private void initStateView() {
        if (mSimpleMultiStateView == null)
            return;
        mSimpleMultiStateView.setEmptyResource(R.layout.view_empty)
                .setRetryResource(R.layout.view_retry)
                .setLoadingResource(R.layout.view_loading)
                .setNoNetResource(R.layout.view_nonet)
                .build()
                .setonReLoadlistener(new MultiStateView.onReLoadListener() {
                    @Override
                    public void onReload() {
                        onRetry();
                    }
                });
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);
        mSwipeBackHelper.setSwipeBackEnable(true) // 设置滑动返回是否可用。默认值为 true
                .setIsOnlyTrackingLeftEdge(false) // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
                .setIsWeChatStyle(true) // 设置是否是微信滑动返回样式。默认值为 true
                .setShadowResId(R.drawable.bga_sbl_shadow)// 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
                .setIsNeedShowShadow(true)// 设置是否显示滑动返回的阴影效果。默认值为 true
                .setIsShadowAlphaGradient(true)// 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
                .setSwipeBackThreshold(0.3f);
        ;// 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    protected void setStatusBarColor(@ColorInt int color) {
        setStatusBarColor(color, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     * @param statusBarAlpha 透明度
     */
    public void setStatusBarColor(@ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        StatusBarUtil.setColorForSwipeBack(this, color, statusBarAlpha);
    }

    protected void showLoadingDialog() {
        if (mLoadingDialog != null)
            mLoadingDialog.show();
    }

    protected void showLoadingDialog(String str) {
        if (mLoadingDialog != null) {
            TextView tv = (TextView) mLoadingDialog.findViewById(R.id.tv_load_dialog);
            tv.setText(str);
            mLoadingDialog.show();
        }
    }

    protected void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    protected SimpleMultiStateView getStateView() {
        return mSimpleMultiStateView;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void showLoading() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showLoadingView();
        }
    }

    @Override
    public void showSuccess() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showContent();
        }
    }

    @Override
    public void showFaild() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showErrorView();
        }
    }

    @Override
    public void showNoNet() {
        if (mSimpleMultiStateView != null) {
            mSimpleMultiStateView.showNoNetView();
        }
    }


    protected void T(String string) {
        T.showShort(MyApp.getContext(), string);
    }


    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    @Override
    public void onSwipeBackLayoutSlide(float v) {

    }

    @Override
    public void onSwipeBackLayoutCancel() {

    }

    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }
}
