package us.pinguo.shareelementdemo.advanced.list;

import android.graphics.drawable.Animatable;
import android.support.annotation.Nullable;
import com.facebook.drawee.controller.ControllerListener;

/**
 * Created by huangwei on 2017/12/4.
 */
public class DeliveryControllerListener<INFO> implements ControllerListener<INFO> {

    private ControllerListener<INFO> listener;

    public DeliveryControllerListener(ControllerListener<INFO> listener) {
        this.listener = listener;
    }

    @Override
    public void onSubmit(String s, Object o) {
        if (listener != null) {
            listener.onSubmit(s, o);
        }
    }

    @Override
    public void onFinalImageSet(String s, @Nullable INFO info, @Nullable Animatable animatable) {
        if (listener != null) {
            listener.onFinalImageSet(s, info, animatable);
        }
    }

    @Override
    public void onIntermediateImageSet(String s, @Nullable INFO info) {
        if (listener != null) {
            listener.onIntermediateImageSet(s, info);
        }
    }

    @Override
    public void onIntermediateImageFailed(String s, Throwable throwable) {
        if (listener != null) {
            listener.onIntermediateImageFailed(s, throwable);
        }
    }

    @Override
    public void onFailure(String s, Throwable throwable) {
        if (listener != null) {
            listener.onFailure(s, throwable);
        }
    }

    @Override
    public void onRelease(String s) {
        if (listener != null) {
            listener.onRelease(s);
        }
    }
}
