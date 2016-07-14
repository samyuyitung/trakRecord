package soylente.com.trakrecord.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by sam on 2016-06-02.
 */

public class Camp implements Parcelable {
    private String campName;

    private double latitude;
    private double longitude;
    private LatLng coords;

    public Camp() {

    }

    public Camp(String name, double lat, double lng) {
        campName = name;
        latitude = lat;
        longitude = lng;
        coords = new LatLng(latitude, longitude);
    }


    public String getCampName() {
        return campName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    public LatLng getCoords() {
        if (coords == null)
            coords = new LatLng(latitude, longitude);
        return coords;
    }

    protected Camp(Parcel in) {
        campName = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        coords = (LatLng) in.readValue(LatLng.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(campName);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeValue(coords);
    }
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Camp> CREATOR = new Parcelable.Creator<Camp>() {
        @Override
        public Camp createFromParcel(Parcel in) {
            return new Camp(in);
        }

        @Override
        public Camp[] newArray(int size) {
            return new Camp[size];
        }
    };

}

