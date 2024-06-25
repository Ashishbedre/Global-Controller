package com.example.demo.service.serviceimpl;

import com.example.demo.Entity.*;
import com.example.demo.dto.*;
import com.example.demo.dto.BackendPackage.DockerVersionInformationDto;
import com.example.demo.dto.BackendPackage.ProductListResponcedto;
import com.example.demo.dto.BackendPackage.VersionControlMicroDto;
import com.example.demo.dto.BackendPackage.VersionInformation;
//import com.example.demo.model.AgentModel;
import com.example.demo.enums.Task;
import com.example.demo.repository.CurrentProductVersionRepository;
import com.example.demo.repository.SiteDetailsRepository;
import com.example.demo.repository.UpdateProductVersionRepository;
import com.example.demo.service.AddSiteService;
import com.example.demo.service.Compatible;
import com.example.demo.service.TokenService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddSiteServiceImpl implements AddSiteService {

    @Autowired
    SiteDetailsRepository siteDetailsRepository;

    @Autowired
    CurrentProductVersionRepository currentProductVersionRepository;

    @Autowired
    UpdateProductVersionRepository updateProductVersionRepository;

    @Autowired
    Compatible compatible;

    @Autowired
    TokenService tokenService;


    @Value("${getUpgradeVersion.api.url}")
    private String getUpgradeVersion;

    @Value("${Notification.api.url}")
    private String createNotification;




    @Override
    public List<TenantDto> getListOfTenant() {
        List<TenantDto> tenantDtos = siteDetailsRepository.findAllDistinctTenantIds()
                .stream()
                .map(string -> {
                    TenantDto dto = new TenantDto();
                    dto.setTenantId(string);
                    return dto;
                })
                .collect(Collectors.toList());

        return tenantDtos;
    }

    @Override
    public List<VersionAddSiteDto> getAllDataProduct(String tenantId) {
        List<String> deploymentIds = siteDetailsRepository.findDistinctDeploymentIdByTenantIdAndProvisionIsNullTrueOrProvisionIsFalse(tenantId);

        return deploymentIds.stream()
                .map(deploymentId -> {
                    VersionAddSiteDto versionAddSiteDto = new VersionAddSiteDto();
                    versionAddSiteDto.setDeploymentId(deploymentId);

                    List<ProductDto> products = currentProductVersionRepository.findByDeploymentId(deploymentId).stream()
                            .map(currentProductVersion -> {
                                ProductDto productDto = new ProductDto();
                                productDto.setProductName(currentProductVersion.getProductName());
                                //Ashish change for tag is null
                                if(currentProductVersion.getProductVersion()==null|| currentProductVersion.getProductVersion().equals("null")){
                                    productDto.setProductVersion(null);
                                }else{
                                    productDto.setProductVersion(currentProductVersion.getProductVersion());
                                }
                                return productDto;
                            })
                            .collect(Collectors.toList());

                    versionAddSiteDto.setVersion(products);
                    return versionAddSiteDto;
                })
                .collect(Collectors.toList());
    }


    @Override
    public VersionUpdateControlDto getListOfUpdatedVersion(String deploymentId) {
        VersionUpdateControlDto versionUpdateControlDto = new VersionUpdateControlDto();
        versionUpdateControlDto.setDeploymentId(deploymentId);

        List<VersionUpgradeDto> versionUpgradeDtoList = new ArrayList<>();

        List<VersionControlMicroDto> versionControlDtoList = currentProductVersionRepository.findByDeploymentId(deploymentId)
                .stream()
                .map(versionModel -> {
                    VersionControlMicroDto versionControlDto = new VersionControlMicroDto();
                    versionControlDto.setRepo(versionModel.getProductName());
                    versionControlDto.setTag(versionModel.getProductVersion());
                    return versionControlDto;
                })
                .collect(Collectors.toList());

        WebClient webClient = WebClient.create();

        String accessToken = tokenService.getAccessToken();

        List<DockerVersionInformationDto> dockerVersionInformationDtoList = webClient.post()
                .uri(getUpgradeVersion)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .bodyValue(versionControlDtoList)
                .retrieve()
                .bodyToFlux(DockerVersionInformationDto.class)
                .collectList()
                .block();

        for (DockerVersionInformationDto dockerVersionInformationDto : dockerVersionInformationDtoList) {
            VersionUpgradeDto versionUpgradeDto = new VersionUpgradeDto();
            versionUpgradeDto.setProduct_name(dockerVersionInformationDto.getProduct());
            //Ashish change for version is null
            if(currentProductVersionRepository.findVersionNameByDeploymentIdAndProductName(deploymentId, dockerVersionInformationDto.getProduct())==null||
                    currentProductVersionRepository.findVersionNameByDeploymentIdAndProductName(deploymentId, dockerVersionInformationDto.getProduct()).equals("null")){
                versionUpgradeDto.setProduct_current_version(null);
            }else{
                versionUpgradeDto.setProduct_current_version(currentProductVersionRepository.findVersionNameByDeploymentIdAndProductName(deploymentId, dockerVersionInformationDto.getProduct()));

            }
//            versionUpgradeDto.setProduct_current_version(currentProductVersionRepository.findVersionNameByDeploymentIdAndProductName(deploymentId, dockerVersionInformationDto.getProduct()));

            List<VersionInformation> versionInformationList = dockerVersionInformationDto.getVersions().stream()
                    .map(versionInformation -> {
                        VersionInformation newVersionInformation = new VersionInformation();
                        newVersionInformation.setVersion(versionInformation.getVersion());
                        return newVersionInformation;
                    })
                    .collect(Collectors.toList());

            versionUpgradeDto.setProduct_upgrade_available_version(versionInformationList);
            versionUpgradeDtoList.add(versionUpgradeDto);
        }

        versionUpdateControlDto.setVersion_control(versionUpgradeDtoList);
        return versionUpdateControlDto;
    }

    @Override
    public List<ProductListResponcedto> saveAddNewSiteData(ProvisionSiteSSDto provisionDto, String deploymentId, String tenantId) {
        JsonNode check = compatible.checkCompatible(provisionDto);
        List<ProductListResponcedto> dataProcess = compatible.processCompatibilityData(check);
        if(dataProcess.get(0).isCompatible()==false ){
                return dataProcess;
        }

        SiteDetails siteDetails = siteDetailsRepository.findByDeploymentIdAndTenantId(deploymentId, tenantId);
        if (siteDetails != null) {
            // Map fields from ProvisionSiteSSDto to SiteDetails
            siteDetails.setDeploymentId(deploymentId);
            siteDetails.setTenantId(tenantId);
            siteDetails.setSiteId(provisionDto.getSiteName());
            siteDetails.setProvision(true);
            siteDetails.setUpdateAvailable(false);
            siteDetails.setActive(false);
            //ashish change for kafka
            siteDetails.setKafka(siteDetailsRepository.findMaxKafka()+1);

            // Convert AddressDto list to Address list
                        Address address = new Address();
                        address.setStreetName(provisionDto.getAddress().getStreetName());
                        address.setCity(provisionDto.getAddress().getCity());
                        address.setState(provisionDto.getAddress().getState());
                        address.setCity(provisionDto.getAddress().getCity());
                        address.setPinCode(provisionDto.getAddress().getPinCode());
                        //Ashish add for latitude and longitude
                        address.setLatitude(provisionDto.getAddress().getLatitude());
                        address.setLongitude(provisionDto.getAddress().getLongitude());
                        //add country
                        address.setCountry(provisionDto.getAddress().getCountry());
                        address.setSite(siteDetails);
            siteDetails.setAddresses(address);

            // Convert PersonDto list to Person list
                        Person person = new Person();
                        person.setFullName(provisionDto.getPersonOfContact().getFullName());
                        person.setEmail(provisionDto.getPersonOfContact().getEmail());
                        person.setContact(provisionDto.getPersonOfContact().getContact());
                        person.setSite(siteDetails);
            siteDetails.setPersonsOfContact(person);

            // Convert VersionSetProductDto list to VersionSetProduct list
            List<VersionSetProductDto> versionSetProducts = provisionDto.getVersionControl().stream()
                    .map(versionSetProductDto -> {
                        VersionSetProductDto versionSetProduct = new VersionSetProductDto();
                        versionSetProduct.setProductName(versionSetProductDto.getProductName());
                        versionSetProduct.setProductSetVersion(versionSetProductDto.getProductSetVersion());
                        return versionSetProduct;
                    })
                    .collect(Collectors.toList());

            for (VersionSetProductDto versionSetProductDto : versionSetProducts) {
                String productName = versionSetProductDto.getProductName();
                String productVersion = versionSetProductDto.getProductSetVersion();
                Optional<CurrentProductVersion> currentVersionOptional = currentProductVersionRepository
                        .findByDeploymentIdAndProductNameAndProductVersion(deploymentId, productName, productVersion);
                if (!currentVersionOptional.isPresent() && !productVersion.equals("Not Deployed") && !productVersion.equals("null")
                        && productVersion!=null) {
                    Optional<UpdateProductVersion> checkUpdateProductVersion = updateProductVersionRepository
                            .findByDeploymentIdAndProductNameAndProductVersion(deploymentId, productName, productVersion);
                            if(!checkUpdateProductVersion.isPresent()) {
                                UpdateProductVersion updateProductVersion = new UpdateProductVersion();
                                updateProductVersion.setDeploymentId(deploymentId);
                                updateProductVersion.setProductName(productName);
                                updateProductVersion.setProductVersion(productVersion);
                                //update Agent
                                updateProductVersion.setProduct_scheduled_update(false);
                                updateProductVersion.setTask(Task.InQueue);
                                updateProductVersionRepository.save(updateProductVersion);
                                siteDetails.setUpdateAvailable(true);
                            }
                }
            }
            siteDetailsRepository.save(siteDetails);
            try{
                createPost(siteDetails.getTenantId(),siteDetails.getSiteId());
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        return dataProcess;
    }




    public void createPost(String tenant,String siteName) {
        // Define the request URL
        String url = createNotification;

        // Create a WebClient instance
        WebClient webClient = WebClient.create();


        // Create a Notification object representing the request body
        List<Notification> notifications = new ArrayList<>();
        Notification notification = new Notification();
        notification.setHeader("Site Management");
        notification.setBody(siteName+" has been provisioned for "+tenant+" successfully");
        notification.setReadStatus(false);
        notification.setCategory("Site Management");
        notification.setSubCategory("Site List");
        notifications.add(notification);
        String accessToken = tokenService.getAccessToken();
        // Make the POST request
        String response = webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .bodyValue(notifications)
                .retrieve()
                .bodyToMono(String.class)
                .block();



    }


}




