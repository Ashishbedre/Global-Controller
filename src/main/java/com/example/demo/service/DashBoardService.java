package com.example.demo.service;

import com.example.demo.Entity.UpdateProductVersion;
import com.example.demo.dto.DashBoardCountDto;
import com.example.demo.dto.UpdateAndDowngradeMonitorDto;

import java.util.List;

public interface DashBoardService {

    DashBoardCountDto countTheElementOfSiteLists();

    List<UpdateAndDowngradeMonitorDto> ListOfUpdateProductVersion();




}
