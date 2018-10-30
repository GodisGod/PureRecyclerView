package study.com.purerecyclerview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


/**
 * Created by  HONGDA on 2018/10/23.
 */
public class PurRecyclerView extends RecyclerView {

    //    初始
    public final static int STATE_DEFAULT = 0;
    //    正在下拉
    public final static int STATE_PULLING = 1;
    //    松手刷新
    public final static int STATE_RELEASE_TO_REFRESH = 2;
    //    刷新中
    public final static int STATE_REFRESHING = 3;

    private int curState = STATE_DEFAULT;

    private float topY = 0;
    private float pullRatio = 0.5f;//阻尼系数
    private boolean isPulling = false;//是否正在下拉

    private Adapter purAdapter;
    private int headerViewHeight = 80;
    //    回弹动画
    private ValueAnimator valueAnimator;

    //计算滑动距离
    private float downY = 0;

    public PurRecyclerView(Context context) {
        this(context, null);
    }

    public PurRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PurRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    //添加header
    private void initView(Context context) {
        headerViewHeight = Util.dp2px(context, 80f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = e.getRawY();//距离屏幕上方的Y坐标
                break;
            case MotionEvent.ACTION_MOVE:
                boolean isTop = isTop();

                float currrentY = e.getRawY();
                float dis = currrentY - downY;
                Log.i("LHD", "摁下的坐标:  " + downY + "  滑动的距离:  " + dis + "  isTop = " + isTop);
                if (isTop) {
                    if (dis <= 0) {//todo 不再下拉

                    } else {
                        //改变headview的高度
//                        PurAdapter adapter = (PurAdapter) purAdapter;
//                        adapter.changeHeaderHeight(dis);
                    }
                    //计算阻尼后的滑动距离 = 滑动距离*阻尼系数
//                    float distance = (e.getY() - topY) * pullRatio;
                }

//                Log.i("LHD", "isTop  ==  " + isTop + "   e.getRawY()  =  " + e.getRawY() + "  headerViewHeight  =  " + headerViewHeight);
//                if (!isPulling) {
//                    if (isTop) {
//                        //recyclerView滑到最顶部的时候记录当前y坐标
//                        topY = e.getY();
//                    } else {//没有滑到最顶部的时候不处理
//                        break;
//                    }
//                }
//
//                //计算阻尼后的滑动距离 = 滑动距离*阻尼系数
//                float distance = (e.getY() - topY) * pullRatio;
////                Log.i("LHD", "onTouchEvent  ==  " + distance + "     (e.getRawY() - topY)   ==  " + (e.getRawY() - topY));
//                //若向上滑动，此时刷新头部已经隐藏，不处理
//                if (distance < 0) break;
//                isPulling = true;
//                //如果是刷新中，距离需要加上头部的高度
//                if (curState == STATE_REFRESHING) {
//                    distance += headerViewHeight;
//                }
//                setState(distance);
                break;
            case MotionEvent.ACTION_UP:
                replyPull();
                break;
        }


        return super.onTouchEvent(e);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        this.purAdapter = adapter;
    }

    /**
     * 判断是否滑到了顶部
     *
     * @return
     */
    private boolean isTop() {
        return !ViewCompat.canScrollVertically(this, -1);
    }

    /**
     * 判断当前是拖动中还是松手刷新
     * 刷新中不在此处判断，在手指抬起时才判断
     */
    private void setState(float distance) {
        //刷新中，状态不变
        if (curState == STATE_REFRESHING) {

        } else if (distance == 0) {
            curState = STATE_DEFAULT;
        } else if (distance >= headerViewHeight) { //文字提示: 松手刷新   过程：松手刷新 -> 刷新中 -> 刷新结束
            int lastState = curState;  //上一个状态是下拉中，现在改成准备松手刷新
            curState = STATE_RELEASE_TO_REFRESH;
//            Log.i("LHD", "刷新文案  调整箭头朝向  上上上上上上 ");
            //todo  刷新文案，调整箭头朝向
            changeRefreshUI(true);
        } else if (distance < headerViewHeight) {//文字提示: 下拉刷新
            int lastState = curState;    //上一个状态是default，现在改成下拉中
            curState = STATE_PULLING;
            //todo  刷新文案，调整箭头朝向
//            Log.i("LHD", "刷新文案  调整箭头朝向  下下下下下下 ");
            changeRefreshUI(false);
        }
        startPull(distance);
    }

    /**
     * 拖动或回弹时，改变顶部的margin
     */
    private void startPull(float distance) {
//            该view的高度不能为0，否则将无法判断是否已滑动到顶部
        if (distance < 1)
            distance = 1;
        //改变headview的高度
        PurAdapter adapter = (PurAdapter) purAdapter;
//        adapter.setShowHeaderView(true);
        adapter.changeHeaderHeight(distance);
    }

    /**
     * 松手回弹 1、非刷新状态回弹   2、刷新状态回弹
     */
    private void replyPull() {
        isPulling = false;
        //回弹位置
        float targetY = 0;
        Log.i("LHD", "replyPull  ===>>  " + curState);
        //判断当前状态
//        curState = STATE_DEFAULT;
        //刷新状态回弹
        if (curState == STATE_REFRESHING) { //刷新中  ->  回弹
            targetY = headerViewHeight;
        } else if (curState == STATE_RELEASE_TO_REFRESH) {//非刷新状态回弹：松手刷新 -> 刷新 -> 回弹
            //改变为刷新状态
            curState = STATE_REFRESHING;
            //todo  开始刷新
            if (refreshListener != null) {
                refreshListener.startRefresh();
            }
            targetY = headerViewHeight;
        } else if (curState == STATE_DEFAULT || curState == STATE_PULLING) {
            curState = STATE_DEFAULT;
        }

        PurAdapter adapter = (PurAdapter) purAdapter;
        View headView = adapter.getHeaderView();
        if (headView == null) return;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) headView.getLayoutParams();
        float distance = layoutParams.height;
        if (distance <= 0) return;

        valueAnimator = ObjectAnimator.ofFloat(distance, targetY).setDuration((long) (distance * 0.5));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float nowDistance = (float) animation.getAnimatedValue();
                startPull(nowDistance);
            }
        });
        valueAnimator.start();
    }

    //获取头部view的高度
    private int getHearderViewHeight() {
        if (purAdapter != null) {
            PurAdapter adapter = (PurAdapter) purAdapter;
            View headerView = adapter.getHeaderView();
            return headerView.getHeight();
        } else {
            return 0;
        }
    }

    //对外方法

    /**
     * 结束刷新
     */
    public void completeRefresh() {
        curState = STATE_DEFAULT;
        replyPull();
        //隐藏headerView
        purAdapter.notifyDataSetChanged();
    }

    private RefreshListener refreshListener;

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    /**
     * 调整文案和箭头朝向
     */
    private void changeRefreshUI(boolean isUP) {
        PurAdapter adapter = (PurAdapter) purAdapter;
        adapter.changeHeaderUI(isUP);
    }

}
