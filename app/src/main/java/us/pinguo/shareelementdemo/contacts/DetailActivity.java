package us.pinguo.shareelementdemo.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hw.ycshareelement.YcShareElement;
import com.hw.ycshareelement.transition.IShareElements;
import com.hw.ycshareelement.transition.ShareElementInfo;
import com.hw.ycshareelement.transition.TextViewStateSaver;
import us.pinguo.shareelementdemo.R;


/**
 * Created by huangwei on 2018/10/6.
 */
public class DetailActivity extends Activity {

    private ImageView mAvatarImg;
    private TextView mNameTxt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mAvatarImg = findViewById(R.id.avatar);
        mNameTxt = findViewById(R.id.name);
        setData();

        YcShareElement.setEnterTransitions(this, new IShareElements() {
            @Override
            public ShareElementInfo[] getShareElements() {
                return new ShareElementInfo[]{new ShareElementInfo(mAvatarImg),
                        new ShareElementInfo(mNameTxt, new TextViewStateSaver())};
            }
        },false);
//        YcShareElement.startTransition(this);
    }

    private void setData(){
        TextView descTxt = findViewById(R.id.desc);
        Contacts item = getIntent().getParcelableExtra(ContactsActivity.KEY_CONTACTS);
        Glide.with(mAvatarImg).load(item.avatarRes).apply(RequestOptions.circleCropTransform()).into(mAvatarImg);
        mNameTxt.setText(item.name);
        descTxt.setText(item.desc);

        ViewCompat.setTransitionName(mAvatarImg,"avatar:"+item.name);
        ViewCompat.setTransitionName(mNameTxt,"name:"+item.name);
    }
}
