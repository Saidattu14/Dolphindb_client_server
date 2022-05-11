package com.example.Server.Model;


public class HeartBeatModel {

    private String date;
    private String time;
    private int heatbeatrate;
    /**
     * This model is used for request body.
     * @return the date, time, heartbeatrate.
     */
    HeartBeatModel(String date, String time, int heatbeatrate)
    {
        this.date = date;
        this.time = time;
        this.heatbeatrate = heatbeatrate;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getHeatbeatrate() {
        return heatbeatrate;
    }

    public void setHeatbeatrate(int heatbeatrate) {
        this.heatbeatrate = heatbeatrate;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
