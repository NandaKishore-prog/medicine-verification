package com.example.Medicine.Project.Controller;// ImageController.java
import com.example.Medicine.Project.Model.ImageRequest;
import com.example.Medicine.Project.Services.ImageProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ImageController {

    @Autowired
    private ImageProcessingService imageProcessingService;
    @PostMapping("/CaptureImages")
    public ResponseEntity<String[]> handleImageUpload(@RequestBody ImageRequest request) {
        System.out.println("log1");
        List<String> result = imageProcessingService.processImages(request.getImages());
        return ResponseEntity.ok(result.toArray(new String[0]));
    }

}
