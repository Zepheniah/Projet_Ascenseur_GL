package control.command;

public class FloorRequest {
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
}
