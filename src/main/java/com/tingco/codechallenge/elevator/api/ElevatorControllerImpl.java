package com.tingco.codechallenge.elevator.api;

import com.tingco.codechallenge.elevator.services.ElevatorTaskHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElevatorControllerImpl implements ElevatorController {

    private final ElevatorTaskHandler handler;

    @Autowired
    public ElevatorControllerImpl(ElevatorTaskHandler handler) {
        this.handler = handler;
    }

    @Override
    public Elevator requestElevator(int toFloor) {
       return this.handler.scheduleElevatorMove(toFloor);
    }

    @Override
    public List<Elevator> getElevators() {
        return this.handler.getCurrentElevators();
    }

    @Override
    public void releaseElevator(int elevatorId) {
        this.handler.cancelCurrentElevatorTask(elevatorId);
    }

    @Override
    public void initWithElevatorsList(List<Elevator> elevators) {
        this.handler.initWithElevators(elevators);
    }
}
