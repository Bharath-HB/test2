package com.ust.Survery_Service.Repository;

import com.ust.Survery_Service.Model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey,Long> {
}
