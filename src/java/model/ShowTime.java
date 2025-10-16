package model;

import java.sql.Time;
import java.util.Date;

public class ShowTime {
    private int showTimeID;
    private Time startTime;
    private Time endTime;
    private Date screeningDate;
    private float basePrice;

    private Movie mv;             
    private ScreeningRoom sc;     

    // Constructor
    public ShowTime() {}

    public ShowTime(int showTimeID, Time startTime, Time endTime, Date screeningDate, float basePrice, Movie mv, ScreeningRoom sc) {
        this.showTimeID = showTimeID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.screeningDate = screeningDate;
        this.basePrice = basePrice;
        this.mv = mv;
        this.sc = sc;
    }

    // Getters and Setters
    public int getShowTimeID() {
        return showTimeID;
    }

    public void setShowTimeID(int showTimeID) {
        this.showTimeID = showTimeID;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Date getScreeningDate() {
        return screeningDate;
    }

    public void setScreeningDate(Date screeningDate) {
        this.screeningDate = screeningDate;
    }

    public float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
    }

    public Movie getMv() {
        return mv;
    }

    public void setMv(Movie mv) {
        this.mv = mv;
    }

    public ScreeningRoom getSc() {
        return sc;
    }

    public void setSc(ScreeningRoom sc) {
        this.sc = sc;
    }
}
