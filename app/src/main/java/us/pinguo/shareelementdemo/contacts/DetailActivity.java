package us.pinguo.shareelementdemo.contacts;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hw.ycshareelement.YcShareElement;
import com.hw.ycshareelement.transform.IShareElements;
import com.hw.ycshareelement.transform.ShareElementInfo;
import com.hw.ycshareelement.transform.TextViewStateSaver;
import us.pinguo.shareelementdemo.R;

/**
 * Created by huangwei on 2018/10/6.
 */
public class DetailActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final ImageView avatarImg = findViewById(R.id.avatar);
        final TextView nameTxt = findViewById(R.id.name);
        nameTxt.setTextColor(Color.RED);
        Glide.with(avatarImg).load(R.drawable.avatar).apply(RequestOptions.circleCropTransform()).into(avatarImg);

        ViewCompat.setTransitionName(avatarImg, "avatar");
        ViewCompat.setTransitionName(nameTxt, "name");

        YcShareElement.setEnterTransition(this, new IShareElements() {
            @Override
            public ShareElementInfo[] getShareElements() {
                return new ShareElementInfo[]{new ShareElementInfo(avatarImg),
                        new ShareElementInfo(nameTxt, new TextViewStateSaver())};
            }
        },false);
//        YcShareElement.startTransition(this);
    }
}
