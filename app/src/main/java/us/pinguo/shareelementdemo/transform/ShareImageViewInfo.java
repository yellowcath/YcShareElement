package us.pinguo.shareelementdemo.transform;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import us.pinguo.shareelementdemo.advanced.BaseData;

import java.util.Map;

/**
 * Created by huangwei on 2018/9/22.
 */
public class ShareImageViewInfo<T extends Parcelable> extends ShareElementInfo<T> {

    /**
     * 实际做动画的ImageView的ScaleType,由{@link YcShareElement#recordShareElementsBaseBounds(Map)}负责填充
     */
    protected transient ImageView.ScaleType mTranfromViewScaleType = ImageView.ScaleType.FIT_CENTER;

    private int mImageWidth;
    private int mImageHeight;

    public ShareImageViewInfo(View view, T data,int imageWidth,int imageHeight) {
        super(view, data);
        mImageWidth = imageWidth;
        mImageHeight = imageHeight;
    }

    public int getImageWidth() {
        return mImageWidth;
    }

    public int getImageHeight() {
        return mImageHeight;
    }

    public ImageView.ScaleType getTranfromViewScaleType() {
        return mTranfromViewScaleType;
    }

    public void setTranfromViewScaleType(ImageView.ScaleType tranfromViewScaleType) {
        mTranfromViewScaleType = tranfromViewScaleType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mImageWidth);
        dest.writeInt(this.mImageHeight);
    }

    protected ShareImageViewInfo(Parcel in) {
        super(in);
        this.mImageWidth = in.readInt();
        this.mImageHeight = in.readInt();
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
