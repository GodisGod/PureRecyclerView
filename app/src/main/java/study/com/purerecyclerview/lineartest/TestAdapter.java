package study.com.purerecyclerview.lineartest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import study.com.purerecyclerview.R;

/**
 * Created by  HONGDA on 2018/10/23.
 */
public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> strs;
    private LayoutInflater inflater;
    private Context ctx;

    public TestAdapter(Context context, List<String> datas) {
        inflater = LayoutInflater.from(context);
        ctx = context;
        strs = new ArrayList<>();
        strs.addAll(datas);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_view, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder == null) return;
        ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.tvItem.setText(strs.get(position));
    }

    @Override
    public int getItemCount() {
        return strs.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private TextView tvItem;

        public ItemHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
        }

    }

}
