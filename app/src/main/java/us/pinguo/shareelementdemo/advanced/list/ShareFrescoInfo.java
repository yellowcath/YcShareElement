package us.pinguo.shareelementdemo.advanced.list;

import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import us.pinguo.shareelementdemo.advanced.BaseData;

/**
 * Created by huangwei on 2018/10/6.
 */
public class ShareFrescoInfo extends ShareContentInfo {
    private ScalingUtils.ScaleType mFromScale;
    private ScalingUtils.ScaleType mToScale;

    public ShareFrescoInfo(@NonNull View view, @Nullable BaseData data, ScalingUtils.ScaleType fromScaleType, ScalingUtils.ScaleType toScaleType) {
        super(view, data);
        mFromScale = fromScaleType;
        mToScale = toScaleType;
    }

    public ScalingUtils.ScaleType getFromScale() {
        return mFromScale;
    }

    public ScalingUtils.ScaleType getToScale() {
        return mToScale;
    }

    private ScalingUtils.ScaleType intToScaleType(int index) {
        switch (index) {
            case 0:
                return ScalingUtils.ScaleType.CENTER_CROP;
            case 1:
                return ScalingUtils.ScaleType.CENTER;
            case 2:
                return ScalingUtils.ScaleType.CENTER_INSIDE;
            case 3:
                return ScalingUtils.ScaleType.FIT_CENTER;
            case 4:
                return ScalingUtils.ScaleType.FIT_END;
            case 5:
                return ScalingUtils.ScaleType.FIT_START;
            case 6:
                return ScalingUtils.ScaleType.FIT_XY;
            case 7:
                return ScalingUtils.ScaleType.FOCUS_CROP;
            default:
                return ScalingUtils.ScaleType.CENTER;
        }
    }

    private int scaleTypeToInt(ScalingUtils.ScaleType scaleType) {
        if (scaleType == ScalingUtils.ScaleType.CENTER_CROP) {
            return 0;
        } else if (scaleType == ScalingUtils.ScaleType.CENTER) {
            return 1;
        } else if (scaleType == ScalingUtils.ScaleType.CENTER_INSIDE) {
            return 2;
        } else if (scaleType == ScalingUtils.ScaleType.FIT_CENTER) {
            return 3;
        } else if (scaleType == ScalingUtils.ScaleType.FIT_END) {
            return 4;
        } else if (scaleType == ScalingUtils.ScaleType.FIT_START) {
            return 5;
        } else if (scaleType == ScalingUtils.ScaleType.FIT_XY) {
            return 6;
        } else if (scaleType == ScalingUtils.ScaleType.FOCUS_CROP) {
            return 7;
        }
        return -1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(scaleTypeToInt(mFromScale));
        dest.writeInt(scaleTypeToInt(mToScale));
    }

    protected ShareFrescoInfo(Parcel in) {
        super(in);
        mFromScale = intToScaleType(in.readInt());
        mToScale = intToScaleType(in.readInt());
    }

    public static final Creator<ShareFrescoInfo> CREATOR = new Creator<ShareFrescoInfo>() {
        @Override
        public ShareFrescoInfo createFromParcel(Parcel source) {
            return new ShareFrescoInfo(source);
        }

        @Override
        public ShareFrescoInfo[] newArray(int size) {
            return new ShareFrescoInfo[size];
        }
    };
}
