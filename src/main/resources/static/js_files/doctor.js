document.getElementById("loginForm").addEventListener("submit", function (event) {
    event.preventDefault(); // prevent default form submission

    const form = event.target;
    const formData = new FormData(form);

    fetch(form.action, {
        method: "POST",
        body: new URLSearchParams(formData), // convert formData to URL-encoded format
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        }
    })
    .then(response => response.text())
    .then(result => {
        if (result.trim().toLowerCase() === "verified") {
            window.location.href = "../Html_files/dotpat.html"; // redirect to your success page
        } else {
            alert("Unauthorized: Please check your credentials.");
        }
    })
    .catch(error => {
        console.error("Login failed:", error);
        alert("An error occurred during login. Please try again.");
    });
});
