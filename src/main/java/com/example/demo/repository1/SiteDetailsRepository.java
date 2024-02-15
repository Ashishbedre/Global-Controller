package com.example.demo.repository1;

import com.example.demo.Entity.SiteDetails;
import com.example.demo.dto.AddressDto;
import com.example.demo.dto.PersonDto;
import com.example.demo.dto.ProvisionDtoUpdate;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import java.util.List;

public interface SiteDetailsRepository extends JpaRepository<SiteDetails, Long> {

    SiteDetails findByDeploymentId(String deploymentId);

    Optional<SiteDetails> findByDeploymentIdAndSiteId(String deploymentId, String siteId);

    List<SiteDetails> findByTenantId(String tenantId);
    @Query("SELECT DISTINCT s.tenantId FROM SiteDetails s")
    List<String> findAllDistinctTenantIds();

    @Query("SELECT DISTINCT s.deploymentId FROM SiteDetails s WHERE s.tenantId = ?1")
    List<String> findDistinctDeploymentIdsByTenantId(String tenantId);

    SiteDetails findByDeploymentIdAndTenantId(String deploymentId, String tenantId);


    @Transactional
    @Modifying
    @Query("DELETE FROM SiteDetails s WHERE s.deploymentId = :deploymentId")
    void deleteByDeploymentId(String deploymentId);
}
