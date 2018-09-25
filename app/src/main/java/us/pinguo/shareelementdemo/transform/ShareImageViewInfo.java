package us.pinguo.shareelementdemo.transform;

import android.os.Parcel;
import android.view.View;
import android.widget.ImageView;
import us.pinguo.shareelementdemo.advanced.BaseData;

import java.util.Map;

/**
 * Created by huangwei on 2018/9/22.
 */
public class ShareImageViewInfo extends ShareElementInfo<BaseData> {

    /**
     * 实际做动画的ImageView的ScaleType,由{@link YcShareElement#recordShareElementsBaseBounds(Map)}负责填充
     */
    public transient ImageView.ScaleType mTranfromViewScaleType = ImageView.ScaleType.FIT_CENTER;

    public ShareImageViewInfo(View view, BaseData data) {
        super(view, data);
    }

    public int getPhotoOriginWidth() {
        return mData.width;
    }

    public int getPhotoOriginHeight() {
        return mData.height;
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
    }

    protected ShareImageViewInfo(Parcel in) {
        super(in);
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
