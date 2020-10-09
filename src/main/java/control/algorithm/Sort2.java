package control.algorithm;

import control.CommandControl;
import control.command.Direction;
import control.command.FloorRequest;

import java.util.Comparator;

public class Sort2 implements Comparator<FloorRequest> {
    private CommandControl commandControl;

    public Sort2(CommandControl commandControl) {
        this.commandControl = commandControl;
    }

    @Override
    public int compare(FloorRequest o1, FloorRequest o2) {
        Direction direction = commandControl.getDirection();
        int delta = direction.equals(Direction.UP) ? 1 : direction.equals(Direction.DOWN) ? -1 : 0;
        Direction d1 = CommandControl.floorDirection(commandControl.getFloor() + delta, o1.getFloor());
        Direction d2 = CommandControl.floorDirection(commandControl.getFloor() + delta, o2.getFloor());
        if (d1.equals(d2)) {
            switch (d1) {
                case UP:
                    return o1.getFloor() - o2.getFloor();
                case DOWN:
                    return o2.getFloor() - o1.getFloor();
            }
        }
        return direction.equals(d1) ? -1 : direction.equals(d2) ? 1 : -1;
    }
}
