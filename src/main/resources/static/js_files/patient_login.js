document.getElementById('verifyBtn').addEventListener('click', verifyUser);
function verifyUser() {
  const name = document.getElementById('name').value.trim();
  const phone = document.getElementById('phone').value.trim();
  const resultDiv = document.getElementById('result');

  if (!name || !phone) {
    resultDiv.innerHTML = '<p style="color: red;">Please enter both name and phone number.</p>';
    return;
  }

  fetch('http://localhost:8080/PatientLogin', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ username: name, password: phone }) // ✅ fixed keys
  })
  .then(response => response.text())
  .then(data => {
    if (data === 'verified') {
      window.location.href = '../';  // redirect on success
    } else {
      alert('Unauthorized username or password');
    }
  })
  .catch(error => {
    console.error('Error during verification:', error);
    alert('Something went wrong. Please try again.');
  });
}
