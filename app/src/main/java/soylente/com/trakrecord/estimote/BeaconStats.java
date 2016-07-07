package soylente.com.trakrecord.estimote;

import java.util.UUID;

/**
 * Created by sam on 2016-03-26.
 */
public class BeaconStats {

    private UUID id;
    private int campNumber;
    private String beaconType;

    public BeaconStats(){}


    public BeaconStats(int mileMarker, String beaconType){
        this.campNumber = mileMarker;
        this.beaconType = beaconType;
    }

    public int getCampNumber() { return campNumber; }

    public String getbeaconType() {
        return beaconType;
    }

    public BeaconStats grabById(UUID id){
        //Search through db
        campNumber = 2;
        beaconType = "as";

        return new BeaconStats(campNumber, beaconType);
    }
    @Override
    public String toString() {
        return "[ca: " + campNumber + ", beacon type: " + beaconType + "]";
    }





}
