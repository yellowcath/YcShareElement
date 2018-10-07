package com.hw.ycshareelement.transform;

import android.graphics.RectF;
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

    protected Bundle mBundle = new Bundle();

    protected ViewStateSaver mViewStateSaver;

    public ShareElementInfo(@NonNull View view, @Nullable T data) {
        this(view, data, null);
    }

    public ShareElementInfo(@NonNull View view, @Nullable T data, ViewStateSaver viewStateSaver) {
        this.mView = view;
        this.mData = data;
        view.setTag(R.id.share_element_info, this);
        mViewStateSaver = viewStateSaver;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public void setBundle(Bundle bundle) {
        mBundle = bundle;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mSnapshot, flags);
        dest.writeParcelable(this.mData, flags);
        dest.writeByte(this.mIsEnter ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.mViewStateSaver, flags);
        dest.writeBundle(mBundle);
    }

    protected ShareElementInfo(Parcel in) {
        this.mSnapshot = in.readParcelable(Parcelable.class.getClassLoader());
        this.mData = in.readParcelable(getClass().getClassLoader());
        this.mIsEnter = in.readByte() != 0;
        this.mViewStateSaver = in.readParcelable(ViewStateSaver.class.getClassLoader());
        this.mBundle = in.readBundle();
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

    public void captureExtraInfo(View view) {
        if (mViewStateSaver != null) {
            mViewStateSaver.captureViewInfo(view, mBundle);
        }
    }

    public void setStartViewState(View shareElementView) {
        if (mViewStateSaver != null) {
            Bundle originStateBundle = new Bundle();
            mViewStateSaver.captureViewInfo(shareElementView, originStateBundle);
            mViewStateSaver.setViewState(shareElementView, mBundle);
            mBundle.clear();
            mBundle.putAll(originStateBundle);
        }
    }

    public void setEndViewState(View shareElementView) {
        if (mViewStateSaver != null) {
            mViewStateSaver.setViewState(shareElementView, mBundle);
        }
    }
}
