package com.example.Survey_Service.Repository;

import com.example.Survey_Service.Model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey,Long> {

}
