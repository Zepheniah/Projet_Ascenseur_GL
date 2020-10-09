package control;
/**
 * @brief Classe gérant les differentes actions lié au bouton présent sur le GUI
 */

import control.command.Acquit;
import control.command.Direction;
import control.command.EmergencyBrake;
import control.command.FloorRequest;

import java.util.Comparator;
import java.util.NavigableSet;
import java.util.TreeSet;

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

    /**
     * Fonction déclenchant le movement de l'ascenseur vers l'étage pris en parametre
     * @param floor
     */
    @Override
    public void reachFloor(int floor) {
        int oldFloor = this.floor;
        this.floor = floor;
        if (Math.abs(floor - oldFloor) > 1) {
            emergencyBreak(new EmergencyBrake());
            System.err.println("Skip reach signal of floor " + (oldFloor + 1) + " to " + (floor - 1));
        }
        evaluateCommand();
    }

    /**
     * Si la requete est valide,la requete est ajouté à la liste des étages demandé
     * Une requete est consideré comme valide si l'ascenseur n'est pas dans l'état arret d'urgence
     * et que l'ascenseur n'est pas deja à l'étage demandé.
     * @param floorRequest Etage demandé par l'utilisateur
     */
    @Override
    public void addFloorRequest(FloorRequest floorRequest) {
        if (!enable || (floor==floorRequest.getFloor() && direction==Direction.NONE))
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

    /**
     * Bloque l'ascenseur empechant tout mouvement jusqu'a qu'il soit acquitté par acquit
     * Vide la liste d'attente des requetes
     */
    @Override
    public void emergencyBreak(EmergencyBrake emergencyBrake) {
        operationalCommand.emergencyBreak();
        commands.clear();
        this.direction = Direction.NONE;
        setEnable(false);
    }

    /**
     * Debloque l'ascenseur
     *
     */
    @Override
    public void acquit(Acquit acquit){
        operationalCommand.acquit();
        commands.clear();
        this.direction = Direction.NONE;
        setEnable(true);
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

    private boolean enable; //!< Booléen permettant de bloquer ou débloqué l'ascenseur
    private int floor;//!< Etage où se trouve l'ascenseur
    private Direction direction;//!< Le sens dans lequel va l'ascenseur
    private NavigableSet<FloorRequest> commands;//! Liste des differentes requete d'étage
    private final OperationalCommand operationalCommand;//!< L'action demandé par l'utilisateur @ref command
}
