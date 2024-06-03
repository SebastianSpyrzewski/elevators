package com.example.elevators;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MyElevator implements Elevator {
    private final int id;
    private State state;
    private Direction direction;
    private int currentFloor;
    private int targetFloor;
    private final int minFloor;
    private final int maxFloor;
    private final ControlPanel cp;

    private enum Type {
        PICKUP, DESTINATION
    }
    private static class Event {
        Integer floor;
        Type type;
        public Event(Integer floor, Type type){
            this.floor=floor;
            this.type=type;
        }
    }

    private PriorityQueue<Event> q;

    public MyElevator(int id, int minFloor, int maxFloor, ControlPanel cp) {
        this.id = id;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.cp = cp;
        state = State.INACTIVE;
        currentFloor = minFloor <= 0 && maxFloor >= 0 ? 0 : minFloor;
        targetFloor = currentFloor;
    }

    @Override
    public void step() {
        if(state==State.CALLED){
            if(targetFloor>currentFloor){
                currentFloor++;
            }
            else if(targetFloor<currentFloor){
                currentFloor--;
            }
        }
        if(state==State.TRANSPORTING){
            if(direction==Direction.UP){
                currentFloor++;
            } else {
                currentFloor--;
            }
        }
        checkForEvents();
    }

    @Override
    public Status getStatus() {
        return new Status(id, currentFloor, targetFloor);
    }

    @Override
    public int distance(int floor, Direction direction) { //function to calculate distance from passenger call or return -1 if this elevator cannot take this passenger
        if(state==State.INACTIVE){
            return Math.abs(currentFloor - floor);
        }
        else if(state==State.CALLED){
            if(this.direction!=direction){
                return -1;
            }
            if(badDirection(targetFloor, floor)){
                return -1;
            }
            return Math.abs(currentFloor-targetFloor) + Math.abs(targetFloor-floor);
        }
        else if (state==State.TRANSPORTING){
            if(this.direction!=direction){
                return -1;
            }
            if((currentFloor-targetFloor)*(currentFloor-floor)<0){
                return -1;
            }
            return Math.abs(currentFloor - floor);
        }
        return -1;
    }

    @Override
    public void call(int floor, Direction direction) {
        if(distance(floor, direction)==-1){
            throw new RuntimeException("This elevator should not be called!");
        }
        if(state == State.INACTIVE){
            state = State.CALLED;
            this.direction = direction;
            targetFloor = floor;
            if(direction==Direction.UP) {
                q = new PriorityQueue<>(Comparator.comparingInt((Event e) -> e.floor));
            } else {
                q = new PriorityQueue<>(Comparator.comparingInt((Event e) -> -e.floor));
            }
        } else {
            q.add(new Event(floor, Type.PICKUP));
        }
        checkForEvents();
    }

    private void checkForEvents() {
        if(state==State.INACTIVE){
            return;
        }
        if(state==State.CALLED && currentFloor == targetFloor){
            state = State.TRANSPORTING;
            pickUp();
            assert q.peek() != null;
            targetFloor = q.peek().floor;
            checkForEvents();
        }
        if(state == State.TRANSPORTING) {
            assert q.peek() != null;
            if (currentFloor == q.peek().floor) {
                Event e = q.poll();
                if (e.type == Type.PICKUP) {
                    pickUp();
                } else {
                    String message = String.format("Passenger arrived at floor %d.\n", e.floor);
                    cp.setMessage(message);
                }
                if (q.isEmpty()) {
                    state = State.INACTIVE;
                } else {
                    targetFloor = q.peek().floor;
                }
                checkForEvents();
            }
        }
    }

    private boolean badDirection(int floor1, int floor2){
        if(floor1 > floor2 && direction==Direction.UP){
            return true;
        }
        if(floor1 < floor2 && direction==Direction.DOWN){
            return true;
        }
        return false;
    }
    private boolean incorrectFloor(int floor){
        return floor < minFloor || floor > maxFloor;
    }

    private void pickUp() {
        cp.setMessage(String.format("Elevator %d picked up passenger at floor %d. Please give target floor:\n", id, currentFloor));
        int floor = cp.getFloor();
        while(incorrectFloor(floor) || badDirection(currentFloor, floor)){
            if(incorrectFloor(floor)){
                cp.setMessage("Incorrect floor. Choose another floor:");
            }
            else {
                cp.setMessage("This elevator is not going that way. Choose another floor:");
            }
            floor = cp.getFloor();
        }
        q.add(new Event(floor, Type.DESTINATION));
    }
}
