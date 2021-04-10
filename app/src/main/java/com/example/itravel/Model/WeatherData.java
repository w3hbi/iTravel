package com.example.itravel.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherData {

    private String mTemperature, micon, mcity, mweatherType;
    private int mCondition;

    public static WeatherData fromJson(JSONObject jsonObject) {
        try {

            WeatherData weatherData = new WeatherData();
            weatherData.mcity = jsonObject.getString("name");
            weatherData.mCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherData.mweatherType = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");


            double tempResult = jsonObject.getJSONObject("main").getDouble("temp")-273.15;
            int roundedValue = (int)Math.rint(tempResult);
            weatherData.mTemperature = Integer.toString(roundedValue);
            return weatherData;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }



    public String getmTemperature() {
        return mTemperature+"°C";
    }

    public void setmTemperature(String mTemperature) {
        this.mTemperature = mTemperature;
    }

    public String getMicon() {
        return micon;
    }

    public void setMicon(String micon) {
        this.micon = micon;
    }

    public String getMcity() {
        return mcity;
    }

    public void setMcity(String mcity) {
        this.mcity = mcity;
    }

    public String getMweatherType() {
        return mweatherType;
    }

    public void setMweatherType(String mweatherType) {
        this.mweatherType = mweatherType;
    }

    public int getmCondition() {
        return mCondition;
    }

    public void setmCondition(int mCondition) {
        this.mCondition = mCondition;
    }
}
