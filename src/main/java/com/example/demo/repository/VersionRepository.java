package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.VersionModel;

import jakarta.transaction.Transactional;

public interface VersionRepository extends JpaRepository<VersionModel, Long>{

	@Query(value="SELECT * from version_data m WHERE m.tenant_id=?1 AND m.deployment_id=?2",nativeQuery = true)
	public List<VersionModel> getAllSiteData(String tenantId,String deploymentId);
	
	@Query(value="SELECT * from version_data m WHERE m.deployment_id=?1",nativeQuery = true)
	public List<VersionModel> getListOfVersion(String deploymentId);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="Update version_data m SET m.product_set_version=?1 WHERE m.deployment_id=?2 AND m.product_name=?3",nativeQuery = true)
	public void updateNewSite(String productSetVersion,String deploymentId,String productName);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="DELETE m from version_data m WHERE m.deployment_id=?1",nativeQuery = true)
	public void deleteData(String deploymentId);
	
	@Query(value="SELECT m.product_version from version_data m WHERE m.deployment_id=?1 AND m.product_name=?2",nativeQuery = true)
	public String getVersion(String deploymentId,String productName);
	
	@Query(value="SELECT m.product_set_version from version_data m WHERE m.deployment_id=?1 AND m.product_name=?2",nativeQuery = true)
	public String getVersionProvision(String deploymentId,String productName);
}
