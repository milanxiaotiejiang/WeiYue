package com.will.weiyue.ui.video;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.will.weiyue.R;
import com.will.weiyue.bean.VideoChannelBean;
import com.will.weiyue.bean.VideoDetailBean;
import com.will.weiyue.component.ApplicationComponent;
import com.will.weiyue.component.DaggerApplicationComponent;
import com.will.weiyue.component.DaggerHttpComponent;
import com.will.weiyue.ui.adapter.VideoPagerAdapter;
import com.will.weiyue.ui.base.BaseFragment;
import com.will.weiyue.ui.base.SupportFragment;
import com.will.weiyue.ui.video.contract.VideoContract;
import com.will.weiyue.ui.video.presenter.VideoPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by android on 2018/1/16.
 */

public class VideoFragment extends BaseFragment<VideoPresenter> implements VideoContract.View {

    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    private VideoPagerAdapter mVideoPagerAdapter;

    public static VideoFragment newInstance() {
        Bundle args = new Bundle();
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getContentLayout() {
        return R.layout.fragment_video;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {
        DaggerHttpComponent.builder()
                .applicationComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void bindView(View view, Bundle saveInstanceState) {

    }

    @Override
    public void initData() {
        mPresenter.getVideoChannel();
    }

    @Override
    public void loadVideoChannel(List<VideoChannelBean> channelBean) {
        mVideoPagerAdapter = new VideoPagerAdapter(getChildFragmentManager(), channelBean.get(0));
        mViewpager.setAdapter(mVideoPagerAdapter);
        mViewpager.setOffscreenPageLimit(1);
        mViewpager.setCurrentItem(0, false);
        mTablayout.setupWithViewPager(mViewpager, true);
    }

    @Override
    public void loadVideoDetails(List<VideoDetailBean> detailBean) {

    }

    @Override
    public void loadMoreVideoDetails(List<VideoDetailBean> detailBean) {

    }
}
