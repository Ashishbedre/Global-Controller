package com.example.demo.service.serviceimpl;

import com.example.demo.Entity.*;
import com.example.demo.dto.*;
import com.example.demo.dto.BackendPackage.DockerVersionInformationDto;
import com.example.demo.dto.BackendPackage.VersionControlMicroDto;
import com.example.demo.dto.BackendPackage.VersionInformation;
//import com.example.demo.model.AgentModel;
import com.example.demo.enums.Task;
import com.example.demo.repository.CurrentProductVersionRepository;
import com.example.demo.repository.SiteDetailsRepository;
import com.example.demo.repository.UpdateProductVersionRepository;
import com.example.demo.service.AddSiteService;
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

    @Value("${getUpgradeVersion.api.url}")
    private String getUpgradeVersion;

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
                                productDto.setProductVersion(currentProductVersion.getProductVersion());
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

        List<DockerVersionInformationDto> dockerVersionInformationDtoList = webClient.post()
                .uri(getUpgradeVersion)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(versionControlDtoList)
                .retrieve()
                .bodyToFlux(DockerVersionInformationDto.class)
                .collectList()
                .block();

        for (DockerVersionInformationDto dockerVersionInformationDto : dockerVersionInformationDtoList) {
            VersionUpgradeDto versionUpgradeDto = new VersionUpgradeDto();
            versionUpgradeDto.setProduct_name(dockerVersionInformationDto.getProduct());
            versionUpgradeDto.setProduct_current_version(currentProductVersionRepository.findVersionNameByDeploymentIdAndProductName(deploymentId, dockerVersionInformationDto.getProduct()));

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
    public void saveAddNewSiteData(ProvisionSiteSSDto provisionDto, String deploymentId, String tenantId) {
        SiteDetails siteDetails = siteDetailsRepository.findByDeploymentIdAndTenantId(deploymentId, tenantId);
        if (siteDetails != null) {
            // Map fields from ProvisionSiteSSDto to SiteDetails
            siteDetails.setDeploymentId(deploymentId);
            siteDetails.setTenantId(tenantId);
            siteDetails.setSiteId(provisionDto.getSiteName());
            siteDetails.setProvision(true);
            siteDetails.setUpdateAvailable(false);
            siteDetails.setActive(false);

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
                if (!currentVersionOptional.isPresent()) {
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

        }
    }
}




