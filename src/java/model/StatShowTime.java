package model;

import java.sql.Time;
import java.util.Date;

public class StatShowTime extends ShowTime {

    private Date startDate;
    private Date endDate;
    private int totalTicket;
    private float totalRevenue;

    // Constructor rỗng
    public StatShowTime() {
        super();
    }
    public StatShowTime(int showTimeID, Time startTime, Time endTime, Date screeningDate, float basePrice,
                        Movie mv, ScreeningRoom sc,
                        Date startDate, Date endDate, int totalTicket, float totalRevenue) {
        super(showTimeID, startTime, endTime, screeningDate, basePrice, mv, sc);
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalTicket = totalTicket;
        this.totalRevenue = totalRevenue;
    }

    // Getters & Setters
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getTotalTicket() {
        return totalTicket;
    }

    public void setTotalTicket(int totalTicket) {
        this.totalTicket = totalTicket;
    }

    public float getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(float totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

   
}
