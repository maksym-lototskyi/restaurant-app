package edu.pjatk.tin.restaurant;

import edu.pjatk.tin.restaurant.domain.reservation.Reservation;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationRepository;
import edu.pjatk.tin.restaurant.domain.reservation.TimeSlot;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTable;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableRepository;
import edu.pjatk.tin.restaurant.domain.restaurant_user.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DbContentLoader implements CommandLineRunner {

    private static final int SEED_SIZE = 10;

    private final RestaurantUserRepository userRepository;
    private final RestaurantTableRepository tableRepository;
    private final ReservationRepository reservationRepository;
    private final PasswordHasher passwordHasher;

    public DbContentLoader(
            RestaurantUserRepository userRepository,
            RestaurantTableRepository tableRepository,
            ReservationRepository reservationRepository,
            PasswordHasher passwordHasher
    ) {
        this.userRepository = userRepository;
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public void run(String... args) {
        if (isDatabasePopulated()) {
            return;
        }

        List<RestaurantUser> users = seedUsers();
        List<RestaurantTable> tables = seedTables();
        seedReservations(users, tables);
    }

    private boolean isDatabasePopulated() {
        return userRepository.count() > 0
                || tableRepository.count() > 0
                || reservationRepository.count() > 0;
    }

    private List<RestaurantUser> seedUsers() {
        List<RestaurantUser> users = new ArrayList<>();
        RestaurantUser user = RestaurantUser.create("john", "doe", Email.of("john.doe@gmail.com"), Password.fromRaw("12345678fG-", passwordHasher), Role.USER);
        RestaurantUser admin = RestaurantUser.create("admin", "kowalski", Email.of("admin@gmail.com"), Password.fromRaw("12345678fG-", passwordHasher), Role.ADMIN);
        users.add(user);
        users.add(admin);

        return userRepository.saveAll(users);
    }

    private List<RestaurantTable> seedTables() {
        List<RestaurantTable> tables = new ArrayList<>();

        for (int i = 0; i < SEED_SIZE; i++) {

            tables.add(RestaurantTable.create(
                    "T" + (i + 1),
                    0,
                    (int) (Math.random() * 10) + 1
            ));
        }

        return tableRepository.saveAll(tables);
    }

    private void seedReservations(List<RestaurantUser> users, List<RestaurantTable> tables) {
        List<Reservation> reservations = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < SEED_SIZE; i++) {
            reservations.add(Reservation.create(
                    TimeSlot.of(
                            LocalDateTime.of(
                                    now.getYear(),
                                    now.getMonth(),
                                    now.getDayOfMonth(),
                                    12 + (i % 2),
                                    ((int) (Math.random() * 4)) * 15
                            )
                                    .plusDays(3)
                    ),
                    users.getFirst().getId(),
                    tables.get(i % tables.size()).getId(),
                    2
            ));
        }

        reservationRepository.saveAll(reservations);
    }
}

