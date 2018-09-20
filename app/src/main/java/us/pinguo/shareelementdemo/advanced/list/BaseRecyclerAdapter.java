package us.pinguo.shareelementdemo.advanced.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangwei on 2018/9/18 0018.
 */
public class BaseRecyclerAdapter<C extends BaseRecyclerCell> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<C> mCellList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        C cell = getItem(getFirstPositionByType(viewType));
        return cell == null ? null : (RecyclerView.ViewHolder) cell.onCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        getItem(position).bindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return mCellList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mCellList.size();
    }

    public C getItem(int position) {
        return mCellList.get(position);
    }

    public void setList(List<C> cellList) {
        mCellList.clear();
        mCellList.addAll(cellList);
        notifyDataSetChanged();
    }

    private int getFirstPositionByType(int viewType) {
        int size = getItemCount();
        for (int i = 0; i < size; i++) {
            if (getItem(i).getType() == viewType) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        int adapterPosition = holder.getAdapterPosition();
        BaseRecyclerCell cell = getItem(adapterPosition);
        cell.onViewRecycled();
    }
}
