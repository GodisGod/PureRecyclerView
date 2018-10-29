package study.com.purerecyclerview.myrecyclerview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import study.com.purerecyclerview.R;

/**
 * Created by  HONGDA on 2018/10/29.
 */
public class MyRefreshHeader implements RefreshListener {

    private final String TAG = getClass().getSimpleName();

    private LinearLayout container;
    private ImageView imgArrow;
    private TextView tvRefresh;
    private int headerViewHeight;

    private ImageView imgRefresh;

    private View rootView;
    private String refreshStr;

    private boolean refreshing = false;
    private boolean releaseToRefresh = false;
    private boolean rotateDown = true;
    private boolean rotateUp = true;

    public MyRefreshHeader(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.item_refresh_header, new LinearLayout(context), false);
        container = (LinearLayout) rootView.findViewById(R.id.ll_header_content);
        imgArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);

        imgRefresh = (ImageView) rootView.findViewById(R.id.iv_refresh);
        tvRefresh = (TextView) rootView.findViewById(R.id.tv_refresh_text);

        rootView.measure(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        headerViewHeight = rootView.getMeasuredHeight();
        Log.e(TAG, "mMeasuredHeight:" + headerViewHeight);
        setVisibleHeight(0);
    }

    private int getVisibleHeight() {
        ViewGroup.LayoutParams lp = container.getLayoutParams();
        return lp.height;
    }

    private void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        ViewGroup.LayoutParams lp = container.getLayoutParams();
        lp.height = height;
        container.setLayoutParams(lp);
    }

    @Override
    public void onMove(float deltaY) {
        setVisibleHeight((int) deltaY + getVisibleHeight());
        if (getVisibleHeight() > headerViewHeight) {
            if (!releaseToRefresh) {
                releaseToRefresh = true;
                if (rotateDown) {
                    // 满足释放刷新的条件
                    rotateUp = true;
                    ObjectAnimator rotate =
                            ObjectAnimator.ofFloat(imgArrow, "rotation", 0f, 180f);
                    tvRefresh.setText("释放刷新");
                    rotate.setDuration(200);
                    rotate.start();
                    rotateDown = false;
                }
            }
//            else {
//                releaseToRefresh = false;
//            }
        } else {
            if (rotateUp) {
                rotateDown = true;
                ObjectAnimator rotate = ObjectAnimator.ofFloat(imgArrow, "rotation", 180f, 0f);
                rotate.setDuration(200);
                rotate.start();
                rotateUp = false;
            }
        }
    }

    @Override
    public void refreshComplete() {
        refreshing = false;
        if (releaseToRefresh) {
            ObjectAnimator rotate =
                    ObjectAnimator.ofFloat(imgArrow, "rotation", 180f, 0f);
            rotate.setDuration(200);
            rotate.start();
        }
        releaseToRefresh = false;
        tvRefresh.setText("下拉刷新");
        smoothScrollTo(0);
        setNormalMode();
    }

    @Override
    public boolean isReleaseToRefresh() {
        return releaseToRefresh;
    }

    @Override
    public boolean isRefreshing() {
        return refreshing;
    }

    @Override
    public void onRefresh() {
        refreshing = true;
        smoothScrollTo(headerViewHeight);
        imgArrow.setVisibility(View.GONE);
        imgRefresh.setVisibility(View.VISIBLE);
        AnimationDrawable drawable =
                (AnimationDrawable) imgRefresh.getDrawable();
        drawable.start();
        if (TextUtils.isEmpty(this.refreshStr)) {
            tvRefresh.setText("刷新中...");
        } else {
            tvRefresh.setText(this.refreshStr);
        }
    }

    @Override
    public View getContainer() {
        return container;
    }

    @Override
    public void smoothScrollTo(int dest) {
        if(dest == 0)
            setNormalMode();
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), dest);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    @Override
    public int getMeasuredHeight() {
        return headerViewHeight;
    }

    public void setNormalMode(){
        imgRefresh.setVisibility(View.GONE);
        imgArrow.setVisibility(View.VISIBLE);
        tvRefresh.setText("下拉刷新");
    }

    public void setRefreshStr(String refreshStr) {
        this.refreshStr = refreshStr;
    }

}
