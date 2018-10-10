package us.pinguo.shareelementdemo.advanced.content;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import us.pinguo.shareelementdemo.R;
import us.pinguo.shareelementdemo.advanced.Video;
import us.pinguo.shareelementdemo.advanced.content.viewpager.BasePagerViewHolder;

/**
 * Created by huangwei on 2018/9/20.
 */
public class VideoContentCell extends BaseContentCell<Video> {
    private boolean mStartPlay;

    public VideoContentCell(Video video) {
        super(video);
    }

    @Override
    protected BasePagerViewHolder createViewHolder(ViewGroup parent) {
        return createHolderByLayout(R.layout.item_content_video, parent);
    }

    @Override
    protected void onBindViewHolder(BasePagerViewHolder viewHolder) {
        //COVER
        final ImageView coverImg = viewHolder.getView(R.id.content_item_video_cover);
        ViewCompat.setTransitionName(coverImg, mData.url);
        Glide.with(coverImg)
                .load(mData.url+"?vframe/jpg/offset/0")
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transform(new FitCenter())
                        .placeholder(new ColorDrawable(Color.GRAY)))
                .into(coverImg);
        //VIDEO
        StandardGSYVideoPlayer videoView = viewHolder.getView(R.id.content_item_video);
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(mData.url)
                .setCacheWithPlay(true)
                .setLooping(true)
                .setGSYVideoProgressListener(new GSYVideoProgressListener() {
                    @Override
                    public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
                        if (progress > 0) {
                            coverImg.setVisibility(View.GONE);
                        }
                    }
                })
                .build(videoView);
        if (mStartPlay) {
            mStartPlay = false;
            videoView.startPlayLogic();
        }
    }

    @Override
    public View getShareElement() {
        return mViewHolder == null ? null : mViewHolder.getView(R.id.content_item_video_cover);
    }

    public void startPlay() {
        if (mViewHolder == null || mViewHolder.itemView == null) {
            mStartPlay = true;
            return;
        }
        StandardGSYVideoPlayer videoView = mViewHolder.getView(R.id.content_item_video);
        videoView.startPlayLogic();
    }

    @Override
    protected int getType() {
        return 1;
    }
}
