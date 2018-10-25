package study.com.purerecyclerview;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by  HONGDA on 2018/10/25.
 */
public class Util {
    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }
}
