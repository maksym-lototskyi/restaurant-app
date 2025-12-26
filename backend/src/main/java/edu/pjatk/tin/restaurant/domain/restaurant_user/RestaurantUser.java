package edu.pjatk.tin.restaurant.domain.restaurant_user;

import edu.pjatk.tin.restaurant.util.validation.ValidationUtil;
import jakarta.persistence.*;

@Entity
public class RestaurantUser {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private RestaurantUserId id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;


    public RestaurantUser(RestaurantUserId id, String firstName, String lastName, String email, String password) {
        this.id = ValidationUtil.requireNonNull(id, "User ID cannot be null");
        this.firstName = ValidationUtil.requireNonBlank(firstName, "First name cannot be null or blank");
        this.lastName = ValidationUtil.requireNonBlank(lastName, "Last name cannot be null or blank");
        this.email = ValidationUtil.requireCorrectStringRegex(email, "Email", "^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$");;
        this.password = ValidationUtil.requireNonBlank(password, "Password cannot be null or blank");
    }

    protected RestaurantUser() {
    }

    public static RestaurantUser create(String firstName, String lastName, String email, String password) {
        return new RestaurantUser(RestaurantUserId.generate(), firstName, lastName, email, password);
    }

    public void changeFirstName(String firstName) {
        this.firstName = ValidationUtil.requireNonBlank(firstName, "First name cannot be null or blank");
    }

    public void changeLastName(String lastName) {
        this.lastName = ValidationUtil.requireNonBlank(lastName, "Last name cannot be null or blank");
    }

    public void changeEmail(String email) {
        this.email = ValidationUtil.requireCorrectStringRegex(email, "Email", "^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$");;
    }

    public void changePassword(String password) {
        this.password = ValidationUtil.requireNonBlank(password, "Password cannot be null or blank");
    }

    public RestaurantUserId getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
