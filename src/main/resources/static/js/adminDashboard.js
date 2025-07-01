import { openModal } from '../components/modals.js';
import { getDoctors, filterDoctors, saveDoctor } from './services/doctorServices.js';
import { createDoctorCard } from './components/doctorCard.js';

//event binding - add doctor button
document.getElementById('addDocBtn').addEventListener('click', () => {
  openModal('addDoctor');
});

//load doctor cards on page load
window.addEventListener('DOMContentLoaded', loadDoctorCards);

async function loadDoctorCards() {
  const contentDiv = document.getElementById("content");
  contentDiv.innerHTML = "";

  const doctors = await getDoctors();
  renderDoctorCards(doctors);
}


//render doctor cards utility
function renderDoctorCards(doctors) {
  const contentDiv = document.getElementById("content");
  contentDiv.innerHTML = "";

  if (doctors.length === 0) {
    contentDiv.innerHTML = "<p>No doctors found</p>";
    return;
  }

  doctors.forEach(doctor => {
    const card = createDoctorCard(doctor);
    contentDiv.appendChild(card);
  });
}
//filter /search logic
document.getElementById("searchBar").addEventListener("input", filterDoctorsOnChange);
document.getElementById("filterTime").addEventListener("change", filterDoctorsOnChange);
document.getElementById("filterSpecialty").addEventListener("change", filterDoctorsOnChange);

async function filterDoctorsOnChange() {
  const name = document.getElementById("searchBar").value;
  const time = document.getElementById("filterTime").value;
  const specialty = document.getElementById("filterSpecialty").value;

  const doctors = await filterDoctors(name, time, specialty);
  renderDoctorCards(doctors);
}


//handle add doctor modal form

document.getElementById('addDoctorForm').addEventListener('submit', async function (e) {
  e.preventDefault();

  const token = localStorage.getItem('token');
  if (!token) {
    alert("Login required to add a doctor.");
    return;
  }

  const doctor = {
    name: document.getElementById('docName').value,
    email: document.getElementById('docEmail').value,
    password: document.getElementById('docPassword').value,
    mobile: document.getElementById('docMobile').value,
    specialty: document.getElementById('docSpecialty').value,
    availability: Array.from(document.querySelectorAll('input[name="availability"]:checked')).map(el => el.value)
  };

  const result = await saveDoctor(doctor, token);

  if (result.success) {
    alert(result.message);
    openModal(null); // Or use closeModal()
    loadDoctorCards(); // Refresh doctor list
  } else {
    alert("Failed to add doctor: " + result.message);
  }
});