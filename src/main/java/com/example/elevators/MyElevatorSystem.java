package com.example.elevators;

import java.util.ArrayList;
import java.util.List;

public class MyElevatorSystem implements ElevatorSystem {
    Elevator[] elevators;
    private final int numberOfElevators;
    private final int minFloor;
    private final int maxFloor;
    List<Request> requests;

    private static class Request{
        Integer floor;
        Direction direction;

        public Request(int floor, Direction direction) {
            this.floor=floor; this.direction=direction;
        }
    }

    public MyElevatorSystem(int numberOfElevators, int minFloor, int maxFloor, ControlPanel cp) {
        this.numberOfElevators = numberOfElevators;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        elevators =  new Elevator[numberOfElevators];
        for(int i=0; i<numberOfElevators; i++){
            elevators[i] = new MyElevator(i+1, minFloor, maxFloor, cp);
        }
        requests = new ArrayList<>();
    }

    @Override
    public void step() {
        for(int i=0; i<numberOfElevators; i++){
            elevators[i].step();
        }
        List<Request> completedRequests = new ArrayList<>();
        for(Request r: requests) {
            int i = checkElevators(r.floor, r.direction);
            if(i!=-1){
                elevators[i].call(r.floor, r.direction);
                completedRequests.add(r);
            }
        }
        requests.removeAll(completedRequests);
    }

    @Override
    public List<Status> getStatus() {
        List<Status> list = new ArrayList<>();
        for(int i=0; i<numberOfElevators; i++){
            list.add(elevators[i].getStatus());
        }
        return list;
    }

    @Override
    public void call(int floor, Direction direction) {
        if (floor > maxFloor || floor < minFloor) {
            System.out.println("Incorrect floor");
            return;
        }
        int i = checkElevators(floor, direction);
        if(i==-1){
            requests.add(new Request(floor, direction));
        }else {
            elevators[i].call(floor, direction);
        }
    }
    private int checkElevators(int floor, Direction direction){
        int e = -1;
        int mind = maxFloor-minFloor;
        for(int i=0; i<numberOfElevators; i++){
            int d = elevators[i].distance(floor, direction);
            if(d==-1){
                continue;
            }
            if(d<mind){
                mind=d;
                e=i;
            }
        }
        return e;
    }
}
