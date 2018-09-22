package us.pinguo.shareelementdemo.simple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import us.pinguo.shareelementdemo.R;
import us.pinguo.shareelementdemo.transform.YcShareElement;

/**
 * Created by huangwei on 2018/9/18 0018.
 */
public class SimpleToActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        YcShareElement.beforeOnCreate(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to);
        YcShareElement.callReadyAfterPreDraw(this);
    }
}
