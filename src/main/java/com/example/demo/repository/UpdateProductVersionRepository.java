package com.example.demo.repository;

import com.example.demo.Entity.UpdateProductVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UpdateProductVersionRepository extends JpaRepository<UpdateProductVersion, Long> {
    Optional<UpdateProductVersion> findByDeploymentIdAndProductNameAndProductVersion(String deploymentId, String productName, String productVersion);
}
