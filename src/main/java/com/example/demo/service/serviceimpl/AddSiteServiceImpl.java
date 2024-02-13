package com.example.demo.service.serviceimpl;

import com.example.demo.Entity.CurrentProductVersion;
import com.example.demo.dto.*;
import com.example.demo.dto.BackendPackage.DockerVersionInformationDto;
import com.example.demo.dto.BackendPackage.VersionInformation;
import com.example.demo.model.AgentModel;
import com.example.demo.model.VersionModel;
import com.example.demo.repository1.CurrentProductVersionRepository;
import com.example.demo.repository1.SiteDetailsRepository;
import com.example.demo.service.AddSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

public class AddSiteServiceImpl implements AddSiteService {

    @Autowired
    SiteDetailsRepository siteDetailsRepository;

    @Autowired
    CurrentProductVersionRepository currentProductVersionRepository;
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

        List<String> list=siteDetailsRepository.findDeploymentIdByTenantId(tenantId);

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
    public void saveAddNewSiteData(ProvisionSiteSSDto provisionDto, String deploymentId, String tenantId) {

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
                .baseUrl("http://localhost:8081")
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
            model.setProduct_current_version(versionRepository.getVersion(deploymentId, dockerVersionInformationDto.getProduct()));

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
    public VersionUpdateControlDto getListOfUpdatedVersion(String deploymentId) {
        return null;
    }
}
