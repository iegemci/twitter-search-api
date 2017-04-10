package vngrs.enesgemci.tweetsearch.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import vngrs.enesgemci.tweetsearch.data.model.base.JsonData;

/**
 * Created by enesgemci on 09/04/2017.
 */

public class HashTag extends JsonData implements Parcelable {

    private String text;
    private List<Integer> indices;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        dest.writeString(this.text);
        dest.writeList(this.indices);
    }

    public HashTag() {
    }

    protected HashTag(Parcel in) {
        this.text = in.readString();
        this.indices = new ArrayList<Integer>();
        in.readList(this.indices, Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<HashTag> CREATOR = new Parcelable.Creator<HashTag>() {
        @Override
        public HashTag createFromParcel(Parcel source) {
            return new HashTag(source);
        }

        @Override
        public HashTag[] newArray(int size) {
            return new HashTag[size];
        }
    };
}