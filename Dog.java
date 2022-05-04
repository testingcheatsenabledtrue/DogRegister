//Tove Lennartsson tole4912

public class Dog {

    private static final double DACHSHUND_TAIL_LENGTH = 3.7;
    private static final int TAIL_LENGTH_DENOMINATOR = 10;

    private Owner owner;

    private String name;
    private String breed;
    private int age;
    private int weight;
    private double tailLength;

    public Dog(String name, String breed, int age, int weight) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.weight = weight;
        this.tailLength = calculateTailLength();
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }

    public void increaseAge(int yearsToAdd) {
        if (yearsToAdd > 0) {
            age += yearsToAdd;
            tailLength = calculateTailLength();
        }
    }

    public int getWeight() {
        return weight;
    }

    public double getTailLength() {
        return tailLength;
    }

    private double calculateTailLength() {
        if (breed.equalsIgnoreCase("Tax") || breed.equalsIgnoreCase("Dachshund")) {
            return DACHSHUND_TAIL_LENGTH;
        } else {
            return ((double) age * (double) weight) / TAIL_LENGTH_DENOMINATOR;
        }
    }

    public Owner getOwner() {
        if (checkForOwner()) {
            return owner;
        }
        return null;
    }

    public void setOwner(Owner owner) {
        if (checkForOwner()) {
            return;
        }

        this.owner = owner;
        owner.addDogToOwner(this);
    }

    public void removeOwner() {
        if (!checkForOwner()) {
            return;
        }

        Owner[] ownerRef = {owner};

        this.owner = null;
        ownerRef[0].removeDogFromOwner(this);
    }

    private boolean checkForOwner() {
        if (owner == null) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + name + ", " + breed + ", " + age + ", " + weight + ", " + tailLength + " ";
    }

}