package model;

import java.util.Date;

public class StatMovie extends Movie {  
    private Date startDate;
    private Date endDate;
    private int totalShowTime;
    private float totalRevenue;

   
    public StatMovie() {
        super();
    }

   
    public StatMovie(Date startDate, Date endDate, int totalShowTime, float totalRevenue) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalShowTime = totalShowTime;
        this.totalRevenue = totalRevenue;
    }

 
    public StatMovie(int movieID, String title, String genre, int duration, String language, 
                     Date releaseDate, String description, String posterURL, String format, 
                     String status, int ageRestriction,
                     Date startDate, Date endDate, int totalShowTime, float totalRevenue) {
        super(movieID, title, genre, duration, language, releaseDate, description, posterURL, format, status, ageRestriction);
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalShowTime = totalShowTime;
        this.totalRevenue = totalRevenue;
    }

  
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

    public int getTotalShowTime() {
        return totalShowTime;
    }

    public void setTotalShowTime(int totalShowTime) {
        this.totalShowTime = totalShowTime;
    }

    public float getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(float totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
