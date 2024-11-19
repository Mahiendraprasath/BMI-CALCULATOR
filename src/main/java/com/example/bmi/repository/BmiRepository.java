package com.example.bmi.repository;

import com.example.bmi.model.BmiRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BmiRepository extends MongoRepository<BmiRecord, String> {
}


