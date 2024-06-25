package com.example.demo.controller;


import com.example.demo.dto.DeploymentUpdateInfoDto;
import com.example.demo.dto.UpdateAgentDataSaveDto;
import com.example.demo.dto.UpdateAvailableDataDto;
import com.example.demo.service.UpdateAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@CrossOrigin
@PreAuthorize("hasAnyRole('client_user', 'client_admin')")
public class  UpdateAgentController {
    @Autowired
    UpdateAgentService updateAgentService;

//    Update Agent save the data to SiteDetails
    @PostMapping("/saveDataToSiteDetailsAndCurrentProductVersion")
    public ResponseEntity<Boolean> saveDataToSiteDetailsAndCurrentProductVersion(@RequestBody UpdateAgentDataSaveDto updateAgentDataSaveDto){
         updateAgentService.saveDataToSiteDetailsAndCurrentProductVersion(updateAgentDataSaveDto);
        return new ResponseEntity<>(true,HttpStatus.OK);
    }
//    send  the data to Update Agent
    @GetMapping("/saveDataToSiteDetailsAndCurrentProductVersion/deployment_id={deployment_id}/tenant_name={tenant_name}")
    public ResponseEntity<DeploymentUpdateInfoDto> getTheUpdateAvailable(@PathVariable("deployment_id") String deploymentId, @PathVariable("tenant_name") String tenantName){
        return new ResponseEntity<>(updateAgentService.getTheUpdateAvailable(deploymentId,tenantName),HttpStatus.OK);
    }
//    Update Agent update the task
    @PostMapping("/saveDataToUpdateVersion")
    public ResponseEntity<Boolean> saveDataToUpdateVersion(@RequestBody UpdateAvailableDataDto updateAvailableDataDto){
        return new ResponseEntity<>(updateAgentService.saveDataToUpdateVersion(updateAvailableDataDto),HttpStatus.OK);
    }


}
