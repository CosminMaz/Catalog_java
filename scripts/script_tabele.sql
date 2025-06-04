-- Tabelul STUDENTI
CREATE TABLE Studenti (
    id_student NUMBER PRIMARY KEY,
    username VARCHAR2(50),
    parola VARCHAR2(100)
);

-- Tabelul PROFESORI
CREATE TABLE Profesori (
    id_profesor NUMBER PRIMARY KEY,
    username VARCHAR2(50),
    parola VARCHAR2(100)
);

-- Tabelul MATERII
CREATE TABLE Materii (
    id_materie NUMBER PRIMARY KEY,
    nume_materie VARCHAR2(100),
    id_profesor NUMBER
);

-- Tabelul NOTA
CREATE TABLE Nota (
    id_nota NUMBER PRIMARY KEY,
    id_materie NUMBER,
    data_nota DATE,
    id_student NUMBER,
    valoare_nota NUMBER(3,1)
);
