package us.pinguo.shareelementdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.hw.ycshareelement.YcShareElement;
import us.pinguo.shareelementdemo.advanced.AdvancedListActivity;
import us.pinguo.shareelementdemo.contacts.ContactsActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        YcShareElement.enableContentTransition(getApplication());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        findViewById(R.id.simple_fragment_btn).setOnClickListener(this);
        findViewById(R.id.simple_activity_btn).setOnClickListener(this);
        findViewById(R.id.recyclerview_btn).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.simple_fragment_btn:
//                startActivity(new Intent(this, SimpleFragmentActivity.class));
//                break;
            case R.id.simple_activity_btn:
                startActivity(new Intent(this, ContactsActivity.class));
                break;
            case R.id.recyclerview_btn:
                startActivity(new Intent(this, AdvancedListActivity.class));
                break;
        }
    }
}
