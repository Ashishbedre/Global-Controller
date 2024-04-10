package com.example.demo.repository;

import com.example.demo.Entity.SiteDetails;
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

    @Query("SELECT DISTINCT s.deploymentId FROM SiteDetails s WHERE s.tenantId = ?1 AND (s.provision IS NULL OR s.provision = false)")
    List<String> findDistinctDeploymentIdByTenantIdAndProvisionIsNullTrueOrProvisionIsFalse(String tenantId);

    @Query("SELECT DISTINCT s FROM SiteDetails s WHERE s.tenantId = ?1 AND s.provision = true")
    List<SiteDetails> findDistinctDeploymentIdByTenantIdAndProvisionIsTrue(String tenantId);

    SiteDetails findByDeploymentIdAndTenantId(String deploymentId, String tenantId);
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM SiteDetails s WHERE s.provision = true AND s.updateAvailable = true AND s.deploymentId = :deploymentId")
    boolean existsByProvisionAndUpdateAvailableAndDeploymentId(String deploymentId);



    @Transactional
    @Modifying
    @Query("DELETE FROM Person p WHERE p.site.deploymentId = :deploymentId")
    void deletePersonsByDeploymentId(@Param("deploymentId") String deploymentId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Address a WHERE a.site.deploymentId = :deploymentId")
    void deleteAddressesByDeploymentId(@Param("deploymentId") String deploymentId);

    @Transactional
    @Modifying
    @Query("DELETE FROM SiteDetails s WHERE s.deploymentId = :deploymentId")
    void deleteSiteDetailsByDeploymentId(@Param("deploymentId") String deploymentId);

    //Dashboard
    @Query("SELECT COUNT(DISTINCT s.tenantId) FROM SiteDetails s WHERE s.tenantId IS NOT NULL")
    Long countDistinctTenantIds();

    @Query("SELECT COUNT(s) FROM SiteDetails s WHERE s.active = true")
    Long countActiveSites();

    @Query("SELECT COUNT(s) FROM SiteDetails s WHERE s.provision = true")
    Long countProvisionedSites();

    @Query("SELECT COUNT(s) FROM SiteDetails s WHERE s.provision = false OR s.provision IS NULL")
    Long countUnprovisionedSites();

    @Transactional
    @Modifying
    @Query("UPDATE SiteDetails s SET s.updateAvailable = :updateAvailable WHERE s.deploymentId = :deploymentId")
    void updateUpdateAvailableByDeploymentId(@Param("updateAvailable") boolean updateAvailable, @Param("deploymentId") String deploymentId);


    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM SiteDetails s WHERE s.deploymentId = :deploymentId AND s.provision = true")
    boolean existsByDeploymentIdAndProvision(@Param("deploymentId") String deploymentId);
}

