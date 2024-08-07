package com.ust.Survery_Service.Client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assessment {
    private String setid;
    private String setname;
    private String createdby;
    private String domain;
    private String status;
    private String updatedby;
    private String createdtimestamp;
    private String updatedtimestamp;

}
