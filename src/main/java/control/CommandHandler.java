package control;

import control.command.Acquit;
import control.command.EmergencyBrake;
import control.command.FloorRequest;

public interface CommandHandler {
    void handle(Acquit acquit);
    void handle(EmergencyBrake emergencyBrake);
    void handle(FloorRequest requestFloor);
}
