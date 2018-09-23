package us.pinguo.shareelementdemo.transform;

import android.graphics.Rect;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
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
    @Override
    public Transition buildShareElementEnterTransition(List<View> shareViewList) {
        TransitionSet transitionSet = new TransitionSet();
        if (shareViewList == null || shareViewList.size() == 0) {
            return transitionSet;
        }
        transitionSet.addTransition(new ChangeClipBounds());
        boolean shareImageViewInfoExisted = false;
        boolean imageViewExisted = false;
        for (View view : shareViewList) {
            if (view instanceof ImageView) {
                imageViewExisted = true;
                if (view.getTag(R.id.share_element_info) instanceof ShareImageViewInfo) {
                    shareImageViewInfoExisted = true;
                }
            }
        }
        if (shareImageViewInfoExisted) {
            transitionSet.addTransition(new ChangeOnlineImageTransform());
//            transitionSet.addTransition(new ChangeImageTransform());
            transitionSet.addTransition(new ChangeBounds());
//            transitionSet.addTransition(new ChangeTransform());
        } else if (imageViewExisted) {
            transitionSet.addTransition(new ChangeImageTransform());
            transitionSet.addTransition(new ChangeBounds());
            transitionSet.addTransition(new ChangeTransform());
            transitionSet.addTransition(new ChangeClipBounds());
        } else {
            transitionSet.addTransition(new ChangeBounds());
            transitionSet.addTransition(new ChangeTransform());
            transitionSet.addTransition(new ChangeClipBounds());

        }
        return transitionSet;
    }

    @Override
    public Transition buildShareElementExitTransition(List<View> shareViewList) {
        return buildShareElementEnterTransition(shareViewList);
    }

    @Override
    public Transition buildEnterTransition() {
        return null;
    }

    @Override
    public Transition buildExitTransition() {
        return null;
    }
}
