package study.com.purerecyclerview.myrecyclerview;

import android.view.View;

/**
 * Created by  HONGDA on 2018/10/29.
 */
public interface RefreshListener {

    void onMove(float deltaY);

    void refreshComplete();

    boolean isReleaseToRefresh();

    boolean isRefreshing();

    void onRefresh();

    View getContainer();

    void smoothScrollTo(int dest);

    int getMeasuredHeight();

}
