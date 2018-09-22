package us.pinguo.shareelementdemo.transform;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import us.pinguo.shareelementdemo.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by huangwei on 2018/9/21 0021.
 */
public class ChangeOnlineImageTransform extends Transition {
    private static final String TAG = "YcShareElement";

    private static final String PROPNAME_SCALE_TYPE = "hw:changeImageTransform:scaletype";
    private static final String PROPNAME_BOUNDS = "hw:changeImageTransform:bounds";
    private static final String PROPNAME_MATRIX = "hw:changeImageTransform:matrix";
    private static final String PROPNAME_INFO = "hw:changeImageTransform:info";

    private static IBitmapSizeCalculator sBitmapSizeCalculator = new DefaultBitmapSizeCalculator();

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureBoundsAndInfo(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureBoundsAndInfo(transitionValues);
    }

    private void captureBoundsAndInfo(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (!(view instanceof ImageView) || view.getVisibility() != View.VISIBLE) {
            return;
        }
        ImageView imageView = (ImageView) view;
        Drawable drawable = imageView.getDrawable();
        if (drawable == null) {
            return;
        }
        Map<String, Object> values = transitionValues.values;
        Object tag = imageView.getTag(R.id.share_element_info);
        if (tag instanceof ShareImageViewInfo) {
            ShareImageViewInfo shareElementInfo = (ShareImageViewInfo) tag;
            values.put(PROPNAME_INFO, shareElementInfo);
        }

        int left = view.getLeft();
        int top = view.getTop();
        int right = view.getRight();
        int bottom = view.getBottom();

        Rect bounds = new Rect(left, top, right, bottom);
        values.put(PROPNAME_BOUNDS, bounds);
        values.put(PROPNAME_SCALE_TYPE, imageView.getScaleType());
        if (imageView.getScaleType() == ImageView.ScaleType.MATRIX) {
            values.put(PROPNAME_MATRIX, imageView.getImageMatrix());
        }
    }

    protected void calculateMatrix(TransitionValues startValues, TransitionValues endValues, Matrix startMatrix, Matrix endMatrix) {
        if (startValues == null || endValues == null || startMatrix == null || endMatrix == null) {
            return;
        }
        if (!startValues.values.containsKey(PROPNAME_INFO)) {
            startMatrix.set(((ImageView) startValues.view).getImageMatrix());
            endMatrix.set(((ImageView) endValues.view).getImageMatrix());
            return;
        }
        ShareImageViewInfo shareElementInfo = (ShareImageViewInfo) startValues.values.get(PROPNAME_INFO);
        ImageView.ScaleType startScaleType = (ImageView.ScaleType) startValues.values.get(PROPNAME_SCALE_TYPE);
        Rect startBounds = (Rect) startValues.values.get(PROPNAME_BOUNDS);
        BitmapSize startBitmapSize = sBitmapSizeCalculator.calculateImageSize(
                startBounds,
                startScaleType,
                shareElementInfo.contentWidth,
                shareElementInfo.contentHeight);
        if (startScaleType == ImageView.ScaleType.MATRIX) {
            startMatrix.set((Matrix) startValues.values.get(PROPNAME_MATRIX));
        } else {
            startMatrix.set(getImageViewMatrix(startBounds, startScaleType, startBitmapSize.width, startBitmapSize.height));
        }

        ImageView.ScaleType endScaleType = (ImageView.ScaleType) endValues.values.get(PROPNAME_SCALE_TYPE);
        Rect endBounds = (Rect) endValues.values.get(PROPNAME_BOUNDS);
        BitmapSize endBitmapSize = sBitmapSizeCalculator.calculateImageSize(
                endBounds,
                endScaleType,
                shareElementInfo.contentWidth,
                shareElementInfo.contentHeight);
        if (endScaleType == ImageView.ScaleType.MATRIX) {
            endMatrix.set((Matrix) endValues.values.get(PROPNAME_MATRIX));
        } else {
            endMatrix.set(getImageViewMatrix(endBounds, endScaleType, endBitmapSize.width, endBitmapSize.height));
        }

    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }
        Rect startBounds = (Rect) startValues.values.get(PROPNAME_BOUNDS);
        Rect endBounds = (Rect) endValues.values.get(PROPNAME_BOUNDS);
        if (startBounds == null || endBounds == null) {
            return null;
        }
        final ImageView imageView = (ImageView) endValues.view;
//        ObjectAnimator boundsAnimator;
//        boundsAnimator = ObjectAnimator.ofObject(imageView, RECT_PROPERTY, new RectEvaluator(), startBounds, endBounds);

        Matrix startMatrix = new Matrix();
        Matrix endMatrix = new Matrix();
        calculateMatrix(startValues, endValues, startMatrix, endMatrix);
        boolean matricesEqual = (startMatrix == null && endMatrix == null) ||
                (startMatrix != null && startMatrix.equals(endMatrix));

        if (startBounds.equals(endBounds) && matricesEqual) {
            return null;
        }

        final Drawable drawable = imageView.getDrawable();
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();

        ObjectAnimator matrixAnimator;
        if (drawableWidth == 0 || drawableHeight == 0) {
            matrixAnimator = createNullAnimator(imageView);
        } else {
            if (startMatrix == null) {
                startMatrix = new Matrix();
            }
            if (endMatrix == null) {
                endMatrix = new Matrix();
            }
            ANIMATED_TRANSFORM_PROPERTY.set(imageView, startMatrix);
            matrixAnimator = createMatrixAnimator(imageView, startMatrix, endMatrix);
            Log.e("hwLog", startMatrix.toShortString() + " /n " + endMatrix.toShortString());
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(matrixAnimator);
        return animatorSet;
    }

    private static Property<ImageView, Rect> RECT_PROPERTY
            = new Property<ImageView, Rect>(Rect.class, "imageRectTransfrom") {
        @Override
        public void set(ImageView object, Rect value) {
            object.layout(value.left, value.top, value.right, value.bottom);
        }

        @Override
        public Rect get(ImageView object) {
            return null;
        }
    };


    private ObjectAnimator createNullAnimator(ImageView imageView) {
        return ObjectAnimator.ofObject(imageView, ANIMATED_TRANSFORM_PROPERTY,
                NULL_MATRIX_EVALUATOR, null, null);
    }

    private ObjectAnimator createMatrixAnimator(final ImageView imageView, Matrix startMatrix,
                                                final Matrix endMatrix) {
        return ObjectAnimator.ofObject(imageView, ANIMATED_TRANSFORM_PROPERTY,
                new MatrixEvaluator(), startMatrix, endMatrix);
    }

    private static Property<ImageView, Matrix> ANIMATED_TRANSFORM_PROPERTY
            = new Property<ImageView, Matrix>(Matrix.class, "animatedTransform") {
        @Override
        public void set(ImageView object, Matrix value) {
//            Drawable drawable = object.getDrawable();
//            object.animateTransform(value);
            animateTransform(object, value);
        }

        @Override
        public Matrix get(ImageView object) {
            return null;
        }
    };

    private static TypeEvaluator<Matrix> NULL_MATRIX_EVALUATOR = new TypeEvaluator<Matrix>() {
        @Override
        public Matrix evaluate(float fraction, Matrix startValue, Matrix endValue) {
            return null;
        }
    };

    public static class MatrixEvaluator implements TypeEvaluator<Matrix> {

        float[] mTempStartValues = new float[9];

        float[] mTempEndValues = new float[9];

        Matrix mTempMatrix = new Matrix();

        @Override
        public Matrix evaluate(float fraction, Matrix startValue, Matrix endValue) {
            startValue.getValues(mTempStartValues);
            endValue.getValues(mTempEndValues);
            for (int i = 0; i < 9; i++) {
                float diff = mTempEndValues[i] - mTempStartValues[i];
                mTempEndValues[i] = mTempStartValues[i] + (fraction * diff);
            }
            mTempMatrix.setValues(mTempEndValues);
            return mTempMatrix;
        }
    }

    private static void animateTransform(ImageView imageView, Matrix matrix) {
        try {
            Method animateTransform = imageView.getClass().getMethod("animateTransform", Matrix.class);
            animateTransform.invoke(imageView, matrix);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Matrix getImageViewMatrix(Rect bounds, ImageView.ScaleType scaleType, int contentWidth, int contentHeight) {
        Matrix matrix = new Matrix();
        final int dwidth = contentWidth;
        final int dheight = contentHeight;

        final int vwidth = bounds.width();
        final int vheight = bounds.height();

        final boolean fits = (dwidth < 0 || vwidth == dwidth)
                && (dheight < 0 || vheight == dheight);

        if (dwidth <= 0 || dheight <= 0 || ImageView.ScaleType.FIT_XY == scaleType) {
            //默认Matrix
        } else {
            // We need to do the scaling ourself, so have the drawable
            // use its native size.
            if (ImageView.ScaleType.MATRIX == scaleType) {
                //调用方处理
                throw new RuntimeException("ImageView.ScaleType.MATRIX == scaleType!!");
            } else if (fits) {
                // The bitmap fits exactly, no transform needed.
            } else if (ImageView.ScaleType.CENTER == scaleType) {
                // Center bitmap in view, no scaling.
                matrix.setTranslate(Math.round((vwidth - dwidth) * 0.5f),
                        Math.round((vheight - dheight) * 0.5f));
            } else if (ImageView.ScaleType.CENTER_CROP == scaleType) {

                float scale;
                float dx = 0, dy = 0;

                if (dwidth * vheight > vwidth * dheight) {
                    scale = (float) vheight / (float) dheight;
                    dx = (vwidth - dwidth * scale) * 0.5f;
                } else {
                    scale = (float) vwidth / (float) dwidth;
                    dy = (vheight - dheight * scale) * 0.5f;
                }

                matrix.setScale(scale, scale);
                matrix.postTranslate(Math.round(dx), Math.round(dy));
            } else if (ImageView.ScaleType.CENTER_INSIDE == scaleType) {
                float scale;
                float dx;
                float dy;

                if (dwidth <= vwidth && dheight <= vheight) {
                    scale = 1.0f;
                } else {
                    scale = Math.min((float) vwidth / (float) dwidth,
                            (float) vheight / (float) dheight);
                }

                dx = Math.round((vwidth - dwidth * scale) * 0.5f);
                dy = Math.round((vheight - dheight * scale) * 0.5f);

                matrix.setScale(scale, scale);
                matrix.postTranslate(dx, dy);
            } else {
                // Generate the required transform.
                RectF tempSrc = new RectF();
                RectF tempDst = new RectF();
                tempSrc.set(0, 0, dwidth, dheight);
                tempDst.set(0, 0, vwidth, vheight);

                matrix.setRectToRect(tempSrc, tempDst, scaleTypeToScaleToFit(scaleType));
            }
        }
        return matrix;
    }

    private static Matrix.ScaleToFit scaleTypeToScaleToFit(ImageView.ScaleType st) {
        // ScaleToFit enum to their corresponding Matrix.ScaleToFit values
        return sS2FArray[st.ordinal() - 1];
    }

    private static final Matrix.ScaleToFit[] sS2FArray = {
            Matrix.ScaleToFit.FILL,
            Matrix.ScaleToFit.START,
            Matrix.ScaleToFit.CENTER,
            Matrix.ScaleToFit.END
    };

    public static void setsBitmapSizeCalculator(IBitmapSizeCalculator bitmapSizeCalculator) {
        if (bitmapSizeCalculator != null) {
            sBitmapSizeCalculator = bitmapSizeCalculator;
        }
    }
}