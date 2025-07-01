function renderFooter() {
  const footer = document.getElementById("footer");
  if (!footer) return;

  footer.innerHTML = `
    <footer class="footer">
      <div class="footer-logo">
        <img src="../assets/images/logo.png" alt="HealthConnect Logo" />
        <p>© ${new Date().getFullYear()} HealthConnect. All rights reserved.</p>
      </div>

      <div class="footer-columns">
        <div class="footer-column">
          <h4>Company</h4>
          <a href="/about">About</a>
          <a href="/careers">Careers</a>
          <a href="/press">Press</a>
        </div>

        <div class="footer-column">
          <h4>Support</h4>
          <a href="/account">Account</a>
          <a href="/help">Help Center</a>
          <a href="/contact">Contact</a>
        </div>

        <div class="footer-column">
          <h4>Legal</h4>
          <a href="/terms">Terms</a>
          <a href="/privacy">Privacy Policy</a>
          <a href="/licensing">Licensing</a>
        </div>
      </div>
    </footer>
  `;
}

document.addEventListener("DOMContentLoaded", renderFooter);