package us.pinguo.shareelementdemo.advanced.content;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;
import us.pinguo.shareelementdemo.R;
import us.pinguo.shareelementdemo.advanced.Image;
import us.pinguo.shareelementdemo.advanced.content.viewpager.BasePagerViewHolder;

/**
 * Created by huangwei on 2018/9/20.
 */
public class ImageContentCell extends BaseContentCell<Image> {

    public ImageContentCell(Image image) {
        super(image);
    }

    @Override
    protected View getShareElement() {
        return mViewHolder==null?null:mViewHolder.getView(R.id.content_item_img);
    }

    @Override
    protected BasePagerViewHolder createViewHolder(ViewGroup parent) {
        return createHolderByLayout(R.layout.item_content_image,parent);
    }

    @Override
    protected void onBindViewHolder(BasePagerViewHolder viewHolder) {
        ImageView imageView = viewHolder.getView(R.id.content_item_img);
        imageView.setTransitionName(mData.url);
        Glide.with(imageView)
                .load(mData.url)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transform(new FitCenter())
                        .skipMemoryCache(true)
                        .placeholder(new ColorDrawable(Color.GRAY)))
                .into(imageView);
    }


    @Override
    protected int getType() {
        return 0;
    }

}
