package com.example.demo.controller;

import com.example.demo.Entity.UpdateProductVersion;
import com.example.demo.dto.DashBoardCountDto;
import com.example.demo.dto.UpdateAndDowngradeMonitorDto;
import com.example.demo.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/DashBoard")
@CrossOrigin
@PreAuthorize("hasAnyRole('client_user', 'client_admin')")
//@PreAuthorize("hasAnyRole('client_user', 'client_admin')")
public class DashBoard {
    @Autowired
    DashBoardService dashBoardService;

//    get the count of tenent ,ActiveSites, UnprovisionedSites,provisionedSites
    @GetMapping("/count_Tenent_StatusActive_Provision_FalseAndTrue")
    public ResponseEntity<DashBoardCountDto> count_Tenent_StatusActive_Provision_FalseAndTrue (){
        return new ResponseEntity<>(dashBoardService.countTheElementOfSiteLists(), HttpStatus.OK);
    }

//    get the list of update table data
    @GetMapping("/getUpdateProductVerion")
    public  ResponseEntity<List<UpdateAndDowngradeMonitorDto>> getUpdateProductVerion(){
        return new ResponseEntity<>(dashBoardService.ListOfUpdateProductVersion(),HttpStatus.OK);
    }

}
