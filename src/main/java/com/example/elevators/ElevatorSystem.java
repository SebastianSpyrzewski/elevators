package com.example.elevators;

import java.util.List;

public interface ElevatorSystem {
    void step();
    List<Status> getStatus();
    void call(int floor, Direction direction);
}
