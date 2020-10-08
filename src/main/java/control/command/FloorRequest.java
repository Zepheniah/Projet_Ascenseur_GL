package control.command;

import control.Command;
import control.CommandHandler;

public class FloorRequest implements Command {
    public FloorRequest(Direction direction, int floor) {
        this.direction = direction;
        this.floor = floor;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getFloor() {
        return floor;
    }

    private final Direction direction;
    private final int floor;

    @Override
    public void accept(CommandHandler commandHandler) {
        commandHandler.handle(this);
    }
}
