package us.pinguo.shareelementdemo.contacts;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huangwei on 2018/10/12.
 */
public class Contacts implements Parcelable{
    public String name;
    public int avatarRes;
    public String desc;

    public Contacts(String name, int avatarRes, String desc) {
        this.name = name;
        this.avatarRes = avatarRes;
        this.desc = desc;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.avatarRes);
        dest.writeString(this.desc);
    }

    protected Contacts(Parcel in) {
        this.name = in.readString();
        this.avatarRes = in.readInt();
        this.desc = in.readString();
    }

    public static final Creator<Contacts> CREATOR = new Creator<Contacts>() {
        @Override
        public Contacts createFromParcel(Parcel source) {
            return new Contacts(source);
        }

        @Override
        public Contacts[] newArray(int size) {
            return new Contacts[size];
        }
    };
}
