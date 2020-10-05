package control;

public interface OperationalCommand {
    void emergencyBreak();
    void stopNextFloor();
    void up();
    void down();
}
