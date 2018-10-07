package us.pinguo.shareelementdemo.advanced.list;

import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.View;
import com.facebook.drawee.drawable.ScalingUtils;
import com.hw.ycshareelement.transform.DefaultShareElementTransitionFactory;

import java.util.List;

/**
 * Created by huangwei on 2018/10/5.
 */
public class FrescoShareElementTransitionfactory extends DefaultShareElementTransitionFactory {

    @Override
    protected TransitionSet buildShareElementsTransition(List<View> shareViewList) {
        TransitionSet transitionSet =  super.buildShareElementsTransition(shareViewList);
        transitionSet.addTransition(new AdvanceDraweeTransition());
        return transitionSet;
    }
}
