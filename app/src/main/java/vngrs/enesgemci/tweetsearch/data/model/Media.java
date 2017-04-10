package vngrs.enesgemci.tweetsearch.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import vngrs.enesgemci.tweetsearch.data.model.base.JsonData;

/**
 * Created by enesgemci on 09/04/2017.
 */

public class Media extends JsonData implements Parcelable {

    private long id;
    private String idStr;
    private List<Integer> indices;
    private String mediaUrl;
    private String mediaUrlHttps;
    private String url;
    private String displayUrl;
    private String expandedUrl;
    private String type;
    private Size sizes;
    private long sourceStatusId;
    private String sourceStatusIdStr;
    private long sourceUserId;
    private String sourceUserIdStr;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getMediaUrlHttps() {
        return mediaUrlHttps;
    }

    public void setMediaUrlHttps(String mediaUrlHttps) {
        this.mediaUrlHttps = mediaUrlHttps;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    public String getExpandedUrl() {
        return expandedUrl;
    }

    public void setExpandedUrl(String expandedUrl) {
        this.expandedUrl = expandedUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Size getSizes() {
        return sizes;
    }

    public void setSizes(Size sizes) {
        this.sizes = sizes;
    }

    public long getSourceStatusId() {
        return sourceStatusId;
    }

    public void setSourceStatusId(long sourceStatusId) {
        this.sourceStatusId = sourceStatusId;
    }

    public String getSourceStatusIdStr() {
        return sourceStatusIdStr;
    }

    public void setSourceStatusIdStr(String sourceStatusIdStr) {
        this.sourceStatusIdStr = sourceStatusIdStr;
    }

    public long getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public String getSourceUserIdStr() {
        return sourceUserIdStr;
    }

    public void setSourceUserIdStr(String sourceUserIdStr) {
        this.sourceUserIdStr = sourceUserIdStr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.idStr);
        dest.writeList(this.indices);
        dest.writeString(this.mediaUrl);
        dest.writeString(this.mediaUrlHttps);
        dest.writeString(this.url);
        dest.writeString(this.displayUrl);
        dest.writeString(this.expandedUrl);
        dest.writeString(this.type);
        dest.writeParcelable(this.sizes, flags);
        dest.writeLong(this.sourceStatusId);
        dest.writeString(this.sourceStatusIdStr);
        dest.writeLong(this.sourceUserId);
        dest.writeString(this.sourceUserIdStr);
    }

    public Media() {
    }

    protected Media(Parcel in) {
        this.id = in.readLong();
        this.idStr = in.readString();
        this.indices = new ArrayList<Integer>();
        in.readList(this.indices, Integer.class.getClassLoader());
        this.mediaUrl = in.readString();
        this.mediaUrlHttps = in.readString();
        this.url = in.readString();
        this.displayUrl = in.readString();
        this.expandedUrl = in.readString();
        this.type = in.readString();
        this.sizes = in.readParcelable(Size.class.getClassLoader());
        this.sourceStatusId = in.readLong();
        this.sourceStatusIdStr = in.readString();
        this.sourceUserId = in.readLong();
        this.sourceUserIdStr = in.readString();
    }

    public static final Parcelable.Creator<Media> CREATOR = new Parcelable.Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel source) {
            return new Media(source);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
}
