package com.hospital.app.service;

import com.hospital.app.model.Doctor;
import com.hospital.app.repo.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DoctorService {
    public List<Doctor> getAllDoctors() {
        DoctorRepository.findAll();
    }

    public Doctor createDoctor(Doctor doctor) {
    }

    public Optional<Object> updateDoctor(Long id, Doctor doctor) {
    }

    public boolean deleteDoctor(Long id) {
        return false;
    }
}
