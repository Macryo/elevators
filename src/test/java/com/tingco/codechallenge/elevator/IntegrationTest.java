package com.tingco.codechallenge.elevator;

import com.tingco.codechallenge.elevator.api.Elevator;
import com.tingco.codechallenge.elevator.api.ElevatorController;
import com.tingco.codechallenge.elevator.api.ElevatorControllerImpl;
import com.tingco.codechallenge.elevator.api.ElevatorImpl;
import com.tingco.codechallenge.elevator.services.ElevatorTaskHandler;
import com.tingco.codechallenge.elevator.services.ElevatorTaskHandlerImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tingco.codechallenge.elevator.config.ElevatorApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

import static org.mockito.Mockito.when;

/**
 * Boiler plate test class to get up and running with a test faster.
 *
 * @author Sven Wesley
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ElevatorApplication.class)
public class IntegrationTest {

    @Test
    public void simulateAnElevatorShaft() {
        //Given
        ElevatorTaskHandler elevatorTaskHandlerMock = Mockito.mock(ElevatorTaskHandler.class);
        Elevator elevatorMock = Mockito.mock(Elevator.class);

        //When
        when(elevatorTaskHandlerMock.scheduleElevatorMove(Mockito.anyInt())).thenReturn(elevatorMock);
        ElevatorController controller = new ElevatorControllerImpl(elevatorTaskHandlerMock);
        for (int i = 0; i < 20; i++) {
            controller.requestElevator(new Random().nextInt(10));
        }

        //Then
        Mockito.verify(elevatorTaskHandlerMock, Mockito.times(20)).scheduleElevatorMove(Mockito.anyInt());
    }

}
