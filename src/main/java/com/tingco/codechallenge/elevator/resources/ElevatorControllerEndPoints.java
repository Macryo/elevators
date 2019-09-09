package com.tingco.codechallenge.elevator.resources;

import com.tingco.codechallenge.elevator.api.Elevator;
import com.tingco.codechallenge.elevator.api.ElevatorController;
import com.tingco.codechallenge.elevator.api.ElevatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Rest Resource.
 *
 * @author Sven Wesley
 *
 */
@RestController
@RequestMapping("/rest/v1")
public final class ElevatorControllerEndPoints {

    private final ElevatorController elevatorController;

    @Autowired
    public ElevatorControllerEndPoints(ElevatorController elevatorController) {
        this.elevatorController = elevatorController;
        List<Elevator> elevators = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            elevators.add(new ElevatorImpl(i+1));
        }
        this.elevatorController.initWithElevatorsList(elevators);
    }

    /**
     * Ping service to test if we are alive.
     *
     * @return String pong
     */
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {

        return "pong";
    }

    @RequestMapping(value = "/elevators", method = RequestMethod.GET)
    public List<Elevator> getElevators(){
        return this.elevatorController.getElevators();
    }

    @RequestMapping(value = "/elevators/request/{toFloor}", method = RequestMethod.GET)
    public Elevator requestElevatorToFloor(@PathVariable("toFloor") int toFloor){
       return this.elevatorController.requestElevator(toFloor);
    }

    @RequestMapping(value = "/elevators/release/{elevatorId}", method = RequestMethod.GET)
    public void releaseElevator(@PathVariable("elevatorId") int elevatorId){
        this.elevatorController.releaseElevator(elevatorId);
    }
}
