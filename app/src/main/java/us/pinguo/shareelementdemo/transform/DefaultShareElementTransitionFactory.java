package us.pinguo.shareelementdemo.transform;

import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.View;
import android.widget.ImageView;
import us.pinguo.shareelementdemo.R;

import java.util.List;

/**
 * Created by huangwei on 2018/9/22.
 */
public class DefaultShareElementTransitionFactory implements IShareElementTransitionFactory {

    private boolean mUseDefaultImageTransform = false;

    @Override
    public Transition buildShareElementEnterTransition(List<View> shareViewList) {
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
    public Transition buildShareElementExitTransition(List<View> shareViewList) {
        return buildShareElementEnterTransition(shareViewList);
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
