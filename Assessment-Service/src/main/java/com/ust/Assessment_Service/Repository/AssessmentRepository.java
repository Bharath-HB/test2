package com.ust.Assessment_Service.Repository;

import com.ust.Assessment_Service.Model.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment,String> {
}
