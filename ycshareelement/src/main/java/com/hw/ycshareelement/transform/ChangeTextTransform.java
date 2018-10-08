package com.hw.ycshareelement.transform;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Property;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        ShareElementInfo info = ShareElementInfo.getFromView(transitionValues.view);
        if (info == null) {
            return;
        }
        captureValues(transitionValues, (TextViewStateSaver) info.getViewStateSaver(), info.isEnter() ? info.getFromViewBundle() : info.getToViewBundle());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        ShareElementInfo info = ShareElementInfo.getFromView(transitionValues.view);
        if (info == null) {
            return;
        }
        captureValues(transitionValues, (TextViewStateSaver) info.getViewStateSaver(), info.isEnter() ? info.getToViewBundle() : info.getFromViewBundle());
    }

    protected void captureValues(TransitionValues value, TextViewStateSaver stateSaver, Bundle viewExtraInfo) {
        value.values.put(PROPNAME_TEXTSIZE, stateSaver.getTextSize(viewExtraInfo));
        value.values.put(PROPNAME_TEXTCOLOR, stateSaver.getTextColor(viewExtraInfo));
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        ShareElementInfo info = ShareElementInfo.getFromView(endValues.view);
        if (info == null) {
            return null;
        }
        final TextView view = (TextView) endValues.view;

        view.setPivotX(0f);
        view.setPivotY(0f);
        float startTextSize = (float) startValues.values.get(PROPNAME_TEXTSIZE);
        final float endTextSize = (float) endValues.values.get(PROPNAME_TEXTSIZE);
        ObjectAnimator textSizeAnimator = ObjectAnimator.ofFloat(view, new TextSizeProperty(), startTextSize, endTextSize);
//        textSizeAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                view.setScaleX(1f);
//                view.setScaleY(1f);
//                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, endTextSize);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });

        int startTextColor = (int) startValues.values.get(PROPNAME_TEXTCOLOR);
        int endTextColor = (int) endValues.values.get(PROPNAME_TEXTCOLOR);
        ObjectAnimator textColorAnimator = ObjectAnimator.ofArgb(view, new TextColorProperty(), startTextColor, endTextColor);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(textSizeAnimator, textColorAnimator);
        return animatorSet;

    }

    private class TextSizeProperty extends Property<TextView, Float> {

        public TextSizeProperty() {
            super(Float.class, "textSize");
        }

        @Override
        public void set(TextView object, Float value) {
            object.setTextSize(TypedValue.COMPLEX_UNIT_PX, value);
        }

        @Override
        public Float get(TextView object) {
            return object.getTextSize();
        }
    }

    private class ScaleProperty extends Property<View, Float> {

        public ScaleProperty() {
            super(Float.class, "scale");
        }

        @Override
        public void set(View object, Float value) {
            object.setScaleX(value);
            object.setScaleY(value);
        }

        @Override
        public Float get(View object) {
            return object.getScaleX();
        }
    }

    private class TextColorProperty extends Property<TextView, Integer> {

        public TextColorProperty() {
            super(Integer.class, "textColor");
        }

        @Override
        public void set(TextView object, Integer value) {
            object.setTextColor(value);
        }

        @Override
        public Integer get(TextView object) {
            return object.getCurrentTextColor();
        }
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
