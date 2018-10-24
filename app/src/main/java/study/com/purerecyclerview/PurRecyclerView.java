package study.com.purerecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by  HONGDA on 2018/10/23.
 */
public class PurRecyclerView extends RecyclerView {

    View headerView;

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
//        LayoutInflater inflater = LayoutInflater.from(context);
//        headerView = inflater.inflate(R.layout.view_header, null);
//        addView(headerView);
    }
}
