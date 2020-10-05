package control;

import control.command.Direction;
import control.command.EmergencyBrake;
import control.command.FloorRequest;

import java.util.Comparator;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.TreeSet;
import java.util.function.Function;

public class ImplCommandControl implements CommandControl {
    public ImplCommandControl(int floor, OperationalCommand operationalCommand) {
        this.floor = floor;
        this.commands = commands;
        this.operationalCommand = operationalCommand;
        this.enable = true;
        this.direction = Direction.NONE;
    }

    public ImplCommandControl(int floor, NavigableSet<FloorRequest> commands, OperationalCommand operationalCommand) {
        this.floor = floor;
        this.commands = commands;
        this.operationalCommand = operationalCommand;
        this.enable = true;
        this.direction = Direction.NONE;
    }

    public ImplCommandControl(int floor, Comparator<FloorRequest> requestComparator, OperationalCommand operationalCommand) {
        this.floor = floor;
        this.operationalCommand = operationalCommand;
        this.enable = true;
        this.commands = new TreeSet<>(requestComparator);
        this.direction = Direction.NONE;
    }

    @Override
    public void reachFloor(int floor) {
        FloorRequest floorRequest = commands.first();
        if (Math.abs(floor - this.floor) > 1) {
            emergencyBreak(new EmergencyBrake());
            System.err.println("Skip reach signal of floor " + (this.floor + 1) + " to " + (floor - 1));
        }
        this.floor = floor;
        evaluateCommand();
    }

    @Override
    public void addFloorRequest(FloorRequest floorRequest) {
        if (!enable)
            return;
        commands.add(floorRequest);
        evaluateCommand();
    }

    private void evaluateCommand() {
        if (commands.isEmpty())
            return;
        FloorRequest floorRequest = commands.first();
        Direction floorDirection = CommandControl.floorDirection(floor, floorRequest.getFloor());
        if (CommandControl.isNextStop(this.floor, floorRequest.getFloor())) {
            if (!floorDirection.equals(direction))
                setDirection(floorDirection);
            setDirection(Direction.NONE);
            commands.pollFirst();
            evaluateCommand();
        } else if (!floorDirection.equals(direction))
            setDirection(floorDirection);
    }

    private void setDirection(Direction direction) {
        this.direction = direction;
        switch (direction) {
            case NONE:
                operationalCommand.stopNextFloor();
                break;
            case UP:
                operationalCommand.up();
                break;
            case DOWN:
                operationalCommand.down();
                break;
        }
    }

    @Override
    public void emergencyBreak(EmergencyBrake emergencyBrake) {
        operationalCommand.emergencyBreak();
        commands.clear();
        this.direction = Direction.NONE;
        setEnable(false);
    }

    @Override
    public int getFloor() {
        return floor;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setCommands(NavigableSet<FloorRequest> commands) {
        this.commands = commands;
    }

    private boolean enable;
    private int floor;
    private Direction direction;
    private NavigableSet<FloorRequest> commands;
    private final OperationalCommand operationalCommand;
}
