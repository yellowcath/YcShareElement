package com.hw.ycshareelement;

import android.app.Activity;
import android.app.Application;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.transition.Transition;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import com.hw.ycshareelement.transition.DefaultShareElementTransitionFactory;
import com.hw.ycshareelement.transition.IShareElements;
import com.hw.ycshareelement.transition.IShareElementSelector;
import com.hw.ycshareelement.transition.IShareElementTransitionFactory;
import com.hw.ycshareelement.transition.ShareElementInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static android.app.Activity.RESULT_OK;

/**
 * Created by huangwei on 2018/9/22.
 */
public class YcShareElement {
    private static final String KEY_SHARE_ELEMENTS = "key_share_elements";

    public static Bundle buildOptionsBundle(@NonNull final Activity activity, @Nullable final IShareElements getShareElement) {
        if (!TransitionHelper.ENABLE) {
            return new Bundle();
        }
        activity.setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                mapSharedElements(activity, getShareElement, names, sharedElements);
                super.onMapSharedElements(names, sharedElements);
            }

            @Override
            public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
                ShareElementInfo info = ShareElementInfo.getFromView(sharedElement);

                if (info != null) {
                    info.captureFromViewInfo(sharedElement);
                    info.setSnapshot(super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds));
                    return info;
                }
                return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
            }

            @Override
            public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
                super.onSharedElementsArrived(sharedElementNames, sharedElements, listener);
            }

            //
            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
            }

            @Override
            public void onRejectSharedElements(List<View> rejectedSharedElements) {
                super.onRejectSharedElements(rejectedSharedElements);
            }


            @Override
            public View onCreateSnapshotView(Context context, Parcelable snapshot) {
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

    public static void setEnterTransitions(@NonNull final Activity activity, @Nullable final IShareElements IShareElements) {
        setEnterTransitions(activity, IShareElements, true,new DefaultShareElementTransitionFactory());
    }

    public static void setEnterTransitions(@NonNull final Activity activity, @Nullable final IShareElements IShareElements,boolean postponeTransition) {
        setEnterTransitions(activity, IShareElements, postponeTransition,new DefaultShareElementTransitionFactory());
    }

    public static void setEnterTransitions(@NonNull final Activity activity, @Nullable final IShareElements IShareElements, boolean postponeTransition,
                                           final IShareElementTransitionFactory transitionFactory) {
        if (!TransitionHelper.ENABLE) {
            return;
        }
        final AtomicBoolean isEnter = new AtomicBoolean(true);
        if(postponeTransition) {
            activity.postponeEnterTransition();
        }
        activity.setEnterSharedElementCallback(new SharedElementCallback() {

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                mapSharedElements(activity, IShareElements, names, sharedElements);
                super.onMapSharedElements(names, sharedElements);
            }

            @Override
            public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
                super.onSharedElementsArrived(sharedElementNames, sharedElements, listener);
            }

            @Override
            public void onRejectSharedElements(List<View> rejectedSharedElements) {
                super.onRejectSharedElements(rejectedSharedElements);
            }

            @Override
            public View onCreateSnapshotView(Context context, Parcelable snapshot) {
                if (snapshot instanceof ShareElementInfo) {
                    View view = super.onCreateSnapshotView(context, ((ShareElementInfo) snapshot).getSnapshot());
                    ShareElementInfo.saveToView(view, (ShareElementInfo) snapshot);
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
                        View shareElementView = sharedElements.get(i);
                        ShareElementInfo shareElementInfo = null;
                        if (isEnter.get()) {
                            //进入时使用前一个Activity传过来的值
                            shareElementInfo = ShareElementInfo.getFromView(snapshotView);
                        } else {
                            //退出时使用当前Activity设置的值
                            shareElementInfo = ShareElementInfo.getFromView(shareElementView);
                        }
                        if (shareElementInfo != null) {
                            shareElementInfo.setEnter(isEnter.get());
                            ShareElementInfo.saveToView(shareElementView, shareElementInfo);
                        }
                    }
                }
                setTransition(activity, sharedElements,transitionFactory);
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                for (int i = 0; sharedElements != null && i < sharedElements.size(); i++) {
                    View shareElementView = sharedElements.get(i);
                    ShareElementInfo shareElementInfo = ShareElementInfo.getFromView(shareElementView);
                    if (shareElementInfo != null) {
                        if (isEnter.get()) {
                            shareElementInfo.captureToViewInfo(shareElementView);
                        } else {
                            View snapshotView = sharedElementSnapshots == null ? null : sharedElementSnapshots.get(i);
                            ShareElementInfo infoFromSnapshot = ShareElementInfo.getFromView(snapshotView);
                            if(infoFromSnapshot!=null) {
                                shareElementInfo.setFromViewBundle(infoFromSnapshot.getFromViewBundle());
                            }
                            shareElementInfo.captureToViewInfo(shareElementView);
                        }
                    }
                }
                isEnter.set(false);
            }

            //
            @Override
            public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
                return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
            }
        });
    }

    public static void setExitTransition(Activity activity,Transition transition){
        if(!TransitionHelper.ENABLE) {
            return;
        }
        activity.getWindow().setExitTransition(transition);
    }
    /**
     * 用于例如ShareElement位于ViewPager或者RecyclerView的情况，需要等它们加载好之后再开始动画
     *
     * @param activity
     */
    public static void postStartTransition(final Activity activity) {
        final View decor = activity.getWindow().getDecorView();
        decor.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                decor.getViewTreeObserver().removeOnPreDrawListener(this);
                startTransition(activity);
                return true;
            }
        });
    }

    /**
     * 直接开始动画，必须确保ShareElement的目标View已经添加到布局里了
     *
     * @param activity
     */
    public static void startTransition(Activity activity) {
        if (!TransitionHelper.ENABLE) {
            return;
        }
        activity.startPostponedEnterTransition();
    }


    private static void setTransition(Activity activity, List<View> sharedElements,IShareElementTransitionFactory transitionFactory) {
        if (!TransitionHelper.ENABLE) {
            return;
        }
        if (transitionFactory != null) {
            Transition enterShareElementTransition = transitionFactory.buildShareElementEnterTransition(sharedElements);
            Transition exitShareElementTransition = transitionFactory.buildShareElementExitTransition(sharedElements);

            if (enterShareElementTransition != null) {
                activity.getWindow().setSharedElementEnterTransition(enterShareElementTransition);
            }
            if (exitShareElementTransition != null) {
                activity.getWindow().setSharedElementExitTransition(exitShareElementTransition);
            }

            Transition enterTransition = transitionFactory.buildEnterTransition();
            Transition exitTransition = transitionFactory.buildExitTransition();

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

    private static void mapSharedElements(Activity activity, IShareElements IShareElements, List<String> names, Map<String, View> sharedElements) {
        ShareElementInfo[] infos = IShareElements == null ? null : IShareElements.getShareElements();

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

    public static void finishAfterTransition(Activity activity, IShareElements IShareElements) {
        ShareElementInfo[] shareElements = IShareElements == null ? null : IShareElements.getShareElements();
        if (shareElements != null) {
            ArrayList<ShareElementInfo> list = new ArrayList<>(Arrays.asList(shareElements));
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(KEY_SHARE_ELEMENTS, list);
            activity.setResult(RESULT_OK, intent);
        }
    }

    public static void onActivityReenter(final Activity activity, int resultCode, Intent data, IShareElementSelector selector) {
        if (!TransitionHelper.ENABLE) {
            return;
        }
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
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onPreDraw() {
                activity.getWindow().getDecorView().getViewTreeObserver().removeOnPreDrawListener(this);
                activity.startPostponedEnterTransition();
                return false;
            }
        });
    }

    private static boolean sInited = false;

    public static void enableContentTransition(Application application) {
        if (!sInited) {
            sInited = true;
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
