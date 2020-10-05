package control;

import control.command.Direction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationOperational implements OperationalCommand, ActionListener {
    public SimulationOperational(JTextField state, JTextField floor, int numFloor, CommandControl commandControl) {
        this.state = state;
        this.floor = floor;
        this.numFloor = numFloor;
        this.commandControl = commandControl;
        direction = Direction.NONE;
        stopNext = false;
    }

    @Override
    public void emergencyBreak() {
        direction = Direction.NONE;
        state.setText("EMERGENCY BREAK");
    }

    @Override
    public void stopNextFloor() {
        stopNext = true;
        state.setText("BREAK NEXT");
    }

    @Override
    public void up() {
        direction = Direction.UP;
        state.setText("GO UP");
    }

    @Override
    public void down() {
        direction = Direction.DOWN;
        state.setText("GO DOWN");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (direction){
            case DOWN:
                --numFloor;
                break;
            case UP:
                ++numFloor;
                break;
            case NONE:
                break;
        }
        if(stopNext) {
            direction = Direction.NONE;
            state.setText("STOP");
        }
        floor.setText(String.valueOf(numFloor));
        commandControl.reachFloor(numFloor);
    }

    public void setCommandControl(CommandControl commandControl) {
        this.commandControl = commandControl;
    }

    private JTextField state;
    private JTextField floor;
    private int numFloor;
    private Direction direction;
    private boolean stopNext;
    private CommandControl commandControl;
}
