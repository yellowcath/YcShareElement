package us.pinguo.shareelementdemo.advanced;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huangwei on 2018/9/19 0019.
 */
public class Video implements Parcelable {
    public String videoUrl;
    public String webpUrl;
    public int width;
    public int height;

    public Video(String videoUrl, String webpUrl, int width, int height) {
        this.videoUrl = videoUrl;
        this.webpUrl = webpUrl;
        this.width = width;
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.videoUrl);
        dest.writeString(this.webpUrl);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }

    protected Video(Parcel in) {
        this.videoUrl = in.readString();
        this.webpUrl = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
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
