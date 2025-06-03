SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE raport_evolutie_medie_lunara(p_id_student NUMBER) IS

  CURSOR c_medii_lunare IS
    SELECT 
      TO_CHAR(data_nota, 'YYYY-MM') AS luna,
      ROUND(AVG(valoare), 2) AS media_lunara
    FROM note
    WHERE id_student = p_id_student
    GROUP BY TO_CHAR(data_nota, 'YYYY-MM')
    ORDER BY luna;

BEGIN
  DBMS_OUTPUT.PUT_LINE('Evolutie medie lunara pentru studentul cu ID=' || p_id_student);

  FOR rec IN c_medii_lunare LOOP
    DBMS_OUTPUT.PUT_LINE('Luna: ' || rec.luna || ' - Media: ' || rec.media_lunara);
  END LOOP;

EXCEPTION
  WHEN NO_DATA_FOUND THEN
    DBMS_OUTPUT.PUT_LINE('Nu exista note pentru acest student.');
END;
/
