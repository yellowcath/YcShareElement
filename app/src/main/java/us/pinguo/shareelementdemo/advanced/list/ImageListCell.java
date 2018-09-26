package us.pinguo.shareelementdemo.advanced.list;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;
import us.pinguo.shareelementdemo.R;
import us.pinguo.shareelementdemo.advanced.Image;

/**
 * Created by huangwei on 2018/9/19 0019.
 */
public class ImageListCell extends BaseListCell<Image,RecyclerView.ViewHolder> {

    public ImageListCell(Image image) {
        super(image);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder,position);
        ImageView imageView = holder.itemView.findViewById(R.id.list_item_img);
        ViewCompat.setTransitionName(imageView,mData.url);
        setSize(imageView);

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
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_image, parent, false);
        return new BaseViewHolder(view);
    }

    private void setSize(ImageView imageView) {
        int width = (imageView.getResources().getDisplayMetrics().widthPixels -
                imageView.getResources().getDimensionPixelSize(R.dimen.divider) * 3) / 2;
        int height = width;//(int) (mData.height / (float) mData.width * width);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = height;
        imageView.setLayoutParams(layoutParams);
    }

    @Override
    protected int getType() {
        return 0;
    }

    @Override
    public View getShareElement() {
        return mViewHolder.itemView.findViewById(R.id.list_item_img);
    }
}
