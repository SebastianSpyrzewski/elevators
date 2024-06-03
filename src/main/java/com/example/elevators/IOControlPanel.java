package com.example.elevators;

import java.util.Scanner;

public class IOControlPanel implements ControlPanel {
    @Override
    public int getFloor() {
        Scanner sc= new Scanner(System.in);
        return sc.nextInt();
    }

    @Override
    public void setMessage(String message) {
        System.out.println(message);
    }
}
