import { createDoctorCard } from './components/doctorCard.js';
import { openModal } from './components/modals.js';
import { getDoctors, filterDoctors } from './services/doctorServices.js';
import { patientLogin, patientSignup } from './services/patientServices.js';



document.addEventListener("DOMContentLoaded", () => {
  loadDoctorCards();
});

//load doctor cards on page load
async function loadDoctorCards() {
  const contentDiv = document.getElementById("content");
  contentDiv.innerHTML = "";

  const doctors = await getDoctors();
  renderDoctorCards(doctors);
}

//render utility

function renderDoctorCards(doctors) {
  const contentDiv = document.getElementById("content");
  contentDiv.innerHTML = "";

  if (!doctors || doctors.length === 0) {
    contentDiv.innerHTML = "<p>No doctors found</p>";
    return;
  }

  doctors.forEach(doc => {
    const card = createDoctorCard(doc);
    contentDiv.appendChild(card);
  });
}

//bind modal triggers
document.addEventListener("DOMContentLoaded", () => {
  const signupBtn = document.getElementById("patientSignup");
  if (signupBtn) signupBtn.addEventListener("click", () => openModal("patientSignup"));

  const loginBtn = document.getElementById("patientLogin");
  if (loginBtn) loginBtn.addEventListener("click", () => openModal("patientLogin"));
});


//search and filter logic
document.getElementById("searchBar").addEventListener("input", filterDoctorsOnChange);
document.getElementById("filterTime").addEventListener("change", filterDoctorsOnChange);
document.getElementById("filterSpecialty").addEventListener("change", filterDoctorsOnChange);

async function filterDoctorsOnChange() {
  const name = document.getElementById("searchBar").value;
  const time = document.getElementById("filterTime").value;
  const specialty = document.getElementById("filterSpecialty").value;

  const doctors = await filterDoctors(name, time, specialty);

  const contentDiv = document.getElementById("content");
  contentDiv.innerHTML = "";

  if (doctors.length > 0) {
    renderDoctorCards(doctors);
  } else {
    contentDiv.innerHTML = "<p>No doctors found with the given filters.</p>";
  }
}


//handle patient signup
window.signupPatient = async function () {
  const name = document.getElementById("signupName").value;
  const email = document.getElementById("signupEmail").value;
  const password = document.getElementById("signupPassword").value;
  const phone = document.getElementById("signupPhone").value;
  const address = document.getElementById("signupAddress").value;

  const patientData = { name, email, password, phone, address };

  const result = await patientSignup(patientData);

  if (result.success) {
    alert(result.message);
    openModal(null); // Or closeModal()
    location.reload();
  } else {
    alert("Signup failed: " + result.message);
  }
};


/// handle patient login

window.loginPatient = async function () {
  const email = document.getElementById("loginEmail").value;
  const password = document.getElementById("loginPassword").value;

  const loginData = { email, password };

  const response = await patientLogin(loginData);

  if (response && response.ok) {
    const data = await response.json();
    localStorage.setItem('token', data.token);
    window.location.href = 'loggedPatientDashboard.html';
  } else {
    alert("Login failed: Invalid credentials.");
  }
};