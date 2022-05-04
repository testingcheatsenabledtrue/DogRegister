import java.util.ArrayList;

public class DogRegisterProgram {

    private Communicator communicator;
    private Register register;
    private CommandManager commandManager;

    private void runProgram() {
        startProgram();
        runCommandLoop();
        quitProgram();
    }

    private void startProgram() {
        this.communicator = new Communicator();
        this.register = new Register(communicator);
        this.commandManager = new CommandManager(setCommands(), communicator);
        communicator.giveStartUpMessage();
    }

    private void runCommandLoop() {
        String command;

        do {
            command = commandManager.handleCommandOptions();
            executeCommand(command);
        } while (!command.equals("exit"));
    }

    private void executeCommand(String command) {
        switch (command) {
            case "register new dog":
                register.addDogToRegister();
                break;
            case "register new owner":
                register.addOwnerToRegister();
                break;
            case "give dog":
                register.assignDogToOwner();
                break;
            case "list dogs":
                register.listDogs();
                break;
            case "list owners":
                register.listOwners();
                break;
            case "increase age":
                register.increaseDogAge();
                break;
            case "remove dog":
                register.removeDog();
                break;
            case "remove owned dog":
                register.removeDogFromOwner();
                break;
            case "remove owner":
                register.removeOwnerFromRegister();
                break;
            case "list command shortcuts":
                commandManager.listCommandShortcuts();
                break;
            case "exit":
                //loop bryts genom villkor i runCommandLoop
                break;
            default:
                communicator.giveErrorMessage("invalid command. Try again!");
        }
    }

    private void quitProgram() {
        communicator.giveShutDownMessage();
    }

    private ArrayList<Command> setCommands() {
        ArrayList<Command> commands = new ArrayList<>();
        commands.add(new Command("register new dog", "rnd", "register dog"));
        commands.add(new Command("register new owner", "rno", "register owner"));
        commands.add(new Command("give dog", "gd"));
        commands.add(new Command("list dogs", "ld"));
        commands.add(new Command("list owners", "lo"));
        commands.add(new Command("increase age", "ia"));
        commands.add(new Command("remove dog", "rd"));
        commands.add(new Command("remove owned dog", "rod"));
        commands.add(new Command("remove owner", "ro"));
        commands.add(new Command("list command shortcuts", "lcs", "list shortcuts"));
        commands.add(new Command("exit", "xt"));

        return commands;
    }

    public static void main(String[] args) {
        DogRegisterProgram program = new DogRegisterProgram();
        program.runProgram();
    }

}
