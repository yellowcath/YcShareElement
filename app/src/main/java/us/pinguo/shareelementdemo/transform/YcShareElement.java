package us.pinguo.shareelementdemo.transform;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import us.pinguo.shareelementdemo.R;
import us.pinguo.shareelementdemo.TransitionHelper;
import us.pinguo.shareelementdemo.simple.SimpleToActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by huangwei on 2018/9/22.
 */
public class YcShareElement {

    private static IShareElementTransitionFactory sTransitionFactory = new DefaultShareElementTransitionFactory();

    public static Bundle buildOptionsBundle(Activity activity, final ShareElementInfo... shareElementInfos) {
        activity.setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                super.onMapSharedElements(names, sharedElements);
                Log.w("hwLog", "onMapSharedElements");
            }

            @Override
            public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
                Log.w("hwLog", "onCaptureSharedElementSnapshot");
                Object tag = sharedElement.getTag(R.id.share_element_info);

                if (tag instanceof ShareElementInfo) {
                    ShareElementInfo shareElementInfo = (ShareElementInfo) tag;
                    shareElementInfo.originData = super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
                    ;
                    return shareElementInfo;
                }
                return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
            }

            @Override
            public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
                super.onSharedElementsArrived(sharedElementNames, sharedElements, listener);
                Log.w("hwLog", "onSharedElementsArrived");

            }

            //以下不回調
            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
                Log.w("hwLog", "onSharedElementStart");
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                Log.w("hwLog", "onSharedElementEnd");
            }

            @Override
            public void onRejectSharedElements(List<View> rejectedSharedElements) {
                super.onRejectSharedElements(rejectedSharedElements);
                Log.w("hwLog", "onRejectSharedElements");
            }


            @Override
            public View onCreateSnapshotView(Context context, Parcelable snapshot) {
                Log.w("hwLog", "onCreateSnapshotView");
                return super.onCreateSnapshotView(context, snapshot);
            }


        });
        View[] viewArray = new View[shareElementInfos.length];
        for (int i = 0; i < viewArray.length; i++) {
            viewArray[i] = shareElementInfos[i].view;
        }
        return TransitionHelper.getTransitionBundle(activity, viewArray);
    }

    public static void beforeOnCreate(final Activity activity) {
        activity.postponeEnterTransition();
        activity.setEnterSharedElementCallback(new SharedElementCallback() {

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                super.onMapSharedElements(names, sharedElements);
                Log.e("hwLog", "onMapSharedElements");
            }

            @Override
            public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
                super.onSharedElementsArrived(sharedElementNames, sharedElements, listener);
                Log.e("hwLog", "onSharedElementsArrived");
            }

            @Override
            public void onRejectSharedElements(List<View> rejectedSharedElements) {
                super.onRejectSharedElements(rejectedSharedElements);
                Log.e("hwLog", "onRejectSharedElements");
            }

            @Override
            public View onCreateSnapshotView(Context context, Parcelable snapshot) {
                Log.e("hwLog", "onCreateSnapshotView");
                if (snapshot instanceof ShareElementInfo) {
                    View view = super.onCreateSnapshotView(context, ((ShareElementInfo) snapshot).originData);
                    view.setTag(R.id.share_element_info, snapshot);
                    return view;
                } else {
                    return super.onCreateSnapshotView(context, snapshot);
                }
            }

            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
                if (sharedElements != null && sharedElementSnapshots != null) {
                    for (int i = 0; i < sharedElements.size(); i++) {
                        View snapshotView = sharedElementSnapshots.get(i);
                        Object tag = snapshotView.getTag(R.id.share_element_info);
                        if (tag instanceof ShareElementInfo) {
                            sharedElements.get(i).setTag(R.id.share_element_info, tag);
                        }
                    }
                }
                setTransform(activity, sharedElements);
                Log.e("hwLog", "onSharedElementStart");

            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                Log.e("hwLog", "onSharedElementEnd");
            }


            //以下不回调
            @Override
            public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
                Log.e("hwLog", "onCaptureSharedElementSnapshot");
                return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
            }
        });
    }

    public static void callReadyAfterPreDraw(final Activity activity){
        final View decor = activity.getWindow().getDecorView();
        decor.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                decor.getViewTreeObserver().removeOnPreDrawListener(this);
                onShareElementReady(activity);
                return true;
            }
        });
    }
    public static void onShareElementReady(Activity activity) {
        activity.startPostponedEnterTransition();
    }


    private static void setTransform(Activity activity, List<View> sharedElements) {
        if (sTransitionFactory != null) {
            Transition enterShareElementTransition = sTransitionFactory.buildShareElementEnterTransition(sharedElements);
            Transition exitShareElementTransition = sTransitionFactory.buildShareElementExitTransition(sharedElements);

            if (enterShareElementTransition != null) {
                activity.getWindow().setSharedElementEnterTransition(enterShareElementTransition);
            }
            if (exitShareElementTransition != null) {
                activity.getWindow().setSharedElementExitTransition(exitShareElementTransition);
            }

            Transition enterTransition = sTransitionFactory.buildEnterTransition();
            Transition exitTransition = sTransitionFactory.buildExitTransition();

            if (enterTransition != null) {
                activity.getWindow().setEnterTransition(enterTransition);
            }
            if (exitTransition != null) {
                activity.getWindow().setExitTransition(exitTransition);
            }
        }
    }

    public static void setShareElementTransitionFactory(IShareElementTransitionFactory transitionFactory) {
        sTransitionFactory = transitionFactory;
    }
}
