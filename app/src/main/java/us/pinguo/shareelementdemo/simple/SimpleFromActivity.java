package us.pinguo.shareelementdemo.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import us.pinguo.shareelementdemo.R;
import us.pinguo.shareelementdemo.TransitionHelper;

/**
 * Created by huangwei on 2018/9/18 0018.
 */
public class SimpleFromActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        TransitionHelper.enableTransition(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from);

        final View imgView = findViewById(R.id.s1_img);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SimpleFromActivity.this, SimpleToActivity.class);
                Bundle optionsBundle = TransitionHelper.getTransitionBundle(SimpleFromActivity.this, imgView);
                startActivity(intent, optionsBundle);
            }
        });
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }
}
