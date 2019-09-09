package com.tingco.codechallenge.elevator.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ElevatorImpl implements Elevator {

    private Log log = LogFactory.getLog(getClass());
    private Direction currentDirection;
    private int currentFloor;
    private int addressedFloor;
    private volatile boolean isBusy;
    private int id;

    public ElevatorImpl(int id) {
        this.currentDirection = Direction.NONE;
        this.currentFloor = 0;
        this.addressedFloor = 0;
        this.isBusy = false;
        this.id = id;
    }

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
        return this.id > 0 ? id : hashCode();
    }


    @Override
    public synchronized void moveElevator(int toFloor) {
        this.setBusyStatus(true);
        this.addressedFloor = toFloor;
        if (toFloor >= 0) {
            this.setDirection(toFloor > this.currentFloor() ? Elevator.Direction.UP : Elevator.Direction.DOWN);
            try {
                TimeUnit.MILLISECONDS.sleep(100 * Math.abs(toFloor - this.currentFloor));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.currentDirection = Direction.NONE;
            this.currentFloor = toFloor;
        } else {
            log.warn("Can't ride from " + this.currentFloor + " to " + toFloor + "!");
        }
        this.setBusyStatus(false);
    }

    @Override
    public boolean isBusy() {
        return this.isBusy;
    }

    @Override
    public void setBusyStatus(boolean status) {
        this.isBusy = status;
    }

    @Override
    public int currentFloor() {
        return this.currentFloor;
    }

    @Override
    public void setDirection(Direction direction) {
        this.currentDirection = direction;
    }

    @Override
    public String toString() {
        return "Elevator" + getId() + "{" +
                "currentDirection=" + currentDirection +
                ", currentFloor=" + currentFloor +
                ", addressedFloor=" + addressedFloor +
                ", isBusy=" + isBusy +
                '}';
    }
}

