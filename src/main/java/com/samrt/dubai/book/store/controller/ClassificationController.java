package com.samrt.dubai.book.store.controller;

import com.samrt.dubai.book.store.entity.Classification;
import com.samrt.dubai.book.store.entity.Customer;
import com.samrt.dubai.book.store.repository.ClassificationRepository;
import com.samrt.dubai.book.store.service.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ClassificationController {

    @Autowired
    private ClassificationService classificationService;

    @Autowired
    ClassificationRepository classificationRepository;

    @GetMapping(path = "classifications")
    public ResponseEntity<List<Classification>> getAllClassification() {
        try {
            List<Classification> classificationList = classificationService.getClassification();
            if (classificationList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(classificationList, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "classification/{id}")
    public ResponseEntity<Classification> getClassificationById(@PathVariable("id") Integer id) {
        Optional<Classification> optionalClassification = classificationRepository.findById(id);
        if (optionalClassification.isPresent()) {
            return new ResponseEntity<>(optionalClassification.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/newClassification")
    public ResponseEntity<?> createClassification(@RequestBody Classification classification) {

        try {
            String massage = "";
            Optional<Classification> isFound = classificationRepository
                    .findByClassificationName(classification.getClassificationName());
            if (isFound.isPresent()) {
                massage = "Classification already exist!";
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .body(massage);
            } else {
                Classification newClassification = classificationRepository
                        .save(new Classification(classification.getClassificationName()));
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(newClassification);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
