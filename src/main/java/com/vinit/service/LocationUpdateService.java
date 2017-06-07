package com.vinit.service;

import com.google.maps.model.LatLng;
import com.vinit.model.LocationUpdateModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: bothravinit_old
 * Date: 2017/06/07
 * Time: 0:22
 */
@Service
@Slf4j
public class LocationUpdateService {

    @Value("${locationUpdateUrl:http://localhost:8080/location_update}")
    private String locationUpdateUrl;

    @Async
    public void postLocations(List<LatLng> latLngsToBePosted, int interval) {

        RestTemplate restTemplate = new RestTemplate();

        LocationUpdateModel locationUpdateModel = new LocationUpdateModel();
        for (LatLng latLng : latLngsToBePosted) {

            try {
                log.info("new location sending - " + locationUpdateModel.getLat() + "," + locationUpdateModel.getLng());

                locationUpdateModel.setLat(latLng.lat);
                locationUpdateModel.setLng(latLng.lng);
                locationUpdateModel.setTimestamp(System.currentTimeMillis());

                HttpEntity httpEntity = new HttpEntity<>(locationUpdateModel, null);
                restTemplate.postForEntity(locationUpdateUrl, httpEntity, String.class); // fire and forget. Ignoring the response for now

                // sleep the thread for the sake of actual simulation
                Thread.sleep(interval);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
