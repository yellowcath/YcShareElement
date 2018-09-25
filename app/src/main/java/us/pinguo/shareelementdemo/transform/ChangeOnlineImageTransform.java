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
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import us.pinguo.shareelementdemo.R;

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
        Rect startBounds = (Rect) startValues.values.get(PROPNAME_BOUNDS);
        Rect endBounds = (Rect) endValues.values.get(PROPNAME_BOUNDS);
        ShareImageViewInfo shareElementInfo = (ShareImageViewInfo) startValues.values.get(PROPNAME_INFO);

        Rect transfromViewRect = new Rect();
        boolean isEnter = shareElementInfo.getTansfromViewBounds().width() == 0;
        if (isEnter) {
            transfromViewRect.set(endBounds);
        } else {
            transfromViewRect.set(shareElementInfo.getTansfromViewBounds());
        }

        ImageView.ScaleType startScaleType = (ImageView.ScaleType) startValues.values.get(PROPNAME_SCALE_TYPE);
        ImageView.ScaleType endScaleType = (ImageView.ScaleType) endValues.values.get(PROPNAME_SCALE_TYPE);
        BitmapInfo transfromBitmapInfo = sBitmapSizeCalculator.calculateImageSize(
                transfromViewRect,
                isEnter ? endScaleType : shareElementInfo.mTranfromViewScaleType,
                shareElementInfo.getImageOriginWidth(),
                shareElementInfo.getImageOriginHeight());

        if (startScaleType == ImageView.ScaleType.MATRIX) {
            startMatrix.set((Matrix) startValues.values.get(PROPNAME_MATRIX));
        } else {
            //注意：这里要计算的是如何给出的ImageView模拟出初始状态的ImageView
            //这里不管是进入还是退出，都用的是第二个Activity的控件，因此Bitmap都是第二个Activity的，需要据此Bitmap的大小计算动画
            startMatrix.set(getImageViewMatrix(startBounds, startScaleType, transfromBitmapInfo.width, transfromBitmapInfo.height));
        }

        if (endScaleType == ImageView.ScaleType.MATRIX) {
            endMatrix.set((Matrix) endValues.values.get(PROPNAME_MATRIX));
        } else {
            //这里要计算的是如何给出的ImageView模拟出结束状态的ImageView
            endMatrix.set(getImageViewMatrix(endBounds, endScaleType, transfromBitmapInfo.width, transfromBitmapInfo.height));
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

        Matrix startMatrix = new Matrix();
        Matrix endMatrix = new Matrix();
        calculateMatrix(startValues, endValues, startMatrix, endMatrix);
        boolean matricesEqual = (startMatrix == null && endMatrix == null) ||
                (startMatrix != null && startMatrix.equals(endMatrix));

        if (startBounds.equals(endBounds) && matricesEqual) {
            return null;
        }

        //matrix
        final Drawable drawable = imageView.getDrawable();
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();

        //Matrix Animator
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
        }

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(matrixAnimator);
        final ImageView.ScaleType scaleType = imageView.getScaleType();
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageView.setScaleType(scaleType);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return animatorSet;
    }

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
            object.setImageMatrix(value);
        }

        @Override
        public Matrix get(ImageView object) {
            return object.getImageMatrix();
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