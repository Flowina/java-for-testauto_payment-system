package entities;

import java.util.Date;
import java.util.Optional;

public class Client {
    private Optional<Long> id;
    private String lastName;
    private String firstName;
    private Date dateOfBirth;

    public Client(Optional<Long> id, String lastName, String firstName, Date dateOfBirth) {
        this.setId(id);
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setDateOfBirth(dateOfBirth);
    }

    public Client(String lastName, String firstName, Date dateOfBirth) {
        this(Optional.empty(), lastName, firstName, dateOfBirth);
    }

    @Override
    public String toString() {
        return (id.isPresent() ? id.get() : "null") + ": " + getLastName() + " " + getFirstName() + " " + getDateOfBirth();
    }

    public Optional<Long> getId() {
        return id;
    }

    public void setId(Optional<Long> id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
