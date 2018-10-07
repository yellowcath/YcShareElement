package us.pinguo.shareelementdemo.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.widget.ImageView;
import android.widget.TextView;
import us.pinguo.shareelementdemo.R;

/**
 * Created by huangwei on 2018/10/6.
 */
public class DetailActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView avatarImg = findViewById(R.id.avatar);
        TextView nameTxt = findViewById(R.id.name);

        ViewCompat.setTransitionName(avatarImg,"avatar");
        ViewCompat.setTransitionName(nameTxt,"name");

        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new ChangeTransform());
        transitionSet.addTarget(avatarImg);
        transitionSet.addTarget(nameTxt);
        getWindow().setSharedElementEnterTransition(transitionSet);
        getWindow().setSharedElementExitTransition(transitionSet);
    }
}
