package us.pinguo.shareelementdemo.simple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import us.pinguo.shareelementdemo.R;
import us.pinguo.shareelementdemo.TransitionHelper;

/**
 * Created by huangwei on 2018/9/18 0018.
 */
public class SimpleToActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        TransitionHelper.enableTransition(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to);

        // Postpone the transition until the window's decor view has
        // finished its layout.
        postponeEnterTransition();

        final View decor = getWindow().getDecorView();
        decor.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                decor.getViewTreeObserver().removeOnPreDrawListener(this);
                startPostponedEnterTransition();
                return true;
            }
        });
    }
}
