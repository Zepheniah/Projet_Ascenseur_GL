package control;


import control.command.Acquit;
import control.command.Direction;
import control.command.EmergencyBrake;
import control.command.FloorRequest;

public interface CommandControl {
    static Direction floorDirection(int from, int to) {
        return to == from ? Direction.NONE : from < to ? Direction.UP : Direction.DOWN;
    }

    /**
     * Verifie si les 2 étages passé en parametre sont consécutif
     * @param from Etage A
     * @param to Etage B
     * @return Si A et B sont consécutif return TRUE sinon False
     */
    static boolean isNextStop(int from, int to) {
        return Math.abs(to - from) == 1;
    }

    void reachFloor(int floor);

    /**
     * Ajoute une requete d'étage par l'utilisateur à la liste des requetes
     * @param floor Etage demandé par l'utilisateur
     */
    void addFloorRequest(FloorRequest floor);
    void emergencyBreak(EmergencyBrake emergencyBrake);

    /**
     * Fonction débloquant l'ascenseur
     * @param acquit
     */
    void acquit(Acquit acquit);

    int getFloor();
    Direction getDirection();
}
