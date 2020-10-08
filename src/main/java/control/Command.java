package control;

public interface Command {
    void accept(CommandHandler commandHandler);
}
