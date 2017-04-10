package vngrs.enesgemci.tweetsearch.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import vngrs.enesgemci.tweetsearch.data.model.base.JsonData;

/**
 * Created by enesgemci on 09/04/2017.
 */

public class Size extends JsonData implements Parcelable {

    private PhotoSize small;
    private PhotoSize medium;
    private PhotoSize thumb;
    private PhotoSize large;

    public PhotoSize getSmall() {
        return small;
    }

    public void setSmall(PhotoSize small) {
        this.small = small;
    }

    public PhotoSize getMedium() {
        return medium;
    }

    public void setMedium(PhotoSize medium) {
        this.medium = medium;
    }

    public PhotoSize getThumb() {
        return thumb;
    }

    public void setThumb(PhotoSize thumb) {
        this.thumb = thumb;
    }

    public PhotoSize getLarge() {
        return large;
    }

    public void setLarge(PhotoSize large) {
        this.large = large;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.small, flags);
        dest.writeParcelable(this.medium, flags);
        dest.writeParcelable(this.thumb, flags);
        dest.writeParcelable(this.large, flags);
    }

    public Size() {
    }

    protected Size(Parcel in) {
        this.small = in.readParcelable(PhotoSize.class.getClassLoader());
        this.medium = in.readParcelable(PhotoSize.class.getClassLoader());
        this.thumb = in.readParcelable(PhotoSize.class.getClassLoader());
        this.large = in.readParcelable(PhotoSize.class.getClassLoader());
    }

    public static final Parcelable.Creator<Size> CREATOR = new Parcelable.Creator<Size>() {
        @Override
        public Size createFromParcel(Parcel source) {
            return new Size(source);
        }

        @Override
        public Size[] newArray(int size) {
            return new Size[size];
        }
    };
}
