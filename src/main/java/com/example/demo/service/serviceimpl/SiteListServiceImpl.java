package com.example.demo.service.serviceimpl;

import com.example.demo.Entity.*;
import com.example.demo.dto.*;
import com.example.demo.dto.BackendPackage.DockerVersionInformationDto;
import com.example.demo.dto.BackendPackage.ProductListResponcedto;
import com.example.demo.dto.BackendPackage.VersionControlMicroDto;
import com.example.demo.dto.BackendPackage.VersionInformation;
import com.example.demo.enums.Task;
import com.example.demo.repository.*;
import com.example.demo.service.Compatible;
import com.example.demo.service.SiteListService;
import com.example.demo.service.TokenService;
import com.fasterxml.jackson.databind.JsonNode;
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

    @Autowired
    Compatible compatible;

    @Value("${getUpgradeVersion.api.url}")
    private String getUpgradeVersion;
    @Value("${getDowngradeVersion.api.url}")
    private String getDowngradeVersion;

    @Autowired
    private TokenService tokenService;
//    String accessToken = tokenService.getAccessToken();

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
//            versionControlMicroDto.setProductVersion(updateProductVersion != null ? updateProductVersion.getProductVersion()
//                    : currentProductVersion.getProductVersion());
//           Ashish add for null handle
            String productVersion = updateProductVersion != null ? updateProductVersion.getProductVersion()
                    : currentProductVersion.getProductVersion();
            if (productVersion == null || "null".equals(productVersion)) {
                versionControlMicroDto.setProductVersion(null);
            } else {
                versionControlMicroDto.setProductVersion(productVersion);
            }

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
        //        Ashish add latitude and longitude
        addressModel.setLatitude(siteDetails.getAddresses().getLatitude());
        addressModel.setLongitude(siteDetails.getAddresses().getLongitude());
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
        List<UpdateProductVersion> updateProductVersion = updateProductVersionRepository
                .findSingleByDeploymentIdOrderByTaskPriority(deploymentId);

        if (!updateProductVersion.isEmpty()) {
//            Task task = updateProductVersion.get(0).getTask();
//            upgradeAndDowngradeDto.setTask(task != Task.Scheduled);
            upgradeAndDowngradeDto.setTask(false);
        } else {
            upgradeAndDowngradeDto.setTask(true);
        }

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
//            productAvailableVersionDto.setProduct_current_version(versionControlMicroDto.getTag());
//            Ashish add for null handle
            String tag = versionControlMicroDto.getTag();
            if (tag == null || "null".equals(tag)) {
                productAvailableVersionDto.setProduct_current_version(null);
            } else {
                productAvailableVersionDto.setProduct_current_version(tag);
            }

            WebClient webClient = WebClient.create();
            String accessToken = tokenService.getAccessToken();
            List<VersionInformation> versionInformationsUpgradeVersion = webClient.post()
                    .uri(getUpgradeVersion)
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(headers -> headers.setBearerAuth(accessToken))
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
                    .headers(headers -> headers.setBearerAuth(accessToken))
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
            //        Ashish add latitude and longitude
                        address.setLongitude(provisionDto.getAddress().getLongitude());
                        address.setLatitude(provisionDto.getAddress().getLatitude());
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
    public List<ProductListResponcedto> saveVersionData(List<VersionControlDataModel> list) {
        List<ProductListResponcedto> dataProcess = checkForCompatible(list);
        Boolean compare = dataProcess.get(0).isCompatible();
        if(dataProcess.isEmpty() || compare==false){
            return dataProcess;
        }else{
            for (VersionControlDataModel versionControlDataModel : list) {
                    saveOrUpdate(versionControlDataModel);
            }
            return dataProcess;
        }
    }

    public List<ProductListResponcedto> checkForCompatible(List<VersionControlDataModel> list){
        if(list.size()==1){
            String product1 = list.get(0).getProduct_name();
            String version1 = list.get(0).getProduct_set_version();
            String product2 = "null";
            String version2 = "null";
            List<CurrentProductVersion> check = currentProductVersionRepository.findByDeploymentId(list.get(0).getDeploymentId());
            for(CurrentProductVersion findTheProduct : check){
                String findProduct = findTheProduct.getProductName() != null ? findTheProduct.getProductName() : "null";
                if (!findProduct.equals(product1)){
                    product2 = findTheProduct.getProductName();
                    version2 = findTheProduct.getProductVersion();
                    break;
                }
            }
            List<VersionSetProductDto> versionControl = new ArrayList<>();
            versionControl.add(new VersionSetProductDto(product1, version1));

//            if (product2 != null && version2 != null) {
                versionControl.add(new VersionSetProductDto(product2, version2));
//            }
            JsonNode jsonString = compatible.checkCompatibleUrl(versionControl);
            List<ProductListResponcedto> dataProcess = compatible.processCompatibilityData(jsonString);
            return dataProcess;
//            if(dataProcess.get(0).isCompatible()==false ){
//                return dataProcess;
//            }
        } else if (list.size()==2) {
            JsonNode jsonString = compatible.checkCompatibleUrl(convertToVersionSetProductDtoList(list));
            List<ProductListResponcedto> dataProcess = compatible.processCompatibilityData(jsonString);
            if(dataProcess.get(0).isCompatible()==false ){
                return dataProcess;
            } else if (dataProcess.get(0).isCompatible()==true) {
                return dataProcess;
            }
        }

        return new ArrayList<>();

    }

    public static List<VersionSetProductDto> convertToVersionSetProductDtoList(List<VersionControlDataModel> versionControlDataModels) {
        return versionControlDataModels.stream()
                .map(model -> new VersionSetProductDto(model.getProduct_name(), model.getProduct_set_version()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SiteDetailsResponseDTO> getSiteDetailsByTenantId(String tenantId) {
        List<Object[]> results = siteDetailsRepository.findSiteDetailsByTenantId(tenantId);
        List<SiteDetailsResponseDTO> siteDetailsList = new ArrayList<>();

        for (Object[] result : results) {
            String siteId = (String) result[0];
            Address address = (Address) result[1];
            Person personOfContact = (Person) result[2];

            SiteDetailsResponseDTO siteDetailsDTO = new SiteDetailsResponseDTO(siteId, mapAddressToDto(address), mapPersonToDto(personOfContact));
            siteDetailsList.add(siteDetailsDTO);
        }

        return siteDetailsList;
    }

    public static AddressDto mapAddressToDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setStreetName(address.getStreetName());
        addressDto.setCity(address.getCity());
        addressDto.setPinCode(address.getPinCode());
        addressDto.setState(address.getState());
        addressDto.setCountry(address.getCountry());
        addressDto.setLatitude(address.getLatitude());
        addressDto.setLongitude(address.getLongitude());
        return addressDto;
    }
    public PersonDto mapPersonToDto(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.setFullName(person.getFullName());
        personDto.setContact(person.getContact());
        personDto.setEmail(person.getEmail());
        return personDto;
    }

    @Override
    public List<SiteDetailsResponseDTO> getSiteDetailsByTenantId() {
        List<Object[]> results = siteDetailsRepository.findSiteDetailsByTenantId();
        List<SiteDetailsResponseDTO> siteDetailsList = new ArrayList<>();

        for (Object[] result : results) {
            String siteId = (String) result[0];
            Address address = (Address) result[1];
            Person personOfContact = (Person) result[2];

            SiteDetailsResponseDTO siteDetailsDTO = new SiteDetailsResponseDTO(siteId, mapAddressToDto(address), mapPersonToDto(personOfContact));
            siteDetailsList.add(siteDetailsDTO);
        }

        return siteDetailsList;
    }



    private void saveOrUpdate(VersionControlDataModel updateProductVersion) {
        String deploymentId = updateProductVersion.getDeploymentId();
        String productName = updateProductVersion.getProduct_name();

        String productVersion = updateProductVersion.getProduct_set_version();

        Optional<CurrentProductVersion> existingEntityOptional = currentProductVersionRepository.findByDeploymentIdAndProductNameAndProductVersion(deploymentId, productName, productVersion);


        // Search for existing entity
        if (!existingEntityOptional.isPresent()) {
            updateProductVersionRepository.findByDeploymentIdAndProductNameForUpdateProductVersion(deploymentId, productName)
                    .ifPresentOrElse(
                            existingEntity -> {
                                existingEntity.setProductVersion(updateProductVersion.getProduct_set_version());
                                existingEntity.setProduct_scheduled_update(updateProductVersion.getProduct_scheduled_update());
                                existingEntity.setProduct_scheduled_update_dateTime(updateProductVersion.getProduct_scheduled_update_dateTime());
                                existingEntity.setTask(Task.InQueue);
                                siteDetailsRepository.updateUpdateAvailableByDeploymentId(true, deploymentId);
                                updateProductVersionRepository.save(existingEntity);
                            }, () -> {
                                UpdateProductVersion model = new UpdateProductVersion();
                                model.setDeploymentId(updateProductVersion.getDeploymentId());
                                model.setProductVersion(updateProductVersion.getProduct_set_version());
                                model.setProductName(updateProductVersion.getProduct_name());
                                model.setProduct_scheduled_update(updateProductVersion.getProduct_scheduled_update());
                                model.setProduct_scheduled_update_dateTime(updateProductVersion.getProduct_scheduled_update_dateTime());
                                model.setTask(Task.InQueue);
                                siteDetailsRepository.updateUpdateAvailableByDeploymentId(true, deploymentId);
                                updateProductVersionRepository.save(model);
                            }
                    );
        }
    }
}


