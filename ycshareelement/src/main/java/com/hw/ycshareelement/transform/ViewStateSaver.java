package com.hw.ycshareelement.transform;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.transition.TransitionValues;
import android.view.View;

/**
 * Created by huangwei on 2018/10/7.
 */
public class ViewStateSaver implements Parcelable{
    /**
     * 保存View的额外信息
     * @param view
     * @param bundle
     */
    protected void captureViewInfo(View view,Bundle bundle){}
    /**
     * 将之前保存的信息设置到目标View上以供{@link android.transition.Transition#captureStartValues(TransitionValues)}获取
     */
    public void setViewState(View view,Bundle bundle){}



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ViewStateSaver() {
    }

    protected ViewStateSaver(Parcel in) {
    }

    public static final Creator<ViewStateSaver> CREATOR = new Creator<ViewStateSaver>() {
        @Override
        public ViewStateSaver createFromParcel(Parcel source) {
            return new ViewStateSaver(source);
        }

        @Override
        public ViewStateSaver[] newArray(int size) {
            return new ViewStateSaver[size];
        }
    };
}
