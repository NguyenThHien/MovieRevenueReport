package model;

import java.util.Date;

public class Ticket {
    private int ticketID;
    private Date bookingTime;
    private String bookingMethod;
    private float priceFinal;
    private ShowTimeSeat stSeat;  // Liên kết tới ShowTimeSeat

    // Constructors
    public Ticket() {}

    public Ticket(int ticketID, Date bookingTime, String bookingMethod, float priceFinal, ShowTimeSeat stSeat) {
        this.ticketID = ticketID;
        this.bookingTime = bookingTime;
        this.bookingMethod = bookingMethod;
        this.priceFinal = priceFinal;
        this.stSeat = stSeat;
    }

    // Getters & Setters
    public int getTicketID() { return ticketID; }
    public void setTicketID(int ticketID) { this.ticketID = ticketID; }

    public Date getBookingTime() { return bookingTime; }
    public void setBookingTime(Date bookingTime) { this.bookingTime = bookingTime; }

    public String getBookingMethod() { return bookingMethod; }
    public void setBookingMethod(String bookingMethod) { this.bookingMethod = bookingMethod; }

    public float getPriceFinal() { return priceFinal; }
    public void setPriceFinal(float priceFinal) { this.priceFinal = priceFinal; }

    public ShowTimeSeat getStSeat() { return stSeat; }
    public void setStSeat(ShowTimeSeat stSeat) { this.stSeat = stSeat; }
}
