package com.hospital.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.Data;
import org.springframework.cglib.core.Local;


import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name ="patient_id")

    private Patient patient;
    @Future(message = "Appointment time must be in the future")
    private LocalDateTime appointmentTime;
    private int status;


    public LocalDate getAppointmentDate() {
        return appointmentTime.toLocalDate();
    }
    public LocalTime getAppointmentTimeOnly(){
        return appointmentTime.toLocalTime();
    }
    public LocalTime getEndTime(){
        return appointmentTime.toLocalTime().plusHours(1);
    }
}
