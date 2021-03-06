package control;
/**
 * @classe SimulationOperational permettant d'adapter le GUI en fonction des actions de l'utilisateur
 */

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
        directionQueue = Direction.NONE;
        stopNext = false;
    }

    @Override
    public void emergencyBreak() {
        direction = Direction.NONE;
        state.setText("EMERGENCY BREAK");
    }
    @Override
    public void acquit(){
        direction = Direction.NONE;
        state.setText("Remise en marche effectué");
    }

    @Override
    public void stopNextFloor() {
        stopNext = true;
        state.setText("BREAK NEXT");
    }

    @Override
    public void up() {
        if (stopNext)
            directionQueue = Direction.UP;
        else {
            direction = Direction.UP;
            state.setText("GO UP");
        }
    }

    @Override
    public void down() {
        if (stopNext)
            directionQueue = Direction.DOWN;
        else {
            direction = Direction.DOWN;
            state.setText("GO DOWN");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (direction) {
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
            stopNext = false;
            direction = directionQueue;
            directionQueue = Direction.NONE;
            switch (direction) {
                case NONE:
                    state.setText("STOP");
                    break;
                case DOWN:
                    state.setText("GO DOWN");
                    break;
                case UP:
                    state.setText("GO UP");
                    break;
            }
        }
        floor.setText(String.valueOf(numFloor));
        commandControl.reachFloor(numFloor);
    }

    public void setCommandControl(CommandControl commandControl) {
        this.commandControl = commandControl;
    }

    private final JTextField state; //!< Text qui sera affiché pour communiquer l'état de l'ascenseur
    private final JTextField floor; //!< Texte communiquant l'étage actuelle
    private int numFloor; //!< Etage actuelle
    private Direction direction;
    private Direction directionQueue;
    private boolean stopNext;
    private CommandControl commandControl;
}
