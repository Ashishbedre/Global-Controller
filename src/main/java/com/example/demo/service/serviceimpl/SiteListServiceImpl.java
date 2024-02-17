package com.example.demo.service.serviceimpl;

import com.example.demo.Entity.Address;
import com.example.demo.Entity.CurrentProductVersion;
import com.example.demo.Entity.Person;
import com.example.demo.Entity.SiteDetails;
import com.example.demo.dto.*;
import com.example.demo.dto.BackendPackage.DockerVersionInformationDto;
import com.example.demo.dto.BackendPackage.VersionControlMicroDto;
import com.example.demo.dto.BackendPackage.VersionInformation;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.CurrentProductVersionRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.SiteDetailsRepository;
import com.example.demo.service.SiteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SiteListServiceImpl implements SiteListService {

    @Autowired
    SiteDetailsRepository siteDetailsRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CurrentProductVersionRepository currentProductVersionRepository;

    @Override
    public List<SiteListDto> getAllSiteList(String tenantId) {
        List<SiteDetails> siteDetailsList = siteDetailsRepository.findDistinctDeploymentIdByTenantIdAndProvisionIsTrue(tenantId);

        return siteDetailsList.stream()
                .map(siteDetails -> {
                    SiteListDto model = new SiteListDto();
                    model.setDeploymentId(siteDetails.getDeploymentId());
                    model.setSiteName(siteDetails.getSiteId());
                    model.setCity(siteDetails.getAddresses().getCity());

                    List<VersionProductDto> versionProductDtoList = currentProductVersionRepository.findByDeploymentId(siteDetails.getDeploymentId())
                            .stream()
                            .map(versionModel -> {
                                VersionProductDto dto = new VersionProductDto();
                                dto.setProductName(versionModel.getProductName());
                                dto.setProductVersion(versionModel.getProductVersion());
                                return dto;
                            })
                            .collect(Collectors.toList());

                    model.setVersionControl(versionProductDtoList);
                    return model;
                })
                .collect(Collectors.toList());
    }


    @Override
    public ProvisionSiteDto getSiteList(String deploymentId) {
        ProvisionSiteDto data=new ProvisionSiteDto();
        SiteDetails siteDetails = siteDetailsRepository.findByDeploymentId(deploymentId);
        data.setSiteName(siteDetails.getSiteId());
        //Address
        AddressModel addressModel = new AddressModel();
        addressModel.setCity(siteDetails.getAddresses().getCity());
        addressModel.setDeploymentId(deploymentId);
        addressModel.setState(siteDetails.getAddresses().getState());
        addressModel.setStreetName(siteDetails.getAddresses().getStreetName());
        addressModel.setPinCode(siteDetails.getAddresses().getPinCode());
        data.setAddress(addressModel);

        //PersonModel
        PersonModel personModel = new PersonModel();
        personModel.setContact(siteDetails.getPersonsOfContact().getContact());
        personModel.setDeploymentId(deploymentId);
        personModel.setEmail(siteDetails.getPersonsOfContact().getEmail());
        personModel.setFullName(siteDetails.getPersonsOfContact().getFullName());
        data.setPersonOfContact(personModel);

        return data;
    }

    @Override
    public VersionUpdateControlDto getListOfVersion(String deploymentId) {
        VersionUpdateControlDto mmm=new VersionUpdateControlDto();

        List<VersionUpgradeDto> kkk=new ArrayList<>();

        List<VersionControlMicroDto> temp=new ArrayList<>();

        List<CurrentProductVersion> model1=currentProductVersionRepository.findByDeploymentId(deploymentId);

        for (CurrentProductVersion versionModel : model1) {

            VersionControlMicroDto dd=new VersionControlMicroDto();

            dd.setRepo(versionModel.getProductName());
            dd.setTag(versionModel.getProductVersion());

            temp.add(dd);
        }

        mmm.setDeploymentId(deploymentId);

        WebClient webForStoringDatabse = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        List<DockerVersionInformationDto> list = webForStoringDatabse.post()
                .uri("v1/globalSDN/SiteManagement/getUpgradeVersion")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(temp)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<DockerVersionInformationDto>>() {})
                .block();

        for (DockerVersionInformationDto dockerVersionInformationDto : list) {

            VersionUpgradeDto model=new VersionUpgradeDto();

            model.setProduct_name(dockerVersionInformationDto.getProduct());
            model.setProduct_current_version(currentProductVersionRepository.findVersionNameByDeploymentIdAndProductName(deploymentId, dockerVersionInformationDto.getProduct()));


            List<VersionInformation> ttt=dockerVersionInformationDto.getVersions();

            List<VersionInformation> ppp=new ArrayList<>();

            for (VersionInformation versionInformation : ttt) {

                VersionInformation qqq=new VersionInformation();

                qqq.setVersion(versionInformation.getVersion());

                ppp.add(qqq);
            }

            model.setProduct_upgrade_available_version(ppp);

            kkk.add(model);
        }

        mmm.setVersion_control(kkk);
        return mmm;
    }

    @Override
    public void UpdateExisitingSite(ProvisionDtoUpdate provisionDto, String deploymentId) {
        Optional<SiteDetails> optionalSiteDetails = siteDetailsRepository
                .findByDeploymentIdAndSiteId(deploymentId, provisionDto.getSiteName());

        if (optionalSiteDetails.isPresent()) {
            SiteDetails siteDetails = optionalSiteDetails.get();
            siteDetails.setSiteId(provisionDto.getSiteName());

            // Convert AddressDto list to Address list
                        Address address = new Address();
                        address.setStreetName(provisionDto.getAddress().getStreetName());
                        address.setCity(provisionDto.getAddress().getCity());
                        address.setState(provisionDto.getAddress().getState());
                        address.setCity(provisionDto.getAddress().getCity());
                        address.setPinCode(provisionDto.getAddress().getPinCode());
                        address.setSite(siteDetails);
            siteDetails.setAddresses(address);

            // Convert PersonDto list to Person list
                        Person person = new Person();
                        person.setFullName(provisionDto.getPersonOfContact().getFullName());
                        person.setEmail(provisionDto.getPersonOfContact().getEmail());
                        person.setContact(provisionDto.getPersonOfContact().getContact());
                        person.setSite(siteDetails);
            siteDetails.setPersonsOfContact(person);

            // Save the updated entity
            siteDetailsRepository.save(siteDetails);
        }
//        else {
//            siteDetailsRepository.updateSiteDetails(provisionDto.getAddress(),provisionDto.getPersonOfContact(),deploymentId);
//        }
    }

    @Override
    public void deleteExistingSite(String deploymentId) {
//        SiteDetails siteDetails = siteDetailsRepository.findByDeploymentId(deploymentId);
//        addressRepository.delete(siteDetails.getSiteId());
//        siteDetailsRepository.deleteByDeploymentId(deploymentId);
    }

    @Override
    public void saveVersionData(List<VersionControlDataModel> list) {
        List<CurrentProductVersion> currentProductVersions = currentProductVersionRepository.findAll(); // Fetch all existing entities

        for (VersionControlDataModel versionControlDataModel : list) {
            boolean found = false;
            for (CurrentProductVersion currentProductVersion : currentProductVersions) {
                if (versionControlDataModel.getDeploymentId().equals(currentProductVersion.getDeploymentId())) {
                    // Update existing entity
                    currentProductVersion.setProductVersion(versionControlDataModel.getProduct_set_version());
                    currentProductVersion.setProductName(versionControlDataModel.getProduct_name());
                    // Update other fields if needed
                    currentProductVersionRepository.save(currentProductVersion);
                    found = true;
                    break;
                }
            }
            if (!found) {
                // Create a new entity if it doesn't exist
                CurrentProductVersion model = new CurrentProductVersion();
                model.setDeploymentId(versionControlDataModel.getDeploymentId());
                model.setProductVersion(versionControlDataModel.getProduct_set_version());
                model.setProductName(versionControlDataModel.getProduct_name());
                // Set other fields if needed
                currentProductVersionRepository.save(model);
            }
        }
    }
}
