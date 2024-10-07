
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
('LAX', 'Los Angeles International Airport', 'Los Ángeles', 'EE_UU'),
('JFK', 'John F. Kennedy International Airport', 'Nueva York', 'EE_UU'),
('ATL', 'Hartsfield-Jackson Atlanta International Airport', 'Atlanta', 'EE_UU'),
('DFW', 'Dallas/Fort Worth International Airport', 'Dallas', 'EE_UU'),
('YYZ', 'Toronto Pearson International Airport', 'Toronto', 'CANADA'),
('GRU', 'São Paulo/Guarulhos International Airport', 'São Paulo', 'BRASIL'),
('MEX', 'Mexico City International Airport', 'Ciudad de México', 'MEXICO'),
('BOG', 'Bogotá El Dorado International Airport', 'Bogotá', 'COLOMBIA'),
('CUN', 'Cancún International Airport', 'Cancún', 'MEXICO'),
('LHR', 'London Heathrow Airport', 'Londres', 'REINO_UNIDO'),
('AMS', 'Amsterdam Airport Schiphol', 'Ámsterdam', 'PAISES_BAJOS'),
('CDG', 'Charles de Gaulle Airport', 'París', 'FRANCIA'),
('FRA', 'Frankfurt Airport', 'Fráncfort', 'ALEMANIA'),
('MUC', 'Munich Airport', 'Múnich', 'ALEMANIA'),
('ZRH', 'Zurich Airport', 'Zúrich', 'SUIZA'),
('CPH', 'Copenhagen Airport', 'Copenhague', 'DINAMARCA'),
('VIE', 'Vienna International Airport', 'Viena', 'AUSTRIA'),
('LIS', 'Lisbon Portela Airport', 'Lisboa', 'PORTUGAL'),
('BRU', 'Brussels Airport', 'Bruselas', 'BELGICA')
ON CONFLICT (airport_code) DO NOTHING;

DROP TABLE IF EXISTS flight;

CREATE TABLE flight (
    flight_id SERIAL PRIMARY KEY,
       flight_number INT NOT NULL,
       num_rows INT NOT NULL,
       departure_time TIMESTAMP NOT NULL,
       arrival_time TIMESTAMP NOT NULL,
       flight_duration INTERVAL, --que hago con esto?
       airplane_type VARCHAR(50) NOT NULL,
       capacity INT NOT NULL,
       availability BOOLEAN NOT NULL,
       price DECIMAL(10, 2) NOT NULL,
       origin_airport_code VARCHAR(3) REFERENCES airport(airport_code),
       destination_airport_code VARCHAR(3) REFERENCES airport(airport_code)
);


INSERT INTO flight
(availability, capacity_plane, flight_airplane, flight_number, flight_price, num_rows, departure_time, arrival_time, flight_duration, origin_airport_id, destination_airport_id)
VALUES
('1001', '2024-10-12 08:00:00', '2024-10-12 12:00:00', 'Airbus A320', 180, 300.50, TRUE, 'MAD', 'LAX'),
('1002', '2024-10-12 14:30:00', '2024-10-12 19:00:00', 'Boeing 737', 200, 450.75, FALSE, 'BCN', 'JFK')
ON CONFLICT (flight_number) DO NOTHING;
