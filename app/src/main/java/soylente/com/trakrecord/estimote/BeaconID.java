package soylente.com.trakrecord.estimote;

import android.os.Parcel;
import android.os.Parcelable;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.Region;
import com.google.android.gms.maps.model.LatLng;

import java.util.UUID;

import soylente.com.trakrecord.DAO.Camp;

public class BeaconID implements Parcelable {

    private UUID proximityUUID;
    private int major;
    private int minor;


    public BeaconID () {

    }

    public static BeaconID fromBeacon(Beacon beacon) {
        return new BeaconID(beacon.getProximityUUID(), beacon.getMajor(), beacon.getMinor());
    }

    public BeaconID(UUID proximityUUID, int major, int minor) {
        this.proximityUUID = proximityUUID;
        this.major = major;
        this.minor = minor;
    }

    public BeaconID(String UUIDString, int major, int minor) {
        this(UUID.fromString(UUIDString), major, minor);
    }

    public UUID getProximityUUID() {
        return proximityUUID;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public Region toBeaconRegion() {
        return new Region(toString(), getProximityUUID(), getMajor(), getMinor());
    }

    public String toString() {
        return getProximityUUID().toString() + ":" + getMajor() + ":" + getMinor();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (getClass() != o.getClass()) {
            return super.equals(o);
        }

        BeaconID other = (BeaconID) o;

        return getProximityUUID().equals(other.getProximityUUID())
                && getMajor() == other.getMajor()
                && getMinor() == other.getMinor();
    }


    protected BeaconID(Parcel in) {
        proximityUUID = (UUID) in.readValue(UUID.class.getClassLoader());
        major = in.readInt();
        minor = in.readInt();
         }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(proximityUUID);
        dest.writeInt(major);
        dest.writeInt(minor);

    }
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BeaconID> CREATOR = new Parcelable.Creator<BeaconID>() {
        @Override
        public BeaconID createFromParcel(Parcel in) {
            return new BeaconID(in);
        }

        @Override
        public BeaconID[] newArray(int size) {
            return new BeaconID[size];
        }
    };
}
