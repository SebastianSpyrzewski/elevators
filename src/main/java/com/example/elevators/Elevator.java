package com.example.elevators;

public interface Elevator {
    void step();
    Status getStatus();
    int distance(int floor, Direction direction);
    void call(int floor, Direction direction);
}
