package com.example.elevators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyElevatorTest {

    @Test
    void getStatus() {
        Elevator e = new MyElevator(1, 0, 10, new TestControlPanel());
        Status s1 = e.getStatus();
        Status s2 = e.getStatus();
        assertEquals(s1.id, s2.id);
        assertEquals(s1.currentFloor, s2.currentFloor);
        assertEquals(s1.targetFloor, s2.targetFloor);
    }

    @Test
    void oneCall() {
        Elevator e = new MyElevator(1, 0, 10, new TestControlPanel());
        e.call(5, Direction.DOWN);
        assertEquals(e.getStatus().targetFloor, 5);
    }

    @Test
    void singleStep() {
        Elevator e = new MyElevator(1, 0, 10, new TestControlPanel());
        assertEquals(e.getStatus().currentFloor, 0);
        e.call(5, Direction.DOWN);
        e.step();
        assertEquals(e.getStatus().currentFloor, 1);
    }

    @Test
    void pickUp() {
        TestControlPanel cp = new TestControlPanel();
        cp.setFloor(2);
        Elevator e = new MyElevator(1, 0, 10, cp);
        assertEquals(e.getStatus().currentFloor, 0);
        e.call(5, Direction.DOWN);
        for(int i=0; i<5; i++){
            e.step();
        }
        assertEquals(cp.getMessage(), "Elevator 1 picked up passenger at floor 5. Please give target floor:\n");
        assertEquals(e.getStatus().currentFloor, 5);
        assertEquals(e.getStatus().targetFloor, 2);
    }

    @Test
    void onePassenger() {
        TestControlPanel cp = new TestControlPanel();
        cp.setFloor(2);
        Elevator e = new MyElevator(1, 0, 10, cp);
        assertEquals(e.getStatus().currentFloor, 0);
        e.call(5, Direction.DOWN);
        for(int i=0; i<8; i++){
            e.step();
        }
        assertEquals(cp.getMessage(), "Passenger arrived at floor 2.\n");
    }
    @Test
    void morePassengers() {
        TestControlPanel cp = new TestControlPanel();
        Elevator e = new MyElevator(1, 0, 10, cp);
        assertEquals(e.getStatus().currentFloor, 0);
        e.call(10, Direction.DOWN);
        e.call(9, Direction.DOWN);
        e.call(8, Direction.DOWN);
        cp.setFloor(4);
        for(int i=0; i<10; i++){
            e.step();
        }
        cp.setFloor(2);
        e.step();
        cp.setFloor(3);
        e.step();
        for(int i=0; i<4; i++){
            e.step();
        }
        assertEquals(cp.getMessage(), "Passenger arrived at floor 4.\n");
        e.step();
        assertEquals(cp.getMessage(), "Passenger arrived at floor 3.\n");
        e.step();
        assertEquals(cp.getMessage(), "Passenger arrived at floor 2.\n");
    }
    @Test
    void wrongDirection(){
        Elevator e = new MyElevator(1, 0, 10, new TestControlPanel());
        e.call(5, Direction.DOWN);
        assertEquals(e.distance(3, Direction.UP), -1);
    }
    @Test
    void wrongDirectionAfterTime(){
        TestControlPanel cp = new TestControlPanel();
        cp.setFloor(2);
        Elevator e = new MyElevator(1, 0, 10, cp);
        e.call(5, Direction.DOWN);
        for(int i=0; i<10; i++){
            e.step();
        }
        assertNotEquals(e.distance(3, Direction.DOWN), -1);
    }
    @Test
    void distance(){
        TestControlPanel cp = new TestControlPanel();
        Elevator e = new MyElevator(1, 0, 10, cp);
        assertEquals(e.distance(5, Direction.DOWN), 5);
        e.call(5, Direction.DOWN);
        cp.setFloor(2);
        for(int i=0; i<4; i++){
            e.step();
        }
        assertEquals(e.distance(3, Direction.DOWN), 3);
        for(int i=0; i<2; i++){
            e.step();
        }
        assertEquals(e.distance(3, Direction.DOWN), 1);
    }
}