package us.pinguo.shareelementdemo.transform;

import android.os.Parcel;
import android.view.View;

/**
 * Created by huangwei on 2018/9/22.
 */
public class ShareImageViewInfo extends ShareElementInfo {

    public int contentWidth;
    public int contentHeight;

    public ShareImageViewInfo(View view,int contentWidth,int contentHeight) {
        super(view);
        this.contentHeight = contentHeight;
        this.contentWidth = contentWidth;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.contentWidth);
        dest.writeInt(this.contentHeight);
    }

    protected ShareImageViewInfo(Parcel in) {
        super(in);
        this.contentWidth = in.readInt();
        this.contentHeight = in.readInt();
    }

    public static final Creator<ShareImageViewInfo> CREATOR = new Creator<ShareImageViewInfo>() {
        @Override
        public ShareImageViewInfo createFromParcel(Parcel source) {
            return new ShareImageViewInfo(source);
        }

        @Override
        public ShareImageViewInfo[] newArray(int size) {
            return new ShareImageViewInfo[size];
        }
    };
}
