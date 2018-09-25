package us.pinguo.shareelementdemo.advanced;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import us.pinguo.shareelementdemo.R;
import us.pinguo.shareelementdemo.transform.IShareElementSelector;
import us.pinguo.shareelementdemo.transform.ShareElementInfo;
import us.pinguo.shareelementdemo.transform.YcShareElement;

import java.util.List;

/**
 * Created by huangwei on 2018/9/18 0018.
 */
public class AdvancedListActivity extends AppCompatActivity {
    private AdvancedListFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_simple);
        mFragment = new AdvancedListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.simple_container, mFragment).commit();
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        YcShareElement.onActivityReenter(this, resultCode, data, new IShareElementSelector() {
            @Override
            public void selectShareElements(List<ShareElementInfo> list) {
                mFragment.selectShareElement(list.get(0));
            }
        });
    }
}
