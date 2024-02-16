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

        List<SiteListDto> data=new ArrayList<>();

        List<SiteDetails> temp=siteDetailsRepository.findByTenantId(tenantId);

        for (SiteDetails agentModel : temp) {

            SiteListDto model=new SiteListDto();

            model.setDeploymentId(agentModel.getDeploymentId());
            model.setSiteName(agentModel.getSiteId());
            String city="";
            try {
                city = agentModel.getAddresses().get(0).getCity();
            }catch (Exception e){
                e.printStackTrace();
            }
            model.setCity(city);

            List<VersionProductDto> ppp=new ArrayList<>();

            List<CurrentProductVersion> fff =currentProductVersionRepository.findByDeploymentId(agentModel.getDeploymentId());

            for (CurrentProductVersion versionModel : fff) {

                VersionProductDto dto=new VersionProductDto();

                dto.setProductName(versionModel.getProductName());
                dto.setProductVersion(versionModel.getProductVersion());

                ppp.add(dto);
            }

            model.setVersionControl(ppp);

            data.add(model);

        }
        return data;

    }

    @Override
    public ProvisionSiteDto getSiteList(String deploymentId) {
        ProvisionSiteDto data=new ProvisionSiteDto();
        SiteDetails siteDetails = siteDetailsRepository.findByDeploymentId(deploymentId);
        data.setSiteName(siteDetails.getSiteId());
        //Address
        List<AddressModel> addressModelsList = new ArrayList<>();
        AddressModel addressModel = new AddressModel();
        addressModel.setCity(siteDetails.getAddresses().get(siteDetails.getAddresses().size()-1).getCity());
        addressModel.setDeploymentId(deploymentId);
        addressModel.setState(siteDetails.getAddresses().get(siteDetails.getAddresses().size()-1).getState());
        addressModel.setStreetName(siteDetails.getAddresses().get(siteDetails.getAddresses().size()-1).getStreetName());
        addressModel.setPinCode(siteDetails.getAddresses().get(siteDetails.getAddresses().size()-1).getPinCode());
        addressModelsList.add(addressModel);
        data.setAddress(addressModelsList);

        //PersonModel
        List<PersonModel> personModelList = new ArrayList<>();
        PersonModel personModel = new PersonModel();
        personModel.setContact(siteDetails.getPersonsOfContact().get(siteDetails.getPersonsOfContact().size()-1).getContact());
        personModel.setDeploymentId(deploymentId);
        personModel.setEmail(siteDetails.getPersonsOfContact().get(siteDetails.getPersonsOfContact().size()-1).getEmail());
        personModel.setFullName(siteDetails.getPersonsOfContact().get(siteDetails.getPersonsOfContact().size()-1).getFullName());
        personModelList.add(personModel);

        data.setPersonOfContact(personModelList);

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
            List<Address> addresses = provisionDto.getAddress().stream()
                    .map(addressDto -> {
                        Address address = new Address();
                        address.setStreetName(addressDto.getStreetName());
                        address.setCity(addressDto.getCity());
                        address.setState(addressDto.getState());
                        address.setCity(addressDto.getCity());
                        address.setPinCode(addressDto.getPinCode());
                        address.setSite(siteDetails);
                        return address;
                    })
                    .collect(Collectors.toList());
            siteDetails.setAddresses(addresses);

            // Convert PersonDto list to Person list
            List<Person> personsOfContact = provisionDto.getPersonOfContact().stream()
                    .map(personDto -> {
                        Person person = new Person();
                        person.setFullName(personDto.getFullName());
                        person.setEmail(personDto.getEmail());
                        person.setContact(personDto.getContact());
                        person.setSite(siteDetails);
                        return person;
                    })
                    .collect(Collectors.toList());
            siteDetails.setPersonsOfContact(personsOfContact);

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
