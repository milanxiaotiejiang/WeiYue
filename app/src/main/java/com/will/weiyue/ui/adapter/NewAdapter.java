package com.will.weiyue.ui.adapter;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.will.weiyue.R;
import com.will.weiyue.bean.Channel;
import com.will.weiyue.ui.inter.OnChannelListener;

import java.util.List;

/**
 * Created by android on 2018/1/17.
 */

public class NewAdapter extends BaseMultiItemQuickAdapter<Channel, BaseViewHolder> {

    private boolean mIsEdit;
    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;
    private long startTime;

    private static final long SPACE_TIME = 100;

    private int ANIM_TIME = 360;

    public NewAdapter(List<Channel> data, ItemTouchHelper helper) {
        super(data);
        mIsEdit = false;
        this.mItemTouchHelper = helper;
        addItemType(Channel.TYPE_MY, R.layout.item_channel_title);
        addItemType(Channel.TYPE_MY_CHANNEL, R.layout.channel_rv_item);
        addItemType(Channel.TYPE_OTHER, R.layout.item_channel_title);
        addItemType(Channel.TYPE_OTHER_CHANNEL, R.layout.channel_rv_item);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final Channel item) {
        switch (helper.getItemViewType()) {
            case Channel.TYPE_MY:
                helper.setText(R.id.tvTitle, item.getChannelName());
                helper.getView(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!mIsEdit) {
                            startEditMode(true);
                        } else {
                            startEditMode(false);
                        }
                    }
                });
                helper.getView(R.id.tv_sort).setTag(true);
                break;
            case Channel.TYPE_MY_CHANNEL:
                helper.setText(R.id.tv_channelname, item.getChannelName())
                        .setVisible(R.id.img_edit, mIsEdit)
                        .addOnClickListener(R.id.img_edit);

                if (item.getChannelType() != 1) {
                    helper.getView(R.id.img_edit).setTag(true);
                    helper.getView(R.id.tv_channelname).setTag(false);
                } else {
                    helper.getView(R.id.tv_channelname).setTag(true);
                }
                helper.getView(R.id.rl_channel).setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (!mIsEdit) {
                            startEditMode(true);
                        }
                        mItemTouchHelper.startDrag(helper);
                        return false;
                    }
                });

                helper.getView(R.id.rl_channel).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (!mIsEdit)
                            return false;
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                startTime = System.currentTimeMillis();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                if (System.currentTimeMillis() - startTime > SPACE_TIME) {
                                    mItemTouchHelper.startDrag(helper);
                                }
                                break;
                            case MotionEvent.ACTION_CANCEL:
                            case MotionEvent.ACTION_UP:
                                startTime = 0;
                                break;
                        }
                        return false;
                    }
                });

                helper.getView(R.id.rl_channel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mIsEdit) {
                            if (item.getChannelType() == 1)
                                return;

                            int otherFirstPosition = getOtherFirstPosition();
                            int currentPosition = helper.getAdapterPosition();
                            View targetView = mRecyclerView.getLayoutManager().findViewByPosition(otherFirstPosition);
                            View currentView = mRecyclerView.getLayoutManager().findViewByPosition(currentPosition);
                            if (mRecyclerView.indexOfChild(targetView) >= 0 && otherFirstPosition != -1) {
                                RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
                                int spanCount = ((GridLayoutManager) manager).getSpanCount();
                                int targetX = targetView.getLeft();
                                int targetY = targetView.getTop();
                                int myChannelSize = getMyChannelSize();
                                if (myChannelSize % spanCount == 1) {
                                    targetY -= targetView.getHeight();
                                }
                                item.setItemtype(Channel.TYPE_OTHER_CHANNEL);
                                item.setChannelSelect(false);

                                if (onChannelListener != null)
                                    onChannelListener.onMoveToOtherChannel(currentPosition, otherFirstPosition - 1);

                                startAnimation(currentView, targetX, targetY);

                            } else {
                                item.setItemtype(Channel.TYPE_OTHER_CHANNEL);
                                item.setChannelSelect(false);
                                if (otherFirstPosition == -1)
                                    otherFirstPosition = mData.size();
                                if (onChannelListener != null)
                                    onChannelListener.onMoveToOtherChannel(currentPosition, otherFirstPosition - 1);
                            }

                        } else {
                            if (onChannelListener != null)
                                onChannelListener.onFinish(item.getChannelName());
                        }
                    }
                });
                break;
            case Channel.TYPE_OTHER:
                helper.setText(R.id.tvTitle, item.getChannelName())
                        .setText(R.id.tv_sort, "点击添加频道")
                        .setVisible(R.id.tv_edit, false);
                helper.getView(R.id.tv_sort).setTag(false);
                break;

            case Channel.TYPE_OTHER_CHANNEL:
                helper.setText(R.id.tv_channelname, item.getChannelName())
                        .setVisible(R.id.img_edit, false);

                helper.getView(R.id.tv_channelname).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int myLastPosition = getMyLastPosition();
                        int currentPosition = helper.getAdapterPosition();
                        //获取到目标View
                        View targetView = mRecyclerView.getLayoutManager().findViewByPosition(myLastPosition);
                        //获取当前需要移动的View
                        View currentView = mRecyclerView.getLayoutManager().findViewByPosition(currentPosition);

                        // 如果targetView不在屏幕内,则indexOfChild为-1  此时不需要添加动画,因为此时notifyItemMoved自带一个向目标移动的动画
                        // 如果在屏幕内,则添加一个位移动画
                        if (mRecyclerView.indexOfChild(targetView) >= 0 && myLastPosition != -1) {
                            RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
                            int spanCount = ((GridLayoutManager) manager).getSpanCount();
                            int targetX = targetView.getLeft() + targetView.getWidth();
                            int targetY = targetView.getTop();

                            int myChannelSize = getMyChannelSize();//这里我是为了偷懒 ，算出来我的频道的大小
                            if (myChannelSize % spanCount == 0) {
                                //添加到我的频道后会换行，所以找到倒数第4个的位置
                                View lastFourthView = mRecyclerView.getLayoutManager().findViewByPosition(getMyLastPosition() - 3);
//                                        View lastFourthView = mRecyclerView.getChildAt(getMyLastPosition() - 3);
                                targetX = lastFourthView.getLeft();
                                targetY = lastFourthView.getTop() + lastFourthView.getHeight();
                            }

                            // 推荐频道 移动到 我的频道的最后一个
                            item.setItemtype(Channel.TYPE_MY_CHANNEL);//改为推荐频道类型
                            item.setChannelSelect(true);

                            if (onChannelListener != null)
                                onChannelListener.onMoveToMyChannel(currentPosition, myLastPosition + 1);
                            startAnimation(currentView, targetX, targetY);
                        } else {
                            item.setItemtype(Channel.TYPE_MY_CHANNEL);//改为推荐频道类型
                            item.setChannelSelect(true);

                            if (myLastPosition == -1) myLastPosition = 0;//我的频道没有了，改成0
                            if (onChannelListener != null)
                                onChannelListener.onMoveToMyChannel(currentPosition, myLastPosition + 1);
                        }
                    }
                });

                break;

        }


    }


    private void startAnimation(final View currentView, int targetX, int targetY) {
        final ViewGroup parent = (ViewGroup) mRecyclerView.getParent();
        final ImageView mirrorView = addMirrorView(parent, currentView);
        TranslateAnimation animation = getTranslateAnimator(targetX - currentView.getLeft(), targetY - currentView.getTop());
        currentView.setVisibility(View.INVISIBLE);
        mirrorView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                parent.removeView(mirrorView);
                if (currentView.getVisibility() == View.INVISIBLE) {
                    currentView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private TranslateAnimation getTranslateAnimator(float targetX, float targetY) {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, targetX,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, targetY);
        translateAnimation.setDuration(ANIM_TIME);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }


    private ImageView addMirrorView(ViewGroup parent, View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        ImageView mirrorView = new ImageView(view.getContext());
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        mirrorView.setImageBitmap(bitmap);
        view.setDrawingCacheEnabled(false);
        int[] locations = new int[2];
        view.getLocationOnScreen(locations);
        int[] parenLocations = new int[2];
        mRecyclerView.getLocationOnScreen(parenLocations);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
        params.setMargins(locations[0], locations[1] - parenLocations[1], 0, 0);
        parent.addView(mirrorView, params);
        return mirrorView;
    }

    private int getMyLastPosition() {
        for (int i = mData.size() - 1; i > -1; i--) {
            Channel channel = (Channel) mData.get(i);
            if (Channel.TYPE_MY_CHANNEL == channel.getItemType()) {
                //找到第一个直接返回
                return i;
            }
        }
        return -1;
    }

    private int getMyChannelSize() {
        int size = 0;
        for (int i = 0; i < mData.size(); i++) {
            Channel channel = mData.get(i);
            if (channel.getItemType() == Channel.TYPE_MY_CHANNEL) {
                size++;
            }
        }
        return size;
    }


    private int getOtherFirstPosition() {
        for (int i = 0; i < mData.size(); i++) {
            Channel channel = mData.get(i);
            if (Channel.TYPE_OTHER_CHANNEL == channel.getItemType()) {
                return i;
            }
        }
        return -1;
    }


    private void startEditMode(boolean isEdit) {
        mIsEdit = isEdit;
        int visibleChildCount = mRecyclerView.getChildCount();
        for (int i = 0; i < visibleChildCount; i++) {
            View view = mRecyclerView.getChildAt(i);
            ImageView imgEdit = (ImageView) view.findViewById(R.id.img_edit);
            TextView tvName = (TextView) view.findViewById(R.id.tv_channelname);
            TextView tvEdit = (TextView) view.findViewById(R.id.tv_edit);
            TextView tvSort = (TextView) view.findViewById(R.id.tv_sort);

            if (imgEdit != null) {
                imgEdit.setVisibility(imgEdit.getTag() != null && isEdit ? View.VISIBLE : View.INVISIBLE);
            }

            if (tvName != null) {
                if (tvName.getTag() == null)
                    return;
                if (isEdit && (Boolean) tvName.getTag()) {
                    tvName.setTextColor(Color.GRAY);
                } else {
                    tvName.setTextColor(Color.BLACK);
                }
            }

            if (tvEdit != null) {
                if (isEdit) {
                    tvEdit.setText("完成");
                } else {
                    tvEdit.setText("编辑");
                }
            }

            if (tvSort != null) {
                if (!(Boolean) tvSort.getTag())
                    return;
                if (isEdit) {
                    tvSort.setText("拖动可以排序");
                } else {
                    tvSort.setText("点击进入频道");
                }
            }

        }
    }

    private OnChannelListener onChannelListener;

    public void onChannelListener(OnChannelListener listener) {
        this.onChannelListener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mRecyclerView = (RecyclerView) parent;
        return super.onCreateViewHolder(parent, viewType);
    }
}
