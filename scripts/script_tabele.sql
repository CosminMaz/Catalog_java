-- Tabelul STUDENTI
CREATE TABLE Studenti (
    id_student NUMBER PRIMARY KEY,
    username VARCHAR2(50),
    parola VARCHAR2(100)
);

COMMIT;

-- Tabelul PROFESORI
CREATE TABLE Profesori (
    id_profesor NUMBER PRIMARY KEY,
    username VARCHAR2(50),
    parola VARCHAR2(100)
);

COMMIT;

-- Tabelul MATERII
CREATE TABLE Materii (
    id_materie NUMBER PRIMARY KEY,
    nume_materie VARCHAR2(100),
    id_profesor NUMBER
);

COMMIT;

-- Tabelul NOTA
CREATE TABLE Nota (
    id_nota NUMBER PRIMARY KEY,
    id_materie NUMBER,
    id_student NUMBER,
    valoare_nota NUMBER(4,2),
    data_nota DATE
);

COMMIT;

-- Verify table creation
BEGIN
    FOR t IN (SELECT table_name 
              FROM user_tables 
              WHERE table_name IN ('STUDENTI', 'PROFESORI', 'MATERII', 'NOTA')) 
    LOOP
        DBMS_OUTPUT.PUT_LINE('Table ' || t.table_name || ' exists');
    END LOOP;
END;
/

EXIT;
