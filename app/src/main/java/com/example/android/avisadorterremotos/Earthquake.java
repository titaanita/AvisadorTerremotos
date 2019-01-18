package com.example.android.avisadorterremotos;

public class Earthquake {
    private String magnitud;
    private String place;

    public Earthquake(String magnitud, String place) {
        this.magnitud = magnitud;
        this.place = place;
    }

    public String getMagnitud() {
        return magnitud;
    }

    public void setMagnitud(String magnitud) {
        this.magnitud = magnitud;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
