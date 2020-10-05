package control;

import algorithme.NearestFloor;
import control.algorithm.Sort;
import control.command.Direction;
import control.command.FloorRequest;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImplCommandControlTest {
    @Test
    void test_floor_request() {
        CommandHandler handler = mock(CommandHandler.class);
    }

    @Test
    void test_direction_up_deduce() {
        for (int i = -20; i <= 20; i++)
            for (int j = i + 1; j <= 20; j++)
                assertEquals(Direction.UP, CommandControl.floorDirection(i, j));
    }

    @Test
    void test_direction_down_deduce() {
        for (int i = -20; i <= 20; i++)
            for (int j = i + 1; j <= 20; j++)
                assertEquals(Direction.DOWN, CommandControl.floorDirection(j, i));
    }

    @Test
    void test_direction_none_deduce() {
        for (int i = -20; i <= 20; i++)
            assertEquals(Direction.NONE, CommandControl.floorDirection(i, i));
    }

    @Test
    void test_nextStop() {
        for (int i = -20; i <= 20; i++) {
            assertTrue(CommandControl.isNextStop(i, i + 1));
            assertTrue(CommandControl.isNextStop(i + 1, i));
        }
        for (int i = -20; i <= 20; i++)
            for (int j = i + 2; j <= 20; j++) {
                assertFalse(CommandControl.isNextStop(i, j));
                assertFalse(CommandControl.isNextStop(j, i));
            }
    }

    @Test
    void test_simulation_one_command() {
        OperationalCommand operationalCommand = mock(OperationalCommand.class);
        ImplCommandControl control = new ImplCommandControl(0, operationalCommand);
        control.setCommands(new TreeSet<>(new Sort()));
        control.addFloorRequest(new FloorRequest(Direction.UP, 10));
        verify(operationalCommand, times(1)).up();
        for (int i = 1; i < 9; i++) {
            control.reachFloor(i);
            verify(operationalCommand, never()).stopNextFloor();
        }
        control.reachFloor(9);
        verify(operationalCommand, never()).down();
        verify(operationalCommand, never()).emergencyBreak();
        verify(operationalCommand, times(1)).stopNextFloor();
    }

    @Test
    void test_simulation_two_command() {
        OperationalCommand operationalCommand = mock(OperationalCommand.class);
        ImplCommandControl control = new ImplCommandControl(0, operationalCommand);
        control.setCommands(new TreeSet<>(new Sort()));
        control.addFloorRequest(new FloorRequest(Direction.UP, -10));
        control.addFloorRequest(new FloorRequest(Direction.UP, 10));
        verify(operationalCommand, times(1)).down();
        verify(operationalCommand, never()).up();
        for (int i = 1; i < 9; i++) {
            control.reachFloor(-i);
            verify(operationalCommand, never()).stopNextFloor();
        }
        control.reachFloor(-9);
        verify(operationalCommand, times(1)).down();
        verify(operationalCommand, times(1)).up();
        verify(operationalCommand, never()).emergencyBreak();
        verify(operationalCommand, times(1)).stopNextFloor();

        for (int i = -8; i < 9; i++) {
            control.reachFloor(i);
            verify(operationalCommand, times(1)).stopNextFloor();
        }

        control.reachFloor(9);
        verify(operationalCommand, times(1)).down();
        verify(operationalCommand, times(1)).up();
        verify(operationalCommand, never()).emergencyBreak();
        verify(operationalCommand, times(2)).stopNextFloor();
    }
}