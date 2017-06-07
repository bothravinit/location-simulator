package com.vinit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * User: bothravinit_old
 * Date: 2017/06/07
 * Time: 0:35
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationUpdateModel {

    private double lat;
    private double lng;
    private long timestamp;

}
