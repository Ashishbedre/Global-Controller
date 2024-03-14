package com.example.demo.service.serviceimpl;

import com.example.demo.Entity.*;
import com.example.demo.dto.*;
import com.example.demo.dto.BackendPackage.DockerVersionInformationDto;
import com.example.demo.dto.BackendPackage.VersionControlMicroDto;
import com.example.demo.dto.BackendPackage.VersionInformation;
import com.example.demo.enums.Task;
import com.example.demo.repository.*;
import com.example.demo.service.SiteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
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

    @Autowired
    UpdateProductVersionRepository updateProductVersionRepository;

    @Value("${getUpgradeVersion.api.url}")
    private String getUpgradeVersion;
    @Value("${getDowngradeVersion.api.url}")
    private String getDowngradeVersion;

    @Override
    public List<SiteListDto> getAllSiteList(String tenantId) {
        List<SiteDetails> siteDetailsList = siteDetailsRepository.findDistinctDeploymentIdByTenantIdAndProvisionIsTrue(tenantId);

        return siteDetailsList.stream()
                .map(siteDetails -> {
                    SiteListDto model = new SiteListDto();
                    model.setDeploymentId(siteDetails.getDeploymentId());
                    model.setActive(siteDetails.getActive());
                    List<UpdateProductVersion> updateProductVersion = updateProductVersionRepository
                            .findSingleByDeploymentIdOrderByTaskPriority(siteDetails.getDeploymentId());
                    if(updateProductVersion.size()!=0){
                        model.setTask(convertTaskInFrontendView(updateProductVersion.get(0).getTask()));
                    }else{
                        model.setTask(null);
                    }
                    model.setSiteName(siteDetails.getSiteId());
                    model.setCity(siteDetails.getAddresses().getCity());
                    model.setVersionControl(converttoVersionProductDto(siteDetails.getDeploymentId()));
                    return model;
                })
                .collect(Collectors.toList());
    }

    public String convertTaskInFrontendView(Task task) {
        switch (task) {
            case Scheduled:
                return "Scheduled for update";
            case InProgress:
                return "Update in progress";
            case InQueue:
                return "In queue";
            case Completed:
                return "Completed";
            default:
                return "";
        }
    }
//                    List<VersionProductDto> versionProductDtoList;
//                    List<UpdateProductVersion> updateProductVersions = updateProductVersionRepository.findByDeploymentId(siteDetails.getDeploymentId());
//                    if (updateProductVersions != null && !updateProductVersions.isEmpty()) {
//                        versionProductDtoList = updateProductVersions.stream()
//                                .map(updateProductVersion -> {
//                                    VersionProductDto versionControlMicroDto = new VersionProductDto();
//                                    versionControlMicroDto.setProductName(updateProductVersion.getProductName());
//                                    versionControlMicroDto.setProductVersion(updateProductVersion.getProductVersion());
//                                    return versionControlMicroDto;
//                                })
//                                .collect(Collectors.toList());
//                    } else {
//                        List<CurrentProductVersion> currentProductVersions = currentProductVersionRepository.findByDeploymentId(siteDetails.getDeploymentId());
//                        versionProductDtoList = currentProductVersions.stream()
//                                .map(currentProductVersion -> {
//                                    VersionProductDto versionControlMicroDto = new VersionProductDto();
//                                    versionControlMicroDto.setProductName(currentProductVersion.getProductName());
//                                    versionControlMicroDto.setProductVersion(currentProductVersion.getProductVersion());
//                                    return versionControlMicroDto;
//                                })
//                                .collect(Collectors.toList());
//                    }


    public List<VersionProductDto> converttoVersionProductDto(String deploymentId) {
        List<CurrentProductVersion> currentProductVersions = currentProductVersionRepository
                .findByDeploymentId(deploymentId);

        return currentProductVersions.stream().map(currentProductVersion -> {
            UpdateProductVersion updateProductVersion = updateProductVersionRepository
                    .findByDeploymentIdAndProductName(deploymentId, currentProductVersion.getProductName())
                    .orElse(null);

            VersionProductDto versionControlMicroDto = new VersionProductDto();
            versionControlMicroDto.setProductName(currentProductVersion.getProductName());
            versionControlMicroDto.setProductVersion(updateProductVersion != null ? updateProductVersion.getProductVersion()
                    : currentProductVersion.getProductVersion());

            return versionControlMicroDto;
        }).collect(Collectors.toList());
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
//        add country
        addressModel.setCountry(siteDetails.getAddresses().getCountry());
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
    public UpgradeAndDowngradeDto getListOfVersion(String deploymentId) {
        UpgradeAndDowngradeDto upgradeAndDowngradeDto = new UpgradeAndDowngradeDto();
        List<VersionControlMicroDto> versionControlMicroDtoList = converttoVersionControlMicroDto(deploymentId);
        upgradeAndDowngradeDto.setDeploymentId(deploymentId);
        upgradeAndDowngradeDto.setProduct_list(upgradeAndDowngrade(versionControlMicroDtoList,deploymentId));
        return upgradeAndDowngradeDto;
    }
//        List<VersionControlMicroDto> versionControlMicroDtoList;
//
//        List<UpdateProductVersion> updateProductVersions = updateProductVersionRepository.findByDeploymentId(deploymentId);
//        if (updateProductVersions != null && !updateProductVersions.isEmpty()) {
//            versionControlMicroDtoList = updateProductVersions.stream()
//                    .map(updateProductVersion -> {
//                        VersionControlMicroDto versionControlMicroDto = new VersionControlMicroDto();
//                        versionControlMicroDto.setRepo(updateProductVersion.getProductName());
//                        versionControlMicroDto.setTag(updateProductVersion.getProductVersion());
//                        return versionControlMicroDto;
//                    })
//                    .collect(Collectors.toList());
//        } else {
//            List<CurrentProductVersion> currentProductVersions = currentProductVersionRepository.findByDeploymentId(deploymentId);
//            versionControlMicroDtoList = currentProductVersions.stream()
//                    .map(currentProductVersion -> {
//                        VersionControlMicroDto versionControlMicroDto = new VersionControlMicroDto();
//                        versionControlMicroDto.setRepo(currentProductVersion.getProductName());
//                        versionControlMicroDto.setTag(currentProductVersion.getProductVersion());
//                        return versionControlMicroDto;
//                    })
//                    .collect(Collectors.toList());
//        }

    public List<VersionControlMicroDto> converttoVersionControlMicroDto(String deploymentId) {
        List<CurrentProductVersion> currentProductVersions = currentProductVersionRepository
                .findByDeploymentId(deploymentId);

        return currentProductVersions.stream().map(currentProductVersion -> {
            UpdateProductVersion updateProductVersion = updateProductVersionRepository
                    .findByDeploymentIdAndProductName(deploymentId, currentProductVersion.getProductName())
                    .orElse(null);

            VersionControlMicroDto versionControlMicroDto = new VersionControlMicroDto();
            versionControlMicroDto.setRepo(currentProductVersion.getProductName());
            versionControlMicroDto.setTag(updateProductVersion != null ? updateProductVersion.getProductVersion()
                    : currentProductVersion.getProductVersion());

            return versionControlMicroDto;
        }).collect(Collectors.toList());
    }
//for optimize the code, I have comment
//    public List<VersionControlMicroDto> converttoVersionControlMicroDto(String deploymentId){
//        List<VersionControlMicroDto> versionControlMicroDtoList = new ArrayList<>();
//        List<CurrentProductVersion> currentProductVersions = currentProductVersionRepository.findByDeploymentId(deploymentId);
//        for(CurrentProductVersion iterateCurrentProductVersion : currentProductVersions){
//            Optional<UpdateProductVersion> updateProductVersionsList = updateProductVersionRepository.
//                    findByDeploymentIdAndProductName(deploymentId,iterateCurrentProductVersion.getProductName());
//            if(updateProductVersionsList.isPresent()){
//                VersionControlMicroDto versionControlMicroDto = new VersionControlMicroDto();
//                UpdateProductVersion updateProductVersion = updateProductVersionsList.get();
//                versionControlMicroDto.setRepo(updateProductVersion.getProductName());
//                versionControlMicroDto.setTag(updateProductVersion.getProductVersion());
//                versionControlMicroDtoList.add(versionControlMicroDto);
//            }else{
//                VersionControlMicroDto versionControlMicroDto = new VersionControlMicroDto();
//                versionControlMicroDto.setRepo(iterateCurrentProductVersion.getProductName());
//                versionControlMicroDto.setTag(iterateCurrentProductVersion.getProductVersion());
//                versionControlMicroDtoList.add(versionControlMicroDto);
//            }
//
//        }
//        return versionControlMicroDtoList;
//    }

    public List<ProductAvailableVersionDto> upgradeAndDowngrade(List<VersionControlMicroDto> versionControlMicroDtoList ,String deploymentId){
        List<ProductAvailableVersionDto> productAvailableVersionDtosList = new ArrayList<>();
        for(VersionControlMicroDto versionControlMicroDto: versionControlMicroDtoList){
            ProductAvailableVersionDto productAvailableVersionDto = new ProductAvailableVersionDto();
            List<VersionControlMicroDto> listAdd = new ArrayList<>();
            VersionControlMicroDto object = new VersionControlMicroDto();
            object.setRepo(versionControlMicroDto.getRepo());
            object.setTag(versionControlMicroDto.getTag());
            listAdd.add(object);
            productAvailableVersionDto.setProduct_name(versionControlMicroDto.getRepo());
            productAvailableVersionDto.setProduct_current_version(versionControlMicroDto.getTag());
            WebClient webClient = WebClient.create();

            List<VersionInformation> versionInformationsUpgradeVersion = webClient.post()
                    .uri(getUpgradeVersion)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(listAdd)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<DockerVersionInformationDto>>() {})
                    .block()
                    .stream()
                    .flatMap(dockerVersionInformationDto -> dockerVersionInformationDto.getVersions().stream())
                    .map(versionInformation -> {
                        VersionInformation versionInformationCopy = new VersionInformation();
                        versionInformationCopy.setVersion(versionInformation.getVersion());
                        return versionInformationCopy;
                    })
                    .collect(Collectors.toList());
            List<VersionInformation> versionInformationsDowngradeVersion = webClient.post()
                    .uri(getDowngradeVersion)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(listAdd)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<DockerVersionInformationDto>>() {})
                    .block()
                    .stream()
                    .flatMap(dockerVersionInformationDto -> dockerVersionInformationDto.getVersions().stream())
                    .map(versionInformation -> {
                        VersionInformation versionInformationCopy = new VersionInformation();
                        versionInformationCopy.setVersion(versionInformation.getVersion());
                        return versionInformationCopy;
                    })
                    .collect(Collectors.toList());

            productAvailableVersionDto.setProduct_upgrade_available_version(versionInformationsUpgradeVersion);
            productAvailableVersionDto.setProduct_downgrade_available_version(versionInformationsDowngradeVersion);
            productAvailableVersionDtosList.add(productAvailableVersionDto);
        }
        return productAvailableVersionDtosList;
    }


    @Override
    public void UpdateExisitingSite(ProvisionDtoUpdate provisionDto, String deploymentId) {
        Optional<SiteDetails> optionalSiteDetails = siteDetailsRepository
                .findByDeploymentIdAndSiteId(deploymentId, provisionDto.getSiteName());

        if (optionalSiteDetails.isPresent()) {
            SiteDetails siteDetails = optionalSiteDetails.get();
            siteDetails.setSiteId(provisionDto.getSiteName());

            // Convert AddressDto list to Address list
                        Address address = addressRepository.findBySiteId(siteDetails.getId());
                        address.setStreetName(provisionDto.getAddress().getStreetName());
                        address.setCity(provisionDto.getAddress().getCity());
                        address.setState(provisionDto.getAddress().getState());
                        address.setCity(provisionDto.getAddress().getCity());
                        address.setPinCode(provisionDto.getAddress().getPinCode());
//                        add country
                        address.setCountry(provisionDto.getAddress().getCountry());
                        address.setSite(siteDetails);
            siteDetails.setAddresses(address);

            // Convert PersonDto list to Person list
                        Person person = personRepository.findBySiteId(siteDetails.getId());
                        person.setFullName(provisionDto.getPersonOfContact().getFullName());
                        person.setEmail(provisionDto.getPersonOfContact().getEmail());
                        person.setContact(provisionDto.getPersonOfContact().getContact());
                        person.setSite(siteDetails);
            siteDetails.setPersonsOfContact(person);

            // Save the updated entity
            siteDetailsRepository.save(siteDetails);
        }
    }

    @Override
    public void deleteExistingSite(String deploymentId) {
        siteDetailsRepository.deleteAddressesByDeploymentId(deploymentId);
        siteDetailsRepository.deletePersonsByDeploymentId(deploymentId);
        siteDetailsRepository.deleteSiteDetailsByDeploymentId(deploymentId);
        currentProductVersionRepository.deleteByDeploymentId(deploymentId);
        updateProductVersionRepository.deleteByDeploymentId(deploymentId);

    }

    @Override
    public void saveVersionData(List<VersionControlDataModel> list) {
        for (VersionControlDataModel versionControlDataModel : list) {
                saveOrUpdate(versionControlDataModel);
        }
    }

    private void saveOrUpdate(VersionControlDataModel updateProductVersion) {
        String deploymentId = updateProductVersion.getDeploymentId();
        String productName = updateProductVersion.getProduct_name();

        // Search for existing entity
        updateProductVersionRepository.findByDeploymentIdAndProductNameForUpdateProductVersion(deploymentId, productName)
                .ifPresentOrElse(
                        existingEntity -> {
                            existingEntity.setProductVersion(updateProductVersion.getProduct_set_version());
                            existingEntity.setProduct_scheduled_update(updateProductVersion.getProduct_scheduled_update());
                            existingEntity.setProduct_scheduled_update_dateTime(updateProductVersion.getProduct_scheduled_update_dateTime());
                            existingEntity.setTask(Task.InQueue);
                            updateProductVersionRepository.save(existingEntity);
                        },() -> {
                            UpdateProductVersion model = new UpdateProductVersion();
                            model.setDeploymentId(updateProductVersion.getDeploymentId());
                            model.setProductVersion(updateProductVersion.getProduct_set_version());
                            model.setProductName(updateProductVersion.getProduct_name());
                            model.setProduct_scheduled_update(updateProductVersion.getProduct_scheduled_update());
                            model.setProduct_scheduled_update_dateTime(updateProductVersion.getProduct_scheduled_update_dateTime());
                            model.setTask(Task.InQueue);
                            updateProductVersionRepository.save(model);
                        }
                );
    }
}


