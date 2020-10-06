package control;


import control.command.Acquit;
import control.command.Direction;
import control.command.EmergencyBrake;
import control.command.FloorRequest;

public interface CommandControl {
    static Direction floorDirection(int from, int to) {
        return to == from ? Direction.NONE : from < to ? Direction.UP : Direction.DOWN;
    }

    static boolean isNextStop(int from, int to) {
        return Math.abs(to - from) == 1;
    }

    void reachFloor(int floor);
    void addFloorRequest(FloorRequest floor);
    void emergencyBreak(EmergencyBrake emergencyBrake);
    void acquit(Acquit acquit);

    int getFloor();
    Direction getDirection();
}
