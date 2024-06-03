package com.example.elevators;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyElevatorSystemTest {
    @Test
    void create(){
        TestControlPanel cp = new TestControlPanel();
        ElevatorSystem es = new MyElevatorSystem(2, 0, 10, cp);
        List<Status> l = es.getStatus();
        assertEquals(l.size(), 2);
        assertEquals(l.get(0).currentFloor, 0);
    }
    @Test
    void oneElevator(){
        TestControlPanel cp = new TestControlPanel();
        ElevatorSystem es = new MyElevatorSystem(1, 0, 10, cp);
        es.call(3, Direction.DOWN);
        List<Status> l = es.getStatus();
        assertEquals(l.get(0).targetFloor, 3);
        cp.setFloor(1);
        for(int i=0; i<10; i++){
            es.step();
        }
        l = es.getStatus();
        assertEquals(l.get(0).currentFloor, 1);
        assertEquals(cp.getMessage(), "Passenger arrived at floor 1.\n");
    }
    @Test
    void moreElevators(){
        TestControlPanel cp = new TestControlPanel();
        ElevatorSystem es = new MyElevatorSystem(2, 0, 10, cp);
        es.call(3, Direction.DOWN);
        es.call(2, Direction.UP);
        List<Status> l = es.getStatus();
        assertEquals(l.get(0).targetFloor, 3);
        assertEquals(l.get(1).targetFloor, 2);
    }
    @Test
    void reachDestination(){
        TestControlPanel cp = new TestControlPanel();
        ElevatorSystem es = new MyElevatorSystem(2, 0, 10, cp);
        es.call(9, Direction.UP);
        cp.setFloor(10);
        for(int i=0; i<10; i++){
            es.step();
        }
        List<Status> l = es.getStatus();
        assertEquals(l.get(0).currentFloor, 10);
        assertEquals(l.get(1).currentFloor, 0);
    }
    @Test
    void closestElevator(){
        TestControlPanel cp = new TestControlPanel();
        ElevatorSystem es = new MyElevatorSystem(2, 0, 10, cp);
        es.call(9, Direction.UP);
        cp.setFloor(10);
        for(int i=0; i<10; i++){
            es.step();
        }
        es.call(1, Direction.UP);
        List<Status> l = es.getStatus();
        assertEquals(l.get(0).targetFloor, 10);
        assertEquals(l.get(1).targetFloor, 1);
    }
    @Test
    void queueingRequests(){
        TestControlPanel cp = new TestControlPanel();
        ElevatorSystem es = new MyElevatorSystem(2, 0, 10, cp);
        es.call(9, Direction.UP);
        es.call(5, Direction.DOWN);
        cp.setFloor(0);
        for(int i=0; i<5; i++){
            es.step();
        }
        cp.setFloor(10);
        es.call(7, Direction.DOWN);
        for(int i=0; i<4; i++){
            es.step();
        }
        List<Status> l = es.getStatus();
        assertEquals(l.get(0).targetFloor, 10);
        assertEquals(l.get(1).targetFloor, 0);
        es.step();
        l=es.getStatus();
        assertEquals(l.get(0).targetFloor, 7);
    }
}