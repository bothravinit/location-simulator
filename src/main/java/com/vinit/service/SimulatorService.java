package com.vinit.service;

import com.google.maps.model.LatLng;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: bothravinit_old
 * Date: 2017/06/06
 * Time: 2:50
 */
@Service
@Slf4j
public class SimulatorService {

    private static final int FREQUENCY_OF_UPDATE = 5; // 5 seconds

    @Autowired
    private LocationUpdateService locationUpdateService;

    @Autowired
    private DirectionsApiService directionsApiService;

    /**
     * returns true if route found else false
     * @param origin
     * @param destination
     * @param speedFactor
     * @return
     */
    public boolean simulateLocations(String origin, String destination, int speedFactor) {

        List<LatLng> latLngsToBePosted = directionsApiService.getDirections(origin, destination, FREQUENCY_OF_UPDATE);
        if (!CollectionUtils.isEmpty(latLngsToBePosted)) {
            locationUpdateService.postLocations(latLngsToBePosted, FREQUENCY_OF_UPDATE * 1000 / speedFactor);

            return true;
        } else
            return false;
    }


}
