package application.core;

/**
 * An exception that gets thrown when a player entered a command
 */

public class CommandException extends IllegalStateException
{
    private final Command COMMAND;

    CommandException(Command command, String msg) {
        super(msg);
        COMMAND = command;
    }

    CommandException(Command command) {
        COMMAND = command;
    }

    public Command getCommand() {
        return COMMAND;
    }
}
