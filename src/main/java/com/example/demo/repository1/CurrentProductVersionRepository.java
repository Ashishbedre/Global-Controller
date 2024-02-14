package com.example.demo.repository1;

import com.example.demo.Entity.CurrentProductVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CurrentProductVersionRepository extends JpaRepository<CurrentProductVersion, Long> {
    List<CurrentProductVersion> findByDeploymentId(String deploymentId);

    @Query("SELECT s.productVersion FROM CurrentProductVersion s WHERE s.deploymentId = ?1 AND s.productName = ?2")
    String findVersionNameByDeploymentIdAndProductName(String deploymentId, String productName);

    Optional<CurrentProductVersion> findByDeploymentIdAndProductNameAndProductVersion(String deploymentId, String productName, String productVersion);

    @Query("SELECT COUNT(cpv) > 0 FROM CurrentProductVersion cpv WHERE cpv.deploymentId = :deploymentId")
    boolean existsByDeploymentId(@Param("deploymentId") String deploymentId);
}
