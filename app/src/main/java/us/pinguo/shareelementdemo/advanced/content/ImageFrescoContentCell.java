package us.pinguo.shareelementdemo.advanced.content;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import androidx.core.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import us.pinguo.shareelementdemo.R;
import us.pinguo.shareelementdemo.advanced.Image;
import us.pinguo.shareelementdemo.advanced.content.viewpager.BasePagerViewHolder;

/**
 * Created by huangwei on 2018/9/20.
 */
public class ImageFrescoContentCell extends ImageContentCell {

    public ImageFrescoContentCell(Image image) {
        super(image);
    }

    @Override
    public View getShareElement() {
        return mViewHolder == null ? null : mViewHolder.getView(R.id.content_item_img);
    }

    @Override
    protected BasePagerViewHolder createViewHolder(ViewGroup parent) {
        return createHolderByLayout(R.layout.item_content_image_fresco, parent);
    }

    @Override
    protected void onBindViewHolder(BasePagerViewHolder viewHolder) {
        Fresco.getImagePipeline().evictFromCache(Uri.parse(mData.url));
        SimpleDraweeView imageView = viewHolder.getView(R.id.content_item_img);
        ViewCompat.setTransitionName(imageView, mData.url);
        Bitmap thumbnail = mData.url.equals(BitmapThumbnail.sKey)?BitmapThumbnail.sBitmap:null;
        GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
        hierarchy.setPlaceholderImage(new BitmapDrawable(imageView.getResources(),thumbnail), ScalingUtils.ScaleType.FIT_CENTER);
        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
        imageView.setImageURI(mData.url);
    }


    @Override
    protected int getType() {
        return 2;
    }
}
