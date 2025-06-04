-- Procedure to add a new student
CREATE OR REPLACE PROCEDURE add_student(
    p_username IN VARCHAR2,
    p_parola IN VARCHAR2
) AS
BEGIN
    INSERT INTO studenti (username, parola)
    VALUES (p_username, p_parola);
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20001, 'Error adding student: ' || SQLERRM);
END;
/

-- Procedure to add a new professor
CREATE OR REPLACE PROCEDURE add_professor(
    p_username IN VARCHAR2,
    p_parola IN VARCHAR2
) AS
BEGIN
    INSERT INTO profesori (username, parola)
    VALUES (p_username, p_parola);
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20002, 'Error adding professor: ' || SQLERRM);
END;
/

-- Procedure to add a new subject
CREATE OR REPLACE PROCEDURE add_subject(
    p_nume_materie IN VARCHAR2,
    p_username_profesor IN VARCHAR2
) AS
    v_id_profesor NUMBER;
BEGIN
    -- Get professor ID
    SELECT id_profesor INTO v_id_profesor
    FROM profesori
    WHERE username = p_username_profesor;
    
    -- Insert new subject
    INSERT INTO materii (nume_materie, id_profesor)
    VALUES (p_nume_materie, v_id_profesor);
    COMMIT;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20003, 'Professor not found: ' || p_username_profesor);
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20004, 'Error adding subject: ' || SQLERRM);
END;
/ 