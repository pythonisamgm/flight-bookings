INSERT INTO Airport (airport_code, airport_name, city, country) VALUES
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
('ORD', 'O\'Hare International Airport', 'Chicago', 'EE_UU'),
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
('BRU', 'Brussels Airport', 'Bruselas', 'BELGICA');


INSERT INTO Flight (flight_number, num_rows, departure_time, arrival_time, flight_duration, flight_airplane, capacity_plane, availability, flight_price, origin_airport_code, destination_airport_code)
VALUES
(1001, 36, '2024-10-12 08:00:00', '2024-10-12 12:00:00', 240, 1, 180, true, 300.50, 'MAD', 'LAX'),
(1002, 40, '2024-10-12 09:30:00', '2024-10-12 13:30:00', 240, 2, 200, true, 350.75, 'BCN', 'JFK'),
(1003, 42, '2024-10-12 11:00:00', '2024-10-12 15:30:00', 270, 3, 210, true, 320.00, 'MAD', 'MEX'),
(1004, 50, '2024-10-12 12:00:00', '2024-10-12 14:00:00', 120, 4, 250, true, 200.25, 'LIS', 'LHR'),
(1005, 44, '2024-10-12 13:30:00', '2024-10-12 16:45:00', 195, 5, 220, true, 280.00, 'AMS', 'FRA');




