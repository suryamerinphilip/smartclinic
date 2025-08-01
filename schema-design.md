# 📁 Smart Clinic Database Design

## 1. Patients
Stores basic patient information.

| Column Name     | Data Type   | Constraints             |
|------------------|------------|--------------------------|
| patient_id       | INT        | PRIMARY KEY, AUTO_INCREMENT |
| first_name       | VARCHAR(50)| NOT NULL                |
| last_name        | VARCHAR(50)| NOT NULL                |
| dob              | DATE       | NOT NULL                |
| gender           | VARCHAR(10)| CHECK (gender IN ('Male', 'Female', 'Other')) |
| contact_number   | VARCHAR(15)|                         |
| email            | VARCHAR(100)|                        |
| address          | TEXT       |                         |
| registered_date  | TIMESTAMP  | DEFAULT CURRENT_TIMESTAMP |

---

## 2. Appointments
Manages scheduling information.

| Column Name     | Data Type   | Constraints             |
|------------------|------------|--------------------------|
| appointment_id   | INT        | PRIMARY KEY, AUTO_INCREMENT |
| patient_id       | INT        | FOREIGN KEY → Patients(patient_id) |
| doctor_id        | INT        | FOREIGN KEY → Doctors(doctor_id) |
| appointment_date | DATETIME   | NOT NULL                |
| status           | VARCHAR(20)| DEFAULT 'Scheduled'     |
| notes            | TEXT       |                         |

---

## 3. Doctors
Clinician details and availability.

| Column Name     | Data Type   | Constraints             |
|------------------|------------|--------------------------|
| doctor_id        | INT        | PRIMARY KEY, AUTO_INCREMENT |
| full_name        | VARCHAR(100)| NOT NULL               |
| specialization   | VARCHAR(100)|                        |
| email            | VARCHAR(100)|                        |
| contact_number   | VARCHAR(15)|                         |
| availability     | VARCHAR(100)|                         |

---

## 4. Medical Records
Stores patient visit history, diagnosis, and treatment.

| Column Name      | Data Type   | Constraints             |
|------------------|------------|--------------------------|
| record_id        | INT        | PRIMARY KEY, AUTO_INCREMENT |
| patient_id       | INT        | FOREIGN KEY → Patients(patient_id) |
| doctor_id        | INT        | FOREIGN KEY → Doctors(doctor_id) |
| visit_date       | DATETIME   | NOT NULL                |
| diagnosis        | TEXT       |                         |
| treatment        | TEXT       |                         |
| prescription     | TEXT       |                         |

---

## 5. Billing
Tracks payments and invoices.

| Column Name      | Data Type   | Constraints             |
|------------------|------------|--------------------------|
| bill_id          | INT        | PRIMARY KEY, AUTO_INCREMENT |
| patient_id       | INT        | FOREIGN KEY → Patients(patient_id) |
| record_id        | INT        | FOREIGN KEY → Medical_Records(record_id) |
| amount           | DECIMAL(10,2)| NOT NULL              |
| payment_status   | VARCHAR(20)| DEFAULT 'Pending'       |
| billing_date     | TIMESTAMP  | DEFAULT CURRENT_TIMESTAMP |
