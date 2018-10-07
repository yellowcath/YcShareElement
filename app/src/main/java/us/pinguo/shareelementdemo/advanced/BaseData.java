package us.pinguo.shareelementdemo.advanced;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseData baseData = (BaseData) o;
        return width == baseData.width &&
                height == baseData.height &&
                Objects.equals(url, baseData.url);
    }

    @Override
    public int hashCode() {

        return Objects.hash(url, width, height);
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
