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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
        Glide.with(avatarImg).load(R.drawable.avatar).apply(RequestOptions.circleCropTransform()).into(avatarImg);
        /**
         * 1、设置相同的TransitionName
         */
        ViewCompat.setTransitionName(avatarImg,"avatar");
        ViewCompat.setTransitionName(nameTxt,"name");
        /**
         * 2、设置WindowTransition,除指定的ShareElement外，其它所有View都会执行这个Transition动画
         */
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new ChangeTransform());
        transitionSet.addTarget(avatarImg);
        transitionSet.addTarget(nameTxt);
        /**
         * 3、设置ShareElementTransition,指定的ShareElement会执行这个Transiton动画
         */
        getWindow().setSharedElementEnterTransition(transitionSet);
        getWindow().setSharedElementExitTransition(transitionSet);
    }
}
