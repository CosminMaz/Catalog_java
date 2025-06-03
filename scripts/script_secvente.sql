-- Drop existing sequences if they exist
DROP SEQUENCE seq_studenti;
DROP SEQUENCE seq_profesori;
DROP SEQUENCE seq_materii;
DROP SEQUENCE seq_note;

-- Create sequences
CREATE SEQUENCE seq_studenti START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_profesori START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_materii START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_note START WITH 1 INCREMENT BY 1;
