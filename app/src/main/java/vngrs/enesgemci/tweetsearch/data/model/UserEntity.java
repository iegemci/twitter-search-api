package vngrs.enesgemci.tweetsearch.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import vngrs.enesgemci.tweetsearch.data.model.base.JsonData;

/**
 * Created by enesgemci on 09/04/2017.
 */

public class UserEntity extends JsonData implements Parcelable {


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public UserEntity() {
    }

    protected UserEntity(Parcel in) {
    }

    public static final Parcelable.Creator<UserEntity> CREATOR = new Parcelable.Creator<UserEntity>() {
        @Override
        public UserEntity createFromParcel(Parcel source) {
            return new UserEntity(source);
        }

        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };
}
