package com.vinit.controller;

import com.vinit.model.SimulationResponseModel;
import com.vinit.service.SimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by IntelliJ IDEA.
 * User: bothravinit_old
 * Date: 2017/06/06
 * Time: 2:50
 */
@CrossOrigin(origins = "*")
@Controller
public class SimulatorController {

    @Autowired
    private SimulatorService simulatorService;

    @Valid
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/simulate", method = RequestMethod.GET)

    public @ResponseBody
    SimulationResponseModel simulateLocation(@RequestParam(required = true) String origin,
                                             @RequestParam(required = true) String destination,
                                             @RequestParam(defaultValue = "1", required = false, value = "speed_factor") Integer speedFactor) {

        // Ideally we should be returning a json format
        boolean routeFound = simulatorService.simulateLocations(origin, destination, speedFactor);
        if(routeFound)
            return new SimulationResponseModel(200, "Route found. Track it using order_tracker.html ");
        else
            return new SimulationResponseModel(400, "No directions found from " + origin + " to " + destination);
    }

}
