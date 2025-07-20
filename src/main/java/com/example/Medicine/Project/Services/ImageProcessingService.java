package com.example.Medicine.Project.Services;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ImageProcessingService {

    private static final String IMAGE_DIR = "images";

    public List<String> processImages(List<String> base64Images) {
        List<String> medicineNames = new ArrayList<>();
        int totalNumber = base64Images.size();
        System.out.println("log2");

        // Create image output directory if it doesn't exist
        File dir = new File(IMAGE_DIR);
        if (!dir.exists()) dir.mkdirs();

        // Save each base64 image
        for (int i = 0; i < totalNumber; i++) {
            String base64 = base64Images.get(i).split(",")[1]; // remove "data:image/png;base64,"
            byte[] imageBytes = Base64.getDecoder().decode(base64);
            try (FileOutputStream fos = new FileOutputStream(IMAGE_DIR + "/image_" + (i + 1) + ".png")) {
                fos.write(imageBytes);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save image " + i, e);
            }
        }

        // Run the Python script after saving images
        try {
            System.out.println("Running Python scripts on multiple images...");

            for (int i = 1; i <= totalNumber; i++) {
                String imagePath = IMAGE_DIR + "/image_" + i + ".png";

                ProcessBuilder processBuilder = new ProcessBuilder(
                        "C:\\Users\\Lenovo\\AppData\\Local\\Programs\\Python\\Python312\\python.exe",
                        "src/main/PythonFiles/pyeasyocr.py",
                        imagePath
                );
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder output = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    output.append(line);
                    System.out.println("Python Output (image " + i + "): " + line);
                }

                medicineNames.add(output.toString());
                int exitCode = process.waitFor();
                System.out.println("Python script exited with code: " + exitCode + " for image_" + i + ".png\n");
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to run Python script", e);
        }

        for (int i = 0; i < medicineNames.size(); i++) {
            System.out.println("Medicine " + (i + 1) + ": " + medicineNames.get(i));
        }

        return medicineNames;
    }

}
