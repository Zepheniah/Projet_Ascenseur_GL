package control;

public interface OperationalCommand {
    void emergencyBreak();

    /**
     * Fonction gérant l'affichage lorsque l'ascenseur est acquitté
     */
    void acquit();

    void stopNextFloor();
    void up();
    void down();
}
