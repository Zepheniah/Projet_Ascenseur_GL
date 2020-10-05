package control;

public interface OperationalCommand {
    void setCommandHandler(CommandHandler handler);
    void emergencyBreak();
    void stopNextFloor();
    void up();
    void down();
}
