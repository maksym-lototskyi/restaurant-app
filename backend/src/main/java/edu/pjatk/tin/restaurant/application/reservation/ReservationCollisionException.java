package edu.pjatk.tin.restaurant.application.reservation;

public class ReservationCollisionException extends RuntimeException {
    public ReservationCollisionException() {
        super("Another reservation is already made for the given time slot");
    }
}
