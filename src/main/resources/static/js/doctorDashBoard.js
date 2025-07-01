import { getAllAppointments } from './services/appointmentRecordService.js';
import { createPatientRow } from './components/patientRows.js';

const tableBody = document.getElementById('patientTableBody');
let selectedDate = new Date().toISOString().split('T')[0];
let token = localStorage.getItem('token');
let patientName = null;

//search bar
document.getElementById('searchBar').addEventListener('input', () => {
  const input = document.getElementById('searchBar').value.trim();
  patientName = input.length > 0 ? input : "null";
  loadAppointments();
});

document.getElementById('todayButton').addEventListener('click', () => {
  selectedDate = new Date().toISOString().split('T')[0];
  document.getElementById('datePicker').value = selectedDate;
  loadAppointments();
});

document.getElementById('datePicker').addEventListener('change', (e) => {
  selectedDate = e.target.value;
  loadAppointments();
});
//load appointments
async function loadAppointments() {
  try {
    const appointments = await getAllAppointments(selectedDate, patientName, token);

    tableBody.innerHTML = '';

    if (!appointments || appointments.length === 0) {
      const row = document.createElement('tr');
      row.innerHTML = `<td colspan="6">No Appointments found for today</td>`;
      tableBody.appendChild(row);
      return;
    }

    appointments.forEach(app => {
      const row = createPatientRow(app);
      tableBody.appendChild(row);
    });

  } catch (error) {
    console.error("Error loading appointments:", error);
    const errorRow = document.createElement('tr');
    errorRow.innerHTML = `<td colspan="6">Something went wrong while fetching appointments</td>`;
    tableBody.appendChild(errorRow);
  }
}


//initial render
window.addEventListener('DOMContentLoaded', () => {
  if (typeof renderContent === 'function') renderContent();
  loadAppointments();
});