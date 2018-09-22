package us.pinguo.shareelementdemo.transform;

import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.request.target.Target;

/**
 * Created by huangwei on 2018/9/22.
 */
public class GlideBitmapSizeCalculator implements IBitmapSizeCalculator {

    @Override
    public BitmapSize calculateImageSize(Rect bounds, ImageView.ScaleType scaleType, int sourceWidth, int sourceHeight) {
        DownsampleStrategy downsampleStrategy;
        // Clone in this method so that if we use this RequestBuilder to load into a View and then
        // into a different target, we don't retain the transformation applied based on the previous
        // View's scale type.
        switch (scaleType) {
            case CENTER_CROP:
                downsampleStrategy = DownsampleStrategy.CENTER_OUTSIDE;
                break;
            case CENTER_INSIDE:
                downsampleStrategy = DownsampleStrategy.CENTER_INSIDE;
                break;
            case FIT_CENTER:
            case FIT_START:
            case FIT_END:
                downsampleStrategy = DownsampleStrategy.FIT_CENTER;
                break;
            case FIT_XY:
                downsampleStrategy = DownsampleStrategy.CENTER_INSIDE;
                break;
            case CENTER:
            case MATRIX:
            default:
                downsampleStrategy = DownsampleStrategy.DEFAULT;
        }
        boolean isSizeOriginal = false;
        int requestedWidth = bounds.width();
        int requestedHeight = bounds.height();
        int targetWidth = isSizeOriginal ? sourceWidth : requestedWidth;
        int targetHeight = isSizeOriginal ? sourceHeight : requestedHeight;
        float exactScaleFactor = downsampleStrategy.getScaleFactor(sourceWidth, sourceHeight, targetWidth,targetHeight);
        int outWidth = round(exactScaleFactor * sourceWidth);
        int outHeight = round(exactScaleFactor * sourceHeight);

        return new BitmapSize(outWidth,outHeight);
    }

    private static int round(double value) {
        return (int) (value + 0.5d);
    }
}
