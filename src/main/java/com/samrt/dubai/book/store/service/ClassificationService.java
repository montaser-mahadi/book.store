package com.samrt.dubai.book.store.service;
import com.samrt.dubai.book.store.entity.Classification;
import com.samrt.dubai.book.store.repository.ClassificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassificationService {

    @Autowired
    private ClassificationRepository classificationRepository;

    public ClassificationService(ClassificationRepository classificationRepository) {

        this.classificationRepository = classificationRepository;
    }

    public Classification  saveClassification(Classification classification){

        return classificationRepository.save(classification);
    }

    public List<Classification> getClassification() {
        List<Classification> classificationList = classificationRepository.findAll();
        System.out.println("Getting data from DB : " + classificationList);
        return classificationList;
    }
    public void deleteById(Classification classification)
    {
        classificationRepository.delete(classification);
    }
}
