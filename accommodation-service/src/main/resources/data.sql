INSERT INTO accommodations (id, name, description, type, city, address)
SELECT *
FROM (
    VALUES
        (1, 'Hotel 1', '...', 'HOTEL', 'Lublin', 'Nadbystrzycka 39'),
        (2, 'Hotel Marmur', '...', 'APARTMENT', 'Gdańsk', 'Morska 3'),
        (3, 'Hotel 3', '...', 'MOTEL', 'Lublin', 'Nadbystrzycka 35')
    ) AS v(id, name, description, type, city, address)
WHERE NOT EXISTS (SELECT 1 FROM accommodations);

INSERT INTO rooms (id, number, occupied, price, guests_count, area, beds_count, floor, accommodation_id)
SELECT *
FROM (
    VALUES
        (1, 101, false, 340, 2, 32, 1, 1, 1),
        (2, 201, true, 270, 1, 32, 1, 2, 1),
        (3, 200, true, 900, 3, 32, 3, 2, 2),
        (4, 304, true, 560, 3, 32, 2, 3, 3),
        (5, 113, false, 200, 1, 32, 1, 1, 3),
        (6, 119, false, 380, 2, 32, 2, 1, 3)
    ) AS v(id, number, occupied, price, guests_count, area, beds_count, floor, accommodation_id)
WHERE NOT EXISTS (SELECT 1 FROM rooms);
