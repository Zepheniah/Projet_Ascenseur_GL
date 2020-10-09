package control.command;

/**
 * Classe représentant une requete d'étage par l'utilisateur
 * Struture de donnée permettant de definir l'etage demandé avec floor
 * et la direction pour pouvoir coller à la spécification demandé sur le comportement de l'ascenseur.
 */
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

    private final Direction direction; //!< Variable defini en fonction de l'étage actuelle et dans quelle direction l'ascenseur va actuellement
    private final int floor; //!< Etage demandé par l'utilisateur
}
