package model;

public class ScreeningRoom {
    private int screeningRoomID;
    private String roomName;
    private int capacity;
    private String screenType;
    private String status;

    // Constructor
    public ScreeningRoom() {}

    public ScreeningRoom(int screeningRoomID, String roomName, int capacity, String screenType, String status) {
        this.screeningRoomID = screeningRoomID;
        this.roomName = roomName;
        this.capacity = capacity;
        this.screenType = screenType;
        this.status = status;
    }

    // Getters and Setters
    public int getScreeningRoomID() {
        return screeningRoomID;
    }

    public void setScreeningRoomID(int screeningRoomID) {
        this.screeningRoomID = screeningRoomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
