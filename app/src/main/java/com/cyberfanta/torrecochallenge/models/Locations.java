package com.cyberfanta.torrecochallenge.models;

public class Locations {
    private String name;
    private String country;
    private String latitude;
    private String longitude;
    private String timezone;

    public Locations(String name, String country, String latitude, String longitude, String timezone) {
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timezone = timezone;
    }

    public Locations() {
    }

    @Override
    public String toString() {
        return "Locations{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", timezone='" + timezone + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
