package us.pinguo.shareelementdemo.advanced.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by huangwei on 2018/9/18 0018.
 */
public abstract class BaseRecyclerCell<DATA,VH> {
    protected DATA mData;

    public BaseRecyclerCell(DATA data) {
        mData = data;
    }

    protected abstract void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position);

    protected abstract VH onCreateViewHolder(@NonNull ViewGroup parent);

    protected abstract int getType();
}
