package com.will.weiyue.ui.inter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.will.weiyue.component.ApplicationComponent;

/**
 * Created by android on 2018/1/15.
 */

public interface IBase {

    View createView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState);

    View getView();

    int getContentLayout();

    void initInjector(ApplicationComponent appComponent);

    void bindView(View view, Bundle saveInstanceState);

    void initData();
}
