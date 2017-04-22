package com.example.dva.bean;

/**
 * Created by D.va on 2017/4/18.
 */

public class TodayWeather {
    private String city;
    private String wendu;
    private String shidu;
    private String pm2_5;
    private String quality;
    private String fengxiang;
    private String fengli;

    @Override
    public String toString() {
        return "TodayWeather{" +
                "city='" + city + '\'' +
                ", wendu='" + wendu + '\'' +
                ", shidu='" + shidu + '\'' +
                ", pm2_5='" + pm2_5 + '\'' +
                ", quality='" + quality + '\'' +
                ", fengxiang='" + fengxiang + '\'' +
                ", fengli='" + fengli + '\'' +
                ", date='" + date + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", type='" + type + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", f1_date='" + f1_date + '\'' +
                ", f1_low='" + f1_low + '\'' +
                ", f1_high='" + f1_high + '\'' +
                ", f1_climate='" + f1_climate + '\'' +
                ", f1_wind='" + f1_wind + '\'' +
                ", f2_date='" + f2_date + '\'' +
                ", f2_low='" + f2_low + '\'' +
                ", f2_high='" + f2_high + '\'' +
                ", f2_climate='" + f2_climate + '\'' +
                ", f2_wind='" + f2_wind + '\'' +
                ", f3_date='" + f3_date + '\'' +
                ", f3_low='" + f3_low + '\'' +
                ", f3_high='" + f3_high + '\'' +
                ", f3_climate='" + f3_climate + '\'' +
                ", f3_wind='" + f3_wind + '\'' +
                '}';
    }

    private String date;
    private String high;
    private String low;
    private String type;
    private String updatetime;
    //future_day_1
    private String f1_date;
    private String f1_low;
    private String f1_high;
    private String f1_climate;
    private String f1_wind;
    //future_day_2
    private String f2_date;
    private String f2_low;
    private String f2_high;
    private String f2_climate;
    private String f2_wind;
    //future_day_3
    private String f3_date;
    private String f3_low;
    private String f3_high;
    private String f3_climate;
    private String f3_wind;

    public String getF3_wind() {
        return f3_wind;
    }

    public void setF3_wind(String f3_wind) {
        this.f3_wind = f3_wind;
    }

    public String getF1_climate() {
        return f1_climate;
    }

    public void setF1_climate(String f1_climate) {
        this.f1_climate = f1_climate;
    }

    public String getF1_date() {
        return f1_date;
    }

    public void setF1_date(String f1_date) {
        this.f1_date = f1_date;
    }

    public String getF1_high() {
        return f1_high;
    }

    public void setF1_high(String f1_high) {
        this.f1_high = f1_high;
    }

    public String getF1_low() {
        return f1_low;
    }

    public void setF1_low(String f1_low) {
        this.f1_low = f1_low;
    }

    public String getF1_wind() {
        return f1_wind;
    }

    public void setF1_wind(String f1_wind) {
        this.f1_wind = f1_wind;
    }

    public String getF2_climate() {
        return f2_climate;
    }

    public void setF2_climate(String f2_climate) {
        this.f2_climate = f2_climate;
    }

    public String getF2_date() {
        return f2_date;
    }

    public void setF2_date(String f2_date) {
        this.f2_date = f2_date;
    }

    public String getF2_high() {
        return f2_high;
    }

    public void setF2_high(String f2_high) {
        this.f2_high = f2_high;
    }

    public String getF2_low() {
        return f2_low;
    }

    public void setF2_low(String f2_low) {
        this.f2_low = f2_low;
    }

    public String getF2_wind() {
        return f2_wind;
    }

    public void setF2_wind(String f2_wind) {
        this.f2_wind = f2_wind;
    }

    public String getF3_climate() {
        return f3_climate;
    }

    public void setF3_climate(String f3_climate) {
        this.f3_climate = f3_climate;
    }

    public String getF3_date() {
        return f3_date;
    }

    public void setF3_date(String f3_date) {
        this.f3_date = f3_date;
    }

    public String getF3_high() {
        return f3_high;
    }

    public void setF3_high(String f3_high) {
        this.f3_high = f3_high;
    }

    public String getF3_low() {
        return f3_low;
    }

    public void setF3_low(String f3_low) {
        this.f3_low = f3_low;
    }



    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public String getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(String pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getFengli() {
        return fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



//    @Override
//    public String toString() {
//        return "TodayWeather{" +
//                "city='" + city + '\'' +
//                ", updatetime='" + updatetime + '\'' +
//                ", wendu='" + wendu + '\'' +
//                ", shidu='" + shidu + '\'' +
//                ", pm2_5='" + pm2_5 + '\'' +
//                ", quality='" + quality + '\'' +
//                ", fengxiang='" + fengxiang + '\'' +
//                ", fengli='" + fengli + '\'' +
//                ", date='" + date + '\'' +
//                ", high='" + high + '\'' +
//                ", low='" + low + '\'' +
//                ", type='" + type + '\'' +
//                '}';
//    }


}
