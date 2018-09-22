package us.pinguo.shareelementdemo.advanced;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huangwei on 2018/9/19 0019.
 */
public class Video extends BaseData {
    public String webpUrl;

    public Video(String url,String webpUrl, int width, int height) {
        super(url, width, height);
        this.webpUrl = webpUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.webpUrl);
    }

    protected Video(Parcel in) {
        super(in);
        this.webpUrl = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
