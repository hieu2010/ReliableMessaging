package com.example.reliablemessaging.weathersensor;

public class Weather {
    private String time; // UTC time stamp (format: YYYY-MM-DD hh:mm:ss)
    private String time_local; // Local time stamp (format: YYYY-MM-DD hh:mm:ss); only provided if tz is set
    private float temp; // The air temperature in °C
    private float dwpt; // The dew point in °C
    private int rhum; // The relative humidity in percent (%)
    private float prcp; // The one hour precipitation total in mm
    private int snow; // The snow depth in mm
    private int wdir; // The wind direction in degrees (°)
    private float wspd; // The average wind speed in km/h
    private float wpgt; // The peak wind gust in km/h
    private float pres; // The sea-level air pressure in hPa
    private int tsun; // The one hour sunshine total in minutes (m)
    private int coco; // The weather condition code

    public Weather(String time, String time_local, float temp, float dwpt, int rhum, float prcp, int snow, int wdir,
                   float wspd, float wpgt, float pres, int tsun, int coco) {
        super();
        this.time = time;
        this.time_local = time_local;
        this.temp = temp;
        this.dwpt = dwpt;
        this.rhum = rhum;
        this.prcp = prcp;
        this.snow = snow;
        this.wdir = wdir;
        this.wspd = wspd;
        this.wpgt = wpgt;
        this.pres = pres;
        this.tsun = tsun;
        this.coco = coco;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime_local() {
        return time_local;
    }

    public void setTime_local(String time_local) {
        this.time_local = time_local;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getDwpt() {
        return dwpt;
    }

    public void setDwpt(float dwpt) {
        this.dwpt = dwpt;
    }

    public int getRhum() {
        return rhum;
    }

    public void setRhum(int rhum) {
        this.rhum = rhum;
    }

    public float getPrcp() {
        return prcp;
    }

    public void setPrcp(float prcp) {
        this.prcp = prcp;
    }

    public int getSnow() {
        return snow;
    }

    public void setSnow(int snow) {
        this.snow = snow;
    }

    public int getWdir() {
        return wdir;
    }

    public void setWdir(int wdir) {
        this.wdir = wdir;
    }

    public float getWspd() {
        return wspd;
    }

    public void setWspd(float wspd) {
        this.wspd = wspd;
    }

    public float getWpgt() {
        return wpgt;
    }

    public void setWpgt(float wpgt) {
        this.wpgt = wpgt;
    }

    public float getPres() {
        return pres;
    }

    public void setPres(float pres) {
        this.pres = pres;
    }

    public int getTsun() {
        return tsun;
    }

    public void setTsun(int tsun) {
        this.tsun = tsun;
    }

    public int getCoco() {
        return coco;
    }

    public void setCoco(int coco) {
        this.coco = coco;
    }

    public static Weather createWeather(String[] attributes) {
        String time= attributes[0];
        String time_local= attributes[1];
        float temp = !attributes[2].isEmpty() ? Float.parseFloat(attributes[2]) : 0;
        float dwpt = !attributes[3].isEmpty() ? Float.parseFloat(attributes[3]) : 0;
        int rhum = !attributes[4].isEmpty() ? Integer.parseInt(attributes[4]) : 0;
        float prcp = !attributes[5].isEmpty() ? Float.parseFloat(attributes[5]) : 0;
        int snow = !attributes[6].isEmpty() ? Integer.parseInt(attributes[6]) : 0;
        int wdir = !attributes[7].isEmpty() ? Integer.parseInt(attributes[7]) : 0;
        float wspd = !attributes[8].isEmpty() ? Float.parseFloat(attributes[8]) : 0;
        float wpgt = !attributes[9].isEmpty() ? Float.parseFloat(attributes[9]) : 0;
        float pres = !attributes[10].isEmpty() ? Float.parseFloat(attributes[10]) : 0;
        int tsun = !attributes[11].isEmpty() ? Integer.parseInt(attributes[11]) : 0;
        int coco = !attributes[12].isEmpty() ? Integer.parseInt(attributes[12]) : 0;

        return new Weather(
                time,
                time_local,
                temp,
                dwpt,
                rhum,
                prcp,
                snow,
                wdir,
                wspd,
                wpgt,
                pres,
                tsun,
                coco);
    }

    @Override
    public String toString() {
        return "[time=" + time + ", time_local=" + time_local + ", temp=" + temp + ", dwpt=" + dwpt + ", rhum="
                + rhum + ", prcp=" + prcp + ", snow=" + snow + ", wdir=" + wdir + ", wspd=" + wspd + ", wpgt=" + wpgt
                + ", pres=" + pres + ", tsun=" + tsun + ", coco=" + coco + "]";
    }

}
