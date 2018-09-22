package us.pinguo.shareelementdemo.advanced;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huangwei on 2018/9/22.
 */
public class BaseData implements Parcelable {
    public String url;
    public int width;
    public int height;

    public BaseData(String url, int width, int height) {
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

    protected BaseData(Parcel in) {
        this.url = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
    }

}
