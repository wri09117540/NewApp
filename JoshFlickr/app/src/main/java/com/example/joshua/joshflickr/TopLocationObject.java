package com.example.joshua.joshflickr;

import java.io.Serializable;

/**
 * Created by Joshua on 11/05/2015.
 */
public class TopLocationObject implements Serializable {

    private String cityName;
    private String countryName;
    private Integer flagResource;
    private String woeid;

    public Integer getFlagResource() {
        return flagResource;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getWoeid() {
        return woeid;
    }

    public TopLocationObject(String cityName, String countryName, Integer flagResource, String woeid) {
        this.cityName = cityName;
        this.countryName = countryName;
        this.flagResource = flagResource;
        this.woeid = woeid;
    }
}
