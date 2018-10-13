package us.pinguo.shareelementdemo.advanced;

import android.os.Bundle;
import android.view.View;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.GenericDraweeView;
import com.hw.ycshareelement.transition.ViewStateSaver;

/**
 * Created by huangwei on 2018/10/7.
 */
public class FrescoViewStateSaver extends ViewStateSaver {

    public ScalingUtils.ScaleType getScaleType(Bundle bundle) {
        int scaleType = bundle.getInt("scaleType", 0);
        return intToScaleType(scaleType);
    }

    @Override
    protected void captureViewInfo(View view, Bundle bundle) {
        if (view instanceof GenericDraweeView) {
            bundle.putInt("scaleType", scaleTypeToInt(((GenericDraweeView) view).getHierarchy().getActualImageScaleType()));
        }
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
}
