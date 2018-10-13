package com.hw.ycshareelement.transition;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by huangwei on 2018/10/7.
 */
public class ViewStateSaver implements Parcelable {

    public ViewStateSaver() {
    }


    protected void captureViewInfo(View view, Bundle bundle) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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
