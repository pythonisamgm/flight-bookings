CREATE TABLE IF NOT EXISTS flight (
    flight_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    flight_number INT NOT NULL,
    num_rows INT,
    departure_time TIMESTAMP,
    arrival_time TIMESTAMP,
    flight_duration INT,
    flight_airplane VARCHAR(255),
    capacity_plane INT,
    availability BOOLEAN,
    flight_price DECIMAL(10,2),
    origin_airport VARCHAR(255),
    destination_airport VARCHAR(255)
);

INSERT INTO flight (availability, capacity_plane, flight_airplane, flight_number, flight_price, num_rows, departure_time, arrival_time, flight_duration, origin_airport, destination_airport)
VALUES (1, 180, 1, 1001, 300.50, 3, '2024-10-12 08:00:00', '2024-10-12 12:00:00', 4, 'MAD', 'LAX'),
(1, 200, 2, 1002, 450.75, 4, '2024-10-12 14:30:00', '2024-10-12 19:00:00', 4.5, 'BCN', 'JFK');