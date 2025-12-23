package edu.pjatk.tin.restaurant.domain;

import edu.pjatk.tin.restaurant.domain.Reservation;
import edu.pjatk.tin.restaurant.validation.ValidationUtils;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurant_user")
@NoArgsConstructor
public class User {
    @Id
    @Getter
    private Long id;

    @Getter
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Getter
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Getter
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Getter
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Reservation> reservations = new HashSet<>();

    public User(String firstName, String lastName, String email, String password, Set<Reservation> reservations) {
        this.firstName = ValidationUtils.requireNonBlank(firstName, "First name cannot be null or blank");
        this.lastName = ValidationUtils.requireNonBlank(lastName, "Last name cannot be null or blank");
        this.email = ValidationUtils.requireCorrectStringRegex(email, "Email", "^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$");;
        this.password = ValidationUtils.requireNonBlank(password, "Password cannot be null or blank");
        this.reservations = reservations;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setCustomer(this);
    }

    public Set<Reservation> getReservations() {
        return Set.copyOf(reservations);
    }

    public void setFirstName(String firstName) {
        this.firstName = ValidationUtils.requireNonBlank(firstName, "First name cannot be null or blank");
    }

    public void setLastName(String lastName) {
        this.lastName = ValidationUtils.requireNonBlank(lastName, "Last name cannot be null or blank");
    }

    public void setEmail(String email) {
        this.email = ValidationUtils.requireCorrectStringRegex(email, "Email", "^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$");;
    }

    public void setPassword(String password) {
        this.password = ValidationUtils.requireNonBlank(password, "Password cannot be null or blank");
    }
}
