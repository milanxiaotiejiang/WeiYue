package com.will.weiyue.ui.news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.widget.MsgView;
import com.will.weiyue.R;
import com.will.weiyue.bean.Channel;
import com.will.weiyue.component.ApplicationComponent;
import com.will.weiyue.component.DaggerHttpComponent;
import com.will.weiyue.database.ChannelDao;
import com.will.weiyue.event.NewChannelEvent;
import com.will.weiyue.event.SelectChannelEvent;
import com.will.weiyue.ui.adapter.ChannelPagerAdapter;
import com.will.weiyue.ui.base.BaseFragment;
import com.will.weiyue.ui.news.contract.NewsContract;
import com.will.weiyue.ui.news.presenter.NewsPresenter;
import com.will.weiyue.widget.ChannelDialogFragment;
import com.will.weiyue.widget.CustomViewPager;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by android on 2018/1/16.
 */

public class NewsFragment extends BaseFragment<NewsPresenter> implements NewsContract.View {

    @BindView(R.id.viewpager)
    CustomViewPager mViewpager;
    @BindView(R.id.iv_edit)
    ImageView mIvEdit;
    @BindView(R.id.SlidingTabLayout)
    com.flyco.tablayout.SlidingTabLayout SlidingTabLayout;

    private ChannelPagerAdapter mChannelPagerAdapter;

    private List<Channel> mSelectedDatas;
    private List<Channel> mUnSelectedDatas;

    private int selectedIndex;
    private String selectedChannel;

    public static NewsFragment newInstance() {
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_news_new;
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
        EventBus.getDefault().register(this);


        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedIndex = position;
                selectedChannel = mSelectedDatas.get(position).getChannelName();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {
        mSelectedDatas = new ArrayList<>();
        mUnSelectedDatas = new ArrayList<>();
        mPresenter.getChannel();
    }


    @Override
    public void onRetry() {

    }

    @Override
    public void loadData(List<Channel> channels, List<Channel> otherChannels) {
        if (channels != null) {
            mSelectedDatas.clear();
            mSelectedDatas.addAll(channels);
            mUnSelectedDatas.clear();
            mUnSelectedDatas.addAll(otherChannels);
            mChannelPagerAdapter = new ChannelPagerAdapter(getChildFragmentManager(), channels);
            mViewpager.setAdapter(mChannelPagerAdapter);
            mViewpager.setOffscreenPageLimit(2);
            mViewpager.setCurrentItem(0, false);
            /** 关联ViewPager,用于不想在ViewPager适配器中设置titles数据的情况 */
            SlidingTabLayout.setViewPager(mViewpager);
            setSlidingTab();
        } else {
            T("数据异常");
        }
    }

    private void setSlidingTab() {
        /**显示指定位置未读红点*/
        SlidingTabLayout.showDot(4);
        /**隐藏指定位置未读红点或消息*/
        SlidingTabLayout.hideMsg(5);
        /**showMsg(int position, int num):position位置，num小于等于0显示红点,num大于0显示数字，作用：显示未读消息，如果消息数量>99,显示效果99+*/
        SlidingTabLayout.showMsg(3, 5);
        /**  setMsgMargin(int position, float leftPadding, float bottomPadding)设置未读消息偏移,原点为文字的右上角.当控件高度固定,消息提示位置易控制,显示效果佳 */
        SlidingTabLayout.setMsgMargin(3, 0, 10);
        /**设置未读消息消息的背景*/
        MsgView msgView = SlidingTabLayout.getMsgView(3);
        if (msgView != null) {
            msgView.setBackgroundColor(Color.parseColor("#6D8FB0"));
        }

    }


    @Subscriber
    private void updateChannel(NewChannelEvent event) {
        if (event == null)
            return;
        if (event.selectedDatas != null && event.unSelectedDatas != null) {
            mSelectedDatas = event.selectedDatas;
            mUnSelectedDatas = event.unSelectedDatas;
            mChannelPagerAdapter.updateChannel(mSelectedDatas);
            SlidingTabLayout.notifyDataSetChanged();
            ChannelDao.saveChannels(event.allChannels);

            List<String> integers = new ArrayList<>();
            for (Channel channel : mSelectedDatas) {
                integers.add(channel.getChannelName());
            }
            if (TextUtils.isEmpty(event.firstChannelName)) {
                if (!integers.contains(selectedChannel)) {
                    selectedChannel = mSelectedDatas.get(selectedIndex).getChannelName();
                    mViewpager.setCurrentItem(selectedIndex, false);
                } else {
                    setViewpagerPosition(integers, selectedChannel);
                }
            } else {
                setViewpagerPosition(integers, event.firstChannelName);
            }
        }
    }

    @Subscriber
    private void selectChannelEvent(SelectChannelEvent selectChannelEvent) {
        if (selectChannelEvent == null)
            return;
        List<String> integers = new ArrayList<>();
        for (Channel channel : mSelectedDatas) {
            integers.add(channel.getChannelName());
        }
        setViewpagerPosition(integers, selectChannelEvent.channelName);
    }

    private void setViewpagerPosition(List<String> integers, String channelName) {
        if (TextUtils.isEmpty(channelName) || integers == null)
            return;
        for (int j = 0; j < integers.size(); j++) {
            if (integers.get(j).equals(channelName)) {
                selectedChannel = integers.get(j);
                selectedIndex = j;
                break;
            }
        }
        mViewpager.postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewpager.setCurrentItem(selectedIndex, false);
            }
        }, 100);
    }

    @OnClick(R.id.iv_edit)
    public void onViewClicked() {
        ChannelDialogFragment dialogFragment = ChannelDialogFragment.newInstance(mSelectedDatas, mUnSelectedDatas);
        dialogFragment.show(getChildFragmentManager(), "CHANNEL");
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
