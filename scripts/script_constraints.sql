ALTER TABLE Materii
ADD CONSTRAINT fk_materii_profesor
FOREIGN KEY (id_profesor) REFERENCES Profesori(id_profesor);

ALTER TABLE Nota
ADD CONSTRAINT fk_nota_materie
FOREIGN KEY (id_materie) REFERENCES Materii(id_materie);

ALTER TABLE Nota
ADD CONSTRAINT fk_nota_student
FOREIGN KEY (id_student) REFERENCES Studenti(id_student);

COMMIT;

EXIT; 