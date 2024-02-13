package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.AgentModel;

import jakarta.transaction.Transactional;

public interface AgentRepository extends JpaRepository<AgentModel, Long>{

	@Query(value="SELECT DISTINCT(m.tenant_id) from agent_data m",nativeQuery = true)
	public List<String> getListOfTenantId();
	
	@Query(value="SELECT * from agent_data m WHERE m.tenant_id=?1 AND m.provision=false",nativeQuery = true)
	public List<AgentModel> getAllSiteData(String tenantId);
	
	@Query(value="SELECT * from agent_data m WHERE m.tenant_id=?1 AND m.provision=true",nativeQuery = true)
	public List<AgentModel> getAllSiteDataProvision(String tenantId);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="Update agent_data m SET m.site_id=?1 WHERE m.deployment_id=?2",nativeQuery = true)
	public void updateNewSite(String siteName,String deploymentId);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="Update agent_data m SET m.provision=false WHERE m.deployment_id=?1",nativeQuery = true)
	public void deleteData(String deploymentId);
	
	@Query(value="SELECT m.deployment_id from agent_data m WHERE m.tenant_id=?1",nativeQuery = true)
	public List<String> getDeploymentId(String tenantId);
	
	@Query(value="SELECT m.site_id from agent_data m WHERE m.deployment_id=?1",nativeQuery = true)
	public String getSiteList(String deploymentId);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="Update agent_data m SET m.provision=true WHERE m.deployment_id=?1",nativeQuery = true)
	public void updateProvisionSite(String deploymentId);
	
}
