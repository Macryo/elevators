package com.tingco.codechallenge.elevator.services;

import com.tingco.codechallenge.elevator.api.Elevator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ElevatorTaskHandlerImpl implements ElevatorTaskHandler {

    private Log log = LogFactory.getLog(getClass());

    private ConcurrentHashMap<Elevator, List<Future<Elevator>>> tasks;
    private ExecutorService executorService;

    public ElevatorTaskHandlerImpl() {
        this.executorService = Executors.newScheduledThreadPool(6);
    }

    public void setTasks(List<Elevator> elevators) {
        this.tasks = new ConcurrentHashMap<>();
        for (Elevator elev : elevators) {
            this.tasks.put(elev, new ArrayList<>());
        }
    }

    @Override
    public Elevator scheduleElevatorMove(int toFloor) {
        Elevator elevator = getIdleElevator();
        Future<Elevator> future = executorService.submit(() -> {
            elevator.moveElevator(toFloor);
            return elevator;
        });

        this.tasks.get(elevator).add(future);
        return elevator;
    }

    @Override
    public List<Elevator> getCurrentElevators() {
        return new ArrayList<>(this.tasks.keySet());
    }

    @Override
    public void cancelCurrentElevatorTask(int elevatorId) {
        Optional<Elevator> elevatorOptional = this.tasks.keySet().stream().filter(elev -> elev.getId() == elevatorId).findFirst();
        if(elevatorOptional.isPresent()){
            Elevator elevator = elevatorOptional.get();
            tasks.get(elevator).removeIf(Future::isDone);
            elevator.setBusyStatus(false);
            log.info("Canceling Elevator" + elevator.getId() + " current tasks.");
        }
    }

    @Override
    public void initWithElevators(List<Elevator> elevators) {
        this.tasks = new ConcurrentHashMap<>();
        for (Elevator elev : elevators) {
            this.tasks.put(elev, new ArrayList<>());
        }
    }

    private void clearAllFinishedTasks() {
        for (Elevator currentElevator : this.tasks.keySet()) {
            this.tasks.get(currentElevator).removeIf(Future::isDone);
            currentElevator.setBusyStatus(false);
        }
    }

    private synchronized Elevator getIdleElevator() {
        this.clearAllFinishedTasks();
        return this.tasks.entrySet()
                .stream()
                .min(Comparator
                        .comparingInt(o -> o.getValue().size()))
                .get().getKey();

    }

}
