import java.util.ArrayList;

//Tove Lennartsson tole4912

public class Communicator {

    private UserInput userInput;

    public Communicator() {
        this.userInput = new UserInput();
    }

    public void listCommandElements(ArrayList<Command> commands) {
        System.out.println("\nAvailable commands: ");

        for (Command command : commands) {
            System.out.println("* " + command);
        }
    }

    public void listStringElements(ArrayList<String> strings) {
        for (String text : strings) {
            System.out.println(text);
        }
    }

    public String getTextInput(String informationWanted) {
        return userInput.readString(informationWanted);
    }

    public int readIntegerInput(String informationWanted) {
        return userInput.readInt(informationWanted);
    }

    public double readTailLengthInput() {
        return userInput.readDouble("Smallest tail length to display");
    }

    public void giveTailLengthMessage(double minTailLength) {
        System.out.println("Dogs with tail length " + minTailLength + " or over: ");
    }

    public void giveTaskResultMessage(String specifiedObject, String resultSpecification) {
        System.out.println(specifiedObject + " is now " + resultSpecification);
    }

    public void giveErrorMessage(String errorSpecification) {
        System.out.println("Error: " + errorSpecification);
    }

    public void giveStartUpMessage() {
        System.out.println("Welcome!");
    }

    public void giveShutDownMessage() {
        System.out.println("Goodbye!");
    }

}
