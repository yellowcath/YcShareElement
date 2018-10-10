package us.pinguo.shareelementdemo.advanced.list;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import us.pinguo.shareelementdemo.R;
import us.pinguo.shareelementdemo.advanced.Video;

/**
 * Created by huangwei on 2018/9/19 0019.
 */
public class VideoListCell extends BaseListCell<Video, RecyclerView.ViewHolder> {

    private static final float MIN_VIDEO_RATIO = 3f / 4;
    private static final float MAX_VIDEO_RATIO = 4f / 3;


    public VideoListCell(Video video) {
        super(video);
    }

    @Override
    protected BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_video, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        SimpleDraweeView simpleDraweeView = holder.itemView.findViewById(R.id.list_item_video);
        ViewCompat.setTransitionName(simpleDraweeView, mData.url);
        setSize(holder.itemView);
        showWebp(simpleDraweeView, mData.webpUrl, true);
    }

    @Override
    public View getShareElement() {
        SimpleDraweeView simpleDraweeView = mViewHolder.itemView.findViewById(R.id.list_item_video);
        return simpleDraweeView;
    }

    public void showWebp(final SimpleDraweeView simpleDraweeView, final String url, boolean autoplay) {
        simpleDraweeView.getHierarchy().setBackgroundImage(new ColorDrawable(Color.GRAY));
        simpleDraweeView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(simpleDraweeView.getController())
                .setControllerListener(new DeliveryControllerListener<ImageInfo>(null) {
                    @Override
                    public void onFinalImageSet(String s, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                        super.onFinalImageSet(s, imageInfo, animatable);
                        simpleDraweeView.getHierarchy().setBackgroundImage(null);
                    }
                })
                .setAutoPlayAnimations(autoplay)
                .setUri(url)
                .build();
        simpleDraweeView.setController(controller);
    }

    @Override
    protected int getType() {
        return 1;
    }

    private void setSize(View view) {
        float videoRatio = mData.width / mData.height;
        videoRatio = Math.max(videoRatio, MIN_VIDEO_RATIO);
        videoRatio = Math.min(videoRatio, MAX_VIDEO_RATIO);

        int width = (view.getResources().getDisplayMetrics().widthPixels -
                view.getResources().getDimensionPixelSize(R.dimen.divider) * 3) / 2;
        int height = width;//(int) (width / videoRatio);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }

    @Override
    public Bitmap getThumbnail() {
        return null;
    }

}
