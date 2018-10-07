package us.pinguo.shareelementdemo.advanced.list;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.ViewGroup;
import com.facebook.drawee.drawable.FadeDrawable;
import com.facebook.drawee.drawable.ScaleTypeDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.DraweeTransition;
import com.facebook.drawee.view.GenericDraweeView;
import us.pinguo.shareelementdemo.R;

import java.lang.reflect.Field;

/**
 * Created by huangwei on 2018/10/5.
 */
public class AdvanceDraweeTransition extends Transition {
    private static final String PROPNAME_BOUNDS = "draweeTransition:bounds";

    private ScalingUtils.ScaleType mFromScale;
    private ScalingUtils.ScaleType mToScale;

    public AdvanceDraweeTransition() {
        addTarget(GenericDraweeView.class);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
        if (transitionValues.view.getTag(R.id.share_element_info) instanceof ShareFrescoInfo) {
            ShareFrescoInfo shareFrescoInfo = (ShareFrescoInfo) transitionValues.view.getTag(R.id.share_element_info);
            mFromScale = shareFrescoInfo.getFromScale();
        }
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
        if (transitionValues.view.getTag(R.id.share_element_info) instanceof ShareFrescoInfo) {
            ShareFrescoInfo shareFrescoInfo = (ShareFrescoInfo) transitionValues.view.getTag(R.id.share_element_info);
            mToScale = shareFrescoInfo.getToScale();
        }
    }

    @Override
    public Animator createAnimator(
            ViewGroup sceneRoot,
            TransitionValues startValues,
            TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        Rect startBounds = (Rect) startValues.values.get(PROPNAME_BOUNDS);
        Rect endBounds = (Rect) endValues.values.get(PROPNAME_BOUNDS);
        if (startBounds == null || endBounds == null) {
            return null;
        }
        if (mFromScale == mToScale) {
            return null;
        }
        final GenericDraweeView draweeView = (GenericDraweeView) startValues.view;
        final ScalingUtils.InterpolatingScaleType scaleType =
                new ScalingUtils.InterpolatingScaleType(mFromScale, mToScale, startBounds, endBounds);
        draweeView.getHierarchy().setActualImageScaleType(scaleType);
        try {
            setScaleTypeToPlaceHolder(draweeView, scaleType);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = (float) animation.getAnimatedValue();
                scaleType.setValue(fraction);
                if (draweeView.getHierarchy().getActualImageScaleType() != scaleType) {
                    draweeView.getHierarchy().setActualImageScaleType(scaleType);
                }
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                draweeView.getHierarchy().setActualImageScaleType(mToScale);
            }
        });
        return animator;
    }

    private void setScaleTypeToPlaceHolder(GenericDraweeView draweeView, ScalingUtils.ScaleType scaleType) throws NoSuchFieldException, IllegalAccessException {
        FadeDrawable drawable;
        GenericDraweeHierarchy hierarchy = draweeView.getHierarchy();
        Field field = GenericDraweeHierarchy.class.getDeclaredField("mFadeDrawable");
        field.setAccessible(true);
        drawable = (FadeDrawable) field.get(hierarchy);
        Drawable placeholderDrawable = drawable.getDrawable(1);
        if (placeholderDrawable instanceof ScaleTypeDrawable) {
            ((ScaleTypeDrawable) placeholderDrawable).setScaleType(scaleType);
        }
    }

    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues.view instanceof GenericDraweeView) {
            transitionValues.values.put(
                    PROPNAME_BOUNDS,
                    new Rect(0, 0, transitionValues.view.getWidth(), transitionValues.view.getHeight()));
        }
    }
}
