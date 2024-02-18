package com.example.demo.repository;

import com.example.demo.Entity.CurrentProductVersion;
import com.example.demo.Entity.UpdateProductVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UpdateProductVersionRepository extends JpaRepository<UpdateProductVersion, Long> {
    Optional<UpdateProductVersion> findByDeploymentIdAndProductNameAndProductVersion(String deploymentId, String productName, String productVersion);
    Optional<List<UpdateProductVersion>> findByDeploymentId(String deploymentId);

    @Query("SELECT s.productVersion FROM UpdateProductVersion s WHERE s.deploymentId = ?1 AND s.productName = ?2")
    String findByDeploymentIdAndProductName(String deploymentId, String productName);

    @Query("SELECT upv FROM UpdateProductVersion upv WHERE upv.deploymentId = :deploymentId AND upv.productName = :productName")
    Optional<UpdateProductVersion> findByDeploymentIdAndProductNameForUpdateProductVersion(@Param("deploymentId") String deploymentId, @Param("productName") String productName);


}
