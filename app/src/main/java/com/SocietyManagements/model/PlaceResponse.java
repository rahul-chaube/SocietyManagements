package com.SocietyManagements.model;

import java.util.ArrayList;

public class PlaceResponse {
    String status;
    ArrayList<PlaceResult> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<PlaceResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<PlaceResult> results) {
        this.results = results;
    }
}
