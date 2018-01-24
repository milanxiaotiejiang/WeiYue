package com.will.weiyue.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by android on 2018/1/17.
 */

public class HackyViewPager extends ViewPager {


    private static final String TAG = "HackyViewPager";

    public HackyViewPager(Context context) {
        super(context);
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            Log.e(TAG,"hacky viewpager error1");
            return false;
        }catch(ArrayIndexOutOfBoundsException e ){
            Log.e(TAG,"hacky viewpager error2");
            return false;
        }
    }
}
