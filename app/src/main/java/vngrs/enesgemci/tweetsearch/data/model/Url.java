package vngrs.enesgemci.tweetsearch.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import vngrs.enesgemci.tweetsearch.data.model.base.JsonData;

/**
 * Created by enesgemci on 09/04/2017.
 */

public class Url extends JsonData implements Parcelable {

    private String url;
    private String expandedUrl;
    private String displayUrl;
    private List<Integer> indices;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExpandedUrl() {
        return expandedUrl;
    }

    public void setExpandedUrl(String expandedUrl) {
        this.expandedUrl = expandedUrl;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.expandedUrl);
        dest.writeString(this.displayUrl);
        dest.writeList(this.indices);
    }

    public Url() {
    }

    protected Url(Parcel in) {
        this.url = in.readString();
        this.expandedUrl = in.readString();
        this.displayUrl = in.readString();
        this.indices = new ArrayList<Integer>();
        in.readList(this.indices, Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Url> CREATOR = new Parcelable.Creator<Url>() {
        @Override
        public Url createFromParcel(Parcel source) {
            return new Url(source);
        }

        @Override
        public Url[] newArray(int size) {
            return new Url[size];
        }
    };
}
