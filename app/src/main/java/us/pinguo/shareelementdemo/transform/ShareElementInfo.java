package us.pinguo.shareelementdemo.transform;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import us.pinguo.shareelementdemo.R;

/**
 * Created by huangwei on 2018/9/22.
 */
public class ShareElementInfo implements Parcelable{

    public View view;

    public Parcelable originData;

    /**
     * 实际做动画的View的大小,由{@link YcShareElement#recordShareElementsBaseBounds}负责填充
     */
    public Rect tansfromViewBounds = new Rect();

    public ShareElementInfo(View view) {
        this.view = view;
        view.setTag(R.id.share_element_info,this);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.originData, flags);
    }

    protected ShareElementInfo(Parcel in) {
        this.originData = in.readParcelable(Parcelable.class.getClassLoader());
    }

    public static final Creator<ShareElementInfo> CREATOR = new Creator<ShareElementInfo>() {
        @Override
        public ShareElementInfo createFromParcel(Parcel source) {
            return new ShareElementInfo(source);
        }

        @Override
        public ShareElementInfo[] newArray(int size) {
            return new ShareElementInfo[size];
        }
    };
}
