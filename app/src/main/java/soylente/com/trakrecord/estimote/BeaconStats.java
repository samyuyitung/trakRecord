package soylente.com.trakrecord.estimote;

import java.util.UUID;

/**
 * Created by sam on 2016-03-26.
 */
public class BeaconStats {

    private UUID id;
    private int campNumber;

    public BeaconStats(){}


    public BeaconStats(int campNumber){
        this.campNumber = campNumber;
    }

    public int getCampNumber() { return campNumber; }


    public BeaconStats grabById(UUID id){


        return new BeaconStats(campNumber);
    }
    @Override
    public String toString() {
        return "[ca: " + campNumber;
    }





}
