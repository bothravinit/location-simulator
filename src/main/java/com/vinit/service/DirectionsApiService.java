package com.vinit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.LatLng;
import com.vinit.config.GdmaConfig;
import com.vinit.utils.UtilityMethods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: bothravinit_old
 * Date: 2017/06/06
 * Time: 0:25
 */
@Service
@Slf4j
public class DirectionsApiService {

    private GeoApiContext context;
    @Autowired
    private GdmaConfig gdmaConfig;
    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {

        log.debug("gdma key : " + gdmaConfig.getKey());
        context = new GeoApiContext().setApiKey(gdmaConfig.getKey())
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);

        log.info("context is setup");
    }

    /**
     * If distance =  1000 meters
     * time = 50 seconds
     * => speed = 20 m/s
     * There are 2 ways to send location updates. Either every 'X' seconds or every 'Y' meters of distance travelled.
     * We will go with timely updates of every 5 seconds.
     * <p>
     * In case of speed factor, we will change the frequency to every (5/speed factor) seconds
     *
     * @param origin
     * @param destination
     * @param frequencyOfUpdate
     * @return
     */
    public List<LatLng> getDirections(String origin, String destination, int frequencyOfUpdate) {
        try {
            DirectionsResult directionsResult = DirectionsApi.getDirections(context, origin, destination).await();

            if (directionsResult.routes == null || directionsResult.routes.length == 0)
                return null;

            log.info("total routes found - " + directionsResult.routes.length);
            DirectionsRoute route = directionsResult.routes[0];

            long totalDistance = route.legs[0].distance.inMeters;
            long totalTime = route.legs[0].duration.inSeconds;

            double speed = totalDistance / totalTime; // meters/second
            double minDistance = speed * frequencyOfUpdate; // distance Between2 Consecutive Location Updates

            List<LatLng> latLngs = route.overviewPolyline.decodePath();

            return getLatLngs(latLngs, minDistance);

        } catch (ApiException | InterruptedException | IOException e) {
            // needs better error handling
            e.printStackTrace();
            return null;
        }
    }

    private List<LatLng> getLatLngs(List<LatLng> latLngs, double minDistance) {

        List<LatLng> latLngsToBePosted = new ArrayList<>();
        double distance = 0;
        latLngsToBePosted.add(latLngs.get(0));

        for (int i = 0; i < latLngs.size() - 1; i++) {

            double straightLineDistance = UtilityMethods.getStraightLineDistance(latLngs.get(i), latLngs.get(i + 1));

            if (straightLineDistance < minDistance) {
                // if distance between 2 lat longs is less than the minimum distance,
                // lets post the 2 lat longs for smooth transition on the UI
                latLngsToBePosted.add(latLngs.get(i + 1));
            } else {
                // since distance between 2 lat longs is very high, lets decide to find 'n' intermediate points
                int count = (int) (straightLineDistance / minDistance);
                double latIncremental = (latLngs.get(i + 1).lat - latLngs.get(i).lat) / count;
                double lngIncremental = (latLngs.get(i + 1).lng - latLngs.get(i).lng) / count;
                for (int j = 1; j <= count; j++) {
                    latLngsToBePosted.add(new LatLng(latLngs.get(i).lat + j * latIncremental,
                            latLngs.get(i).lng + j * lngIncremental));
                }
            }
            log.info("" + straightLineDistance);
            distance += straightLineDistance;
        }
        log.info("total distance : " + distance);
        return latLngsToBePosted;
    }

}
