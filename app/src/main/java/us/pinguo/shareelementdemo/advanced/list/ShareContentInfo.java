package us.pinguo.shareelementdemo.advanced.list;

import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import us.pinguo.shareelementdemo.advanced.BaseData;
import com.hw.ycshareelement.transition.ShareElementInfo;

/**
 * Created by huangwei on 2018/9/27.
 */
public class ShareContentInfo extends ShareElementInfo<BaseData> {

    public ShareContentInfo(@NonNull View view, @Nullable BaseData data) {
        super(view, data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    protected ShareContentInfo(Parcel in) {
        super(in);
    }

    public static final Creator<ShareContentInfo> CREATOR = new Creator<ShareContentInfo>() {
        @Override
        public ShareContentInfo createFromParcel(Parcel source) {
            return new ShareContentInfo(source);
        }

        @Override
        public ShareContentInfo[] newArray(int size) {
            return new ShareContentInfo[size];
        }
    };
}
