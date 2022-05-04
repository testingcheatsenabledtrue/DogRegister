import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Register {

    private static final int NO_INDEX = -1;

    private ArrayList<Dog> listOfDogs;

    private ArrayList<Owner> listOfOwners;

    private Communicator communicator;

    public Register(Communicator communicator) {
        this.listOfDogs = new ArrayList<>();
        this.listOfOwners = new ArrayList<>();
        this.communicator = communicator;
    }

    public void addDogToRegister() {
        Dog dogToAdd = createDog();

        listOfDogs.add(dogToAdd);

        communicator.giveTaskResultMessage(dogToAdd.getName(), "registered");
    }

    private Dog createDog() {
        String name = communicator.getTextInput("Name of dog");
        String breed = communicator.getTextInput("Breed of Dog");
        int age = communicator.readIntegerInput("Age of dog");
        int weight = communicator.readIntegerInput("Weight of dog");

        return new Dog(name, breed, age, weight);
    }

    public void addOwnerToRegister() {
        Owner ownerToAdd = createOwner();

        listOfOwners.add(ownerToAdd);

        communicator.giveTaskResultMessage(ownerToAdd.getName(), "registered");
    }

    private Owner createOwner() {
        String ownerName = getNameInput();

        return new Owner(ownerName);
    }

    public void assignDogToOwner() {
        if (testNoDogsInRegister() || testNoOwnersInRegister()) {
            return;
        }

        String dogName = communicator.getTextInput("Name of dog");
        int dogIndex = findDogIndex(dogName);

        if (testNameNotInRegister(dogIndex, dogName) || testDogOwnerAlreadyExists(dogIndex)) {
            return;
        }

        String ownerName = communicator.getTextInput("Name of owner");
        int ownerIndex = findOwnerIndex(ownerName);

        if (testNameNotInRegister(ownerIndex, ownerName)) {
            return;
        }

        listOfOwners.get(ownerIndex).addDogToOwner(listOfDogs.get(dogIndex));

        communicator.giveTaskResultMessage(dogName, "owned by " + ownerName);
    }

    public void listDogs() {
        if (testNoDogsInRegister()) {
            return;
        }

        sortDogs();

        double minTailLength = communicator.readTailLengthInput();
        ArrayList<String> dogsFound = findDogsByTailLength(minTailLength);

        if (dogsFound.isEmpty()) {
            communicator.giveErrorMessage("no dog in register with tail length " + minTailLength + " or over");
        } else {
            communicator.giveTailLengthMessage(minTailLength);
            communicator.listStringElements(dogsFound);
        }
    }

    public void listOwners() {
        if (testNoOwnersInRegister()) {
            return;
        }

        ArrayList<String> ownerListWithDogs = new ArrayList<>();

        for (Owner owner : listOfOwners) {
            ownerListWithDogs.add(owner + " " + Arrays.toString(findOwnedDogs(owner).toArray()));
        }
        communicator.listStringElements(ownerListWithDogs);
    }

    public void increaseDogAge() {
        String dogName = getNameInput();
        int dogIndex = findDogIndex(dogName);

        if (testNameNotInRegister(dogIndex, dogName)) {
            return;
        }

        findDogByName(dogName).increaseAge(1);

        communicator.giveTaskResultMessage(dogName, listOfDogs.get(dogIndex).getAge() + " years old");
    }

    public void removeDog() {
        String dogName = getNameInput();
        int dogIndex = findDogIndex(dogName);

        if (testNameNotInRegister(dogIndex, dogName)) {
            return;
        }

        Dog dogToRemove = listOfDogs.get(dogIndex);
        Owner owner = dogToRemove.getOwner();

        if (owner != null) {
            owner.removeDogFromOwner(dogToRemove);
        }

        listOfDogs.remove(dogIndex);

        communicator.giveTaskResultMessage(dogName, "removed from the register");
    }

    public void removeDogFromOwner() {
        String dogName = getNameInput();
        int dogIndex = findDogIndex(dogName);

        if (testNameNotInRegister(dogIndex, dogName)) {
            return;
        }
        Dog dogToRemove = listOfDogs.get(dogIndex);

        if (dogToRemove.getOwner() == null) {
            communicator.giveErrorMessage(dogName + " has no owner");
            return;
        }

        Owner ownerToRemove = dogToRemove.getOwner();

        ownerToRemove.removeDogFromOwner(dogToRemove);

        dogToRemove.removeOwner();

        communicator.giveTaskResultMessage(dogName, "removed from " + ownerToRemove.getName());
    }

    public void removeOwnerFromRegister() {
        String ownerName = getNameInput();

        int ownerIndex = findOwnerIndex(ownerName);

        if (testNameNotInRegister(ownerIndex, ownerName)) {
            return;
        }

        Owner ownerToRemove = listOfOwners.get(ownerIndex);

        for (Dog dog : findOwnedDogs(ownerToRemove)) {
            listOfDogs.remove(dog);
        }

        listOfOwners.remove(ownerToRemove);

        communicator.giveTaskResultMessage(ownerName, "removed from the register");
    }

    private int sortDogs() {
        int numberOfSwaps = 0;

        for (Dog dog : listOfDogs) {
            int currentIndex = listOfDogs.indexOf(dog);
            int smallestDogIndex = findSmallestDog(listOfDogs.indexOf(dog));

            if (smallestDogIndex > currentIndex) {
                swapUsingCollections(smallestDogIndex, currentIndex);
                numberOfSwaps++;
            }
        }
        return numberOfSwaps;
    }

    private void swapUsingCollections(int dogIndexA, int dogIndexB) {
        Collections.swap(listOfDogs, dogIndexA, dogIndexB);
    }

    private int compareDogs(Dog dogA, Dog dogB) {
        int indexOfSmallestDog = compareDogTails(dogA, dogB);

        if (indexOfSmallestDog == NO_INDEX) {
            indexOfSmallestDog = compareDogNames(dogA, dogB);
        }

        return indexOfSmallestDog;
    }

    private int compareDogTails(Dog dogA, Dog dogB) {
        double tailLengthA = dogA.getTailLength();
        double tailLengthB = dogB.getTailLength();

        if (tailLengthA < tailLengthB) {
            return listOfDogs.indexOf(dogA);
        } else if (tailLengthB < tailLengthA) {
            return listOfDogs.indexOf(dogB);
        }

        return NO_INDEX;
    }

    private int compareDogNames(Dog dogA, Dog dogB) {
        String nameA = dogA.getName().toLowerCase();
        String nameB = dogB.getName().toLowerCase();

        int shortestName = Math.min(nameA.length(), nameB.length());

        for (int i = 0; i < shortestName; i++) {
            if (nameA.charAt(i) < nameB.charAt(i)) {
                return listOfDogs.indexOf(dogA);
            } else if (nameB.charAt(i) < nameA.charAt(i)) {
                return listOfDogs.indexOf(dogB);
            }
        }

        if (shortestName == nameB.length()) {
            return listOfDogs.indexOf(dogB);
        }
        return listOfDogs.indexOf(dogA);
    }

    private int findSmallestDog(int refDogIndex) {
        for (int i = refDogIndex; i < listOfDogs.size(); i++) {
            refDogIndex = compareDogs(listOfDogs.get(i), listOfDogs.get(refDogIndex));
        }
        return refDogIndex;
    }

    private ArrayList<String> findDogsByTailLength(double shortestTailLength) {
        ArrayList<String> dogsByTailLength = new ArrayList<>();

        for (Dog dog : listOfDogs) {

            if (dog.getTailLength() >= shortestTailLength) {
                String dogInfo = "" + dog;

                if (dog.getOwner() != null) {
                    dogInfo += ", owned by " + dog.getOwner().getName();
                }

                dogsByTailLength.add(dogInfo);
            }
        }
        return dogsByTailLength;
    }

    private ArrayList<Dog> findOwnedDogs(Owner owner) {
        ArrayList<Dog> ownedDogs = new ArrayList<>();

        for (Dog dog : listOfDogs) {
            if ((dog.getOwner() != null) && (dog.getOwner().equals(owner))) {
                ownedDogs.add(dog);
            }
        }
        return ownedDogs;
    }

    private Dog findDogByName(String dogName) { //7.1
        for (Dog dog : listOfDogs) {
            if (dog.getName().equalsIgnoreCase(dogName)) {
                return dog;
            }
        }
        return null;
    }

    private int findDogIndex(String dogName) {
        if (listOfDogs.isEmpty()) {
            return NO_INDEX;
        }

        for (Dog dog : listOfDogs) {
            if (dog.getName().equalsIgnoreCase(dogName)) {
                return listOfDogs.indexOf(dog);
            }
        }
        return NO_INDEX;
    }

    private int findOwnerIndex(String ownerName) {
        if (listOfOwners.isEmpty()) {
            return NO_INDEX;

        }
        for (Owner owner : listOfOwners) {
            if (owner.getName().equalsIgnoreCase(ownerName)) {
                return listOfOwners.indexOf(owner);
            }
        }
        return NO_INDEX;
    }

    private String getNameInput() {
        return communicator.getTextInput("Name");
    }

    private boolean testNoDogsInRegister() {
        if (listOfDogs.isEmpty()) {
            communicator.giveErrorMessage("no dogs in register");
            return true;
        }
        return false;
    }

    private boolean testNoOwnersInRegister() {
        if (listOfOwners.isEmpty()) {
            communicator.giveErrorMessage("no owners in register");
            return true;
        }
        return false;
    }

    private boolean testNameNotInRegister(int index, String name) {
        if (index == NO_INDEX) {
            communicator.giveErrorMessage("no " + name + " in register");
            return true;
        }
        return false;
    }

    private boolean testDogOwnerAlreadyExists(int dogIndex) {
        if (listOfDogs.get(dogIndex).getOwner() != null) {
            communicator.giveErrorMessage("dog already has owner");
            return true;
        }
        return false;
    }

}
