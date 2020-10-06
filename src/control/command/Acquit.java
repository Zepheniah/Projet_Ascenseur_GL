package control.command;
/**
 * @package control.command
 * Package regroupant les differentes actions possibles par l'utilisateur
 */

import control.Command;
import control.CommandHandler;

public class Acquit implements Command {
    @Override
    public void accept(CommandHandler commandHandler) {
        commandHandler.handle(this);
    }
}
