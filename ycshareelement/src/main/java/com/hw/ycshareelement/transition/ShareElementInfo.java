package com.hw.ycshareelement.transition;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.hw.ycshareelement.R;

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
     * 用于Transition判断当前是进入还是退出
     */
    protected boolean mIsEnter;

    protected Bundle mFromViewBundle = new Bundle();

    protected Bundle mToViewBundle = new Bundle();

    protected ViewStateSaver mViewStateSaver;

    public ShareElementInfo(@NonNull View view) {
        this(view, null, null);
    }

    public ShareElementInfo(@NonNull View view, @Nullable T data) {
        this(view, data, null);
    }

    public ShareElementInfo(@NonNull View view, ViewStateSaver viewStateSaver) {
        this(view, null, viewStateSaver);
    }

    public ShareElementInfo(@NonNull View view, @Nullable T data, ViewStateSaver viewStateSaver) {
        this.mView = view;
        this.mData = data;
        view.setTag(R.id.share_element_info, this);
        mViewStateSaver = viewStateSaver;
    }

    public Bundle getFromViewBundle() {
        return mFromViewBundle;
    }

    public void setFromViewBundle(Bundle fromViewBundle) {
        mFromViewBundle = fromViewBundle;
    }

    public Bundle getToViewBundle() {
        return mToViewBundle;
    }

    public void setToViewBundle(Bundle toViewBundle) {
        mToViewBundle = toViewBundle;
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

    public boolean isEnter() {
        return mIsEnter;
    }

    public void setEnter(boolean enter) {
        mIsEnter = enter;
    }

    public ViewStateSaver getViewStateSaver() {
        return mViewStateSaver;
    }

    public static ShareElementInfo getFromView(View view) {
        if (view == null) {
            return null;
        }
        Object tag = view.getTag(R.id.share_element_info);
        return tag instanceof ShareElementInfo ? (ShareElementInfo) tag : null;
    }

    public static void saveToView(View view, ShareElementInfo info) {
        if (view == null) {
            return;
        }
        view.setTag(R.id.share_element_info, info);
    }


    public void captureFromViewInfo(View view) {
        if (mViewStateSaver != null) {
            mViewStateSaver.captureViewInfo(view, mFromViewBundle);
        }
    }

    public void captureToViewInfo(View view) {
        if (mViewStateSaver != null) {
            mViewStateSaver.captureViewInfo(view, mToViewBundle);
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mSnapshot, flags);
        dest.writeParcelable(this.mData, flags);
        dest.writeByte(this.mIsEnter ? (byte) 1 : (byte) 0);
        dest.writeBundle(this.mFromViewBundle);
        dest.writeBundle(this.mToViewBundle);
        dest.writeParcelable(this.mViewStateSaver, flags);
    }

    protected ShareElementInfo(Parcel in) {
        this.mSnapshot = in.readParcelable(Parcelable.class.getClassLoader());
        this.mData = in.readParcelable(getClass().getClassLoader());
        this.mIsEnter = in.readByte() != 0;
        this.mFromViewBundle = in.readBundle();
        this.mToViewBundle = in.readBundle();
        this.mViewStateSaver = in.readParcelable(ViewStateSaver.class.getClassLoader());
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
