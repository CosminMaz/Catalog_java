-- Script pentru calcularea mediei studenților
CREATE OR REPLACE PROCEDURE calculeaza_medii_studenti (
    p_an_studiu IN NUMBER DEFAULT NULL,
    p_materie_id IN NUMBER DEFAULT NULL
) AS
    CURSOR c_studenti IS
        SELECT s.student_id, s.nume, s.prenume, s.an_studiu
        FROM studenti s
        WHERE p_an_studiu IS NULL OR s.an_studiu = p_an_studiu
        ORDER BY s.nume, s.prenume;
    
    v_medie NUMBER(5,2);
    v_count NUMBER;
    v_materie_nume VARCHAR2(100);
BEGIN
    -- Verificare dacă materia există (dacă a fost specificată)
    IF p_materie_id IS NOT NULL THEN
        SELECT COUNT(*) INTO v_count FROM materii WHERE materie_id = p_materie_id;
        IF v_count = 0 THEN
            RAISE_APPLICATION_ERROR(-20002, 'Materia specificată nu există.');
        END IF;
        
        SELECT nume INTO v_materie_nume FROM materii WHERE materie_id = p_materie_id;
        DBMS_OUTPUT.PUT_LINE('Mediile studentilor la materia: ' || v_materie_nume);
        DBMS_OUTPUT.PUT_LINE('------------------------------------------------');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Mediile generale ale studentilor' || 
                             CASE WHEN p_an_studiu IS NOT NULL THEN ' (anul ' || p_an_studiu || ')' ELSE '' END);
        DBMS_OUTPUT.PUT_LINE('------------------------------------------------');
    END IF;
    
    FOR stud IN c_studenti LOOP
        IF p_materie_id IS NULL THEN
            -- Medie generală
            SELECT AVG(valoare), COUNT(*)
            INTO v_medie, v_count
            FROM note n
            WHERE n.student_id = stud.student_id;
        ELSE
            -- Medie la materie specificată
            SELECT AVG(valoare), COUNT(*)
            INTO v_medie, v_count
            FROM note n
            WHERE n.student_id = stud.student_id AND n.materie_id = p_materie_id;
        END IF;
        
        IF v_count > 0 THEN
            DBMS_OUTPUT.PUT_LINE(
                RPAD(stud.nume || ' ' || stud.prenume || ' (An ' || stud.an_studiu || ')', 40) || 
                ' - Media: ' || TO_CHAR(v_medie, '9.99') || 
                ' (din ' || v_count || ' note)'
            );
        ELSE
            DBMS_OUTPUT.PUT_LINE(
                RPAD(stud.nume || ' ' || stud.prenume || ' (An ' || stud.an_studiu || ')', 40) || 
                ' - Nu are note' || 
                CASE WHEN p_materie_id IS NOT NULL THEN ' la această materie' END
            );
        END IF;
    END LOOP;
    
    DBMS_OUTPUT.PUT_LINE('------------------------------------------------');
    DBMS_OUTPUT.PUT_LINE('Calculul mediilor finalizat.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Eroare la calcularea mediilor: ' || SQLERRM);
END;
/