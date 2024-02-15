//package com.example.demo.repository;
//
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//
//import com.example.demo.model.VersionControlDataModel;
//
//import jakarta.transaction.Transactional;
//
//public interface VersionSiteRepository extends JpaRepository<VersionControlDataModel, Long>{
//
//	@Query(value="SELECT COUNT(*) FROM version_control_data m WHERE m.deployment_id=?1", nativeQuery = true)
//	public Integer checkIfDeploymentId(String deploymentId);
//
//	@Modifying(clearAutomatically = true)
//	@Transactional
//	@Query(value="UPDATE version_control_data m SET m.product_set_version=?1,m.product_scheduled_update=?2,m.product_scheduled_update_dateTime=?3 WHERE m.deployment_id=?4", nativeQuery = true)
//	public void updateVersionInfo(String productCurrentVersion,Boolean productScheduledUpdate,LocalDateTime productScheduledUpdateDateTime,String deploymentId);
//
//	@Query(value="SELECT * from version_control_data m WHERE m.deployment_id=?1",nativeQuery = true)
//	public List<VersionControlDataModel> getAllData(String deploymentId);
//}
