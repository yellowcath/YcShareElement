package us.pinguo.shareelementdemo.simple;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;
import com.hw.ycshareelement.YcShareElement;
import us.pinguo.shareelementdemo.R;
import us.pinguo.shareelementdemo.advanced.BaseData;
import com.hw.ycshareelement.transition.IShareElements;
import com.hw.ycshareelement.transition.ShareElementInfo;

/**
 * Created by huangwei on 2018/9/18 0018.
 */
public class SimpleFromActivity extends AppCompatActivity implements IShareElements {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from);

        final ImageView imgView = findViewById(R.id.s1_img);
        Glide.with(this)
                .load(R.drawable.test)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .transform(new FitCenter())
                        .placeholder(new ColorDrawable(Color.GRAY)))
                .into(imgView);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SimpleFromActivity.this, SimpleToActivity.class);
                Bundle optionsBundle = YcShareElement.buildOptionsBundle(SimpleFromActivity.this,SimpleFromActivity.this);
                startActivity(intent, optionsBundle);
            }
        });
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    @Override
    public ShareElementInfo[] getShareElements() {
        final ImageView imgView = findViewById(R.id.s1_img);
        return new ShareElementInfo[]{new ShareElementInfo(imgView, new BaseData(null,1024, 768))};
    }
}
