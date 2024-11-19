package com.example.bmi.service;



import com.example.bmi.model.BmiRecord;
import com.example.bmi.repository.BmiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BMIService {

    @Autowired
    private BmiRepository bmiRepository;

    public BmiRecord calculateAndSaveBMI(String name, double height, double weight) {
        double bmiValue = weight / (height * height);
        String category;

        if (bmiValue < 18.5) {
            category = "Underweight";
        } else if (bmiValue < 24.9) {
            category = "Normal weight";
        } else if (bmiValue < 29.9) {
            category = "Overweight";
        } else {
            category = "Obesity";
        }

        BmiRecord bmi = new BmiRecord();
        bmi.setName(name);
        bmi.setHeight(height);
        bmi.setWeight(weight);
        bmi.setBmi(bmiValue);
        bmi.setCategory(category);

        return bmiRepository.save(bmi);
    }
}

