package com.example.demo.controller;

import com.example.demo.dto.DashBoardCountDto;
import com.example.demo.Entity.Notifications;
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

    @GetMapping("/count_Tenent_StatusActive_Provision_FalseAndTrue")
    public ResponseEntity<DashBoardCountDto> count_Tenent_StatusActive_Provision_FalseAndTrue (){
        return new ResponseEntity<>(dashBoardService.countTheElementOfSiteLists(), HttpStatus.OK);
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<Notifications>> notifications(){
        return  new ResponseEntity<>(dashBoardService.getAllNotifications(),HttpStatus.OK);
    }

    @PutMapping("/updateNotifications")
    public ResponseEntity<Boolean> updateNotifications(@RequestBody List<Notifications> updateNotifications){
        return  new ResponseEntity<>(dashBoardService.updateNotifications(updateNotifications),HttpStatus.OK);
    }

}
