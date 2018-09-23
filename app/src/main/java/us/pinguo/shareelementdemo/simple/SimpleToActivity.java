package us.pinguo.shareelementdemo.simple;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;
import us.pinguo.shareelementdemo.R;
import us.pinguo.shareelementdemo.transform.NoClipTransformation;
import us.pinguo.shareelementdemo.transform.YcShareElement;

/**
 * Created by huangwei on 2018/9/18 0018.
 */
public class SimpleToActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        YcShareElement.beforeOnCreate(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to);
        ImageView imageView = findViewById(R.id.s2_img);
        Glide.with(this)
                .load(R.drawable.test)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .transform(new FitCenter())
                        .placeholder(new ColorDrawable(Color.GRAY)))
                .into(imageView);
        YcShareElement.callReadyAfterPreDraw(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
