package us.pinguo.shareelementdemo.transform;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by huangwei on 2018/9/26.
 */
public class ShareVideoViewInfo<T extends Parcelable> extends ShareElementInfo<T> {
    public ShareVideoViewInfo(@NonNull View view, @Nullable T data) {
        super(view, data);
    }

    protected ShareVideoViewInfo(Parcel in) {
        super(in);
    }
}
