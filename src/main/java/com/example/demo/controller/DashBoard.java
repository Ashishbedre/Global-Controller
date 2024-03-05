package com.example.demo.controller;

import com.example.demo.Entity.UpdateProductVersion;
import com.example.demo.dto.DashBoardCountDto;
import com.example.demo.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/DashBoard")
@CrossOrigin
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
    public  ResponseEntity<List<UpdateProductVersion>> getUpdateProductVerion(){
        return new ResponseEntity<>(dashBoardService.ListOfUpdateProductVersion(),HttpStatus.OK);
    }

}
