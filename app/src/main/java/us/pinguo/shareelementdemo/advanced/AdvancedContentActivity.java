package us.pinguo.shareelementdemo.advanced;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.hw.ycshareelement.YcShareElement;
import com.hw.ycshareelement.transform.GetShareElement;
import com.hw.ycshareelement.transform.ShareElementInfo;
import us.pinguo.shareelementdemo.R;

/**
 * Created by huangwei on 2018/9/18 0018.
 */
public class AdvancedContentActivity extends AppCompatActivity implements GetShareElement {

    AdvancedContentFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        YcShareElement.postponeEnterTransition(this, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        getWindow().setBackgroundDrawable(new ColorDrawable(0xFF323232));
        mFragment = new AdvancedContentFragment();
        mFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.simple_container, mFragment).commit();
    }

    @Override
    public void finishAfterTransition() {
        YcShareElement.finishAfterTransition(this, this);
        super.finishAfterTransition();
    }

    @Override
    public ShareElementInfo[] getShareElements() {
        return mFragment.getShareElements();
    }
}
