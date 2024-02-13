package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BackendPackage.DockerImageVersionDto;
import com.example.demo.dto.BackendPackage.DockerVersionInformationDto;
import com.example.demo.service.backendService;


@RestController
@RequestMapping("/v1")
@CrossOrigin
public class BackendController {

	
	@Autowired
	backendService backendService;
	
//	//Getting the version information from the docker hub for downgrading
//	@GetMapping("/provision/version_docker_downgrade_information")
//	public List<DockerVersionInformationDto> getUpdatedDataInformation(@RequestBody DockerImageVersionDto versionDto)
//	{
//		return backendService.getUpdatedVersionInformation(versionDto);
//	}
//	
//	//Getting the version information from the docker hub for upgrading
//	@GetMapping("/provision/version_docker_upgrade_information")
//	public List<DockerVersionInformationDto> getUpdatedDataInform(@RequestBody DockerImageVersionDto versionDto)
//	{
//		return backendService.getUpdatedVersionInform(versionDto);
//	}
	
	//Getting the latest version data in the repository
	@GetMapping("/provision/batch_upgrade/get_latest_version")
	public List<DockerVersionInformationDto> getTwoUpdatedData(@RequestBody DockerImageVersionDto versionDto)
	{
		return backendService.getTwoUpdatedData(versionDto);
	}
	
}
