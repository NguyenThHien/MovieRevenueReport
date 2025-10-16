package model;

public class Seat {
    private int seatID;
    private String seatName;
    private String seatType;
    private String status;
    private float priceMultiplier;
    private ScreeningRoom scRoom; // Quan hệ với ScreeningRoom (giả sử bạn đã có class này)

    // Constructors
    public Seat() {}

    public Seat(int seatID, String seatName, String seatType, String status, float priceMultiplier, ScreeningRoom scRoom) {
        this.seatID = seatID;
        this.seatName = seatName;
        this.seatType = seatType;
        this.status = status;
        this.priceMultiplier = priceMultiplier;
        this.scRoom = scRoom;
    }

    // Getters & Setters
    public int getSeatID() { return seatID; }
    public void setSeatID(int seatID) { this.seatID = seatID; }

    public String getSeatName() { return seatName; }
    public void setSeatName(String seatName) { this.seatName = seatName; }

    public String getSeatType() { return seatType; }
    public void setSeatType(String seatType) { this.seatType = seatType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public float getPriceMultiplier() { return priceMultiplier; }
    public void setPriceMultiplier(float priceMultiplier) { this.priceMultiplier = priceMultiplier; }

    public ScreeningRoom getScRoom() { return scRoom; }
    public void setScRoom(ScreeningRoom scRoom) { this.scRoom = scRoom; }
}
