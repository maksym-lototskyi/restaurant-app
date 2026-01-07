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

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false, unique = true))
    private Email email;

    @Embedded
    @AttributeOverride(name = "hashedValue", column = @Column(name = "password", nullable = false))
    private Password password;

    @Enumerated(EnumType.STRING)
    private Role role;


    public RestaurantUser(RestaurantUserId id, String firstName, String lastName, Email email, Password password, Role role) {
        this.id = ValidationUtil.requireNonNull(id, "User ID cannot be null");
        this.firstName = ValidationUtil.requireNonBlank(firstName, "First name cannot be null or blank");
        ValidationUtil.requireValueInRange(firstName.length(), 1, 50, "First name must be between 1 and 50 characters");
        this.lastName = ValidationUtil.requireNonBlank(lastName, "Last name cannot be null or blank");
        ValidationUtil.requireValueInRange(lastName.length(), 1, 50, "Last name must be between 1 and 50 characters");
        this.email = ValidationUtil.requireNonNull(email, "Email cannot be null");
        this.password = ValidationUtil.requireNonNull(password, "Password cannot be null");
        this.role = ValidationUtil.requireNonNull(role, "Role cannot be null");
    }

    protected RestaurantUser() {
    }

    public static RestaurantUser create(String firstName, String lastName, Email email, Password password, Role role) {
        return new RestaurantUser(RestaurantUserId.generate(), firstName, lastName, email, password, role);
    }

    public void changeFirstName(String firstName) {
        this.firstName = ValidationUtil.requireNonBlank(firstName, "First name cannot be null or blank");
    }

    public void changeLastName(String lastName) {
        this.lastName = ValidationUtil.requireNonBlank(lastName, "Last name cannot be null or blank");
    }

    public void changeEmail(Email email) {
        this.email = ValidationUtil.requireNonNull(email, "Email cannot be null");
    }

    public void changePassword(Password password) {
        this.password = ValidationUtil.requireNonNull(password, "Password cannot be null");
    }

    public void changeRole(Role role) {
        this.role = ValidationUtil.requireNonNull(role, "Role cannot be null");
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

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
