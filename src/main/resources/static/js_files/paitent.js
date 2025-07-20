const userList = [
  { name: "John Doe", phone: "1234567890" },
  { name: "nanda", phone: "12345" }
];

let stream;
let capturedImages = [];

const verifyBtn = document.getElementById('verifyBtn');
const captureBtn = document.getElementById('captureBtn');
const stopBtn = document.getElementById('stopBtn');

verifyBtn.addEventListener('click', verifyUser);
captureBtn.addEventListener('click', capturePhoto);
stopBtn.addEventListener('click', stopCamera);

function verifyUser() {
  const name = document.getElementById('name').value.trim();
  const phone = document.getElementById('phone').value.trim();
  const resultDiv = document.getElementById('result');
  const scannerDiv = document.getElementById('scanner');

  if (!name || !phone) {
    resultDiv.innerHTML = '<p style="color: red;">Please enter both name and phone.</p>';
    return;
  }

  const userExists = userList.some(user => user.name === name && user.phone === phone);

  if (userExists) {
    resultDiv.innerHTML = `<p style="color: green;">Verification successful! You can now take a photo.</p>`;
    scannerDiv.style.display = 'block';
    startCamera();
  } else {
    resultDiv.innerHTML = '<p style="color: red;">User not found. Please check your details.</p>';
    scannerDiv.style.display = 'none';
  }
}

function startCamera() {
  const video = document.getElementById('video');
  navigator.mediaDevices.getUserMedia({ video: true })
    .then(mediaStream => {
      stream = mediaStream;
      video.srcObject = stream;
      video.style.display = 'block';
    })
    .catch(err => {
      console.error("Camera error:", err);
      alert("Failed to access camera.");
    });
}

function capturePhoto() {
  const video = document.getElementById('video');
  const canvas = document.getElementById('canvas');
  const context = canvas.getContext('2d');
  const capturedImagesDiv = document.getElementById('capturedImages');

  context.drawImage(video, 0, 0, canvas.width, canvas.height);
  const imageDataUrl = canvas.toDataURL('image/png');

  const img = document.createElement('img');
  img.src = imageDataUrl;
  capturedImagesDiv.appendChild(img);

  capturedImages.push(imageDataUrl);
}

function stopCamera() {
  if (stream) {
    stream.getTracks().forEach(track => track.stop());
  }
  document.getElementById('video').style.display = 'none';
  processCapturedImages();
}

function processCapturedImages() {
  if (capturedImages.length === 0) {
    alert("No images captured.");
    return;
  }

  capturedImages.forEach((imageDataUrl, index) => {
    fetch('http://localhost:5000/save_image', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        image: imageDataUrl,
        filename: `captured_image_${index}.png`
      })
    })
    .then(res => {
      if (!res.ok) throw new Error("Image save failed");
      return res.json();
    })
    .then(data => {
      console.log(`Image ${index} saved:`, data);
      if (index === capturedImages.length - 1) {
        fetch('http://localhost:5000/detect_tablet', { method: 'POST' })
          .then(res => res.json())
          .then(data => {
            console.log('PUOCR result:', data);
            const container = document.getElementById('capturedImages');
            container.innerHTML = "";

            data.forEach(entry => {
              const div = document.createElement('div');
              div.style.marginBottom = "20px";

              if (!entry.url || entry.url === "nil") {
                const nilText = document.createTextNode('No text was detected.');
                div.appendChild(nilText);
              } else {
                const img = document.createElement('img');
                img.src = "http://localhost:5000/" + entry.url;
                img.alt = "Processed Image";
                img.style.width = "300px";
                img.style.border = "2px solid green";
                img.style.marginTop = "10px";
                div.appendChild(img);
              }

              const detectedTexts = Array.isArray(entry.text) ? entry.text.join(", ") : (entry.text || "No text found");
              const textBlock = document.createElement('p');
              textBlock.textContent = "Detected Text: " + detectedTexts;
              textBlock.style.color = "darkgreen";
              textBlock.style.marginTop = "5px";

              div.appendChild(textBlock);
              container.appendChild(div);
            });
          })
          .catch(err => console.error('PUOCR failed:', err));
      }
    })
    .catch(error => console.error(`Error saving image ${index}:`, error));
  });
}
