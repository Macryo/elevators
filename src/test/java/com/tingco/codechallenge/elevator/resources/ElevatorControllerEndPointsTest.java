package com.tingco.codechallenge.elevator.resources;

import com.tingco.codechallenge.elevator.api.Elevator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tingco.codechallenge.elevator.config.ElevatorApplication;

/**
 * Boiler plate test class to get up and running with a test faster.
 *
 * @author Sven Wesley
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ElevatorApplication.class)
public class ElevatorControllerEndPointsTest {

    @Autowired
    private ElevatorControllerEndPoints endPoints;

    @Test
    public void ping() {

        Assert.assertEquals("pong", endPoints.ping());

    }

    @Test
    public void testRequestElevatorToFloor(){
        //Given
        //When
        Elevator elevator = this.endPoints.requestElevatorToFloor(1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Then
        Assert.assertEquals(elevator.getAddressedFloor(), 1);
        Assert.assertEquals(elevator.getDirection(), Elevator.Direction.NONE);
    }

}
