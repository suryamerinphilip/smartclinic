import { API_BASE_URL } from "../config/config.js";
const DOCTOR_API = API_BASE_URL + '/doctor'

//get all doctors
export async function getDoctors() {
  try {
    const response = await fetch(DOCTOR_API);
    if (response.ok) {
      const data = await response.json();
      return data.doctors || []; // Adjust key based on API response
    }
  } catch (error) {
    console.error("Error fetching doctors:", error);
  }
  return [];
}

//delete doctor by Id
export async function deleteDoctor(id, token) {
  try {
    const response = await fetch(`${DOCTOR_API}/${id}`, {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    });

    const result = await response.json();
    return {
      success: response.ok,
      message: result.message || 'Doctor deleted'
    };
  } catch (error) {
    console.error("Delete doctor error:", error);
    return { success: false, message: 'Something went wrong' };
  }
}
//save doctors
export async function saveDoctor(doctor, token) {
  try {
    const response = await fetch(DOCTOR_API, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(doctor)
    });

    const result = await response.json();
    return {
      success: response.ok,
      message: result.message || 'Doctor saved successfully'
    };
  } catch (error) {
    console.error("Save doctor error:", error);
    return { success: false, message: 'Unable to save doctor' };
  }
}

//filter doctors
export async function filterDoctors(name, time, specialty) {
  try {
    const params = new URLSearchParams();
    if (name) params.append('name', name);
    if (time) params.append('time', time);
    if (specialty) params.append('specialty', specialty);

    const response = await fetch(`${DOCTOR_API}/filter?${params.toString()}`);
    if (response.ok) {
      const data = await response.json();
      return data.doctors || [];
    }
  } catch (error) {
    console.error("Filter doctors error:", error);
  }
  return [];
}