package control;

public interface OperationalCommand {
    void emergencyBreak();
    void acquit();
    void stopNextFloor();
    void up();
    void down();
}
