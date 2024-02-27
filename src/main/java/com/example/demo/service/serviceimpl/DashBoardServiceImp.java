package com.example.demo.service.serviceimpl;

import com.example.demo.dto.DashBoardCountDto;
import com.example.demo.repository.SiteDetailsRepository;
import com.example.demo.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashBoardServiceImp implements DashBoardService {

    @Autowired
    SiteDetailsRepository siteDetailsRepository;
    @Override
    public DashBoardCountDto countTheElementOfSiteLists() {
        DashBoardCountDto dashBoardCountDto = new DashBoardCountDto();
        try {
            dashBoardCountDto.setTenants(siteDetailsRepository.countDistinctTenantIds());
            dashBoardCountDto.setActiveSite(siteDetailsRepository.countActiveSites());
            dashBoardCountDto.setSiteProvisionalFalse(siteDetailsRepository.countUnprovisionedSites());
            dashBoardCountDto.setSiteProvisionalTrue(siteDetailsRepository.countProvisionedSites());
        }catch (Exception e){
            e.printStackTrace();
        }

        return dashBoardCountDto;
    }
}
