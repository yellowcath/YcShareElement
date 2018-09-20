package us.pinguo.shareelementdemo.advanced.content;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.player.SystemPlayerManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import us.pinguo.shareelementdemo.R;
import us.pinguo.shareelementdemo.advanced.Image;
import us.pinguo.shareelementdemo.advanced.Video;
import us.pinguo.shareelementdemo.advanced.content.viewpager.BasePagerCell;
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
        return createHolderByLayout(R.layout.item_content_video,parent);
    }

    @Override
    protected void onBindViewHolder(BasePagerViewHolder viewHolder) {
        StandardGSYVideoPlayer videoView = viewHolder.getView(R.id.content_item_video);
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption
//                .setThumbImageView(imageView)

                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(mData.videoUrl)
                .setCacheWithPlay(true)
                .setLooping(true)
        .build(videoView);
        if(mStartPlay){
            mStartPlay = false;
            videoView.startPlayLogic();
        }
    }

    public void startPlay(){
        if(mViewHolder==null || mViewHolder.itemView==null){
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
