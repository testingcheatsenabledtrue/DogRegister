import java.util.Arrays;

public class Owner {

    private static final int INDEX_SHIFT = 1;
    private static final int NO_INDEX = -1;

    private Dog[] dogArray;

    private String name;

    public Owner(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addDogToOwner(Dog dogToAdd) {
        if (!checkNoOtherOwner(dogToAdd)) {
            return;
        } else if (checkIfMyDog(dogToAdd)) {
            return;
        }

        addDogToArray(dogToAdd);
        dogToAdd.setOwner(this);
    }

    private void addDogToArray(Dog dogToAdd) {
        if (dogArray == null) {
            dogArray = new Dog[1];
            dogArray[0] = dogToAdd;
        }
        dogArray = Arrays.copyOf(dogArray, dogArray.length + INDEX_SHIFT);

        dogArray[dogArray.length - INDEX_SHIFT] = dogToAdd;
    }

    public void removeDogFromOwner(Dog dogToRemove) {
        if (!checkNoOtherOwner(dogToRemove)) {
            return;
        } else if (!checkIfMyDog(dogToRemove)) {
            return;
        }

        int dogIndex = findDogIndex(dogToRemove.getName());

        removeDogFromArray(dogIndex);

        dogToRemove.removeOwner();
    }

    private void removeDogFromArray(int dogIndex) {
        Dog[] transferArray = new Dog[dogArray.length - INDEX_SHIFT];
        boolean dogIndexIsPassed = false;

        for (int i = 0; i < dogArray.length; i++) {
            if (i == dogIndex) {
                dogIndexIsPassed = true; //hunden på indexet läggs inte till i ny array
            } else if (dogIndexIsPassed) {
                transferArray[i - INDEX_SHIFT] = dogArray[i]; //för att inte lämna hål i ny array
            } else {
                transferArray[i] = dogArray[i]; //hundar läggs på samma index i ny array
            }
        }
        dogArray = Arrays.copyOf(transferArray, transferArray.length);
    }

    private int findDogIndex(String dogName) {
        for (int i = 0; i < dogArray.length; i++) {
            if (dogName.equalsIgnoreCase(dogArray[i].getName())) {
                return i;
            }
        }
        return NO_INDEX;
    }

    private boolean checkIfMyDog(Dog dogToCheck) {
        if (dogArray == null) {
            return false;
        }

        String dogName = dogToCheck.getName();

        for (Dog dog : dogArray) {
            if (dogName.equalsIgnoreCase(dog.getName())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkNoOtherOwner(Dog dogToCheck) {
        if (dogToCheck.getOwner() == null) {
            return true;
        } else if (dogToCheck.getOwner() != this) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

}
