package com.samrt.dubai.book.store.repository;

import com.samrt.dubai.book.store.entity.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification, Integer> {

    Optional <Classification> findByClassificationName(String classificationName);

}
