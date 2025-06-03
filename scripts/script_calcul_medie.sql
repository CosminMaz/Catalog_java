CREATE OR REPLACE FUNCTION calc_media_ponderata(p_id_student NUMBER) RETURN NUMBER IS
  v_media NUMBER;
BEGIN
  SELECT ROUND(SUM(n.valoare * m.credite) / SUM(m.credite), 2)
  INTO v_media
  FROM note n
  JOIN materii m ON n.id_materie = m.id_materie
  WHERE n.id_student = p_id_student;

  RETURN NVL(v_media, 0);
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RETURN 0;
END;
/
