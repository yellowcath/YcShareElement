package us.pinguo.shareelementdemo.advanced;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huangwei on 2018/9/19 0019.
 */
public class Image implements Parcelable{
    public String url;
    public int width;
    public int height;

    public Image(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }

    protected Image(Parcel in) {
        this.url = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
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
