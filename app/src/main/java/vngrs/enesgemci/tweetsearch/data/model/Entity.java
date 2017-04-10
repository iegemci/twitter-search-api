package vngrs.enesgemci.tweetsearch.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import vngrs.enesgemci.tweetsearch.data.model.base.JsonData;

/**
 * Created by enesgemci on 09/04/2017.
 */

public class Entity extends JsonData implements Parcelable {

    private List<HashTag> hashtags;
    private List<Symbol> symbols;
    private List<User> userMentions;
    private List<Url> urls;
    private List<Media> media;

    public List<HashTag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<HashTag> hashtags) {
        this.hashtags = hashtags;
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    public List<User> getUserMentions() {
        return userMentions;
    }

    public void setUserMentions(List<User> userMentions) {
        this.userMentions = userMentions;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.hashtags);
        dest.writeList(this.symbols);
        dest.writeList(this.userMentions);
        dest.writeList(this.urls);
        dest.writeList(this.media);
    }

    public Entity() {
    }

    protected Entity(Parcel in) {
        this.hashtags = new ArrayList<HashTag>();
        in.readList(this.hashtags, HashTag.class.getClassLoader());
        this.symbols = new ArrayList<Symbol>();
        in.readList(this.symbols, Symbol.class.getClassLoader());
        this.userMentions = new ArrayList<User>();
        in.readList(this.userMentions, User.class.getClassLoader());
        this.urls = new ArrayList<Url>();
        in.readList(this.urls, Url.class.getClassLoader());
        this.media = new ArrayList<Media>();
        in.readList(this.media, Media.class.getClassLoader());
    }

    public static final Parcelable.Creator<Entity> CREATOR = new Parcelable.Creator<Entity>() {
        @Override
        public Entity createFromParcel(Parcel source) {
            return new Entity(source);
        }

        @Override
        public Entity[] newArray(int size) {
            return new Entity[size];
        }
    };
}
