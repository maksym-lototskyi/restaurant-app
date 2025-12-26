CREATE TABLE IF NOT EXISTS hall (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE ,
    width INTEGER NOT NULL,
    length INTEGER NOT NULL,
    floor_number INTEGER NOT NULL
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS table_type(
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    number_of_seats INTEGER NOT NULL,
    width INTEGER NOT NULL,
    length INTEGER NOT NULL
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS restaurant_table (
    id BINARY(16) PRIMARY KEY,
    table_number VARCHAR(10) NOT NULL UNIQUE,
    table_type_id BINARY(16) NOT NULL,
    hall_id BINARY(16) NOT NULL,
    position_x INTEGER NOT NULL,
    position_y INTEGER NOT NULL,
    rotation VARCHAR(50) NOT NULL,
    CONSTRAINT fk_hall_table
        FOREIGN KEY (hall_id) REFERENCES hall(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_table_type_table
        FOREIGN KEY (table_type_id) REFERENCES table_type(id)
            ON DELETE CASCADE
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS restaurant_user(
    id BINARY(16) PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS reservation(
    id BINARY(16) PRIMARY KEY,
    customer_id BINARY(16) NOT NULL,
    table_id BINARY(16) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT user_reservation_fk
        FOREIGN KEY (customer_id) REFERENCES restaurant_user(id),
    CONSTRAINT table_reservation_fk
        FOREIGN KEY (table_id) REFERENCES restaurant_table(id)
)ENGINE=InnoDB;
