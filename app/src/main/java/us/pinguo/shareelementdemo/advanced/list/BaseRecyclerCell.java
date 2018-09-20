package us.pinguo.shareelementdemo.advanced.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by huangwei on 2018/9/18 0018.
 */
public abstract class BaseRecyclerCell<DATA,VH extends RecyclerView.ViewHolder> {
    protected DATA mData;
    protected VH mViewHolder;

    public BaseRecyclerCell(DATA data) {
        mData = data;
    }

    void bindViewHolder(@NonNull VH holder, int position){
        mViewHolder = holder;
        onBindViewHolder(holder,position);
    }

    protected abstract void onBindViewHolder(@NonNull VH holder, int position);

    protected abstract VH onCreateViewHolder(@NonNull ViewGroup parent);

    protected abstract int getType();

    public DATA getData(){
        return mData;
    }

    public void onViewRecycled() {
        mViewHolder = null;
    }
}
