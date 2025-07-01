CREATE OR REPLACE PROCEDURE GetDailyAppointmentReportByDoctor (
    report_date IN DATE
)
IS
BEGIN
    FOR rec IN (
        SELECT
            d.name AS doctor_name,
            a.appointment_time,
            a.status,
            p.name AS patient_name,
            p.phone AS patient_phone
        FROM
            appointment a
        JOIN
            doctor d ON a.doctor_id = d.id
        JOIN
            patient p ON a.patient_id = p.id
        WHERE
            TRUNC(a.appointment_time) = report_date
        ORDER BY
            d.name, a.appointment_time
    ) LOOP
        DBMS_OUTPUT.PUT_LINE(
            'Doctor: ' || rec.doctor_name ||
            ', Time: ' || TO_CHAR(rec.appointment_time, 'HH24:MI') ||
            ', Status: ' || rec.status ||
            ', Patient: ' || rec.patient_name ||
            ', Phone: ' || rec.patient_phone
        );
    END LOOP;
END;