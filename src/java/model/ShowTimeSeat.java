package model;

public class ShowTimeSeat {
    private int showTimeSeatID;
    private String statusSeatInShowTime;
    private ShowTime showTime;  
    private Seat seat;         

   
    public ShowTimeSeat() {}

    public ShowTimeSeat(int showTimeSeatID, String statusSeatInShowTime, ShowTime showTime, Seat seat) {
        this.showTimeSeatID = showTimeSeatID;
        this.statusSeatInShowTime = statusSeatInShowTime;
        this.showTime = showTime;
        this.seat = seat;
    }

    // Getters & Setters
    public int getShowTimeSeatID() { return showTimeSeatID; }
    public void setShowTimeSeatID(int showTimeSeatID) { this.showTimeSeatID = showTimeSeatID; }

    public String getStatusSeatInShowTime() { return statusSeatInShowTime; }
    public void setStatusSeatInShowTime(String statusSeatInShowTime) { this.statusSeatInShowTime = statusSeatInShowTime; }

    public ShowTime getShowTime() { return showTime; }
    public void setShowTime(ShowTime showTime) { this.showTime = showTime; }

    public Seat getSeat() { return seat; }
    public void setSeat(Seat seat) { this.seat = seat; }
}
