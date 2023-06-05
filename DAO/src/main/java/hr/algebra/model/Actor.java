package hr.algebra.model;

public class Actor extends Person {
    public Actor(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Actor(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }
}
