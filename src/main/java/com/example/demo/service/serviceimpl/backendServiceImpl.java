package com.example.demo.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.dto.AddressDto;
import com.example.demo.dto.PersonDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.ProvisionDto;
import com.example.demo.dto.TenantDto;
import com.example.demo.dto.VersionAddSiteDto;
import com.example.demo.dto.VersionProductDto;
import com.example.demo.dto.BackendPackage.DockerImageVersionDto;
import com.example.demo.dto.BackendPackage.DockerVersionInformationDto;
import com.example.demo.dto.BackendPackage.VersionInformation;
import com.example.demo.model.AddressModel;
import com.example.demo.model.AgentModel;
import com.example.demo.model.PersonModel;
import com.example.demo.model.UpdateVersionModel;
import com.example.demo.model.VersionModel;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.AgentRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.UpdatedVersionRepository;
import com.example.demo.repository.VersionRepository;
import com.example.demo.service.backendService;

@Service
public class backendServiceImpl implements backendService{

	@Autowired
	AgentRepository agentRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	VersionRepository versionRepository;
	
	@Autowired
	UpdatedVersionRepository updatedVersionRepository;
	
	
	//Saving the data from the agent
	@Override
	public void saveAgentData(AgentModel agentModel) {

		AgentModel model= new AgentModel();
		
		model.setDeploymentId(agentModel.getDeploymentId());
		model.setSiteId(agentModel.getSiteId());
		model.setTenantId(agentModel.getTenantId());
		model.setProvision(false);
		
		agentRepository.save(model);
		
	}


	//Getting the list of tenant
	@Override
	public List<TenantDto> getListOfTenant() {
		
		List<TenantDto> temp=new ArrayList<>();
		List<String> list=agentRepository.getListOfTenantId();
		
		for (String string : list) {
			
			TenantDto dto=new TenantDto();
			
			dto.setTenantId(string);
			
			temp.add(dto);
		}
		return temp;
	}


	//Getting all the data from the controller
	@Override
	public List<ProvisionDto> getAllData(String tenantId) {
		
		   List<ProvisionDto> ttt=new ArrayList<>();
		   
	       List<AgentModel> temp=agentRepository.getAllSiteData(tenantId);
	       
	       for (AgentModel agentModel : temp) {
			
	    	   ProvisionDto zxc=new ProvisionDto();
	    	   
	    	   zxc.setDeploymentId(agentModel.getDeploymentId());
	    	   zxc.setSiteName(agentModel.getSiteId());
	    	   
	    	   List<AddressDto> easy =new ArrayList<>();
	    	   
	    	   List<AddressModel> data=addressRepository.getAllSiteData(agentModel.getDeploymentId());
	    	   
	    	   for (AddressModel addressModel : data) {
				
	    		   AddressDto cc=new AddressDto();
	    		   
	    		   cc.setCity(addressModel.getCity());
	    		   cc.setPinCode(addressModel.getPinCode());
	    		   cc.setState(addressModel.getState());
	    		   cc.setStreetName(addressModel.getState());
	    		   
	    		   easy.add(cc);
	    		   
	    		   
			}
	    	   zxc.setAddress(easy);
	    	   
	    	   List<PersonDto> iop=new ArrayList<>();
	    	   
	    	   List<PersonModel> bnm= personRepository.getAllSiteData(agentModel.getDeploymentId());
	    	   
	    	   for (PersonModel personModel : bnm) {
				
	    		   PersonDto qwe=new PersonDto();
	    		   qwe.setContact(personModel.getContact());
	    		   qwe.setEmail(personModel.getEmail());
	    		   qwe.setFullName(personModel.getFullName());
	    		   
	    		   iop.add(qwe);
			}
	    	   zxc.setPersonOfContact(iop);
	    	   
	    	   List<VersionProductDto> mmm=new ArrayList<>();
	    	   
	    	   List<VersionModel> asd=versionRepository.getListOfVersion(agentModel.getDeploymentId());
	    	   
	    	   for (VersionModel versionModel : asd) {
				
	    		   VersionProductDto dto=new VersionProductDto();
	    		   
	    		   dto.setProductName(versionModel.getProductName());
	    		   dto.setProductVersion(versionModel.getProductVersion());
	    		   
	    		   mmm.add(dto);
			}
	    	   zxc.setVersionControl(mmm);
	    	   
	    	   
	    	   ttt.add(zxc);
		}
	       	       
	       
	       return ttt;
	}


	//Getting the list of version number
	@Override
	public List<VersionAddSiteDto> getAllDataProduct(String tenantId) {

		    List<VersionAddSiteDto> data=new ArrayList<>();
		
			List<AgentModel> list=agentRepository.getAllSiteData(tenantId);
			
			for (AgentModel versionModel : list) {
				
				VersionAddSiteDto model=new VersionAddSiteDto();
				
				model.setDeploymentId(versionModel.getDeploymentId());
				
				List<ProductDto> zxc=new ArrayList<>();
				List<VersionModel> qwerty=versionRepository.getListOfVersion(versionModel.getDeploymentId());
				
				for (VersionModel versionModel2 : qwerty) {
					
					ProductDto modell=new ProductDto();
					
					modell.setProductName(versionModel2.getProductName());
					modell.setProductVersion(versionModel2.getProductVersion());
					
					zxc.add(modell);
				}
				
				model.setVersion(zxc);
				
				data.add(model);
			}
			
			return data;
	    }


	//Saving the list of version data
	@Override
	public void saveVersionData(List<VersionModel> model) {

		for (VersionModel versionModel : model) {
		   
			VersionModel data=new VersionModel();
			
			data.setDeploymentId(versionModel.getDeploymentId());
			data.setProductName(versionModel.getProductName());
			data.setProductVersion(versionModel.getProductVersion());
			data.setTenantId(versionModel.getTenantId());
			
			versionRepository.save(data);
		}
		
	}

    // Updating the version info.
	@Override
	public void updatedVersionInfo(List<UpdateVersionModel> versionModel) {
		
		for (UpdateVersionModel updateVersionModel : versionModel) {
			
			UpdateVersionModel updateModel =new UpdateVersionModel();
			
			updateModel.setDeploymentId(updateVersionModel.getDeploymentId());
			updateModel.setProductName(updateVersionModel.getProductName());
			updateModel.setVersion(updateVersionModel.getVersion());
			
			updatedVersionRepository.save(updateModel);
		}
		
	}


	//Getting the updated information regarding the version information for Downgrading information
//	@Override
//	public List<DockerVersionInformationDto> getUpdatedVersionInformation(DockerImageVersionDto versionDto) {
//				
//		
//		WebClient webForStoringDatabse = WebClient.builder()
//				                                  .baseUrl("http://localhost:8081")
//				                                  .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//				                                  .build();
//		
//		List<DockerVersionInformationDto> list =webForStoringDatabse.post()
//				                                .uri("/v1/globalSDN/SiteManagement/getDowngradeVersion")
//				                                .contentType(MediaType.APPLICATION_JSON)
//				                                .bodyValue(versionDto)
//				                                .retrieve()
//				                                .bodyToMono(new ParameterizedTypeReference<List<DockerVersionInformationDto>>() {})
//				                                .block();
//		return list;
//	}


	//Getting the upgraded version information from docker hub 
//	@Override
//	public List<DockerVersionInformationDto> getUpdatedVersionInform(DockerImageVersionDto versionDto) {
//
//
//		WebClient webForStoringDatabse = WebClient.builder()
//				                                  .baseUrl("http://localhost:8081")
//				                                  .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//				                                  .build();
//		
//		List<DockerVersionInformationDto> list =webForStoringDatabse.post()
//				                                                    .uri("v1/globalSDN/SiteManagement/getUpgradeVersion")
//				                                                    .contentType(MediaType.APPLICATION_JSON)
//				                                                    .bodyValue(versionDto)
//				                                                    .retrieve()
//				                                                    .bodyToMono(new ParameterizedTypeReference<List<DockerVersionInformationDto>>() {})
//				                                                    .block();
//		return list;
//	}


	@Override
	public List<DockerVersionInformationDto> getTwoUpdatedData(DockerImageVersionDto versionDto) {

		WebClient webForStoringDatabse = WebClient.builder()
                .baseUrl("http://localhost:8081")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
		
		List<DockerVersionInformationDto> model=new ArrayList<>();

        List<DockerVersionInformationDto> list =webForStoringDatabse.post()
                                                                   .uri("v1/globalSDN/SiteManagement/getUpgradeVersion")
                                                                   .contentType(MediaType.APPLICATION_JSON)
                                                                   .bodyValue(versionDto)
                                                                   .retrieve()
                                                                   .bodyToMono(new ParameterizedTypeReference<List<DockerVersionInformationDto>>() {})
                                                                   .block();
        
       for (DockerVersionInformationDto dockerVersionInformationDto : list) {
		
    	     DockerVersionInformationDto asd=new DockerVersionInformationDto();
    	     
    	     asd.setProduct(dockerVersionInformationDto.getProduct());
    	     
    	     List<VersionInformation> pp=new ArrayList<>();
    	     List<VersionInformation> hh=dockerVersionInformationDto.getVersions();
    	     int ff=0;
    	     for (VersionInformation versionInformation : hh) {
				if(ff<2)
				{
					VersionInformation ll=new VersionInformation();
	    	    	 
	    	    	 ll.setVersion(versionInformation.getVersion());
	    	    	 
	    	    	 ff++;
	    	    	 
	    	    	 pp.add(ll);
				}
    	    	 
    	    	 
			}
    	     
    	     asd.setVersions(pp);
    	     
    	     
    	     model.add(asd);
    	   
	}
       return model;
	}
	

	

	
	
}
