package study.com.purerecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  HONGDA on 2018/10/23.
 */
public class PurAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int ITEM_HEAD = 0;
    private final static int ITEM_NORMAL = 1;
    private final static int ITEM_FOOT = 2;

    private List<String> strs;
    private LayoutInflater inflater;

    public PurAdapter(Context context, List<String> datas) {
        inflater = LayoutInflater.from(context);
        strs = new ArrayList<>();
        strs.addAll(datas);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = inflater.inflate(R.layout.item_view, parent, true);
//        View view = inflater.inflate(R.layout.item_view, parent); 会报错n: ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflate

//        View view = inflater.inflate(R.layout.item_view, parent, false);

        switch (viewType) {
            case ITEM_HEAD:
                View view = inflater.inflate(R.layout.view_header, null);
                return new HeaderHolder(view);
            case ITEM_NORMAL:
                View headerView = inflater.inflate(R.layout.item_view, null);
                return new ItemHolder(headerView);
            case ITEM_FOOT:
                View footView = inflater.inflate(R.layout.view_footer, null);
                return new FootHolder(footView);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder == null) return;
        int type = getItemViewType(position);
        if (holder instanceof HeaderHolder) {
            HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.tvHeader.setText("下拉刷新");
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

    public class ItemHolder extends RecyclerView.ViewHolder {
        private TextView tvItem;

        public ItemHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
        }

    }

    public class HeaderHolder extends RecyclerView.ViewHolder {
        private ImageView imgArrow;
        private TextView tvHeader;

        public HeaderHolder(View itemView) {
            super(itemView);
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

}
