

document.addEventListener("DOMContentLoaded", renderHeader);

function renderHeader() {
  const headerDiv = document.getElementById("header");
  if (!headerDiv) return;

  // Skip rendering header on homepage
  if (window.location.pathname.endsWith("/")) {
    localStorage.removeItem("userRole");
    localStorage.removeItem("token");
    return;
  }

  const role = localStorage.getItem("userRole");
  const token = localStorage.getItem("token");

  // Handle invalid session states
  if ((role === "loggedPatient" || role === "admin" || role === "doctor") && !token) {
    localStorage.removeItem("userRole");
    alert("Session expired or invalid login. Please log in again.");
    window.location.href = "/";
    return;
  }

  let headerContent = `<div class="nav-container">`;

  // Role-based rendering
  if (role === "admin") {
    headerContent += `
      <button id="addDocBtn" class="adminBtn" onclick="openModal('addDoctor')">Add Doctor</button>
      <a href="#" id="logoutLink">Logout</a>
    `;
  } else if (role === "doctor") {
    headerContent += `
      <a href="/doctor/dashboard">Home</a>
      <a href="#" id="logoutLink">Logout</a>
    `;
  } else if (role === "loggedPatient") {
    headerContent += `
      <a href="/patient/dashboard">Home</a>
      <a href="/patient/appointments">Appointments</a>
      <a href="#" id="logoutLink">Logout</a>
    `;
  } else if (role === "patient") {
    headerContent += `
      <a href="/login">Login</a>
      <a href="/signup">Sign Up</a>
    `;
  }

  headerContent += `</div>`;
  headerDiv.innerHTML = headerContent;

  attachHeaderButtonListeners();
}

// Attach listeners after dynamic DOM insertion
function attachHeaderButtonListeners() {
  const logoutBtn = document.getElementById("logoutLink");
  if (logoutBtn) {
    logoutBtn.addEventListener("click", logout);
  }

  const addDocBtn = document.getElementById("addDocBtn");
  if (addDocBtn) {
    addDocBtn.addEventListener("click", () => openModal("addDoctor"));
  }
}

// Clears session and returns to homepage
function logout() {
  localStorage.removeItem("userRole");
  localStorage.removeItem("token");
  window.location.href = "/";
}

// Optional: Specific patient logout preserving "patient" view
function logoutPatient() {
  localStorage.removeItem("token");
  localStorage.setItem("userRole", "patient");
  window.location.href = "/patient/dashboard";
}