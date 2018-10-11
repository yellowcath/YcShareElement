package us.pinguo.shareelementdemo.contacts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
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
public class ContactActivity extends Activity {
    private ImageView mAvatarImg;
    private TextView mNameTxt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        YcShareElement.enableContentTransition(getApplication());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        mAvatarImg = findViewById(R.id.avatar);
        mNameTxt = findViewById(R.id.name);
        mNameTxt.setTextColor(Color.BLUE);
        Glide.with(mAvatarImg).load(R.drawable.avatar).apply(RequestOptions.circleCropTransform()).into(mAvatarImg);

        ViewCompat.setTransitionName(mAvatarImg, "avatar");
        ViewCompat.setTransitionName(mNameTxt, "name");

        findViewById(R.id.contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              gotoDetailActivity();
            }
        });
    }

    private void gotoDetailActivity(){
        Intent intent = new Intent(this, DetailActivity.class);
        Bundle bundle = YcShareElement.buildOptionsBundle(ContactActivity.this, new IShareElements() {
            @Override
            public ShareElementInfo[] getShareElements() {
                return new ShareElementInfo[]{new ShareElementInfo(mAvatarImg),
                        new ShareElementInfo(mNameTxt, new TextViewStateSaver())};
            }
        });
        ActivityCompat.startActivity(ContactActivity.this, intent, bundle);
    }
}
