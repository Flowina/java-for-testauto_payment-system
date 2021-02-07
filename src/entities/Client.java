package entities;

import java.util.Date;

public class Client {
    private int id;
    private String lastName;
    private String firstName;
    private Date dateOfBirth;

    public Client(int id, String lastName, String firstName, Date dateOfBirth) {
        this.setId(id);
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.setDateOfBirth(dateOfBirth);
    }

    public Client(String lastName, String firstName, Date dateOfBirth) {
        this(0, lastName, firstName, dateOfBirth);
    }

    @Override
    public String toString() {
        return getId() + " " + getLastName() + " " + getFirstName() + " " + getDateOfBirth();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
