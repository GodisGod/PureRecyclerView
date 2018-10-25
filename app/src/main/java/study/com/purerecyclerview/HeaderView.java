package study.com.purerecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by  HONGDA on 2018/10/25.
 * 可以根据自身高度改变自身状态的view
 */
public class HeaderView extends LinearLayout {

    private Context context;
    private LayoutInflater layoutInflater;

    public HeaderView(Context context) {
        this(context, null);
    }

    public HeaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        View headerView = layoutInflater.inflate(R.layout.view_header, this, false);
        addView(headerView);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        Log.i("LHD", "header View = setLayoutParams = " + params.height);

    }
}
