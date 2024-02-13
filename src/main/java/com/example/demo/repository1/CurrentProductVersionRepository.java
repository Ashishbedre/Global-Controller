package com.example.demo.repository1;

import com.example.demo.Entity.CurrentProductVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrentProductVersionRepository extends JpaRepository<CurrentProductVersion, Long> {
    List<CurrentProductVersion> findByDeploymentId(String deploymentId);
}
