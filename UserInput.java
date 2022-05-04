import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInput {

    private static ArrayList<InputStream> listOfInputStreams = new ArrayList<>();

    private Scanner input;

    public UserInput() {
        this(System.in);
    }

    public UserInput(InputStream inputStream) {
        if (listOfInputStreams.contains(inputStream)) {
            throw new IllegalStateException("Source already exists");
        } else {
            this.input = new Scanner(inputStream);
            listOfInputStreams.add(inputStream);
        }
    }

    public int readInt(String userInstruction) {
        System.out.println(userInstruction + "?>");
        int inputInt = input.nextInt();

        input.nextLine();

        return inputInt;
    }

    public double readDouble(String userInstruction) {
        System.out.println(userInstruction + "?>");
        double inputDouble = input.nextDouble();

        input.nextLine();

        return inputDouble;
    }

    public String readString(String userInstruction) {
        System.out.println(userInstruction + "?>");

        return fixInputString(input.nextLine(), userInstruction);
    }

    private String fixInputString(String inputString, String userInstruction) {
        while (inputString.isBlank()) {
            System.out.print("Error: invalid input. " + userInstruction + "?>");
            inputString = input.next();
        }
        return inputString.trim();
    }

}
