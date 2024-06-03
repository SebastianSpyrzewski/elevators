package com.example.elevators;

public class TestControlPanel implements ControlPanel {
    private int floor;
    private String message;

    @Override
    public int getFloor() {
        return floor;
    }

    @Override
    public void setMessage(String message) {
        this.message=message;
    }
    public void setFloor(int floor) {
        this.floor=floor;
    }

    public String getMessage() {
        return message;
    }

}
