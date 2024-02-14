package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.PersonModel;

import jakarta.transaction.Transactional;

public interface PersonRepositoryOver extends JpaRepository<PersonModel, Long>{

	@Query(value="SELECT * from person_data m WHERE m.deployment_id=?1",nativeQuery = true)
	public List<PersonModel> getAllSiteData(String deploymentId);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="Update person_data m SET m.full_name=?1,m.contact=?2,m.email=?3 WHERE m.deployment_id=?4",nativeQuery = true)
	public void updateNewSite(String fullName,String contact,String email,String deploymentId);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="DELETE m from person_data m WHERE m.deployment_id=?1",nativeQuery = true)
	public void deleteData(String deploymentId);

	@Query(value="SELECT * from person_data m WHERE m.deployment_id=?1",nativeQuery = true)
	public List<PersonModel> getSiteData(String deploymentId);

	@Query(value="SELECT * from person_data m WHERE m.deployment_id=?1",nativeQuery = true)
	public PersonModel getPersonData(String deploymentId);
}
