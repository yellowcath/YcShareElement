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
    private int mVideoWidth;
    private int mVideoHeight;

    public ShareVideoViewInfo(@NonNull View view, @Nullable T data, int videoWidth, int videoHeight) {
        super(view, data);
        mVideoWidth = videoWidth;
        mVideoHeight = videoHeight;
    }

    public int getVideoWidth() {
        return mVideoWidth;
    }

    public int getVideoHeight() {
        return mVideoHeight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mVideoWidth);
        dest.writeInt(this.mVideoHeight);
    }

    protected ShareVideoViewInfo(Parcel in) {
        super(in);
        this.mVideoWidth = in.readInt();
        this.mVideoHeight = in.readInt();
    }

    public static final Creator<ShareVideoViewInfo> CREATOR = new Creator<ShareVideoViewInfo>() {
        @Override
        public ShareVideoViewInfo createFromParcel(Parcel source) {
            return new ShareVideoViewInfo(source);
        }

        @Override
        public ShareVideoViewInfo[] newArray(int size) {
            return new ShareVideoViewInfo[size];
        }
    };
}
