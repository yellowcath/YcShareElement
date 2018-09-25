package us.pinguo.shareelementdemo.advanced.content;

import android.view.View;
import us.pinguo.shareelementdemo.advanced.BaseData;
import us.pinguo.shareelementdemo.advanced.content.viewpager.BasePagerCell;
import us.pinguo.shareelementdemo.advanced.content.viewpager.BasePagerViewHolder;

/**
 * Created by huangwei on 2018/9/20.
 */
public abstract class BaseContentCell<DATA extends BaseData> extends BasePagerCell<DATA ,BasePagerViewHolder> {
    public BaseContentCell(DATA data) {
        super(data);
    }

    public abstract View getShareElement();
}
