package us.pinguo.shareelementdemo.advanced.viewpager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huangwei on 2017/9/29.
 */
public abstract class BasePagerCell<DATA, VH extends BasePagerViewHolder> implements BasePagerAdapter.OnViewRecycleListener {

    protected DATA mData;
    VH mViewHolder;

    public BasePagerCell(DATA data) {
        mData = data;
    }


    abstract VH createViewHolder(ViewGroup parent);

    protected abstract void onBindViewHolder(VH viewHolder);

    abstract int getType();

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
}