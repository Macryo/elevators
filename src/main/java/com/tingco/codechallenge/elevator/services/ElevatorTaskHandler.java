package com.tingco.codechallenge.elevator.services;

import com.tingco.codechallenge.elevator.api.Elevator;

import java.util.List;

public interface ElevatorTaskHandler {

    Elevator scheduleElevatorMove(int toFloor);

    List<Elevator> getCurrentElevators();

    void cancelCurrentElevatorTask(int elevatorId);

    void initWithElevators(List<Elevator> elevators);
}
