package com.example.bmi.controller;




import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


import com.example.bmi.model.BmiRecord;
import com.example.bmi.repository.BmiRepository;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/bmi")
public class BmiController {

    @Autowired
    private BmiRepository bmiRepository;

    @PostMapping("/calcumolate")
    public ResponseEntity<Map<String, String>> calculateAndStoreBMI(@RequestBody Map<String, Object> request) {
        String name = (String) request.getOrDefault("name", "Guest");
        double height = Double.parseDouble(request.getOrDefault("height", 0.0).toString());
        double weight = Double.parseDouble(request.getOrDefault("weight", 0.0).toString());

        if (height <= 0 || weight <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid height or weight"));
        }

        // Calculate BMI and category
        double bmi = weight / (height * height);
        String category;
        if (bmi < 18.5) {
            category = "Underweight";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            category = "Normal weight";
        } else if (bmi >= 25 && bmi < 29.9) {
            category = "Overweight";
        } else {
            category = "Obesity";
        }

        // Create and save BmiRecord
        BmiRecord record = new BmiRecord();
        record.setName(name);
        record.setHeight(height);
        record.setWeight(weight);
        record.setBmi(bmi);
        record.setCategory(category);

        // Save to MongoDB
        BmiRecord savedRecord = bmiRepository.save(record); // Save record to MongoDB
        System.out.println("Saved Record: " + savedRecord); // Log the saved record to confirm

        // Return response
        return ResponseEntity.ok(Map.of(
            "name", name,
            "bmi", String.format("%.2f", bmi),
            "category", category
        ));
    }
}
