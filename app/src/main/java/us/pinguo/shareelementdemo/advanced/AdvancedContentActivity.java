package us.pinguo.shareelementdemo.advanced;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import us.pinguo.shareelementdemo.R;
import us.pinguo.shareelementdemo.TransitionHelper;

/**
 * Created by huangwei on 2018/9/18 0018.
 */
public class AdvancedContentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        TransitionHelper.enableTransition(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        AdvancedContentFragment advancedContentFragment = new AdvancedContentFragment();
        advancedContentFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.simple_container,advancedContentFragment).commit();
//        postponeEnterTransition();
//
//        getWindow().getDecorView().post(new Runnable() {
//            @Override
//            public void run() {
//                new Handler().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        startPostponedEnterTransition();
//                    }
//                });
//            }
//        });
    }
}
