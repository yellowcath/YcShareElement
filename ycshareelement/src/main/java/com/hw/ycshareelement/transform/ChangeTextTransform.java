package com.hw.ycshareelement.transform;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hw.ycshareelement.R;

/**
 * Created by huangwei on 2018/10/6.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ChangeTextTransform extends Transition {

    protected static final String PROPNAME_TEXTSIZE = "ChangeTextTransform::textSize";
    protected static final String PROPNAME_TEXTCOLOR = "ChangeTextTransform::textColor";

    public ChangeTextTransform() {
        addTarget(TextView.class);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {

        captureValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    protected void captureValues(TransitionValues value) {
        if (!checkTransfrom(value)) {
            return;
        }
        TextView view = (TextView) value.view;
        value.values.put(PROPNAME_TEXTSIZE, view.getTextSize());
        value.values.put(PROPNAME_TEXTCOLOR, view.getCurrentTextColor());
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (!checkTransfrom(startValues)) {
            return null;
        }
        TextView view = (TextView) endValues.view;

        float startTextSize = (float) startValues.values.get(PROPNAME_TEXTSIZE);
        float endTextSize = (float) endValues.values.get(PROPNAME_TEXTSIZE);
        ObjectAnimator textSizeAnimator = ObjectAnimator.ofFloat(view, "textSize", startTextSize, endTextSize);

        int startTextColor = (int) startValues.values.get(PROPNAME_TEXTCOLOR);
        int endTextColor = (int) endValues.values.get(PROPNAME_TEXTCOLOR);
        ObjectAnimator textColorAnimator = ObjectAnimator.ofArgb(view, "textColor", startTextColor, endTextColor);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(textSizeAnimator, textColorAnimator);
        return animatorSet;

    }

    private boolean checkTransfrom(TransitionValues transitionValues) {
        return transitionValues.view != null && ShareElementInfo.getFromView(transitionValues.view) != null;
    }

    //    @Override
//    public void captureStartValues(TransitionValues transitionValues) {
//        ShareElementInfo info = ShareElementInfo.getFromView(transitionValues.view);
//        if (info instanceof ShareTextInfo && transitionValues.view instanceof TextView) {
//            if(info.isEnter()){
//                transitionValues.values.put(PROPNAME_TEXTSIZE,((ShareTextInfo) info).getTextSize());
//                transitionValues.values.put(PROPNAME_TEXTCOLOR,((ShareTextInfo) info).getTextColor());
//            }else{
//                transitionValues.values.put(PROPNAME_TEXTSIZE,((TextView) transitionValues.view).getTextSize());
//                transitionValues.values.put(PROPNAME_TEXTCOLOR,((TextView) transitionValues.view).getCurrentTextColor());
//            }
//        }
//    }
//
//    @Override
//    public void captureEndValues(TransitionValues transitionValues) {
//        ShareElementInfo info = ShareElementInfo.getFromView(transitionValues.view);
//        if (info instanceof ShareTextInfo && transitionValues.view instanceof TextView) {
//            if(!info.isEnter()){
//                transitionValues.values.put(PROPNAME_TEXTSIZE,((ShareTextInfo) info).getTextSize());
//                transitionValues.values.put(PROPNAME_TEXTCOLOR,((ShareTextInfo) info).getTextColor());
//            }else{
//                transitionValues.values.put(PROPNAME_TEXTSIZE,((TextView) transitionValues.view).getTextSize());
//                transitionValues.values.put(PROPNAME_TEXTCOLOR,((TextView) transitionValues.view).getCurrentTextColor());
//            }
//        }
//    }
}
