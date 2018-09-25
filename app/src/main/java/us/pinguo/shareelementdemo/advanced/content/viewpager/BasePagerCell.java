package us.pinguo.shareelementdemo.advanced.content.viewpager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huangwei on 2017/9/29.
 */
public abstract class BasePagerCell<DATA, VH extends BasePagerViewHolder> implements BasePagerAdapter.OnViewRecycleListener {

    protected DATA mData;
    protected VH mViewHolder;

    public BasePagerCell(DATA data) {
        mData = data;
    }


    protected abstract VH createViewHolder(ViewGroup parent);

    protected abstract void onBindViewHolder(VH viewHolder);

    protected abstract int getType();

    @Override
    public void onViewRecycled() {
        mViewHolder = null;
    }

    void bindViewHolder(VH viewHolder) {
        mViewHolder = viewHolder;
        onBindViewHolder(viewHolder);
    }

    void updateData(DATA data) {
        this.mData = data;
        if (mViewHolder != null) {
            onBindViewHolder(mViewHolder);
        }
    }

    public DATA getData() {
        return mData;
    }

    protected BasePagerViewHolder createHolderByLayout(int layoutId, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new BasePagerViewHolder(view);
    }

    public int hashCode() {
        if (mData != null) {
            return mData.hashCode();
        }
        return super.hashCode();
    }

    public VH getViewHolder() {
        return mViewHolder;
    }
}