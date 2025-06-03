BEGIN
  -- Populare profesori (10)
  FOR i IN 1..10 LOOP
    INSERT INTO profesori (id_profesor, nume, prenume, email, username, password)
    VALUES (
      i,
      'NumeProf' || i,
      'PrenumeProf' || i,
      'prof' || i || '@univ.ro',
      'profuser' || i,
      'pass' || i
    );
  END LOOP;

  -- Populare materii (20) cu credite random intre 3 si 6
  FOR i IN 1..20 LOOP
    INSERT INTO materii (id_materie, nume_materie, id_profesor, credite)
    VALUES (
      i,
      'Materie' || i,
      MOD(i,10) + 1,  -- alocam profesor circular (1-10)
      DBMS_RANDOM.VALUE(3,6)
    );
  END LOOP;

  -- Populare studenti (100)
  FOR i IN 1..100 LOOP
    INSERT INTO studenti (id_student, nume, prenume, grupa, email, username, password)
    VALUES (
      i,
      'NumeStud' || i,
      'PrenumeStud' || i,
      'Grupa' || MOD(i,5)+1,
      'student' || i || '@univ.ro',
      'user' || i,
      'pass' || i
    );
  END LOOP;

  -- Populare note (aprox 500 note)
  FOR i IN 1..500 LOOP
    INSERT INTO note (id_nota, id_student, id_materie, valoare, data_nota)
    VALUES (
      i,
      MOD(i,100) + 1,   -- id_student intre 1 si 100
      MOD(i,20) + 1,    -- id_materie intre 1 si 20
      ROUND(DBMS_RANDOM.VALUE(5,10),1),  -- nota intre 5.0 si 10.0
      ADD_MONTHS(SYSDATE, -MOD(i,12))    -- data in ultimul an, distribuita pe luni
    );
  END LOOP;

  COMMIT;
END;
/
