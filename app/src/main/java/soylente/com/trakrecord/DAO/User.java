package soylente.com.trakrecord.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by sam on 2016-07-14.
 */

public class User implements Parcelable {
    private String name;
    private ArrayList<Boolean> foundCamps;

    public User() {

    }

    public User(String name) {
        this.name = name;
        foundCamps = new ArrayList<>();
        for(int i = 0; i < 10; i++)
            foundCamps.add(false);
    }


    public String getName() {
        return name;
    }

    public ArrayList getFoundCamps() {
        return foundCamps;
    }

    protected User(Parcel in) {
        name = in.readString();
        foundCamps =  in.readArrayList(Boolean.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeList(foundCamps);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}

