package com.jstechnologies.coronalive;

import org.json.JSONException;
import org.json.JSONObject;

public class DataModel {
    private String country_name;
    private String cases;
    private String deaths;
    private String region;
    private String total_recovered;
    private String new_deaths;
    private String new_cases;
    private String serious_critical;
    private String active_cases;
    private String total_cases_per_1m_population;


    // Getter Methods

    public String getCountry_name() {
        return country_name;
    }

    public String getCases() {
        return cases;
    }

    public String getDeaths() {
        return deaths;
    }

    public String getRegion() {
        return region;
    }

    public String getTotal_recovered() {
        return total_recovered;
    }

    public String getNew_deaths() {
        return new_deaths;
    }

    public String getNew_cases() {
        return new_cases;
    }

    public String getSerious_critical() {
        return serious_critical;
    }

    public String getActive_cases() {
        return active_cases;
    }

    public String getTotal_cases_per_1m_population() {
        return total_cases_per_1m_population;
    }

    // Setter Methods

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setTotal_recovered(String total_recovered) {
        this.total_recovered = total_recovered;
    }

    public void setNew_deaths(String new_deaths) {
        this.new_deaths = new_deaths;
    }

    public void setNew_cases(String new_cases) {
        this.new_cases = new_cases;
    }

    public void setSerious_critical(String serious_critical) {
        this.serious_critical = serious_critical;
    }

    public void setActive_cases(String active_cases) {
        this.active_cases = active_cases;
    }

    public void setTotal_cases_per_1m_population(String total_cases_per_1m_population) {
        this.total_cases_per_1m_population = total_cases_per_1m_population;
    }
    public static DataModel ParseData(JSONObject object) throws JSONException {
        DataModel model= new DataModel();
        model.setActive_cases(object.getString("active_cases"));
        model.setCountry_name(object.getString("country_name"));
        model.setCases(object.getString("cases"));
        model.setDeaths(object.getString("deaths"));
        model.setRegion(object.getString("region"));
        model.setTotal_recovered(object.getString("total_recovered"));
        model.setNew_deaths(object.getString("new_deaths"));
        model.setNew_cases(object.getString("new_cases"));
        model.setSerious_critical(object.getString("serious_critical"));
        model.setTotal_cases_per_1m_population(object.getString("total_cases_per_1m_population"));
        return model;

    }

}