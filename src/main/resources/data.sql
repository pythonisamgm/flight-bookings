-- Inserción de aeropuertos
INSERT INTO airport (airport_code, airport_name, city, country) VALUES
('MAD', 'Madrid-Barajas Adolfo Suárez', 'Madrid', 'España'),
('BCN', 'Barcelona-El Prat', 'Barcelona', 'España'),
('VLC', 'Valencia', 'Valencia', 'España'),
('AGP', 'Málaga-Costa del Sol', 'Málaga', 'España'),
('ALC', 'Alicante-Elche', 'Alicante', 'España'),
('SVQ', 'Sevilla', 'Sevilla', 'España'),
('BIO', 'Bilbao', 'Bilbao', 'España'),
('PMI', 'Palma de Mallorca', 'Palma de Mallorca', 'España'),
('LPA', 'Gran Canaria', 'Las Palmas', 'España'),
('TFS', 'Tenerife Sur', 'Tenerife', 'España'),
('LAX', 'Los Angeles International Airport', 'Los Ángeles', 'EE.UU.'),
('JFK', 'John F. Kennedy International Airport', 'Nueva York', 'EE.UU.'),
('ORD', 'O\'Hare International Airport', 'Chicago', 'EE.UU.'),
('ATL', 'Hartsfield-Jackson Atlanta International Airport', 'Atlanta', 'EE.UU.'),
('DFW', 'Dallas/Fort Worth International Airport', 'Dallas', 'EE.UU.'),
('YYZ', 'Toronto Pearson International Airport', 'Toronto', 'Canadá'),
('GRU', 'São Paulo/Guarulhos International Airport', 'São Paulo', 'Brasil'),
('MEX', 'Mexico City International Airport', 'Ciudad de México', 'México'),
('BOG', 'Bogotá El Dorado International Airport', 'Bogotá', 'Colombia'),
('CUN', 'Cancún International Airport', 'Cancún', 'México'),
('LHR', 'London Heathrow Airport', 'Londres', 'Reino Unido'),
('AMS', 'Amsterdam Airport Schiphol', 'Ámsterdam', 'Países Bajos'),
('CDG', 'Charles de Gaulle Airport', 'París', 'Francia'),
('FRA', 'Frankfurt Airport', 'Fráncfort', 'Alemania'),
('MUC', 'Munich Airport', 'Múnich', 'Alemania'),
('ZRH', 'Zurich Airport', 'Zúrich', 'Suiza'),
('CPH', 'Copenhagen Airport', 'Copenhague', 'Dinamarca'),
('VIE', 'Vienna International Airport', 'Viena', 'Austria'),
('LIS', 'Lisbon Portela Airport', 'Lisboa', 'Portugal'),
('BRU', 'Brussels Airport', 'Bruselas', 'Bélgica');

-- Inserción de vuelos
INSERT INTO flight
(availability, capacity_plane, flight_airplane, flight_number, flight_price, num_rows, departure_time, arrival_time, flight_duration, origin_airport_id, destination_airport_id)
VALUES
(1, 180, 1, 1001, 300.50, 36, '2024-10-12 08:00:00', '2024-10-12 12:00:00', 4, 'MAD', 'LAX'),
(1, 200, 2, 1002, 450.75, 40, '2024-10-12 14:30:00', '2024-10-12 19:00:00', 4.5, 'BCN', 'JFK'),
(1, 150, 3, 1003, 200.00, 30, '2024-10-12 09:00:00', '2024-10-12 11:30:00', 2.5, 'AGP', 'LHR'),
(1, 220, 1, 1004, 350.25, 44, '2024-10-13 10:45:00', '2024-10-13 13:45:00', 3, 'BIO', 'AMS'),
(1, 250, 2, 1005, 320.00, 50, '2024-10-13 18:00:00', '2024-10-13 21:30:00', 3.5, 'PMI', 'CDG'),
(1, 300, 3, 1006, 400.50, 60, '2024-10-13 11:00:00', '2024-10-13 14:30:00', 3.5, 'MAD', 'FRA'),
(1, 170, 1, 1007, 250.75, 34, '2024-10-14 07:30:00', '2024-10-14 10:30:00', 3, 'SVQ', 'ZRH'),
(1, 300, 2, 1008, 480.00, 60, '2024-10-14 16:15:00', '2024-10-14 20:15:00', 4, 'BCN', 'ATL');


