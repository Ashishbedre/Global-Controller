package com.example.demo.repository;

import com.example.demo.Entity.CurrentProductVersion;
import com.example.demo.Entity.UpdateProductVersion;
import com.example.demo.enums.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UpdateProductVersionRepository extends JpaRepository<UpdateProductVersion, Long> {
    Optional<UpdateProductVersion> findByDeploymentIdAndProductNameAndProductVersion(String deploymentId, String productName, String productVersion);
    List<UpdateProductVersion> findByDeploymentId(String deploymentId);

    List<UpdateProductVersion> findByDeploymentIdAndTaskIsNot(String deploymentId, Task task);


//    @Query("SELECT s.productVersion FROM UpdateProductVersion s WHERE s.deploymentId = ?1 AND s.productName = ?2")
//    String findByDeploymentIdAndProductName(String deploymentId, String productName);

    @Query("SELECT upv FROM UpdateProductVersion upv WHERE upv.deploymentId = :deploymentId AND upv.productName = :productName")
    Optional<UpdateProductVersion> findByDeploymentIdAndProductNameForUpdateProductVersion(@Param("deploymentId") String deploymentId, @Param("productName") String productName);

    @Transactional
    @Modifying
    void deleteByDeploymentId(String deploymentId);

    List<UpdateProductVersion> findByTask(Task task);
    @Transactional
    @Modifying
    @Query("UPDATE CurrentProductVersion cpv SET cpv.productVersion = :productVersion WHERE cpv.productName = :productName AND cpv.deploymentId = :deploymentId")
    void updateProductVersion(String productName, String deploymentId, String productVersion);

    @Transactional
    @Modifying
    @Query("DELETE FROM UpdateProductVersion upv WHERE upv.task = :task AND upv.productName = :productName AND upv.deploymentId = :deploymentId")
    void deleteCompletedUpdates(Task task, String productName, String deploymentId);

    Optional<UpdateProductVersion> findByDeploymentIdAndProductName(String deploymentId, String productName);

    @Query("SELECT DISTINCT u.deploymentId FROM UpdateProductVersion u")
    List<String> findDistinctDeploymentId();


    @Query("SELECT u FROM UpdateProductVersion u WHERE u.deploymentId = :deploymentId " +
            "ORDER BY CASE u.task " +
            "WHEN 'InProgress' THEN 1 " +
            "WHEN 'InQueue' THEN 2 " +
            "WHEN 'Scheduled' THEN 3 " +
            "WHEN 'Complete' THEN 4 " +
            "ELSE 5 " +
            "END, u.product_scheduled_update_dateTime ASC")
    List<UpdateProductVersion> findSingleByDeploymentIdOrderByTaskPriority(String deploymentId);

}
