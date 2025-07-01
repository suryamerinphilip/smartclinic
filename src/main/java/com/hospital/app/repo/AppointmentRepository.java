package com.hospital.app.repo;

import com.hospital.app.model.Appointment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository  extends JpaRepository<Appointment,Long> {
    // Appointments for a doctor within a time range
    @Query("SELECT a FROM Appointment a LEFT JOIN FETCH a.doctor d LEFT JOIN FETCH d.availability av " +
            "WHERE d.id = :doctorId AND a.appointmentTime BETWEEN :start AND :end")
    List<Appointment> findByDoctorIdAndAppointmentTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    // Filter by doctor ID, partial patient name, and time range
    @Query("SELECT a FROM Appointment a LEFT JOIN FETCH a.patient p LEFT JOIN FETCH a.doctor d " +
            "WHERE d.id = :doctorId AND LOWER(p.name) LIKE LOWER(CONCAT('%', :patientName, '%')) " +
            "AND a.appointmentTime BETWEEN :start AND :end")
    List<Appointment> findByDoctorIdAndPatient_NameContainingIgnoreCaseAndAppointmentTimeBetween(
            Long doctorId, String patientName, LocalDateTime start, LocalDateTime end);

    // Delete all appointments for a doctor
    @Modifying
    @Transactional
    void deleteAllByDoctorId(Long doctorId);

    // Find appointments by patient ID
    List<Appointment> findByPatientId(Long patientId);

    // Find appointments by patient ID and status, ordered by appointment time
    List<Appointment> findByPatient_IdAndStatusOrderByAppointmentTimeAsc(Long patientId, int status);

    // Filter by partial doctor name and patient ID
    @Query("SELECT a FROM Appointment a JOIN a.doctor d " +
            "WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :doctorName, '%')) " +
            "AND a.patient.id = :patientId")
    List<Appointment> filterByDoctorNameAndPatientId(String doctorName, Long patientId);

    // Filter by doctor name, patient ID, and status
    @Query("SELECT a FROM Appointment a JOIN a.doctor d " +
            "WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :doctorName, '%')) " +
            "AND a.patient.id = :patientId AND a.status = :status")
    List<Appointment> filterByDoctorNameAndPatientIdAndStatus(String doctorName, Long patientId, int status);
}


