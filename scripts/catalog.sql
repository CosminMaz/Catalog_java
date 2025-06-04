INSERT INTO profesori (username, parola) VALUES ('prof1', 'parola1');
INSERT INTO profesori (username, parola) VALUES ('prof2', 'parola2');
INSERT INTO profesori (username, parola) VALUES ('prof3', 'parola3');
INSERT INTO profesori (username, parola) VALUES ('prof4', 'parola4');
INSERT INTO profesori (username, parola) VALUES ('prof5', 'parola5');
INSERT INTO profesori (username, parola) VALUES ('prof6', 'parola6');
INSERT INTO profesori (username, parola) VALUES ('prof7', 'parola7');
INSERT INTO profesori (username, parola) VALUES ('prof8', 'parola8');
INSERT INTO profesori (username, parola) VALUES ('prof9', 'parola9');
INSERT INTO profesori (username, parola) VALUES ('prof10', 'parola10');

COMMIT;

INSERT INTO studenti (username, parola) VALUES ('stud1', 'pass1');
INSERT INTO studenti (username, parola) VALUES ('stud2', 'pass2');
INSERT INTO studenti (username, parola) VALUES ('stud3', 'pass3');
INSERT INTO studenti (username, parola) VALUES ('stud4', 'pass4');
INSERT INTO studenti (username, parola) VALUES ('stud5', 'pass5');

COMMIT;

INSERT INTO materii (nume_materie, id_profesor) VALUES ('Matematica', 1);
INSERT INTO materii (nume_materie, id_profesor) VALUES ('Informatica', 2);
INSERT INTO materii (nume_materie, id_profesor) VALUES ('Fizica', 3);
INSERT INTO materii (nume_materie, id_profesor) VALUES ('Chimie', 4);
INSERT INTO materii (nume_materie, id_profesor) VALUES ('Biologie', 5);
INSERT INTO materii (nume_materie, id_profesor) VALUES ('Istorie', 6);
INSERT INTO materii (nume_materie, id_profesor) VALUES ('Geografie', 7);
INSERT INTO materii (nume_materie, id_profesor) VALUES ('Engleza', 8);
INSERT INTO materii (nume_materie, id_profesor) VALUES ('Romana', 9);
INSERT INTO materii (nume_materie, id_profesor) VALUES ('Sport', 10);

COMMIT;

DECLARE
    v_student_id NUMBER;
    v_materie_id NUMBER;
BEGIN
    -- For each student
    FOR student IN (SELECT id_student FROM studenti) LOOP
        -- For each subject
        FOR materie IN (SELECT id_materie FROM materii) LOOP
            -- Insert a grade
            INSERT INTO nota (id_materie, id_student, valoare_nota, data_nota)
            VALUES (
                materie.id_materie,
                student.id_student,
                ROUND(DBMS_RANDOM.VALUE(5, 10), 2),
                SYSDATE - ROUND(DBMS_RANDOM.VALUE(0, 180)) -- Random date in the last 6 months
            );
        END LOOP;
    END LOOP;
END;
/

COMMIT;

EXIT;