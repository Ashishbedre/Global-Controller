//package com.example.demo.service.serviceimpl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import com.example.demo.dto.AddSiteListDto;
//import com.example.demo.dto.AddressDto;
//import com.example.demo.dto.DeploymentIdDto;
//import com.example.demo.dto.PersonDto;
//import com.example.demo.dto.ProvisionDtoUpdate;
//import com.example.demo.dto.ProvisionSiteDto;
//import com.example.demo.dto.ProvisionSiteSSDto;
//import com.example.demo.dto.SiteListDto;
//import com.example.demo.dto.VersionProductDto;
//import com.example.demo.dto.VersionSetProductDto;
//import com.example.demo.dto.VersionUpdateControlDto;
//import com.example.demo.dto.VersionUpgradeDto;
//import com.example.demo.dto.BackendPackage.DockerVersionInformationDto;
//import com.example.demo.dto.BackendPackage.VersionInformation;
//import com.example.demo.model.AddressModel;
//import com.example.demo.model.AgentModel;
//import com.example.demo.model.PersonModel;
//import com.example.demo.model.VersionControlDataModel;
//import com.example.demo.model.VersionModel;
//import com.example.demo.repository.AddressRepositoryOver;
//import com.example.demo.repository.AgentRepository;
//import com.example.demo.repository.PersonRepositoryOver;
//import com.example.demo.repository.UpdatedVersionRepository;
//import com.example.demo.repository.VersionRepository;
//import com.example.demo.repository.VersionSiteRepository;
//import com.example.demo.service.frontendService;
//
//@Service
//public class frontendImpl implements frontendService{
//
//
//	@Autowired
//	VersionRepository versionRepository;
//
//	@Autowired
//	UpdatedVersionRepository updatedVersionRepository;
//
//	@Autowired
//	AgentRepository agentRepository;
//
//	@Autowired
//	AddressRepositoryOver addressRepository;
//
//	@Autowired
//	PersonRepositoryOver personRepository;
//
//	@Autowired
//	VersionSiteRepository versionSiteRepository;
//
//
//	//Getting the List of Frontend data
//	@Override
//	public VersionUpdateControlDto getListOfVersion(String deploymentId) {
//
//		VersionUpdateControlDto mmm=new VersionUpdateControlDto();
//
//        List<VersionUpgradeDto> kkk=new ArrayList<>();
//
//        List<com.example.demo.dto.BackendPackage.VersionControlDto> temp=new ArrayList<>();
//
//        List<VersionModel> model1=versionRepository.getListOfVersion(deploymentId);
//
//        for (VersionModel versionModel : model1) {
//
//        	com.example.demo.dto.BackendPackage.VersionControlDto dd=new com.example.demo.dto.BackendPackage.VersionControlDto();
//
//        	dd.setRepo(versionModel.getProductName());
//        	dd.setTag(versionModel.getProductVersion());
//
//        	temp.add(dd);
//		}
//
//		mmm.setDeploymentId(deploymentId);
//
//		WebClient webForStoringDatabse = WebClient.builder()
//                								  .baseUrl("http://localhost:8081")
//                								  .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                								  .build();
//
//		List<DockerVersionInformationDto> list = webForStoringDatabse.post()
//                													.uri("v1/globalSDN/SiteManagement/getUpgradeVersion")
//                													.contentType(MediaType.APPLICATION_JSON)
//                													.bodyValue(temp)
//                													.retrieve()
//                													.bodyToMono(new ParameterizedTypeReference<List<DockerVersionInformationDto>>() {})
//                													.block();
//
//		for (DockerVersionInformationDto dockerVersionInformationDto : list) {
//
//              VersionUpgradeDto model=new VersionUpgradeDto();
//
//			  model.setProduct_name(dockerVersionInformationDto.getProduct());
//			  model.setProduct_current_version(versionRepository.getVersionProvision(deploymentId, dockerVersionInformationDto.getProduct()));
//
//
//			  List<VersionInformation> ttt=dockerVersionInformationDto.getVersions();
//
//			  List<VersionInformation> ppp=new ArrayList<>();
//
//				for (VersionInformation versionInformation : ttt) {
//
//					 VersionInformation qqq=new VersionInformation();
//
//					 qqq.setVersion(versionInformation.getVersion());
//
//					 ppp.add(qqq);
//				}
//
//				model.setProduct_upgrade_available_version(ppp);
//
//				kkk.add(model);
//			}
//
//			mmm.setVersion_control(kkk);
//			return mmm;
//	}
//
//
//	//Adding new site in the database
//	@Override
//	public void saveAddNewSiteData(ProvisionSiteSSDto provisionDto,String deploymentId,String tenantId) {
//
//	        agentRepository.updateNewSite(provisionDto.getSiteName(),deploymentId);
//
//	        List<AddressDto> temp = provisionDto.getAddress();
//
//	        for (AddressDto addressModel : temp) {
//
//
//	        	  AddressModel model=new AddressModel();
//
//	        	  model.setCity(addressModel.getCity());
//	        	  model.setDeploymentId(deploymentId);
//	        	  model.setState(addressModel.getState());
//	        	  model.setPinCode(addressModel.getPinCode());
//	        	  model.setStreetName(addressModel.getStreetName());
//
//	        	  addressRepository.save(model);
//			}
//
//	        List<PersonDto> ruk=provisionDto.getPersonOfContact();
//
//	        for (PersonDto personModel : ruk) {
//
//	        	PersonModel model=new PersonModel();
//
//	        	model.setContact(personModel.getContact());
//	        	model.setDeploymentId(deploymentId);
//	        	model.setEmail(personModel.getEmail());
//	        	model.setFullName(personModel.getFullName());
//
//	        	personRepository.save(model);
//			}
//
//	        List<VersionSetProductDto> gg=provisionDto.getVersionControl();
//
//	        for (VersionSetProductDto versionModel : gg) {
//
//	        	VersionModel model=new VersionModel();
//
//	        	model.setDeploymentId(deploymentId);
//	        	model.setProductName(versionModel.getProductName());
//	        	model.setProductSetVersion(versionModel.getProductSetVersion());
//	        	model.setTenantId(tenantId);
//
//	        	versionRepository.save(model);
//			}
//
//	        agentRepository.updateProvisionSite(deploymentId);
//	}
//
//
//	//Updating the existing site in the database
//	@Override
//	public void UpdateExisitingSite(ProvisionDtoUpdate provisionDto, String deploymentId) {
//
//		agentRepository.updateNewSite(provisionDto.getSiteName(),deploymentId);
//
//        List<AddressDto> temp = provisionDto.getAddress();
//
//        for (AddressDto addressModel : temp) {
//
//        	addressRepository.updateNewSite(addressModel.getStreetName(),addressModel.getCity(),addressModel.getPinCode(),addressModel.getState(),deploymentId);
//
//		}
//
//        List<PersonDto> ruk=provisionDto.getPersonOfContact();
//
//        for (PersonDto personModel : ruk) {
//
//        	personRepository.updateNewSite(personModel.getFullName(), personModel.getContact(), personModel.getEmail(), deploymentId);
//		}
//
//	}
//
//
//	//Deleting the existing site in the database
//	@Override
//	public void deleteExistingSite(String deploymentId) {
//
//
//		agentRepository.deleteData(deploymentId);
//		addressRepository.deleteData(deploymentId);
//		personRepository.deleteData(deploymentId);
//	}
//
//
//	@Override
//	public List<DeploymentIdDto> getDeploymentId(String tenantId) {
//
//		List<DeploymentIdDto> data=new ArrayList<>();
//
//		List<String> temp=agentRepository.getDeploymentId(tenantId);
//
//		for (String string : temp) {
//
//			DeploymentIdDto add=new DeploymentIdDto();
//
//			add.setDeploymentId(string);
//
//			data.add(add);
//		}
//
//		return data;
//	}
//
//
//	@Override
//	public ProvisionSiteDto getSiteList(String deploymentId) {
//
//		ProvisionSiteDto data=new ProvisionSiteDto();
//
//		data.setSiteName(agentRepository.getSiteList(deploymentId));
//
//		data.setAddress(addressRepository.getSiteData(deploymentId));
//
//		data.setPersonOfContact(personRepository.getSiteData(deploymentId));
//
//		return data;
//	}
//
//
////	@Override
////	public VersionDataDto getVersionData(String deploymentId) {
////
////        VersionDataDto temp=new VersionDataDto();
////
////        List<VersionControlDataDto> tt=new ArrayList<>();
////
////		List<VersionControlDataModel> list=versionSiteRepository.getAllData(deploymentId);
////
////		for (VersionControlDataModel versionControlDataModel : list) {
////
////			VersionControlDataDto model=new VersionControlDataDto();
////
////			model.setProduct_set_version(versionControlDataModel.getProduct_set_version());
////			model.setProduct_name(versionControlDataModel.getProduct_name());
////			model.setProduct_scheduled_update(versionControlDataModel.getProduct_scheduled_update());
////			model.setProduct_scheduled_update_dateTime(versionControlDataModel.getProduct_scheduled_update_dateTime());
////
////			tt.add(model);
////		}
////
////		temp.setVersionControl(tt);
////		return temp;
////	}
//
//
//	@Override
//	public void saveVersionData(List<VersionControlDataModel> list) {
//
//		for (VersionControlDataModel versionControlDataModel : list) {
//
//			if(versionSiteRepository.checkIfDeploymentId(versionControlDataModel.getDeploymentId())==0)
//			{
//				VersionControlDataModel model=new VersionControlDataModel();
//
//				model.setDeploymentId(versionControlDataModel.getDeploymentId());
//				model.setProduct_set_version(versionControlDataModel.getProduct_set_version());
//				model.setProduct_name(versionControlDataModel.getProduct_name());
//				model.setProduct_scheduled_update(versionControlDataModel.getProduct_scheduled_update());
//				model.setProduct_scheduled_update_dateTime(versionControlDataModel.getProduct_scheduled_update_dateTime());
//
//				versionSiteRepository.save(model);
//			}else {
//
//				versionSiteRepository.updateVersionInfo(versionControlDataModel.getProduct_set_version(),versionControlDataModel.getProduct_scheduled_update(),versionControlDataModel.getProduct_scheduled_update_dateTime(),versionControlDataModel.getDeploymentId());
//			}
//		}
//	}
//
//
//	@Override
//	public List<SiteListDto> getAllSiteList(String tenantId) {
//
//		   List<SiteListDto> data=new ArrayList<>();
//
//		  List<AgentModel> temp=agentRepository.getAllSiteDataProvision(tenantId);
//
//		  for (AgentModel agentModel : temp) {
//
//			  SiteListDto model=new SiteListDto();
//
//			  model.setDeploymentId(agentModel.getDeploymentId());
//			  model.setSiteName(agentModel.getSiteId());
//
//			 String city= addressRepository.cityName(agentModel.getDeploymentId());
//
//			 model.setCity(city);
//
//			 List<VersionProductDto> ppp=new ArrayList<>();
//
//			 List<VersionModel> fff =versionRepository.getListOfVersion(agentModel.getDeploymentId());
//
//			 for (VersionModel versionModel : fff) {
//
//				 VersionProductDto dto=new VersionProductDto();
//
//				 dto.setProductName(versionModel.getProductName());
//				 dto.setProductVersion(versionModel.getProductVersion());
//
//				 ppp.add(dto);
//			}
//
//			 model.setVersionControl(ppp);
//
//			 data.add(model);
//
//		}
//		return data;
//	}
//
//
//	@Override
//	public AddSiteListDto getAddSiteDetails(String deploymentId) {
//
//		AddSiteListDto dto=new AddSiteListDto();
//
//		dto.setDeploymentId(deploymentId);
//		dto.setSiteName(agentRepository.getSiteList(deploymentId));
//
//		AddressModel model=addressRepository.getSpecificSite(deploymentId);
//		AddressDto temp=new AddressDto();
//
//		temp.setCity(model.getCity());
//		temp.setPinCode(model.getPinCode());
//		temp.setState(model.getState());
//		temp.setStreetName(model.getStreetName());
//
//		dto.setAddress(temp);
//
//		PersonModel dust=personRepository.getPersonData(deploymentId);
//
//		PersonDto dust1=new PersonDto();
//
//		dust1.setContact(dust.getContact());
//		dust1.setEmail(dust.getEmail());
//		dust1.setFullName(dust.getFullName());
//
//		dto.setPersonOfContact(dust1);
//
//		List<VersionProductDto> ff=new ArrayList<>();
//        List<VersionModel> temp1=versionRepository.getListOfVersion(deploymentId);
//
//        for (VersionModel versionModel : temp1) {
//
//        	VersionProductDto qq=new VersionProductDto();
//
//        	qq.setProductName(versionModel.getProductName());
//        	qq.setProductVersion(versionModel.getProductVersion());
//
//        	ff.add(qq);
//		}
//
//        dto.setVersionControl(ff);
//
//		return dto;
//	}
//
//
//	@Override
//	public List<VersionControlDataModel> getVersionInfo(String deploymentId) {
//
//		List<VersionControlDataModel> temp=versionSiteRepository.getAllData(deploymentId);
//		return temp;
//	}
//
//
//	@Override
//	public VersionUpdateControlDto getListOfUpdatedVersion(String deploymentId) {
//
//		VersionUpdateControlDto mmm=new VersionUpdateControlDto();
//
//        List<VersionUpgradeDto> kkk=new ArrayList<>();
//
//        List<com.example.demo.dto.BackendPackage.VersionControlDto> temp=new ArrayList<>();
//
//        List<VersionModel> model1=versionRepository.getListOfVersion(deploymentId);
//
//        for (VersionModel versionModel : model1) {
//
//        	com.example.demo.dto.BackendPackage.VersionControlDto dd=new com.example.demo.dto.BackendPackage.VersionControlDto();
//
//        	dd.setRepo(versionModel.getProductName());
//        	dd.setTag(versionModel.getProductVersion());
//
//        	temp.add(dd);
//		}
//
//		mmm.setDeploymentId(deploymentId);
//
//		WebClient webForStoringDatabse = WebClient.builder()
//                								  .baseUrl("http://localhost:8081")
//                								  .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                								  .build();
//
//		List<DockerVersionInformationDto> list = webForStoringDatabse.post()
//                													 .uri("v1/globalSDN/SiteManagement/getUpgradeVersion")
//                													 .contentType(MediaType.APPLICATION_JSON)
//                													 .bodyValue(temp)
//                													 .retrieve()
//                													 .bodyToMono(new ParameterizedTypeReference<List<DockerVersionInformationDto>>() {})
//                													 .block();
//
//		for (DockerVersionInformationDto dockerVersionInformationDto : list) {
//
//              VersionUpgradeDto model=new VersionUpgradeDto();
//
//			  model.setProduct_name(dockerVersionInformationDto.getProduct());
//			  model.setProduct_current_version(versionRepository.getVersion(deploymentId, dockerVersionInformationDto.getProduct()));
//
//			  List<VersionInformation> ttt=dockerVersionInformationDto.getVersions();
//
//			  List<VersionInformation> ppp=new ArrayList<>();
//
//				for (VersionInformation versionInformation : ttt) {
//
//					 VersionInformation qqq=new VersionInformation();
//
//					 qqq.setVersion(versionInformation.getVersion());
//
//					 ppp.add(qqq);
//				}
//
//				model.setProduct_upgrade_available_version(ppp);
//
//				kkk.add(model);
//			}
//
//			mmm.setVersion_control(kkk);
//
//			return mmm;
//
//	}
//
//}
