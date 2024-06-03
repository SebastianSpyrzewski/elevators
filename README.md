# Elevator system
## Introduction
This project represents elevator system, able to parse user's requests and manipulate multiple elevators at once.
## Structure
Project is written in Java, using Intellij IDEA and it is recommended to use it as well to view and run project. However, it is also possible to run the project from the command line - to compile, one needs to execute command
```
javac -d <target_directory> src/main/java/com/example/elevators/*.java
```
and then from choosen directory run 
```
java com.example.elevators.Program
```
Code is divided into multiple classes, and in directory src/test there are unit tests of two main classes - MyElevator and MyElevatorSystem.
## Usage
After starting the program, user at first will be prompted to enter number of elevators and range of floors to cover (we assumed that number of elevators is small and we can iterate through them in constant time, while number of floors can be arbitraly big). Then user can enter one of the following commands:
- _status_ - this command will display status of each elevator in form "Elevator nr id: current floor->target floor"
- _call a b_ - this command correspond to person at floor _a_ calling the elevator. Integer _b_ gives information about direction we want to travel - _b>0_ for travelling up, and _b<=0_ for travelling down.
- _step n_ - simulates _n_ steps of the elevator system, where one step is the time in which one elevator moves by one floor. If during these steps any elevator would stop to take a passenger, user will be prompted to enter the target floor (in tests directory, this is simulated by a special class).
- _finish_ - stops the simulation.
## Algorithm
There are two main problems about organizing system of elevators: how to decide which elevator should respond to the call from given floor and in what order elevator should stop at requested floors.
For the first problem, we decided to implement following solution: for call at floor _f_ in direction _d_ we look for elevators which are either inactive or they are travelling in the same direction as _d_ and floor _f_ is on their way. Then from these elevators we choose the closest one to _f_. If there are no such elevators, this request waits until one starts to meet the conditions.
For the second problem we used priority queue data structure to stop at floors in order - it is not optimal to go back and forth.
