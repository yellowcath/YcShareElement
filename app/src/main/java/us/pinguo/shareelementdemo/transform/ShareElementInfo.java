package us.pinguo.shareelementdemo.transform;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import us.pinguo.shareelementdemo.R;

/**
 * Created by huangwei on 2018/9/22.
 */
public class ShareElementInfo<T extends Parcelable> implements Parcelable {

    protected transient View mView;
    /**
     * 用于存放{@link android.app.SharedElementCallback#onCreateSnapshotView}里的snapshot
     */
    protected Parcelable mSnapshot;
    /**
     * 存放View相关的数据。用于定位切换页面后新的ShareElement
     */
    protected T mData;
    /**
     * 实际做动画的View的大小,由{@link YcShareElement#recordShareElementsBaseBounds}负责填充
     */
    protected Rect mTansfromViewBounds = new Rect();

    public ShareElementInfo(@NonNull View view, @Nullable T data) {
        this.mView = view;
        this.mData = data;
        view.setTag(R.id.share_element_info, this);
    }

    public View getView() {
        return mView;
    }

    public Parcelable getSnapshot() {
        return mSnapshot;
    }

    public void setSnapshot(Parcelable snapshot) {
        mSnapshot = snapshot;
    }

    public T getData() {
        return mData;
    }

    public Rect getTansfromViewBounds() {
        return mTansfromViewBounds;
    }

    public void setTansfromViewBounds(Rect tansfromViewBounds) {
        mTansfromViewBounds.set(tansfromViewBounds);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mSnapshot, flags);
        dest.writeParcelable(mData, 0);
    }

    protected ShareElementInfo(Parcel in) {
        this.mSnapshot = in.readParcelable(Parcelable.class.getClassLoader());
        this.mData = in.readParcelable(getClass().getClassLoader());
    }

    public static final Creator<ShareElementInfo> CREATOR = new Creator<ShareElementInfo>() {
        @Override
        public ShareElementInfo createFromParcel(Parcel source) {
            return new ShareElementInfo(source);
        }

        @Override
        public ShareElementInfo[] newArray(int size) {
            return new ShareElementInfo[size];
        }
    };
}
