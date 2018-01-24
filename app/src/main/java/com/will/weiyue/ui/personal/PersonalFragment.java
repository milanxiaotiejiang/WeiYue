package com.will.weiyue.ui.personal;

import android.os.Bundle;
import android.view.View;

import com.will.weiyue.R;
import com.will.weiyue.component.ApplicationComponent;
import com.will.weiyue.ui.base.BaseFragment;
import com.will.weiyue.ui.base.SupportFragment;

/**
 * Created by android on 2018/1/16.
 */

public class PersonalFragment extends BaseFragment{

    public static PersonalFragment newInstance() {
        Bundle args = new Bundle();
        PersonalFragment fragment = new PersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_personal;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle saveInstanceState) {

    }

    @Override
    public void initData() {

    }

}
