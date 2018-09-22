package us.pinguo.shareelementdemo.transform;

import android.graphics.Rect;
import android.widget.ImageView;

/**
 * Created by huangwei on 2018/9/22.
 */
public class DefaultBitmapSizeCalculator implements IBitmapSizeCalculator {

    @Override
    public BitmapSize calculateImageSize(Rect bounds, ImageView.ScaleType scaleType, int originImageWidth, int originImageHeight) {
        return new BitmapSize(originImageWidth, originImageHeight);
    }
}
