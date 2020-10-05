package control;

import control.command.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

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
            assertTrue(CommandControl.isNextStop(i, i+1));
            assertTrue(CommandControl.isNextStop(i+1, i));
        }
        for (int i = -20; i <= 20; i++)
            for (int j = i + 2; j <= 20; j++) {
                assertFalse(CommandControl.isNextStop(i,j));
                assertFalse(CommandControl.isNextStop(j,i));
            }
    }
}