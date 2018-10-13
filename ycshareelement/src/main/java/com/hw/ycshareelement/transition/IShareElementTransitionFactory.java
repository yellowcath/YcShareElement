package com.hw.ycshareelement.transition;

import android.transition.Transition;
import android.view.View;

import java.util.List;

/**
 * Created by huangwei on 2018/9/22.
 */
public interface IShareElementTransitionFactory {
    Transition buildShareElementEnterTransition(List<View> shareViewList);
    Transition buildShareElementExitTransition(List<View> shareViewList);

    Transition buildEnterTransition();
    Transition buildExitTransition();
}
