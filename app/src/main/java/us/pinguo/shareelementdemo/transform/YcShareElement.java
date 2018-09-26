package us.pinguo.shareelementdemo.transform;

import android.app.Activity;
import android.app.Application;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import us.pinguo.shareelementdemo.R;
import us.pinguo.shareelementdemo.TransitionHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static android.app.Activity.RESULT_OK;

/**
 * Created by huangwei on 2018/9/22.
 */
public class YcShareElement {
    private static final String KEY_SHARE_ELEMENTS = "key_share_elements";

    private static IShareElementTransitionFactory sTransitionFactory = new DefaultShareElementTransitionFactory();

    public static Bundle buildOptionsBundle(@NonNull final Activity activity, @Nullable final GetShareElement getShareElement) {
        activity.setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                mapSharedElements(activity, getShareElement, names, sharedElements);
                super.onMapSharedElements(names, sharedElements);
                Log.w("hwLog", "onMapSharedElements");
            }

            @Override
            public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
                Log.w("hwLog", "onCaptureSharedElementSnapshot");
                Object tag = sharedElement.getTag(R.id.share_element_info);

                if (tag instanceof ShareElementInfo) {
                    ShareElementInfo shareElementInfo = (ShareElementInfo) tag;
                    shareElementInfo.setSnapshot(super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds));
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
        ShareElementInfo[] infos = getShareElement == null ? null : getShareElement.getShareElements();
        int len = infos == null ? 0 : infos.length;
        View[] viewArray = new View[len];
        for (int i = 0; i < len; i++) {
            viewArray[i] = infos[i].getView();
        }
        return TransitionHelper.getTransitionBundle(activity, viewArray);
    }

    public static void postponeEnterTransition(@NonNull final Activity activity, @Nullable final GetShareElement getShareElement) {
        activity.postponeEnterTransition();
        activity.setEnterSharedElementCallback(new SharedElementCallback() {

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                mapSharedElements(activity, getShareElement, names, sharedElements);
                super.onMapSharedElements(names, sharedElements);
                Log.e("hwLog", "onMapSharedElements");
                recordShareElementsBaseBounds(sharedElements);
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
                    View view = super.onCreateSnapshotView(context, ((ShareElementInfo) snapshot).getSnapshot());
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

    private static void recordShareElementsBaseBounds(Map<String, View> sharedElements) {
        if (sharedElements == null) {
            return;
        }
        Iterator<View> values = sharedElements.values().iterator();
        while (values.hasNext()) {
            View view = values.next();
            Object tag = view.getTag(R.id.share_element_info);
            if (tag instanceof ShareElementInfo) {
                ((ShareElementInfo) tag).getTansfromViewBounds().set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            }
            if (tag instanceof ShareImageViewInfo && view instanceof ImageView) {
                ((ShareImageViewInfo) tag).setTranfromViewScaleType(((ImageView) view).getScaleType());
            }
        }
    }

    public static void callReadyAfterPreDraw(final Activity activity) {
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
        //防止状态栏闪烁
        Transition enterTransition = activity.getWindow().getEnterTransition();
        Transition exitTransition = activity.getWindow().getExitTransition();
        if (enterTransition != null) {
            enterTransition.excludeTarget(Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME, true);
            enterTransition.excludeTarget(Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME, true);
        }
        if (exitTransition != null) {
            exitTransition.excludeTarget(Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME, true);
            exitTransition.excludeTarget(Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME, true);
        }
    }

    public static void setShareElementTransitionFactory(IShareElementTransitionFactory transitionFactory) {
        sTransitionFactory = transitionFactory;
    }

    private static void mapSharedElements(Activity activity, GetShareElement getShareElement, List<String> names, Map<String, View> sharedElements) {
        ShareElementInfo[] infos = getShareElement == null ? null : getShareElement.getShareElements();

        names.clear();
        sharedElements.clear();
        if (infos == null) {
            return;
        }
        for (ShareElementInfo info : infos) {
            View view = info.getView();
            if (isViewInBounds(activity.getWindow().getDecorView(), view)) {
                names.add(ViewCompat.getTransitionName(view));
                sharedElements.put(ViewCompat.getTransitionName(view), view);
            }
        }
    }

    private static boolean isViewInBounds(View decorView, View view) {
        Rect rect = new Rect();
        decorView.getHitRect(rect);
        return view.getLocalVisibleRect(rect);
    }

    public static void finishAfterTransition(Activity activity, GetShareElement getShareElement) {
        ShareElementInfo[] shareElements = getShareElement == null ? null : getShareElement.getShareElements();
        if (shareElements != null) {
            ArrayList<ShareElementInfo> list = new ArrayList<>(Arrays.asList(shareElements));
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(KEY_SHARE_ELEMENTS, list);
            activity.setResult(RESULT_OK, intent);
        }
    }

    public static void onActivityReenter(final Activity activity, int resultCode, Intent data, IShareElementSelector selector) {
        if (selector == null || resultCode != RESULT_OK || data == null) {
            return;
        }
        data.setExtrasClassLoader(ShareElementInfo.class.getClassLoader());
        if (!data.hasExtra(KEY_SHARE_ELEMENTS)) {
            return;
        }
        activity.postponeEnterTransition();
        ArrayList<ShareElementInfo> shareElementsList = data.getParcelableArrayListExtra(KEY_SHARE_ELEMENTS);
        selector.selectShareElements(shareElementsList);

        activity.getWindow().getDecorView().getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                activity.getWindow().getDecorView().getViewTreeObserver().removeOnPreDrawListener(this);
                activity.startPostponedEnterTransition();
                return false;
            }
        });
    }

    private static AtomicBoolean sInited = new AtomicBoolean(false);

    public static void init(Application application) {
        if (!sInited.getAndSet(true)) {
            application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    TransitionHelper.enableTransition(activity);
                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {

                }
            });
        }
    }
}
