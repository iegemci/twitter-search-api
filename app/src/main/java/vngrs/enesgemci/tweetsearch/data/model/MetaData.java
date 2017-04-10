package vngrs.enesgemci.tweetsearch.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import vngrs.enesgemci.tweetsearch.data.model.base.JsonData;

/**
 * Created by enesgemci on 09/04/2017.
 */

public class MetaData extends JsonData implements Parcelable {

    private double completedIn;
    private long maxId;
    private String maxIdStr;
    private String query;
    private String refreshUrl;
    private int count;
    private long sinceId;
    private String sinceIdStr;

    public double getCompletedIn() {
        return completedIn;
    }

    public void setCompletedIn(double completedIn) {
        this.completedIn = completedIn;
    }

    public long getMaxId() {
        return maxId;
    }

    public void setMaxId(long maxId) {
        this.maxId = maxId;
    }

    public String getMaxIdStr() {
        return maxIdStr;
    }

    public void setMaxIdStr(String maxIdStr) {
        this.maxIdStr = maxIdStr;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getRefreshUrl() {
        return refreshUrl;
    }

    public void setRefreshUrl(String refreshUrl) {
        this.refreshUrl = refreshUrl;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getSinceId() {
        return sinceId;
    }

    public void setSinceId(long sinceId) {
        this.sinceId = sinceId;
    }

    public String getSinceIdStr() {
        return sinceIdStr;
    }

    public void setSinceIdStr(String sinceIdStr) {
        this.sinceIdStr = sinceIdStr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.completedIn);
        dest.writeLong(this.maxId);
        dest.writeString(this.maxIdStr);
        dest.writeString(this.query);
        dest.writeString(this.refreshUrl);
        dest.writeInt(this.count);
        dest.writeLong(this.sinceId);
        dest.writeString(this.sinceIdStr);
    }

    public MetaData() {
    }

    protected MetaData(Parcel in) {
        this.completedIn = in.readDouble();
        this.maxId = in.readLong();
        this.maxIdStr = in.readString();
        this.query = in.readString();
        this.refreshUrl = in.readString();
        this.count = in.readInt();
        this.sinceId = in.readLong();
        this.sinceIdStr = in.readString();
    }

    public static final Parcelable.Creator<MetaData> CREATOR = new Parcelable.Creator<MetaData>() {
        @Override
        public MetaData createFromParcel(Parcel source) {
            return new MetaData(source);
        }

        @Override
        public MetaData[] newArray(int size) {
            return new MetaData[size];
        }
    };
}
