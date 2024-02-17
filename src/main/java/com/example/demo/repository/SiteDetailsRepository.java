package com.example.demo.repository;

import com.example.demo.Entity.SiteDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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

    @Query("SELECT DISTINCT s.deploymentId FROM SiteDetails s WHERE s.tenantId = ?1 AND (s.provision IS NULL OR s.provision = false)")
    List<String> findDistinctDeploymentIdByTenantIdAndProvisionIsNullTrueOrProvisionIsFalse(String tenantId);

    @Query("SELECT DISTINCT s FROM SiteDetails s WHERE s.tenantId = ?1 AND s.provision = true")
    List<SiteDetails> findDistinctDeploymentIdByTenantIdAndProvisionIsTrue(String tenantId);

    SiteDetails findByDeploymentIdAndTenantId(String deploymentId, String tenantId);


    @Transactional
    @Modifying
    @Query("DELETE FROM SiteDetails s WHERE s.deploymentId = :deploymentId")
    void deleteByDeploymentId(String deploymentId);
}
