package com.hw.ycshareelement.transition;

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
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by huangwei on 2018/10/6.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ChangeTextTransition extends Transition {

    protected static final String PROPNAME_TEXTSIZE = "ChangeTextTransition::textSize";
    protected static final String PROPNAME_TEXTCOLOR = "ChangeTextTransition::textColor";

    public ChangeTextTransition() {
        addTarget(TextView.class);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        ShareElementInfo info = ShareElementInfo.getFromView(transitionValues.view);
        if (info == null || !(info.getViewStateSaver() instanceof TextViewStateSaver)) {
            return;
        }
        captureValues(transitionValues, (TextViewStateSaver) info.getViewStateSaver(), info.isEnter() ? info.getFromViewBundle() : info.getToViewBundle());
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        ShareElementInfo info = ShareElementInfo.getFromView(transitionValues.view);
        if (info == null || !(info.getViewStateSaver() instanceof TextViewStateSaver)) {
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
        ShareElementInfo info = endValues==null?null:ShareElementInfo.getFromView(endValues.view);
        if (info == null || !(info.getViewStateSaver() instanceof TextViewStateSaver)) {
            return null;
        }
        final TextView view = (TextView) endValues.view;

        view.setPivotX(0f);
        view.setPivotY(0f);
        float startTextSize = (float) startValues.values.get(PROPNAME_TEXTSIZE);
        final float endTextSize = (float) endValues.values.get(PROPNAME_TEXTSIZE);
        ObjectAnimator textSizeAnimator = ObjectAnimator.ofFloat(view, new TextSizeProperty(), startTextSize, endTextSize);

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
}
