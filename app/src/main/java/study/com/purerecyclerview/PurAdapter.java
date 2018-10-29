package study.com.purerecyclerview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static study.com.purerecyclerview.PurRecyclerView.STATE_DEFAULT;
import static study.com.purerecyclerview.PurRecyclerView.STATE_PULLING;
import static study.com.purerecyclerview.PurRecyclerView.STATE_RELEASE_TO_REFRESH;

/**
 * Created by  HONGDA on 2018/10/23.
 */
public class PurAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int ITEM_HEAD = 0;
    private final static int ITEM_NORMAL = 1;
    private final static int ITEM_FOOT = 2;

    private List<String> strs;
    private LayoutInflater inflater;
    private Context ctx;

    private View headerView;
    private View footerView;

    private boolean isReleaseToRefresh = false;

    private boolean showHeaderView = false;

    private int rotationDuration = 200;
    private ValueAnimator ivAnim;

    public PurAdapter(Context context, List<String> datas) {
        inflater = LayoutInflater.from(context);
        ctx = context;
        strs = new ArrayList<>();
        strs.addAll(datas);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_HEAD:
                View view = inflater.inflate(R.layout.view_header, parent, false);
                HeaderHolder headerHolder = new HeaderHolder(view);
                headerView = headerHolder.viewTop;
                return headerHolder;
            case ITEM_NORMAL:
                View itemView = inflater.inflate(R.layout.item_view, parent, false);
                return new ItemHolder(itemView);
            case ITEM_FOOT:
                footerView = inflater.inflate(R.layout.view_footer, parent, false);
                return new FootHolder(footerView);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder == null) return;
        if (holder instanceof HeaderHolder) {
            HeaderHolder headerHolder = (HeaderHolder) holder;
//            Log.i("LHD", "showHeaderView = " + showHeaderView + "    isReleaseToRefresh   ==  " + isReleaseToRefresh);
            if (isReleaseToRefresh) {
                headerHolder.imgArrow.setImageResource(R.drawable.arrow_down);
                headerHolder.tvHeader.setText("松手刷新");
            } else {
                headerHolder.tvHeader.setText("下拉刷新");
            }
            startArrowAnim(headerHolder.imgArrow, -180f);
        } else if (holder instanceof FootHolder) {
            FootHolder footHolder = (FootHolder) holder;
            footHolder.tvFooter.setText("上拉加载更多");
        } else {
            ItemHolder itemHolder = (ItemHolder) holder;
            int itemPosition = position - 1;
            itemHolder.tvItem.setText(strs.get(itemPosition));
        }
    }

    @Override
    public int getItemCount() {
        return strs.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_HEAD;
        } else if (position == getItemCount() - 1) {// position 的取值范围是[0 , getItemCount()-1]  一共有getItemCount()个元素，要注意position是从0开始计算的
            return ITEM_FOOT;
        } else {
            return ITEM_NORMAL;
        }
    }

    public View getHeaderView() {
        return headerView;
    }

    public View getFooterView() {
        return footerView;
    }

    public void changeHeaderHeight(float distance) {
        Log.i("LHD", "distance = " + distance);
        if (headerView != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) headerView.getLayoutParams();
            layoutParams.height = (int) distance;
            headerView.setLayoutParams(layoutParams);
            notifyDataSetChanged();
        }
    }

    /**
     * 改变下拉刷新view的UI
     */
    public void changeHeaderUI(boolean isReleaseToRefresh) {
        this.isReleaseToRefresh = isReleaseToRefresh;
        notifyDataSetChanged();
    }

    public void roollBack() {

    }

    public void setShowHeaderView(boolean showHeaderView) {
        this.showHeaderView = showHeaderView;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private TextView tvItem;

        public ItemHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
        }

    }

    public class HeaderHolder extends RecyclerView.ViewHolder {
        private View viewTop;
        private ImageView imgArrow;
        private TextView tvHeader;

        public HeaderHolder(View itemView) {
            super(itemView);
            viewTop = itemView.findViewById(R.id.view_top);
            imgArrow = itemView.findViewById(R.id.img_arrow);
            tvHeader = itemView.findViewById(R.id.tv_header);
        }

    }

    public class FootHolder extends RecyclerView.ViewHolder {
        private ImageView imgArrow;
        private TextView tvFooter;

        public FootHolder(View itemView) {
            super(itemView);
            imgArrow = itemView.findViewById(R.id.img_arrow);
            tvFooter = itemView.findViewById(R.id.tv_footer);
        }

    }

    private void startArrowAnim(final ImageView imageView, float roration) {
        if (ivAnim != null) {
            ivAnim.removeAllUpdateListeners();
            ivAnim.cancel();
        }
        final float fRoration = roration;
        float startRotation = imageView.getRotation();
        ivAnim = ObjectAnimator.ofFloat(startRotation, fRoration).setDuration(rotationDuration);
        ivAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                imageView.setRotation((Float) animation.getAnimatedValue());
                if (((Float) animation.getAnimatedValue()) == fRoration) {
                    ivAnim.removeAllUpdateListeners();
                    ivAnim.cancel();
                }
            }
        });
        ivAnim.start();
    }
}
