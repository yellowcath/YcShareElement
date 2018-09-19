package us.pinguo.shareelementdemo.advanced;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import us.pinguo.shareelementdemo.R;

/**
 * Created by huangwei on 2018/9/18 0018.
 */
public class AdvancedListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_simple);
        getSupportFragmentManager().beginTransaction().add(R.id.simple_container, new AdvancedListFragment()).commit();
    }
}
