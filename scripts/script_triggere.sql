-- Trigger pentru STUDENTI
CREATE OR REPLACE TRIGGER trg_studenti_id
BEFORE INSERT ON studenti
FOR EACH ROW
BEGIN
  SELECT seq_studenti.NEXTVAL INTO :NEW.id_student FROM dual;
END;
/

-- Trigger pentru PROFESORI
CREATE OR REPLACE TRIGGER trg_profesori_id
BEFORE INSERT ON profesori
FOR EACH ROW
BEGIN
  SELECT seq_profesori.NEXTVAL INTO :NEW.id_profesor FROM dual;
END;
/

-- Trigger pentru MATERII
CREATE OR REPLACE TRIGGER trg_materii_id
BEFORE INSERT ON materii
FOR EACH ROW
BEGIN
  SELECT seq_materii.NEXTVAL INTO :NEW.id_materie FROM dual;
END;
/

-- Trigger pentru NOTE
CREATE OR REPLACE TRIGGER trg_note_id
BEFORE INSERT ON note
FOR EACH ROW
BEGIN
  SELECT seq_note.NEXTVAL INTO :NEW.id_nota FROM dual;
END;
/
