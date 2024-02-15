package com.example.demo.service.serviceimpl;

import com.example.demo.Entity.*;
import com.example.demo.dto.*;
import com.example.demo.dto.BackendPackage.DockerVersionInformationDto;
import com.example.demo.dto.BackendPackage.VersionInformation;
//import com.example.demo.model.AgentModel;
import com.example.demo.model.VersionModel;
import com.example.demo.repository1.CurrentProductVersionRepository;
import com.example.demo.repository1.SiteDetailsRepository;
import com.example.demo.repository1.UpdateProductVersionRepository;
import com.example.demo.service.AddSiteService;
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
public class AddSiteServiceImpl implements AddSiteService {

    @Autowired
    SiteDetailsRepository siteDetailsRepository;

    @Autowired
    CurrentProductVersionRepository currentProductVersionRepository;

    @Autowired
    UpdateProductVersionRepository updateProductVersionRepository;

    @Override
    public List<TenantDto> getListOfTenant() {
        List<TenantDto> temp=new ArrayList<>();
        List<String> list=siteDetailsRepository.findAllDistinctTenantIds();

        for (String string : list) {

            TenantDto dto=new TenantDto();

            dto.setTenantId(string);

            temp.add(dto);
        }
        return temp;
    }

    @Override
    public List<VersionAddSiteDto> getAllDataProduct(String tenantId) {
        List<VersionAddSiteDto> data=new ArrayList<>();

        List<String> list=siteDetailsRepository.findDistinctDeploymentIdsByTenantId(tenantId);

        for (String versionModel : list) {

            VersionAddSiteDto model=new VersionAddSiteDto();

            model.setDeploymentId(versionModel);

            List<ProductDto> zxc=new ArrayList<>();
            List<CurrentProductVersion> qwerty=currentProductVersionRepository.findByDeploymentId(versionModel);

            for (CurrentProductVersion versionModel2 : qwerty) {

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


    @Override
    public VersionUpdateControlDto getListOfUpdatedVersion(String deploymentId) {

        VersionUpdateControlDto mmm=new VersionUpdateControlDto();

        List<VersionUpgradeDto> kkk=new ArrayList<>();

        List<com.example.demo.dto.BackendPackage.VersionControlDto> temp=new ArrayList<>();

        List<CurrentProductVersion> model1=currentProductVersionRepository.findByDeploymentId(deploymentId);

        for (CurrentProductVersion versionModel : model1) {

            com.example.demo.dto.BackendPackage.VersionControlDto dd=new com.example.demo.dto.BackendPackage.VersionControlDto();

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
    public void saveAddNewSiteData(ProvisionSiteSSDto provisionDto, String deploymentId, String tenantId) {
        SiteDetails siteDetails = siteDetailsRepository.findByDeploymentIdAndTenantId(deploymentId, tenantId);
        if (siteDetails != null) {
            // Map fields from ProvisionSiteSSDto to SiteDetails
            siteDetails.setDeploymentId(deploymentId);
            siteDetails.setTenantId(tenantId);
            siteDetails.setSiteId(provisionDto.getSiteName());
            siteDetails.setProvision(true);
            siteDetails.setAvailable(false);
            siteDetails.setActive(false);

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

            // Convert VersionSetProductDto list to VersionSetProduct list
            List<VersionSetProductDto> versionSetProducts = provisionDto.getVersionControl().stream()
                    .map(versionSetProductDto -> {
                        VersionSetProductDto versionSetProduct = new VersionSetProductDto();
                        versionSetProduct.setProductName(versionSetProductDto.getProductName());
                        versionSetProduct.setProductSetVersion(versionSetProductDto.getProductSetVersion());
                        return versionSetProduct;
                    })
                    .collect(Collectors.toList());
            // Save the SiteDetails entity

            // Check if the product version exists in CurrentProductVersion
            Optional<CurrentProductVersion> currentVersionOptional = currentProductVersionRepository.findByDeploymentIdAndProductNameAndProductVersion(
                    deploymentId,
                    provisionDto.getVersionControl().get(0).getProductName(),
                    provisionDto.getVersionControl().get(0).getProductSetVersion()
            );

            // If version exists, save it to UpdateProductVersion
            currentVersionOptional.ifPresent(currentProductVersion -> {
                UpdateProductVersion updateProductVersion = new UpdateProductVersion();
                siteDetails.setAvailable(false);
                updateProductVersion.setDeploymentId(currentProductVersion.getDeploymentId());
                updateProductVersion.setProductName(currentProductVersion.getProductName());
                updateProductVersion.setProductVersion(currentProductVersion.getProductVersion());
                updateProductVersionRepository.save(updateProductVersion);
            });
            siteDetailsRepository.save(siteDetails);

        }
    }
}


////Ashish
//
//    // Convert VersionSetProductDto list to VersionSetProduct list
//    List<VersionSetProductDto> versionSetProducts = provisionDto.getVersionControl().stream()
//            .map(versionSetProductDto -> {
//                VersionSetProductDto versionSetProduct = new VersionSetProductDto();
//                versionSetProduct.setProductName(versionSetProductDto.getProductName());
//                versionSetProduct.setProductSetVersion(versionSetProductDto.getProductSetVersion());
//                return versionSetProduct;
//            })
//            .collect(Collectors.toList());
//
//    // Check if the product version exists in CurrentProductVersion
//    Optional<CurrentProductVersion> currentVersionOptional = currentProductVersionRepository.findByDeploymentIdAndProductName(
//            deploymentId,
//            provisionDto.getVersionControl().get(0).getProductName()
//    );
//
//// If version exists, update it
//currentVersionOptional.ifPresent(currentProductVersion -> {
//        if (currentProductVersion.getProductVersion().equals(provisionDto.getVersionControl().get(0).getProductSetVersion())) {
//        // If the product version matches, update the SiteDetails entity
//        siteDetails.setAvailable(false);
//        siteDetailsRepository.save(siteDetails);
//        } else {
//        // If the product version does not match, save it to UpdateProductVersion
//        UpdateProductVersion updateProductVersion = new UpdateProductVersion();
//        siteDetails.setAvailable(false);
//        updateProductVersion.setDeploymentId(currentProductVersion.getDeploymentId());
//        updateProductVersion.setProductName(currentProductVersion.getProductName());
//        updateProductVersion.setProductVersion(currentProductVersion.getProductVersion());
//        updateProductVersionRepository.save(updateProductVersion);
//        siteDetailsRepository.save(siteDetails);
//        }
//        });




