package com.hw.ycshareelement.transform;

import android.annotation.TargetApi;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionValues;
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

    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {

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
