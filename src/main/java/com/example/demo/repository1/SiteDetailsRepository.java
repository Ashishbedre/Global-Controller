package com.example.demo.repository1;

import com.example.demo.Entity.SiteDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SiteDetailsRepository extends JpaRepository<SiteDetails, Long> {
    @Query("SELECT DISTINCT s.tenantId FROM SiteDetails s")
    List<String> findAllDistinctTenantIds();

    List<String> findDeploymentIdByTenantId(String tenantId);
}
