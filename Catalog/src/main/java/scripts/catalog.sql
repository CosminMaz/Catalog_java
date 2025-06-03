-- === PROFESORI ===
INSERT INTO profesori (nume, prenume, email) VALUES ('Ionescu', 'Mihai', 'mihai.ionescu@facultate.ro');
INSERT INTO profesori (nume, prenume, email) VALUES ('Popa', 'Elena', 'elena.popa@facultate.ro');
INSERT INTO profesori (nume, prenume, email) VALUES ('Vasilescu', 'Dan', 'dan.vasilescu@facultate.ro');

-- === MATERII ===
INSERT INTO materii (nume_materie, id_profesor) VALUES ('Programare Java', 1);
INSERT INTO materii (nume_materie, id_profesor) VALUES ('Baze de date', 2);
INSERT INTO materii (nume_materie, id_profesor) VALUES ('Algoritmi', 3);

-- === STUDENTI ===
INSERT INTO studenti (nume, prenume, grupa, email) VALUES ('Georgescu', 'Andrei', '302A', 'andrei.georgescu@student.ro');
INSERT INTO studenti (nume, prenume, grupa, email) VALUES ('Marin', 'Ioana', '302A', 'ioana.marin@student.ro');
INSERT INTO studenti (nume, prenume, grupa, email) VALUES ('Dumitrescu', 'Paul', '302B', 'paul.dumitrescu@student.ro');

-- === NOTE ===
INSERT INTO note (id_student, id_materie, valoare) VALUES (1, 1, 9.5); -- Andrei - Java
INSERT INTO note (id_student, id_materie, valoare) VALUES (1, 2, 8.0); -- Andrei - BD
INSERT INTO note (id_student, id_materie, valoare) VALUES (2, 2, 10.0); -- Ioana - BD
INSERT INTO note (id_student, id_materie, valoare) VALUES (2, 3, 9.0); -- Ioana - Algoritmi
INSERT INTO note (id_student, id_materie, valoare) VALUES (3, 1, 7.5); -- Paul - Java
