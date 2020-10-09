package control;

import control.algorithm.Sort;
import control.command.Acquit;
import control.command.Direction;
import control.command.EmergencyBrake;
import control.command.FloorRequest;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImplCommandControlTest {
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
        assertEquals(Direction.UP, control.getDirection());
        for (int i = 1; i < 9; i++) {
            control.reachFloor(i);
            assertEquals(i, control.getFloor());
            verify(operationalCommand, never()).stopNextFloor();
            assertEquals(Direction.UP, control.getDirection());
        }
        control.reachFloor(9);
        assertEquals(9, control.getFloor());
        assertEquals(Direction.NONE, control.getDirection());
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
        assertEquals(Direction.DOWN, control.getDirection());
        verify(operationalCommand, times(1)).down();
        verify(operationalCommand, never()).up();
        for (int i = 1; i < 9; i++) {
            control.reachFloor(-i);
            assertEquals(Direction.DOWN, control.getDirection());
            assertEquals(-i, control.getFloor());
            verify(operationalCommand, never()).stopNextFloor();
        }
        control.reachFloor(-9);
        assertEquals(-9, control.getFloor());
        assertEquals(Direction.UP, control.getDirection());
        verify(operationalCommand, times(1)).down();
        verify(operationalCommand, times(1)).up();
        verify(operationalCommand, never()).emergencyBreak();
        verify(operationalCommand, times(1)).stopNextFloor();

        for (int i = -8; i < 9; i++) {
            control.reachFloor(i);
            assertEquals(Direction.UP, control.getDirection());
            assertEquals(i, control.getFloor());
            verify(operationalCommand, times(1)).stopNextFloor();
        }

        control.reachFloor(9);
        assertEquals(9, control.getFloor());
        assertEquals(Direction.NONE, control.getDirection());
        verify(operationalCommand, times(1)).down();
        verify(operationalCommand, times(1)).up();
        verify(operationalCommand, never()).emergencyBreak();
        verify(operationalCommand, times(2)).stopNextFloor();
    }

    @Test
    void test_emergency_break_and_acquit() {
        OperationalCommand operationalCommand = mock(OperationalCommand.class);
        ImplCommandControl control = new ImplCommandControl(0, operationalCommand);

        control.setCommands(new TreeSet<>(new Sort()));
        control.addFloorRequest(new FloorRequest(Direction.UP, 10));

        assertEquals(Direction.UP, control.getDirection());
        verify(operationalCommand, times(1)).up();
        verify(operationalCommand, never()).down();
        verify(operationalCommand, never()).emergencyBreak();

        control.emergencyBreak(new EmergencyBrake());
        assertEquals(Direction.NONE, control.getDirection());
        verify(operationalCommand, times(1)).up();
        verify(operationalCommand, times(1)).emergencyBreak();
        verify(operationalCommand, never()).down();


        control.addFloorRequest(new FloorRequest(Direction.UP, 10));
        assertEquals(Direction.NONE, control.getDirection());
        verify(operationalCommand, times(1)).up();
        verify(operationalCommand, times(1)).emergencyBreak();
        verify(operationalCommand, never()).down();

        control.acquit(new Acquit());
        assertEquals(Direction.NONE, control.getDirection());
        verify(operationalCommand, times(1)).up();
        verify(operationalCommand, times(1)).emergencyBreak();
        verify(operationalCommand, never()).down();

        control.addFloorRequest(new FloorRequest(Direction.UP, 10));
        assertEquals(Direction.UP, control.getDirection());
        verify(operationalCommand, times(2)).up();
        verify(operationalCommand, times(1)).emergencyBreak();
        verify(operationalCommand, never()).down();
    }

    @Test
    void test_miss_one_floor() {
        OperationalCommand operationalCommand = mock(OperationalCommand.class);
        ImplCommandControl control = new ImplCommandControl(0, new TreeSet<>(new Sort()), operationalCommand);

        control.addFloorRequest(new FloorRequest(Direction.UP, 10));
        control.reachFloor(2);
        assertEquals(Direction.NONE, control.getDirection());
        verify(operationalCommand, times(1)).emergencyBreak();

    }

    @Test
    void test_change_direction() {
        OperationalCommand operationalCommand = mock(OperationalCommand.class);
        ImplCommandControl control = new ImplCommandControl(0, new TreeSet<>(new Sort()), operationalCommand);

        control.addFloorRequest(new FloorRequest(Direction.UP, 10));
        assertEquals(Direction.UP, control.getDirection());
        verify(operationalCommand, times(0)).stopNextFloor();
        verify(operationalCommand, times(0)).down();
        verify(operationalCommand, times(1)).up();
        verify(operationalCommand, never()).emergencyBreak();
        control.addFloorRequest(new FloorRequest(Direction.UP, -2));
        assertEquals(Direction.DOWN, control.getDirection());
        verify(operationalCommand, times(0)).stopNextFloor();
        verify(operationalCommand, times(1)).down();
        verify(operationalCommand, times(1)).up();
        verify(operationalCommand, never()).emergencyBreak();
    }

    @Test
    void test_change_direction_close() {
        OperationalCommand operationalCommand = mock(OperationalCommand.class);
        ImplCommandControl control = new ImplCommandControl(0, new TreeSet<>(new Sort()), operationalCommand);

        control.addFloorRequest(new FloorRequest(Direction.UP, 10));
        assertEquals(Direction.UP, control.getDirection());
        verify(operationalCommand, times(0)).stopNextFloor();
        verify(operationalCommand, times(0)).down();
        verify(operationalCommand, times(1)).up();
        verify(operationalCommand, never()).emergencyBreak();
        control.addFloorRequest(new FloorRequest(Direction.UP, -1));
        assertEquals(Direction.UP, control.getDirection());
        verify(operationalCommand, times(1)).stopNextFloor();
        verify(operationalCommand, times(1)).down();
        verify(operationalCommand, times(2)).up();
        verify(operationalCommand, never()).emergencyBreak();
    }
}