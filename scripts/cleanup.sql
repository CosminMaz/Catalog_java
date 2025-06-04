DECLARE
    v_table_exists NUMBER;
    v_sequence_exists NUMBER;
    v_trigger_exists NUMBER;
    v_procedure_exists NUMBER;
BEGIN
    -- Drop tables if they exist
    FOR t IN (SELECT table_name FROM user_tables WHERE table_name IN ('NOTA', 'MATERII', 'STUDENTI', 'PROFESORI')) LOOP
        BEGIN
            EXECUTE IMMEDIATE 'DROP TABLE ' || t.table_name || ' CASCADE CONSTRAINTS';
            DBMS_OUTPUT.PUT_LINE('Dropped table ' || t.table_name);
        EXCEPTION
            WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('Error dropping table ' || t.table_name || ': ' || SQLERRM);
        END;
    END LOOP;

    -- Drop sequences if they exist
    FOR s IN (SELECT sequence_name FROM user_sequences WHERE sequence_name IN ('STUDENTI_SEQ', 'PROFESORI_SEQ', 'NOTE_SEQ', 'MATERII_SEQ')) LOOP
        BEGIN
            EXECUTE IMMEDIATE 'DROP SEQUENCE ' || s.sequence_name;
            DBMS_OUTPUT.PUT_LINE('Dropped sequence ' || s.sequence_name);
        EXCEPTION
            WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('Error dropping sequence ' || s.sequence_name || ': ' || SQLERRM);
        END;
    END LOOP;

    -- Drop procedures if they exist
    FOR p IN (SELECT object_name FROM user_procedures WHERE object_type = 'PROCEDURE' AND object_name IN ('ADD_STUDENT', 'ADD_PROFESSOR', 'ADD_SUBJECT')) LOOP
        BEGIN
            EXECUTE IMMEDIATE 'DROP PROCEDURE ' || p.object_name;
            DBMS_OUTPUT.PUT_LINE('Dropped procedure ' || p.object_name);
        EXCEPTION
            WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('Error dropping procedure ' || p.object_name || ': ' || SQLERRM);
        END;
    END LOOP;

    -- Drop triggers if they exist
    FOR tr IN (SELECT trigger_name FROM user_triggers WHERE trigger_name IN ('STUDENTI_BI', 'PROFESORI_BI', 'NOTE_BI', 'MATERII_BI')) LOOP
        BEGIN
            EXECUTE IMMEDIATE 'DROP TRIGGER ' || tr.trigger_name;
            DBMS_OUTPUT.PUT_LINE('Dropped trigger ' || tr.trigger_name);
        EXCEPTION
            WHEN OTHERS THEN
                DBMS_OUTPUT.PUT_LINE('Error dropping trigger ' || tr.trigger_name || ': ' || SQLERRM);
        END;
    END LOOP;

    -- Verify cleanup
    SELECT COUNT(*) INTO v_table_exists
    FROM user_tables 
    WHERE table_name IN ('NOTA', 'MATERII', 'STUDENTI', 'PROFESORI');
    
    SELECT COUNT(*) INTO v_sequence_exists
    FROM user_sequences 
    WHERE sequence_name IN ('STUDENTI_SEQ', 'PROFESORI_SEQ', 'NOTE_SEQ', 'MATERII_SEQ');
    
    SELECT COUNT(*) INTO v_trigger_exists
    FROM user_triggers 
    WHERE trigger_name IN ('STUDENTI_BI', 'PROFESORI_BI', 'NOTE_BI', 'MATERII_BI');
    
    SELECT COUNT(*) INTO v_procedure_exists
    FROM user_procedures 
    WHERE object_type = 'PROCEDURE' 
    AND object_name IN ('ADD_STUDENT', 'ADD_PROFESSOR', 'ADD_SUBJECT');
    
    DBMS_OUTPUT.PUT_LINE('Cleanup verification:');
    DBMS_OUTPUT.PUT_LINE('Tables remaining: ' || v_table_exists);
    DBMS_OUTPUT.PUT_LINE('Sequences remaining: ' || v_sequence_exists);
    DBMS_OUTPUT.PUT_LINE('Triggers remaining: ' || v_trigger_exists);
    DBMS_OUTPUT.PUT_LINE('Procedures remaining: ' || v_procedure_exists);

    COMMIT;
END;
/

EXIT; 