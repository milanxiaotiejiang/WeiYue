package com.will.weiyue.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.will.weiyue.net.JanDanApi;
import com.will.weiyue.ui.base.BaseFragment;
import com.will.weiyue.ui.jandan.JdDetailFragment;

import java.util.List;

/**
 * Created by android on 2018/1/22.
 */

public class JanDanPagerAdapter extends FragmentStatePagerAdapter {

    private Activity mContext;
    private List<String> titles;

    public JanDanPagerAdapter(FragmentManager fm, Activity context, List<String> titles) {
        super(fm);
        this.mContext = context;
        this.titles = titles;
    }

    @Override
    public BaseFragment getItem(int position) {
        switch (position) {
            case 0:
                return JdDetailFragment.newInstance(JanDanApi.TYPE_FRESH, new FreshNewsAdapter(mContext, null));
            case 1:
                return JdDetailFragment.newInstance(JanDanApi.TYPE_BORED, new BoredPicAdapter(mContext, null));
            case 2:
                return JdDetailFragment.newInstance(JanDanApi.TYPE_GIRLS, new BoredPicAdapter(mContext, null));
            case 3:
                return JdDetailFragment.newInstance(JanDanApi.TYPE_Duan, new JokesAdapter(null));
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
