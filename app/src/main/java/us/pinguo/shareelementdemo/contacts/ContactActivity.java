package us.pinguo.shareelementdemo.contacts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hw.ycshareelement.YcShareElement;
import com.hw.ycshareelement.transform.GetShareElement;
import com.hw.ycshareelement.transform.ShareElementInfo;
import com.hw.ycshareelement.transform.TextViewStateSaver;
import us.pinguo.shareelementdemo.R;

/**
 * Created by huangwei on 2018/10/6.
 */
public class ContactActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        final ImageView avatarImg = findViewById(R.id.avatar);
        final TextView nameTxt = findViewById(R.id.name);
        nameTxt.setTextColor(Color.BLUE);
        Glide.with(avatarImg).load(R.drawable.avatar).apply(RequestOptions.circleCropTransform()).into(avatarImg);

        ViewCompat.setTransitionName(avatarImg, "avatar");
        ViewCompat.setTransitionName(nameTxt, "name");

        findViewById(R.id.contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, DetailActivity.class);
                Bundle bundle = YcShareElement.buildOptionsBundle(ContactActivity.this, new GetShareElement() {
                    @Override
                    public ShareElementInfo[] getShareElements() {
                        return new ShareElementInfo[]{new ShareElementInfo(avatarImg),
                                new ShareElementInfo(nameTxt, new TextViewStateSaver())};
                    }
                });
                ActivityCompat.startActivity(ContactActivity.this, intent, bundle);
            }
        });
    }
}
