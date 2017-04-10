package vngrs.enesgemci.tweetsearch.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import vngrs.enesgemci.tweetsearch.data.model.base.JsonData;

/**
 * Created by enesgemci on 09/04/2017.
 */

public class StatusMetaData extends JsonData implements Parcelable {

    private String isoLanguageCode;
    private String resultType;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.isoLanguageCode);
        dest.writeString(this.resultType);
    }

    public StatusMetaData() {
    }

    protected StatusMetaData(Parcel in) {
        this.isoLanguageCode = in.readString();
        this.resultType = in.readString();
    }

    public static final Parcelable.Creator<StatusMetaData> CREATOR = new Parcelable.Creator<StatusMetaData>() {
        @Override
        public StatusMetaData createFromParcel(Parcel source) {
            return new StatusMetaData(source);
        }

        @Override
        public StatusMetaData[] newArray(int size) {
            return new StatusMetaData[size];
        }
    };
}
