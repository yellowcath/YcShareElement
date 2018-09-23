package us.pinguo.shareelementdemo.transform;

import android.graphics.Rect;
import android.os.Parcel;
import android.view.View;
import android.widget.ImageView;

import java.util.Map;

/**
 * Created by huangwei on 2018/9/22.
 */
public class ShareImageViewInfo extends ShareElementInfo {
    /**
     * 图片的原始尺寸
     */
    public int photoOriginWidth;
    public int photoOriginHeight;

    /**
     * 实际做动画的ImageView的ScaleType,由{@link YcShareElement#recordShareElementsBaseBounds(Map)}负责填充
     */
    public ImageView.ScaleType tranfromViewScaleType = ImageView.ScaleType.FIT_CENTER;

    public ShareImageViewInfo(View view, int photoOriginWidth, int photoOriginHeight) {
        super(view);
        this.photoOriginHeight = photoOriginHeight;
        this.photoOriginWidth = photoOriginWidth;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.photoOriginWidth);
        dest.writeInt(this.photoOriginHeight);
    }

    protected ShareImageViewInfo(Parcel in) {
        super(in);
        this.photoOriginWidth = in.readInt();
        this.photoOriginHeight = in.readInt();
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
