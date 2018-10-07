package us.pinguo.shareelementdemo.advanced;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huangwei on 2018/9/19 0019.
 */
public class Image extends BaseData{

    private boolean mShowInFresco;

    public Image(String url, int width, int height) {
        super(url, width, height);
    }

    public boolean isShowInFresco() {
        return mShowInFresco;
    }

    public Image setShowInFresco(boolean showInFresco) {
        mShowInFresco = showInFresco;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte(this.mShowInFresco ? (byte) 1 : (byte) 0);
    }

    protected Image(Parcel in) {
        super(in);
        this.mShowInFresco = in.readByte() != 0;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
