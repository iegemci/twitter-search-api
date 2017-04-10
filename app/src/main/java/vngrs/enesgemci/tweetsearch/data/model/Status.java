package vngrs.enesgemci.tweetsearch.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.TextUtils;

import com.google.gson.annotations.JsonAdapter;

import vngrs.enesgemci.tweetsearch.data.model.base.JsonData;
import vngrs.enesgemci.tweetsearch.network.converter.TwitterTypeAdapter;

/**
 * Created by enesgemci on 09/04/2017.
 */

public class Status extends JsonData implements Parcelable {

    private String createdAt;
    private long id;
    private String idStr;

    @JsonAdapter(TwitterTypeAdapter.class)
    private SpannableString text;

    private boolean truncated;
    private Entity entities;
    private StatusMetaData metadata;
    private String source;
    private long inReplyToStatusId;
    private String inReplyToStatusIdStr;
    private long inReplyToUserId;
    private String inReplyToUserIdStr;
    private String inReplyToScreenName;
    private Entity extendedEntities;
    private User user;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

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

    public SpannableString getText() {
        return text;
    }

    public void setText(SpannableString text) {
        this.text = text;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public Entity getEntities() {
        return entities;
    }

    public void setEntities(Entity entities) {
        this.entities = entities;
    }

    public StatusMetaData getMetadata() {
        return metadata;
    }

    public void setMetadata(StatusMetaData metadata) {
        this.metadata = metadata;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    public void setInReplyToStatusId(long inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    public String getInReplyToStatusIdStr() {
        return inReplyToStatusIdStr;
    }

    public void setInReplyToStatusIdStr(String inReplyToStatusIdStr) {
        this.inReplyToStatusIdStr = inReplyToStatusIdStr;
    }

    public long getInReplyToUserId() {
        return inReplyToUserId;
    }

    public void setInReplyToUserId(long inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    public String getInReplyToUserIdStr() {
        return inReplyToUserIdStr;
    }

    public void setInReplyToUserIdStr(String inReplyToUserIdStr) {
        this.inReplyToUserIdStr = inReplyToUserIdStr;
    }

    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    public Entity getExtendedEntities() {
        return extendedEntities;
    }

    public void setExtendedEntities(Entity extendedEntities) {
        this.extendedEntities = extendedEntities;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createdAt);
        dest.writeLong(this.id);
        dest.writeString(this.idStr);
        TextUtils.writeToParcel(text, dest, flags);
        dest.writeByte(this.truncated ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.entities, flags);
        dest.writeParcelable(this.metadata, flags);
        dest.writeString(this.source);
        dest.writeLong(this.inReplyToStatusId);
        dest.writeString(this.inReplyToStatusIdStr);
        dest.writeLong(this.inReplyToUserId);
        dest.writeString(this.inReplyToUserIdStr);
        dest.writeString(this.inReplyToScreenName);
        dest.writeParcelable(this.extendedEntities, flags);
        dest.writeParcelable(this.user, flags);
    }

    public Status() {
    }

    protected Status(Parcel in) {
        this.createdAt = in.readString();
        this.id = in.readLong();
        this.idStr = in.readString();
        this.text = (SpannableString) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        this.truncated = in.readByte() != 0;
        this.entities = in.readParcelable(Entity.class.getClassLoader());
        this.metadata = in.readParcelable(StatusMetaData.class.getClassLoader());
        this.source = in.readString();
        this.inReplyToStatusId = in.readLong();
        this.inReplyToStatusIdStr = in.readString();
        this.inReplyToUserId = in.readLong();
        this.inReplyToUserIdStr = in.readString();
        this.inReplyToScreenName = in.readString();
        this.extendedEntities = in.readParcelable(Entity.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Status> CREATOR = new Creator<Status>() {
        @Override
        public Status createFromParcel(Parcel source) {
            return new Status(source);
        }

        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };
}
