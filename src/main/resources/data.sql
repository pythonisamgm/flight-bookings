DROP TABLE IF EXISTS flight;
DROP TABLE IF EXISTS airport;

CREATE TABLE airport (
    airport_code VARCHAR(3) PRIMARY KEY,
    airport_name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL
);

INSERT INTO airport (airport_code, airport_name, city, country) VALUES
('MAD', 'Madrid-Barajas Adolfo Suárez', 'Madrid', 'ESPAÑA'),
('BCN', 'Barcelona-El Prat', 'Barcelona', 'ESPAÑA'),
('VLC', 'Valencia', 'Valencia', 'ESPAÑA'),
('AGP', 'Málaga-Costa del Sol', 'Málaga', 'ESPAÑA'),
('ALC', 'Alicante-Elche', 'Alicante', 'ESPAÑA'),
('SVQ', 'Sevilla', 'Sevilla', 'ESPAÑA'),
('BIO', 'Bilbao', 'Bilbao', 'ESPAÑA'),
('PMI', 'Palma de Mallorca', 'Palma de Mallorca', 'ESPAÑA'),
('LPA', 'Gran Canaria', 'Las Palmas', 'ESPAÑA'),
('TFS', 'Tenerife Sur', 'Tenerife', 'ESPAÑA'),
('LAX', 'Los Angeles International Airport', 'Los Ángeles', 'EE.UU.'),
('JFK', 'John F. Kennedy International Airport', 'Nueva York', 'EE.UU.'),
('ATL', 'Hartsfield-Jackson Atlanta International Airport', 'Atlanta', 'EE.UU.'),
('DFW', 'Dallas/Fort Worth International Airport', 'Dallas', 'EE.UU.'),
('YYZ', 'Toronto Pearson International Airport', 'Toronto', 'CANADA'),
('GRU', 'São Paulo/Guarulhos International Airport', 'São Paulo', 'BRASIL'),
('MEX', 'Mexico City International Airport', 'Ciudad de México', 'MÉXICO'),
('BOG', 'Bogotá El Dorado International Airport', 'Bogotá', 'COLOMBIA'),
('CUN', 'Cancún International Airport', 'Cancún', 'MÉXICO'),
('LHR', 'London Heathrow Airport', 'Londres', 'REINO UNIDO'),
('AMS', 'Amsterdam Airport Schiphol', 'Ámsterdam', 'PAÍSES BAJOS'),
('CDG', 'Charles de Gaulle Airport', 'París', 'FRANCIA'),
('FRA', 'Frankfurt Airport', 'Fráncfort', 'ALEMANIA'),
('MUC', 'Munich Airport', 'Múnich', 'ALEMANIA'),
('ZRH', 'Zurich Airport', 'Zúrich', 'SUIZA'),
('CPH', 'Copenhagen Airport', 'Copenhague', 'DINAMARCA'),
('VIE', 'Vienna International Airport', 'Viena', 'AUSTRIA'),
('LIS', 'Lisbon Portela Airport', 'Lisboa', 'PORTUGAL'),
('BRU', 'Brussels Airport', 'Bruselas', 'BÉLGICA');

CREATE TABLE flight (
    flight_id INT AUTO_INCREMENT PRIMARY KEY,
    flight_number INT NOT NULL,
    num_rows INT NOT NULL,
    departure_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    flight_duration INT, -- En MySQL no hay un tipo INTERVAL, así que usaremos INT para duración en minutos.
    airplane_type VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    availability BOOLEAN NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    origin_airport_code VARCHAR(3),
    destination_airport_code VARCHAR(3),
    FOREIGN KEY (origin_airport_code) REFERENCES airport(airport_code),
    FOREIGN KEY (destination_airport_code) REFERENCES airport(airport_code)
);

INSERT INTO flight (availability, capacity, airplane_type, flight_number, price, num_rows, departure_time, arrival_time, flight_duration, origin_airport_code, destination_airport_code)
VALUES
(TRUE, 180, 'Airbus A320', 1001, 300.50, 300, '2024-10-12 08:00:00', '2024-10-12 12:00:00', 240, 'MAD', 'LAX'),
(FALSE, 200, 'Boeing 737', 1002, 450.75, 300, '2024-10-12 14:30:00', '2024-10-12 19:00:00', 210, 'BCN', 'JFK');