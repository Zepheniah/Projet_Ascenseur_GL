package control.command;

import control.Command;
import control.CommandHandler;

public class EmergencyBrake implements Command {
    @Override
    public void accept(CommandHandler commandHandler) {
        commandHandler.handle(this);
    }
}
