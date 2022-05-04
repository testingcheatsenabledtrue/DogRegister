//Tove Lennartsson tole4912

public class Command {

    private String command;
    private String commandShortcut;
    private String altCommand;

    public Command(String command, String shortcut) {
        this.command = command;
        this.commandShortcut = shortcut;
    }

    public Command(String command, String shortcut, String altCommand) {
        this.command = command;
        this.commandShortcut = shortcut;
        this.altCommand = altCommand;
    }

    public String getCommandShortcut() {
        return commandShortcut;
    }

    public String getAltCommand() {
        if (altCommand == null) {
            altCommand = "error";
        }
        return altCommand;
    }

    public String toString() {
        return command;
    }

}
