package com.tingco.codechallenge.elevator.api;


import com.tingco.codechallenge.elevator.api.Elevator;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ElevatorImpl implements Elevator {

    private Direction currentDirection;
    private int currentFloor;
    private int addressedFloor;
    private boolean isBusy;

    public ElevatorImpl() {
        this.currentDirection = Direction.NONE;
        this.currentFloor = 0;
        this.addressedFloor = 0;
        this.isBusy = false;
    }

    @Override
    public Direction getDirection() {
        return this.currentDirection;
    }

    @Override
    public int getAddressedFloor() {
        return this.addressedFloor;
    }

    @Override
    public int getId() {
        return this.hashCode();
    }


    @Override
    public void moveElevator(int toFloor) {
        if (toFloor != this.currentFloor && !isBusy()) {
            this.isBusy = true;
            this.addressedFloor = toFloor;
            this.transit(this.currentFloor, this.addressedFloor);
        }
    }

    private void transit(int from, int to) {

        this.currentDirection = from > to ? Direction.DOWN : Direction.UP;
        int difference = Math.abs(from - to);

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        for (int i = 0; i < difference; i++) {
            executorService.schedule(() -> {
                this.changeFloor(from, to);
            }, 3, TimeUnit.SECONDS);
        }
        this.isBusy = false;
    }

    private void changeFloor(int from, int to) {
        this.currentFloor += from > to ? -1 : 1;
        System.out.println(this.currentFloor());
    }

    @Override
    public boolean isBusy() {
        return this.isBusy;
    }

    @Override
    public int currentFloor() {
        return this.currentFloor;
    }
}
