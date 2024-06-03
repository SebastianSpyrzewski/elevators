package com.example.elevators;

public class Status {
    public final int id;
    public final int currentFloor;
    public final int targetFloor;

    public Status(int id, int currentFloor, int targetFloor) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.targetFloor = targetFloor;
    }

    void print(){
        System.out.printf("Elevator nr %d: %d->%d\n", id, currentFloor, targetFloor);
    }
}
