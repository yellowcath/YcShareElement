package us.pinguo.shareelementdemo.advanced.content.viewpager;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by huangwei on 2018/9/19 0019.
 */
public class BasePagerAdapter<DATA, CELL extends BasePagerCell<DATA, BasePagerViewHolder>> extends PagerAdapter {
    protected static final int MAX_CACHE_VIEW = 5;

    protected SparseArray<LinkedList<BasePagerViewHolder>> mViewCaches = new SparseArray<>();

    protected ArrayList<CELL> mDataList = new ArrayList<>();

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        BasePagerCell<DATA, BasePagerViewHolder> cell = (BasePagerCell<DATA, BasePagerViewHolder>) obj;
        return cell.mViewHolder != null && view == cell.mViewHolder.itemView;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        CELL cell = getItem(position);

        BasePagerViewHolder viewHolder = popViewFromCache(cell);
        if (viewHolder == null) {
            viewHolder = cell.createViewHolder(container);
        }

        if (viewHolder != null) {
            container.addView(viewHolder.itemView);
            cell.bindViewHolder(viewHolder);
        }
        return cell;
    }

    private BasePagerViewHolder popViewFromCache(CELL item) {
        if (item == null) {
            return null;
        }
        int type = item.getType();
        LinkedList<BasePagerViewHolder> cache = mViewCaches.get(type);

        if (cache != null && !cache.isEmpty()) {
            return cache.pop();
        }
        return null;
    }

    private void cacheItem(CELL cell) {
        int type = cell.getType();
        LinkedList<BasePagerViewHolder> cache = mViewCaches.get(type);
        if (cache == null) {
            cache = new LinkedList<BasePagerViewHolder>();
            mViewCaches.put(type, cache);
        }

        if (cell.mViewHolder != null && cache.size() < MAX_CACHE_VIEW) {
            cache.add(cell.mViewHolder);
        }
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object obj) {
        CELL cell = (CELL) obj;
        if (cell.mViewHolder != null && cell.mViewHolder.itemView != null) {
            container.removeView(cell.mViewHolder.itemView);
        }
        cacheItem(cell);
        cell.onViewRecycled();
    }

    @Override
    public int getCount() {
        if (mDataList == null) {
            return 0;
        }
        return mDataList.size();
    }

    public CELL getItem(int position) {
        if (mDataList != null && position < getCount() && position >= 0) {
            return mDataList.get(position);
        }
        return null;
    }

    public void removeItem(int index) {
        if (index >= 0 && index < getCount() && mDataList.size() > 0) {
            mDataList.remove(index);
        }
    }

    public int getItemIndex(CELL item) {
        if (mDataList == null) {
            return -1;
        } else {
            return mDataList.indexOf(item);
        }
    }


    public void addItems(List<CELL> itemList) {
        mDataList.addAll(itemList);
    }

    public void clear() {
        if (mDataList != null) {
            mDataList.clear();
        }
    }

    interface OnViewRecycleListener {
        void onViewRecycled();
    }
}
