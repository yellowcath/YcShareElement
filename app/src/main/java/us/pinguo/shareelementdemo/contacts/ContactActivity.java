package us.pinguo.shareelementdemo.contacts;

import android.app.Activity;
import android.content.Intent;
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
import us.pinguo.shareelementdemo.R;

/**
 * Created by huangwei on 2018/10/6.
 */
public class ContactActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        final ImageView avatarImg = findViewById(R.id.avatar);
        final TextView nameTxt = findViewById(R.id.name);
        Glide.with(avatarImg).load(R.drawable.avatar).apply(RequestOptions.circleCropTransform()).into(avatarImg);

        ViewCompat.setTransitionName(avatarImg,"avatar");
        ViewCompat.setTransitionName(nameTxt,"name");

        findViewById(R.id.contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this,DetailActivity.class);
                Pair<View,String> pair1 = new Pair<>((View)avatarImg,ViewCompat.getTransitionName(avatarImg));
                Pair<View,String> pair2 = new Pair<>((View)nameTxt,ViewCompat.getTransitionName(nameTxt));
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(ContactActivity.this, pair1, pair2);
                ActivityCompat.startActivity(ContactActivity.this,intent,activityOptionsCompat.toBundle());
            }
        });
    }
}
