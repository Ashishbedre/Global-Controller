package com.example.demo.controller;


import com.example.demo.dto.UpdateAgentDataSaveDto;
import com.example.demo.service.UpdateAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@CrossOrigin
public class UpdateAgentController {
    @Autowired
    UpdateAgentService updateAgentService;

    @PostMapping("/saveDataToSiteDetailsAndCurrentProductVersion")
    public ResponseEntity<?> belowVersion(@RequestBody UpdateAgentDataSaveDto updateAgentDataSaveDto){
         updateAgentService.saveDataToSiteDetailsAndCurrentProductVersion(updateAgentDataSaveDto);
        return new ResponseEntity<>( HttpStatus.OK);

    }
}
