import { API_BASE_URL } from "../config/config.js";

const PATIENT_API = API_BASE_URL + '/patient';

//patient signup
export async function patientSignup(data) {
  try {
    const response = await fetch(`${PATIENT_API}/signup`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    });

    const result = await response.json();
    return {
      success: response.ok,
      message: result.message || 'Signup successful'
    };
  } catch (error) {
    console.error("Signup error:", error);
    return { success: false, message: 'Signup failed. Please try again.' };
  }
}

//patient login

export async function patientLogin(data) {
  try {
    const response = await fetch(`${PATIENT_API}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    });

    return response; // Caller handles status & token extraction
  } catch (error) {
    console.error("Login error:", error);
    return null;
  }
}
//get logged in patient data
export async function getPatientData(token) {
  try {
    const response = await fetch(`${PATIENT_API}/profile`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    });

    if (response.ok) {
      return await response.json();
    }
  } catch (error) {
    console.error("Patient data error:", error);
  }
  return null;
}

//get appointments
export async function getPatientAppointments(id, token, user) {
  try {
    const url = `${PATIENT_API}/appointments/${user}/${id}`;
    const response = await fetch(url, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    });

    if (response.ok) {
      const data = await response.json();
      return data.appointments || [];
    }
  } catch (error) {
    console.error("Appointment fetch error:", error);
  }
  return null;
}

//filter appointments

export async function filterAppointments(condition, name, token) {
  try {
    const params = new URLSearchParams();
    if (condition) params.append('status', condition);
    if (name) params.append('name', name);

    const response = await fetch(`${PATIENT_API}/appointments/filter?${params.toString()}`, {
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    });

    if (response.ok) {
      const data = await response.json();
      return data.appointments || [];
    }
  } catch (error) {
    console.error("Filter error:", error);
    alert("Something went wrong while filtering appointments.");
  }
  return [];
}