package com.will.weiyue.ui.inter;

/**
 * Created by android on 2018/1/19.
 */

public interface OnChannelListener {

    void onItemMove(int starPos, int endPos);

    void onMoveToMyChannel(int starPos, int endPos);

    void onMoveToOtherChannel(int starPos, int endPos);

    void onFinish(String selectedChannelName);

}
