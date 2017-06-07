package com.vinit.controller;

import com.vinit.model.LocationUpdateModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: bothravinit_old
 * Date: 2017/06/06
 * Time: 16:04
 */
// cross origin is required since being hit by browser js directly
@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class LocationController {

    // used as a global variable between update location and get location
    // ideally this should be stored in some DB and retrieved from there
    private LocationUpdateModel currentLocation = new LocationUpdateModel();

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/location_update", method = RequestMethod.POST)
    public String simulateLocation(@RequestBody LocationUpdateModel locationUpdateModel) {

        currentLocation = locationUpdateModel;
        log.info("new location received - " + locationUpdateModel.getLat() + "," + locationUpdateModel.getLng());
//        simulatorService.simulateLocations(origin,destination);
        return "OK";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/get_location")
    public LocationUpdateModel getLocation() {
        return currentLocation;
    }

}
