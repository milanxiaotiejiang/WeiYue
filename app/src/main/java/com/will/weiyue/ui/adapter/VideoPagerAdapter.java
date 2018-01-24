package com.will.weiyue.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.will.weiyue.bean.VideoChannelBean;
import com.will.weiyue.ui.base.BaseFragment;
import com.will.weiyue.ui.video.DetailFragment;

/**
 * Created by android on 2018/1/22.
 */

public class VideoPagerAdapter extends FragmentStatePagerAdapter {


    private VideoChannelBean videoChannelBean;

    public VideoPagerAdapter(FragmentManager fm, VideoChannelBean videoChannelBean) {
        super(fm);
        this.videoChannelBean = videoChannelBean;
    }

    @Override
    public BaseFragment getItem(int position) {//clientvideo_9
        return DetailFragment.newInstance("clientvideo_" + videoChannelBean.getTypes().get(position).getId());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return videoChannelBean.getTypes().get(position).getName();
    }

    @Override
    public int getCount() {
        return videoChannelBean != null ? videoChannelBean.getTypes().size() : 0;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
