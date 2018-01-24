package com.will.weiyue.ui.jandan;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.will.weiyue.R;
import com.will.weiyue.component.ApplicationComponent;
import com.will.weiyue.ui.adapter.JanDanPagerAdapter;
import com.will.weiyue.ui.base.BaseFragment;
import com.will.weiyue.ui.base.SupportFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by android on 2018/1/16.
 */

public class JanDanFragment extends BaseFragment{

    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    private JanDanPagerAdapter mJanDanPagerAdapter;

    public static JanDanFragment newInstance() {
        Bundle args = new Bundle();
        JanDanFragment fragment = new JanDanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_jiandan;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle saveInstanceState) {

    }

    @Override
    public void initData() {
        List<String> strings = new ArrayList<>();
        strings.add("新鲜事");
        strings.add("无聊图");
        strings.add("妹子图");
        strings.add("段子");
        mJanDanPagerAdapter = new JanDanPagerAdapter(getChildFragmentManager(), getActivity(), strings);
        mViewpager.setAdapter(mJanDanPagerAdapter);
        mViewpager.setOffscreenPageLimit(1);
        mViewpager.setCurrentItem(0, false);
        mTabLayout.setupWithViewPager(mViewpager, true);
    }

    @Override
    public void onRetry() {

    }


}
