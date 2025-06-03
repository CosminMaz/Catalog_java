-- STUDENTI
CREATE TABLE studenti (
  id_student NUMBER PRIMARY KEY,
  nume VARCHAR2(100) NOT NULL,
  prenume VARCHAR2(100) NOT NULL,
  grupa VARCHAR2(10),
  email VARCHAR2(100),
  username VARCHAR2(50) UNIQUE NOT NULL,
  password VARCHAR2(100) NOT NULL
);

-- PROFESORI
CREATE TABLE profesori (
  id_profesor NUMBER PRIMARY KEY,
  nume VARCHAR2(100) NOT NULL,
  prenume VARCHAR2(100) NOT NULL,
  email VARCHAR2(100),
  username VARCHAR2(50) UNIQUE NOT NULL,
  password VARCHAR2(100) NOT NULL
);

-- MATERII
CREATE TABLE materii (
  id_materie NUMBER PRIMARY KEY,
  nume_materie VARCHAR2(100) NOT NULL,
  id_profesor NUMBER NOT NULL,
  CONSTRAINT fk_materie_profesor FOREIGN KEY (id_profesor) REFERENCES profesori(id_profesor)
);

-- NOTE
CREATE TABLE note (
  id_nota NUMBER PRIMARY KEY,
  id_student NUMBER NOT NULL,
  id_materie NUMBER NOT NULL,
  valoare NUMBER(3,1) CHECK (valoare BETWEEN 1 AND 10),
  data_nota DATE DEFAULT SYSDATE,
  CONSTRAINT fk_nota_student FOREIGN KEY (id_student) REFERENCES studenti(id_student),
  CONSTRAINT fk_nota_materie FOREIGN KEY (id_materie) REFERENCES materii(id_materie)
);
