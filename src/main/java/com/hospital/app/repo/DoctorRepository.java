package com.hospital.app.repo;

import com.hospital.app.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // Find doctor by exact email
    Doctor findByEmail(String email);

    // Find doctors by partial name match using LIKE and CONCAT
    @Query("SELECT d FROM Doctor d WHERE d.name LIKE CONCAT('%', :name, '%')")
    List<Doctor> findByNameLike(String name);

    // Case-insensitive partial name match and exact specialty match
    @Query("SELECT d FROM Doctor d " +
            "WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "AND LOWER(d.specialty) = LOWER(:specialty)")
    List<Doctor> findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(String name, String specialty);

    // Case-insensitive specialty match
    List<Doctor> findBySpecialtyIgnoreCase(String specialty);

}
