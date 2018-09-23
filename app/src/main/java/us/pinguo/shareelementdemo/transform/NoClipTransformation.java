package us.pinguo.shareelementdemo.transform;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Created by huangwei on 2018/9/23.
 */
public class NoClipTransformation extends BitmapTransformation {
    private static final String ID = "com.bumptech.glide.load.resource.bitmap.nocrop";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return toTransform;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}
