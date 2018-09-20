package us.pinguo.shareelementdemo.advanced.content;

import android.os.Parcelable;
import us.pinguo.shareelementdemo.advanced.content.viewpager.BasePagerCell;
import us.pinguo.shareelementdemo.advanced.content.viewpager.BasePagerViewHolder;

/**
 * Created by huangwei on 2018/9/20.
 */
public abstract class BaseContentCell<DATA extends Parcelable> extends BasePagerCell<DATA ,BasePagerViewHolder> {
    public BaseContentCell(DATA data) {
        super(data);
    }
}
