package us.pinguo.shareelementdemo.transform;

import android.animation.Animator;
import android.graphics.Rect;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.view.ViewGroup;
import us.pinguo.shareelementdemo.R;

/**
 * Created by huangwei on 2018/9/26.
 */
public class ChangeVideoBounds extends ChangeBounds {
    private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (!(startValues.view.getTag(R.id.share_element_info) instanceof ShareVideoViewInfo)) {
            return super.createAnimator(sceneRoot, startValues, endValues);
        }
        Rect startBounds = (Rect) startValues.values.get(PROPNAME_BOUNDS);
        Rect endBounds = (Rect) endValues.values.get(PROPNAME_BOUNDS);
        ShareVideoViewInfo info = (ShareVideoViewInfo) startValues.view.getTag(R.id.share_element_info);

        //返回动画
        float videoRatio = info.getVideoWidth() / (float) info.getVideoHeight();
        float startRatio = startBounds.width() / (float) startBounds.height();
        float endRatio = endBounds.width() / (float) endBounds.height();

        if (videoRatio > startRatio) {

        } else {

        }

        return super.createAnimator(sceneRoot, startValues, endValues);
    }
}
