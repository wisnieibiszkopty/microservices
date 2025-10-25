INSERT INTO accommodations (id, name, description, type, city, address) VALUES
(1, 'Hotel 1', '...', 'HOTEL', 'Lublin', 'Nadbystrzycka 39');
INSERT INTO accommodations (id, name, description, type, city, address) VALUES
(2, 'Hotel Marmur', '...', 'APARTMENT', 'Gdańsk', 'Morska 3');
INSERT INTO accommodations (id, name, description, type, city, address) VALUES
(3, 'Hotel 3', '...', 'MOTEL', 'Lublin', 'Nadbystrzycka 35');

INSERT INTO rooms (id, number, occupied, price, guests_count, area, beds_count, floor, accommodation_id)
VALUES (1, 101, false, 340, 2, 32, 1, 1, 1);
INSERT INTO rooms (id, number, occupied, price, guests_count, area, beds_count, floor, accommodation_id)
VALUES (2, 201, true, 270, 1, 32, 1, 2, 1);
INSERT INTO rooms (id, number, occupied, price, guests_count, area, beds_count, floor, accommodation_id)
VALUES (3, 200, true, 900, 3, 32, 3, 2, 2);
INSERT INTO rooms (id, number, occupied, price, guests_count, area, beds_count, floor, accommodation_id)
VALUES (4, 304, true, 560, 3, 32, 2, 3, 3);
INSERT INTO rooms (id, number, occupied, price, guests_count, area, beds_count, floor, accommodation_id)
VALUES (5, 113, false, 200, 1, 32, 1, 1, 3);
INSERT INTO rooms (id, number, occupied, price, guests_count, area, beds_count, floor, accommodation_id)
VALUES (6, 119, false, 380, 2, 32, 2, 1, 3);