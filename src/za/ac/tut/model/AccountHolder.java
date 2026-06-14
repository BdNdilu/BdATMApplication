package za.ac.tut.model;

/**
 * Represents a customer with a name and surname.
 * Used in composition inside Account.
 *
 * @author bdndi
 */
public class AccountHolder {
    private String name;
    private String surname;

    public AccountHolder(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getFullName() {
        return name + " " + surname;
    }
}
