package us.pinguo.shareelementdemo.advanced;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huangwei on 2018/9/19 0019.
 */
public class Image extends BaseData{
    public Image(String url, int width, int height) {
        super(url, width, height);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    protected Image(Parcel in) {
        super(in);
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
