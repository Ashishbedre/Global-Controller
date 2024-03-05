package com.example.demo.service;

import com.example.demo.Entity.UpdateProductVersion;
import com.example.demo.dto.DashBoardCountDto;

import java.util.List;

public interface DashBoardService {

    DashBoardCountDto countTheElementOfSiteLists();

    List<UpdateProductVersion> ListOfUpdateProductVersion();




}
