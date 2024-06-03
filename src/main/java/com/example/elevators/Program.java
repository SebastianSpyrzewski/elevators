package com.example.elevators;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws Exception {
        System.out.println("Enter number of elevators (max 16), minimum floor and maximum floor:");
        Scanner sc= new Scanner(System.in); //System.in is a standard input stream
        ElevatorSystem es = new MyElevatorSystem(sc.nextInt(), sc.nextInt(), sc.nextInt(), new IOControlPanel());
        while(true){
            String command = sc.next().toLowerCase();
            if(command.equals("call")){
                int floor = sc.nextInt();
                int dir = sc.nextInt();
                if(dir>0){
                    es.call(floor, Direction.UP);
                } else {
                    es.call(floor, Direction.DOWN);
                }
            } else if (command.equals("step")) {
                int n = sc.nextInt();
                for(int i=0; i<n; i++){
                    es.step();
                }
            } else if (command.equals("status")) {
                List<Status> l = es.getStatus();
                for(Status s : l){
                    s.print();
                }
            }else if (command.equals("finish")) {
                break;
            }
        }
    }
}
