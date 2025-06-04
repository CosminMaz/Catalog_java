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

INSERT INTO studenti (username, parola) VALUES ('stud1', 'pass1');
INSERT INTO studenti (username, parola) VALUES ('stud2', 'pass2');
INSERT INTO studenti (username, parola) VALUES ('stud3', 'pass3');
INSERT INTO studenti (username, parola) VALUES ('stud4', 'pass4');
INSERT INTO studenti (username, parola) VALUES ('stud5', 'pass5');

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

DECLARE
  v_id_student NUMBER := 1;
  v_count NUMBER := 0;
BEGIN
  FOR n IN (SELECT id_nota FROM nota ORDER BY id_nota) LOOP
    UPDATE nota
    SET id_student = v_id_student
    WHERE id_nota = n.id_nota;

    v_count := v_count + 1;
    IF v_count = 10 THEN
      v_count := 0;
      v_id_student := v_id_student + 1;
    END IF;
  END LOOP;
END;
/

COMMIT;

BEGIN
  FOR n IN (SELECT id_nota FROM nota) LOOP
    UPDATE nota
    SET valoare_nota = ROUND(DBMS_RANDOM.VALUE(5, 10), 1)
    WHERE id_nota = n.id_nota;
  END LOOP;
END;
/

COMMIT;