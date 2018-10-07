package com.hw.ycshareelement.transform;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by huangwei on 2018/9/22.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DefaultShareElementTransitionFactory implements IShareElementTransitionFactory {

    protected boolean mUseDefaultImageTransform = false;

    @Override
    public Transition buildShareElementEnterTransition(List<View> shareViewList) {
        return buildShareElementsTransition(shareViewList);
    }

    @Override
    public Transition buildShareElementExitTransition(List<View> shareViewList) {
        return buildShareElementsTransition(shareViewList);
    }

    protected TransitionSet buildShareElementsTransition(List<View> shareViewList) {
        TransitionSet transitionSet = new TransitionSet();
        if (shareViewList == null || shareViewList.size() == 0) {
            return transitionSet;
        }
        transitionSet.addTransition(new ChangeClipBounds());
        boolean imageViewExisted = false;
        for (View view : shareViewList) {
            if (view instanceof ImageView) {
                imageViewExisted = true;
                break;
            }
        }
        transitionSet.addTransition(new ChangeTransform());
        transitionSet.addTransition(new ChangeClipBounds());
        transitionSet.addTransition(new ChangeBounds());

        if (imageViewExisted) {
            if (mUseDefaultImageTransform) {
                transitionSet.addTransition(new ChangeImageTransform());
            } else {
                transitionSet.addTransition(new ChangeOnlineImageTransform());
            }
        }
        return transitionSet;
    }

    @Override
    public Transition buildEnterTransition() {
        return new Fade();
    }

    @Override
    public Transition buildExitTransition() {
        return new Fade();
    }
}
