package www.fiberathome.com.parkingapp.model;

import android.hardware.Sensor;

import java.util.ArrayList;
import java.util.List;

public class SensorList {

    public List<Sensors> sensors;
    public String next_page_token;

    public SensorList(){
        sensors = new ArrayList<Sensors>();
    }

    public boolean canLoadMore(){
        return next_page_token != null;
    }
}
