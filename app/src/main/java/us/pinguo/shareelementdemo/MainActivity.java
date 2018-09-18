package us.pinguo.shareelementdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import us.pinguo.shareelementdemo.simple.SimpleFragmentActivity;
import us.pinguo.shareelementdemo.simple.SimpleFromActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.simple_fragment_btn).setOnClickListener(this);
        findViewById(R.id.simple_activity_btn).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.simple_fragment_btn:
                startActivity(new Intent(this, SimpleFragmentActivity.class));
                break;
            case R.id.simple_activity_btn:
                startActivity(new Intent(this, SimpleFromActivity.class));
                break;
        }
    }
}
