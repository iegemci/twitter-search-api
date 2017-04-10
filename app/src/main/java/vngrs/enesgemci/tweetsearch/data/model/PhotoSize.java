package vngrs.enesgemci.tweetsearch.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import vngrs.enesgemci.tweetsearch.data.model.base.JsonData;

/**
 * Created by enesgemci on 09/04/2017.
 */

public class PhotoSize extends JsonData implements Parcelable {

    private int w;
    private int h;
    private String resize;

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public String getResize() {
        return resize;
    }

    public void setResize(String resize) {
        this.resize = resize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.w);
        dest.writeInt(this.h);
        dest.writeString(this.resize);
    }

    public PhotoSize() {
    }

    protected PhotoSize(Parcel in) {
        this.w = in.readInt();
        this.h = in.readInt();
        this.resize = in.readString();
    }

    public static final Parcelable.Creator<PhotoSize> CREATOR = new Parcelable.Creator<PhotoSize>() {
        @Override
        public PhotoSize createFromParcel(Parcel source) {
            return new PhotoSize(source);
        }

        @Override
        public PhotoSize[] newArray(int size) {
            return new PhotoSize[size];
        }
    };
}
