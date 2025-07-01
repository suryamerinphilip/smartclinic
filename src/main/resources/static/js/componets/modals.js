import { openModal } from '../components/modals.js';
import { API_BASE_URL } from '../config/config.js';
const ADMIN_API = API_BASE_URL + '/admin';
const DOCTOR_API = API_BASE_URL + '/doctor/login'
window.onload = function () {
  const adminBtn = document.getElementById('adminLogin');
  const doctorBtn = document.getElementById('doctorLogin');

  if (adminBtn) {
    adminBtn.addEventListener('click', () => {
      openModal('adminLogin');
    });
  }

  if (doctorBtn) {
    doctorBtn.addEventListener('click', () => {
      openModal('doctorLogin');
    });
  }
};
window.doctorLoginHandler = async function () {
  try {
    const email = document.getElementById('doctorEmail')?.value;
    const password = document.getElementById('doctorPassword')?.value;

    if (!email || !password) {
      alert('Please enter both email and password.');
      return;
    }

    const doctor = { email, password };

    const response = await fetch(DOCTOR_API, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(doctor)
    });

    if (response.ok) {
      const data = await response.json();
      localStorage.setItem('token', data.token);
      selectRole('doctor');
    } else {
      alert('Invalid credentials!');
    }
  } catch (error) {
    console.error('Doctor login error:', error);
    alert('An unexpected error occurred.');
  }
};
