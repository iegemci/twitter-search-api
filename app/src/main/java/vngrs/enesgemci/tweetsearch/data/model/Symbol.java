package vngrs.enesgemci.tweetsearch.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import vngrs.enesgemci.tweetsearch.data.model.base.JsonData;

/**
 * Created by enesgemci on 09/04/2017.
 */

public class Symbol extends JsonData implements Parcelable {
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public Symbol() {
    }

    protected Symbol(Parcel in) {
    }

    public static final Parcelable.Creator<Symbol> CREATOR = new Parcelable.Creator<Symbol>() {
        @Override
        public Symbol createFromParcel(Parcel source) {
            return new Symbol(source);
        }

        @Override
        public Symbol[] newArray(int size) {
            return new Symbol[size];
        }
    };
}
