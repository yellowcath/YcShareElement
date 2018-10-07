package us.pinguo.shareelementdemo.advanced.list;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.common.references.CloseableReference;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import us.pinguo.shareelementdemo.R;
import us.pinguo.shareelementdemo.advanced.Image;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Created by huangwei on 2018/9/19 0019.
 */
public class ImageFrescoListCell extends ImageListCell {
    private SoftReference<CloseableStaticBitmap> mBitmapReference;

    public ImageFrescoListCell(Image image) {
        super(image);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SimpleDraweeView imageView = holder.itemView.findViewById(R.id.list_item_img_fresco);
        ViewCompat.setTransitionName(imageView, mData.url);
        setSize(holder.itemView);

        GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);

        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder().setUri(mData.url).setOldController(imageView.getController())
                .setControllerListener(new ControllerListener<ImageInfo>() {
                    @Override
                    public void onSubmit(String id, Object callerContext) {

                    }

                    @Override
                    public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                        if(imageInfo instanceof CloseableStaticBitmap){
                            CloseableStaticBitmap bitmapCloseableReference = ((CloseableStaticBitmap) imageInfo);
                            mBitmapReference = new SoftReference<>(bitmapCloseableReference);
                        }
                    }

                    @Override
                    public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {

                    }

                    @Override
                    public void onIntermediateImageFailed(String id, Throwable throwable) {

                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {

                    }

                    @Override
                    public void onRelease(String id) {

                    }
                }).build();
        imageView.setController(controller);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnCellClickListener!=null){
                    mOnCellClickListener.onCellClick(ImageFrescoListCell.this);
                }
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_image_fresco, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    protected int getType() {
        return 3;
    }

    @Override
    public View getShareElement() {
        return mViewHolder.itemView.findViewById(R.id.list_item_img_fresco);
    }

    @Override
    public Bitmap getThumbnail() {
        if (mViewHolder == null || mViewHolder.itemView == null) {
            return null;
        }
        if (mBitmapReference!=null && mBitmapReference.get()!=null) {
            Bitmap bitmap = Bitmap.createBitmap(mBitmapReference.get().getUnderlyingBitmap());
            return bitmap;
        }
        return null;
    }
}
