package soylente.com.trakrecord.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.UUID;

/**
 * Created by sam on 2016-06-02.
 */

public class Camp implements Parcelable {
    private String campName;
    private int campNumber;

    private double latitude;
    private double longitude;
    private LatLng coords;
    private boolean isFound;
    private UUID beaconID;

    public Camp() {

    }

    public Camp(String name, int num, double lat, double lng, UUID id) {
        campName = name;
        campNumber = num;
        latitude = lat;
        longitude = lng;
        coords = new LatLng(latitude, longitude);
        isFound = false;
        beaconID = id;

    }


    public String getCampName() {
        return campName;
    }
    public int getCampNumber() { return  campNumber; }
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public boolean getIsFound(){return isFound; }
    public UUID getBeaconID(){return beaconID; }



    public void setFound(boolean f){
        isFound = f;
    }



    public LatLng getCoords() {
        if (coords == null)
            coords = new LatLng(latitude, longitude);
        return coords;
    }

    protected Camp(Parcel in) {
        campName = in.readString();
        campNumber = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        coords = (LatLng) in.readValue(LatLng.class.getClassLoader());
        beaconID = (UUID) in.readValue(UUID.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(campName);
        dest.writeInt(campNumber);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeValue(coords);
        dest.writeValue(beaconID);
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

