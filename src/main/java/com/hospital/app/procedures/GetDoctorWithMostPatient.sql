CREATE OR REPLACE PROCEDURE GetDoctorWithMostPatientsByYear (
    input_year IN NUMBER,
    o_doctor_id OUT NUMBER,
    o_patients_seen OUT NUMBER
)
IS
BEGIN
    SELECT doctor_id, COUNT(patient_id)
    INTO o_doctor_id, o_patients_seen
    FROM appointment
    WHERE EXTRACT(YEAR FROM appointment_time) = input_year
    GROUP BY doctor_id
    ORDER BY COUNT(patient_id) DESC
    FETCH FIRST 1 ROWS ONLY;
END;
/

