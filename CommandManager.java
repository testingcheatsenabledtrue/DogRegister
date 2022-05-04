import java.util.ArrayList;

//Tove Lennartsson tole4912

public class CommandManager {

    private ArrayList<Command> commandList;

    private Communicator communicator;

    public CommandManager(ArrayList<Command> commandList, Communicator communicator) {
        this.commandList = commandList;
        this.communicator = communicator;
    }

    public String handleCommandOptions() {
        communicator.listCommandElements(commandList);
        return translateCommandInput(communicator.getTextInput("Command"));
    }

    private String translateCommandInput(String commandInput) {
        commandInput = commandInput.toLowerCase();
        for (Command command : commandList) {
            if (command.toString().equals(commandInput)) {
                return command.toString();
            } else if (command.getCommandShortcut().equals(commandInput)) {
                return command.toString();
            } else if (command.getAltCommand().equals(commandInput)) {
                return command.toString();
            }
        }
        return "error";
    }

    public void listCommandShortcuts() {
        communicator.listStringElements(getCommandShortcuts());
    }

    private ArrayList<String> getCommandShortcuts() {
        ArrayList<String> commandShortcuts = new ArrayList<>();
        for (Command command : commandList) {
            commandShortcuts.add(command.toString() + ": " + command.getCommandShortcut());
        }
        return commandShortcuts;
    }

}
