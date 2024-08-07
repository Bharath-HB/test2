package com.ust.Survery_Service.Client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assessment {
    private String setid;
    private String setname;
    private String createdBy;
    private String domain;
    private String status;
    private String updatedBy;
    private String createdtimestamp;
    private String updatedtimestamp;
    private List<Question> questions;


}
